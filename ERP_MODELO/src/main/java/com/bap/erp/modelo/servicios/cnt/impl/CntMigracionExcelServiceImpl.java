package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGeneralesNivel;
import com.bap.erp.modelo.entidades.cnf.ParTipoRetencion;
import com.bap.erp.modelo.entidades.cnf.ParTiposDatoNivel;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.entidades.cnt.CntDefinicionCuentasMigracion;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import com.bap.erp.modelo.enums.EnumAjuste;
import com.bap.erp.modelo.enums.EnumFacturaCompraCombustible;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumHabilitaCentroCosto;
import com.bap.erp.modelo.enums.EnumParametroAutomatico;
import com.bap.erp.modelo.enums.EnumTieneAuxiliar;
import com.bap.erp.modelo.enums.EnumTieneCentroDeCosto;
import com.bap.erp.modelo.enums.EnumTipoCalculoAutomatico;
import com.bap.erp.modelo.enums.EnumTipoMovimiento;
import com.bap.erp.modelo.enums.EnumTipoRetencion;
import com.bap.erp.modelo.enums.EnumTiposDatoNivel;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntMigracionExcelServiceImpl extends GenericDaoImpl<CntEntidad> implements CntMigracionExcelService {

    private final static Log log = LogFactory.getLog(CntMascarasServiceImpl.class);
    @Autowired
    private CntMascarasService cntMascarasService;
    @Autowired
    private CntNivelesService cntNivelesService;
    @Autowired
    private ParParametricasService parParametricasService;
    @Autowired
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    @Autowired
    private CntEntidadesService cntEntidadesService;

    //Genera Tamanio de Niveles a apartir del primer registro de la columna mascaraGenrada del documento Excel.
    public List<CntNivel> generaNivelConElRegistroDelExcel(String nivel) throws Exception {
        try {
            String[] arrayNivel = nivel.split("-");
            List<CntNivel> niveles = new ArrayList<CntNivel>();
            CntNivel cntNivel;
            int cont = 1;
            for (int i = arrayNivel.length; i > 0; i--) {
                cntNivel = new CntNivel();
                cntNivel.setNivel(cont);
                cntNivel.setTamanio(arrayNivel[i - 1].length());
                niveles.add(cntNivel);
                cont++;
            }
            return niveles;
        } catch (Exception e) {
            throw e;
        }

    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntMascara guardarCntMascaraExcel(CntMascara cntMascaras, List<CntNivel> cntNivelesList) throws Exception {
        try {
            cntMascarasService.persistCntMascara(cntMascaras);
            int nivel = cntNivelesList.size();
            for (CntNivel cntNivel : cntNivelesList) {
                cntNivel.setCntMascara(cntMascaras);
                ParTiposDatoNivel parTiposDatoNivel = (ParTiposDatoNivel) parParametricasService.find(ParTiposDatoNivel.class, EnumTiposDatoNivel.NUMERO.getCodigo());
                cntNivel.setTipoNivel(parTiposDatoNivel);
                cntNivel.setTipoMovimiento(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
                cntNivel.setFechaAlta(cntMascaras.getFechaAlta());
                cntNivel.setUsuarioAlta(cntMascaras.getUsuarioAlta());
                cntNivelesService.persist(cntNivel);
            }
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
        return cntMascaras;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaListaPlanCuentasConParametrosAutomaticos(CntMascara cntMascaras, List<CntEntidad> cntPlanCuentasList) throws Exception {
        try {
            for (CntEntidad cntEntidad : cntPlanCuentasList) {
                cntEntidad.setCntMascara(cntMascaras);
                cntEntidad.setUsuarioAlta(cntMascaras.getUsuarioAlta());
                cntEntidad.setFechaAlta(cntMascaras.getFechaAlta());
                cntEntidadesService.persistCntObjetos(cntEntidad);
                if (cntEntidad.getNivel() <= 2) {
                    cuentasNivelDosAndUnoBaseMigrada(cntMascaras, cntEntidad);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardarCntMascarCntNivelesCntPlancuentasCntParametroAutomatico(CntMascara cntMascaras, List<CntNivel> cntNivelesList, List<CntEntidad> listaCntEntidad, List<CntDefinicionCuentasMigracion> listaSuperioresPadres) throws Exception {
        try {
            guardarCntMascaraExcel(cntMascaras, cntNivelesList);
            guardaListaPlanCuentasConParametrosAutomaticos(cntMascaras, listaCntEntidad);
            asignaPadresAPlanDeCuentas();
            asignacionCuentasSuperiores(cntMascaras, listaSuperioresPadres);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void cuentasNivelDosAndUnoBaseMigrada(CntMascara cntMascaras, CntEntidad cntEntidad) throws Exception {
        ParAjustes parAjustes = (ParAjustes) parParametricasService.find(ParAjustes.class, EnumAjuste.SIN_AJUSTE.getCodigo());
        ParTipoRetencion parTiporetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo());
        CntParametroAutomatico cntParametroAutomatico = new CntParametroAutomatico();
        cntParametroAutomatico.setTipoCalculoAutomatico(EnumTipoCalculoAutomatico.NINGUNO.getCodigo());
        cntParametroAutomatico.setCntEntidad(cntEntidad);
        cntParametroAutomatico.setFacturaCompra(EnumFacturaCompraCombustible.NINGUNO.getCodigo());
        cntParametroAutomatico.setFacturaVenta(EnumParametroAutomatico.NINGUNO.getCodigo());
        cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.NINGUNO.getCodigo());
        cntParametroAutomatico.setCreditoFiscalNoDeducible(EnumParametroAutomatico.NINGUNO.getCodigo());
        cntParametroAutomatico.setParTipoRetencion(parTiporetencion);
        cntParametroAutomatico.setParTipoRetencionGrossingUp(parTiporetencion);
        cntParametroAutomatico.setTipoMovimiento(EnumTipoMovimiento.AMBOS.getCodigo());
        cntParametroAutomatico.setParAjustes(parAjustes);
        cntParametroAutomatico.setUsuarioAlta(cntMascaras.getUsuarioAlta());
        cntParametroAutomatico.setFechaAlta(cntMascaras.getFechaAlta());

        try {
            cntParametroAutomaticoService.persistCntParametroAutomatico(cntParametroAutomatico);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void asignacionCuentasSuperiores(CntMascara cntMascaras, List<CntDefinicionCuentasMigracion> listaSuperioresPadres) throws Exception {
        try {
            for (CntDefinicionCuentasMigracion cuentasMigracion : listaSuperioresPadres) {
                ParValor parValor = parParametricasService.findParValor(cuentasMigracion.getCodigoCuentaGeneral());
                CntEntidad cntEntidad = optieneCntEntidadPorMascaraGenerada(cuentasMigracion.getCntEntidad().getMascaraGenerada());
                parValor.setValor(Long.toString(cntEntidad.getIdEntidad()));
                parValor.setFechaModificacion(cntMascaras.getFechaAlta());
                parValor.setUsuarioModificacion(cntMascaras.getUsuarioAlta());
                parParametricasService.mergeParValor(parValor);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public CntEntidad optieneCntEntidadPorMascaraGenerada(String mascaraGenerada) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select a from CntEntidad a "
                    + "where a.mascaraGenerada='" + mascaraGenerada + "' "
                    + "and a.fechaBaja is null");

            return lista.isEmpty() ? new CntEntidad() : lista.get(0);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void asignaPadresAPlanDeCuentas() throws Exception {
        try {
            List<CntEntidad> listaCuentasBD = cntEntidadesService.cntEntidadesPorGrupoNivelList("PCTA");
            String mascaraCereada = listaCuentasBD.get(0).getCntMascara().getMascara().replace("9", "0");
            String mascaraConsulta = "";
            int nivel = listaCuentasBD.get(0).getCntMascara().getTamanioNivel();
            int[] niveles;
            niveles = new int[nivel];
            List<CntNivel> listaNiveles = cntNivelesService.listaDeNivelesPlanCuentas("PCTA");
            int contador = 0;
            int limite = 0;
            CntEntidad cntEntidadPadre = new CntEntidad();
            for (CntNivel cntNivel : listaNiveles) {
                niveles[contador] = cntNivel.getTamanio();
                contador++;
            }
            for (CntEntidad cntEntidad : listaCuentasBD) {
                limite = 0;
                if (cntEntidad.getNivel() != nivel) {
                    for (int i = 0; i < nivel - cntEntidad.getNivel(); i++) {
                        limite = limite + niveles[i];
                    }
                    limite = limite + nivel - cntEntidad.getNivel() - 1;
                    mascaraConsulta = cntEntidad.getMascaraGenerada().substring(0, limite) + "" + mascaraCereada.substring(limite, mascaraCereada.length());
                    cntEntidadPadre = cntEntidadesService.cntEntidadPorMascara(mascaraConsulta);
                    cntEntidad.setIdEntidadPadre(cntEntidadPadre.getIdEntidad());
                    cntEntidadesService.mergeCntObjetos(cntEntidad);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
