package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.commons.entities.mappings.EntidadArbolPojo;
import com.bap.erp.commons.entities.mappings.EntidadPojo;
import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGeneralesNivel;
import com.bap.erp.modelo.entidades.cnf.ParGruposNivel;
import com.bap.erp.modelo.entidades.cnf.ParPlanCuentas;
import com.bap.erp.modelo.entidades.cnf.ParTipoRetencion;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import com.bap.erp.modelo.entidades.cnt.CntPojoParametrizacionAjustesPlanCuentas;
import com.bap.erp.modelo.enums.EnumAjuste;
import com.bap.erp.modelo.enums.EnumCuentaGeneral;
import com.bap.erp.modelo.enums.EnumFacturaCompraCombustible;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumMovimientoFactura;
import com.bap.erp.modelo.enums.EnumParametroAutomatico;
import com.bap.erp.modelo.enums.EnumTieneAuxiliar;
import com.bap.erp.modelo.enums.EnumTieneCentroDeCosto;
import com.bap.erp.modelo.enums.EnumTieneProyecto;
import com.bap.erp.modelo.enums.EnumTipoCalculoAutomatico;
import com.bap.erp.modelo.enums.EnumTipoConfiguracionDeCentroDeCosto;
import com.bap.erp.modelo.enums.EnumTipoMovimiento;
import com.bap.erp.modelo.enums.EnumTipoRetencion;
import com.bap.erp.modelo.enums.EnumTransaccionRealizada;
import com.bap.erp.modelo.pojo.CntEntidadPojo;
import com.bap.erp.modelo.servicios.cnf.ParCuentasGeneralesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntEntidadesServiceImpl extends GenericDaoImpl<CntEntidad> implements CntEntidadesService {

    @Autowired
    private CntMascarasService cntMascarasService;
    @Autowired
    private ParParametricasService parParametricasService;
    @Autowired
    private ParCuentasGeneralesService parCuentasGeneralesService;
    @Autowired
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    @Autowired
    private CntNivelesService cntNivelesService;
    @Autowired
    private CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService;
    @Autowired
    private CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService;
    @Autowired
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    //@ManagedProperty(value = "#{parParametricasService}")
    private ParGruposNivel parGruposNivel;

//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public String eliminaCaracterDescripcion(CntEntidad cntEntidad) {
        String descripcionNueva = "";
        boolean valido = true;
        boolean noPunto = true;
        String descripcionActual = cntEntidad.getDescripcion();

        for (int i = 0; i < descripcionActual.length(); i++) {
            valido = true;
            if (descripcionActual.charAt(i) == '.' && noPunto) {
                valido = false;
            }
            if (valido) {
                descripcionNueva += descripcionActual.charAt(i);
                noPunto = false;
            }

        }
        return descripcionNueva;
    }

    //<editor-fold defaultstate="collapsed" desc="AUTOR: HENRRY GUZMAN">
    public String generaCodigoNiveleAndSubNivel(CntEntidad cntEntidad, String tipo, int numero) {
        int nivelObjetoCuenta1 = 0;
        int nivelMascaraAux;
        String mascaraOficial;
        mascaraOficial = "";
        nivelMascaraAux = generaNivelCodigoCuentasGenerales(cntEntidad.getMascaraGenerada());
        String[] arrayMascara = cntEntidad.getMascaraGenerada().split("-");
        if (tipo.equals("M")) {
            return mascaraOficial = "";
        }
        if (tipo.equals("N")) {
            nivelObjetoCuenta1 = cntEntidad.getNivel();
        } else {
            nivelObjetoCuenta1 = cntEntidad.getNivel() - 1;
        }
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjetoCuenta1 == nivelMascaraAux) {
                if (controlaGeneracionNumero(arrayMascara[i], numero)) {
                    arrayMascara[i] = generaCodigoConCeros(numero, arrayMascara[i].length());
                } else {
                    return "El numero es mayor al Tamaño no insista::";
                }

            }
            if (i == 0) {
                mascaraOficial = mascaraOficial + arrayMascara[i];
            } else {
                mascaraOficial = mascaraOficial + "-" + arrayMascara[i];
            }

            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return mascaraOficial;

    }

    public int generaNivelCodigoCuentasGenerales(String codigo) {
        String[] arrayMascara = codigo.split("-");
        return arrayMascara.length;
    }

    public boolean controlaGeneracionNumero(String subCodigo, int numero) {
        int valor = 0;
        for (int i = 1; i <= subCodigo.length(); i++) {
            valor = valor * 10 + 9;
        }
        return numero <= valor;
    }

    public String generaCodigoConCeros(int numero, int tamanio) {
        int cont = 0;
        String codigo;
        codigo = "";
        int numeroAux = numero;
        while (numeroAux > 0) {
            numeroAux = numeroAux / 10;
            cont++;
        }
        tamanio = tamanio - cont;
        for (int i = 1; i <= tamanio; i++) {
            codigo = codigo + "0";
        }
        codigo = codigo + Integer.toString(numero);
        return codigo;

    }
    //</editor-fold>

    public String verificaExistenciaCodigo(String cuenta) {
        List<CntEntidad> lista = hibernateTemplate.find(
                "select o from CntEntidad o "
                + "where o.mascaraGenerada='" + cuenta + "' "
                + "and o.cntMascara.grupoNivel.codigo='PCTA' "
                + "and o.fechaBaja is null");
        return lista.size() > 0 ? "Codigo Existente" : "Codigo Disponible";
    }

    public int obtieneNiveleCuentaSubAndPadre(CntEntidad cntEntidad, String tipo) {
        if (tipo.equals("N")) {
            return cntEntidad.getNivel();
        } else {
            return cntEntidad.getNivel() - 1;
        }

    }

    public boolean verificaExistenciaMascara(CntEntidad cntEntidad) {
        List<CntEntidad> lista = hibernateTemplate.find(
                "select o from CntEntidad o "
                + "where o.mascaraGenerada='" + cntEntidad.getMascaraGenerada() + "' "
                + "and o.cntMascara.grupoNivel.codigo='PCTA' "
                + "and o.fechaBaja is null");
        return lista.size() > 0 ? false : true;
    }

    public String generaEspaciosEnDescripcionNivelesSubAndPadre(CntEntidad cntEntidad) {
        String descripcionNueva = "";
        int limite = generaNivelCodigoCuentasGenerales(cntEntidad.getMascaraGenerada()) - cntEntidad.getNivel();
        for (int i = 1; i <= limite; i++) {
            descripcionNueva = descripcionNueva + "...";
        }
        return descripcionNueva + cntEntidad.getDescripcion();
    }

//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
//    public void persistCntObjetosConAsignacionPorDefinicion(CntEntidad cntEntidad, String tipo, List<CntDefinicion> cntDefinicionList,CntParametroAutomatico cntParametroAutomatico) throws Exception {
//        try {
//            CntMascara cntMascaras = (CntMascara) cntMascarasService.find(CntMascara.class, cntEntidad.getCntMascara().getIdMascara());
//            cntEntidad.setCntMascara(cntMascaras);
//            cntEntidad.setTipo(cntMascaras.getGrupoNivel().getCodigo());
//            super.persist(cntEntidad);
//            cntParametroAutomaticoService.persistCntParametroAutomatico(cntParametroAutomatico);
//        } catch (Exception e) {
//            throw e;
//        }
//    }
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad persistCntObjetosConAsignacionPorDefinicion(CntEntidad cntEntidad, String tipo, CntParametroAutomatico cntParametroAutomatico) throws Exception {
        try {
            CntMascara cntMascaras = (CntMascara) cntMascarasService.find(CntMascara.class, cntEntidad.getCntMascara().getIdMascara());
            cntEntidad.setCntMascara(cntMascaras);
            cntEntidad.setTipo(cntMascaras.getGrupoNivel().getCodigo());
            cntEntidad.setHabilitaCentroCosto("N");
            cntEntidad.setParametrosIndividuales("N");
            cntEntidad = super.persist(cntEntidad);
            //Pregunto el nivel para que no realize el persist de CntParametroAutomatico para niveles superior a 2
            if (cntEntidad.getNivel() < 3) {
                cntParametroAutomaticoService.persistCntParametroAutomatico(cntParametroAutomatico);
            }
            return cntEntidad;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad persistCntObjetosConAsignacionPorDefinicionConAsignacionAuxiliares(CntEntidad cntEntidad, String tipo, CntParametroAutomatico cntParametroAutomatico, List<CntAuxiliar> selectAuxiliarParaAsignacion, List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) throws Exception {
        try {
            CntMascara cntMascaras = (CntMascara) cntMascarasService.find(CntMascara.class, cntEntidad.getCntMascara().getIdMascara());
            cntEntidad.setCntMascara(cntMascaras);
            cntEntidad.setTipo(cntMascaras.getGrupoNivel().getCodigo());
            if (listaCntConfiguracionCentrocosto.isEmpty()) {
                cntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
            } else {
                cntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.SI.getCodigo());
            }
            cntEntidad = super.persist(cntEntidad);
            if (cntEntidad.getNivel() < 3) {
                cntParametroAutomaticoService.persistCntParametroAutomatico(cntParametroAutomatico);
            }
            if (cntEntidad.getNivel() == 1) {
                if (!listaCntConfiguracionCentrocosto.isEmpty() || listaCntConfiguracionCentrocosto != null) {
                    cntConfiguracionCentroCostoService.guardaCntConfiguracionCentroCostoDefinicion(listaCntConfiguracionCentrocosto, cntEntidad.getUsuarioAlta(), cntEntidad);
                }
                if (!selectAuxiliarParaAsignacion.isEmpty() || selectAuxiliarParaAsignacion != null) {
                    cntAuxiliaresPorCuentaService.persistCntAuxiliaresPorCuentaListaSeleccionada(cntEntidad, selectAuxiliarParaAsignacion);
                }
            }
            return cntEntidad;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad mergeCntObjetosConAsignacionPorDefinicionConAsignacionAuxiliares(CntEntidad cntEntidad, String tipo, CntParametroAutomatico cntParametroAutomatico, List<CntAuxiliar> selectAuxiliarParaModificacion, List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) throws Exception {
        try {
            if (cntEntidad.getNivel() == 1) {
                if (!listaCntConfiguracionCentrocosto.isEmpty() || listaCntConfiguracionCentrocosto != null) {
                    cntConfiguracionCentroCostoService.modificarCntConfiguracionCentroCostoDefinicion(listaCntConfiguracionCentrocosto, cntEntidad.getUsuarioAlta(), cntEntidad);
                }
                if (!selectAuxiliarParaModificacion.isEmpty() || selectAuxiliarParaModificacion != null) {
                    cntAuxiliaresPorCuentaService.mergeCntAuxiliaresPorCuentaListaSeleccionada(cntEntidad, selectAuxiliarParaModificacion);
                } else {
                    cntAuxiliaresPorCuentaService.bajaListaAuxiliaresPorEntidad(cntEntidad);

                }
            }
            return cntEntidad;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad mergegetIdObjeto(CntEntidad cntEntidad) throws Exception {
        try {
            hibernateTemplate.merge(cntEntidad);
        } catch (Exception e) {
            throw e;
        }
        return cntEntidad;
    }

    public boolean verificaExistenciaMascaraNivelAndSubNivel(String mascaraGenerada) {
        List<CntEntidad> lista = hibernateTemplate.find(
                "select o from CntEntidad o "
                + "where o.mascaraGenerada='" + mascaraGenerada + "' "
                + "and o.cntMascara.grupoNivel.codigo='PCTA' "
                + "and o.fechaBaja is null");
        return lista.size() <= 0;
    }

    public boolean activaTipoMovimientoCombo(CntEntidad cntEntidad) {
        int nivelObjetoCuenta;
        nivelObjetoCuenta = cntEntidad.getNivel();
        return nivelObjetoCuenta == 1;
    }

    public String generaCodigoNivelesSubAndPadre(CntEntidad getIdObjeto, String tipo) {
        int nivelObjetoCuenta = 0;
        int numero;
        String mascaraOficial;
        mascaraOficial = "";
        int nivelMascaraAux;
        nivelMascaraAux = generaNivelCodigoCuentasGenerales(getIdObjeto.getMascaraGenerada());
        String[] arrayMascara = getIdObjeto.getMascaraGenerada().split("-");
        if (tipo.equals("M")) {
            return mascaraOficial = "";
        }
        if (tipo.equals("N")) {
            nivelObjetoCuenta = getIdObjeto.getNivel();
        } else {
            nivelObjetoCuenta = getIdObjeto.getNivel() - 1;
        }
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjetoCuenta == nivelMascaraAux) {
                numero = Integer.parseInt(arrayMascara[i]);
                numero++;
                if (controlaGeneracionNumero(arrayMascara[i], numero)) {
                    arrayMascara[i] = generaCodigoConCeros(numero, arrayMascara[i].length());
                } else {
                    return "El numero es mayor al Tamaño no insista::";
                }

            }
            if (i == 0) {
                mascaraOficial = mascaraOficial + arrayMascara[i];
            } else {
                mascaraOficial = mascaraOficial + "-" + arrayMascara[i];
            }

            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return mascaraOficial;

    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad persistCntObjetos(CntEntidad cntEntidad) throws Exception {
        try {
            super.persist(cntEntidad);
        } catch (Exception e) {
            throw e;
        }
        return cntEntidad;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad mergeCntObjetos(CntEntidad cntEntidad) throws Exception {
        try {
            super.merge(cntEntidad);
        } catch (Exception e) {
            throw e;
        }
        return cntEntidad;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad removeCntObjetos(CntEntidad cntEntidad) throws Exception {
        try {
            super.remove(cntEntidad);
        } catch (Exception e) {
            throw e;
        }
        return cntEntidad;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad mergeCntEntidadAndCntParametroAutomatico(CntEntidad cntEntidad, CntParametroAutomatico cntParametroAutomatico) throws Exception {
        try {
            cntEntidad = mergeCntObjetos(cntEntidad);
            if (cntEntidad.getNivel() <= 2 && cntParametroAutomatico != null) {
                cntParametroAutomaticoService.mergeCntParametroAutomatico(cntParametroAutomatico);
            }
        } catch (Exception e) {
            throw e;
        }
        return cntEntidad;
    }

    public String generaNumeroSiguiente(CntEntidad cntEntidad, String tipo) {
        System.out.println("OBJETO:"+cntEntidad);
        int nivelObjetoCuenta;
        int nivelMascaraAux;
        int numero;
        nivelMascaraAux = cntEntidad.getCntMascara().getTamanioNivel();
        String[] arrayMascara = cntEntidad.getMascaraGenerada().split("-");
        if (tipo.equals("N")) {
            nivelObjetoCuenta = cntEntidad.getNivel();
        } else {
            nivelObjetoCuenta = cntEntidad.getNivel() - 1;
        }
        int valorPosicion = nivelMascaraAux - nivelObjetoCuenta;
        for (String arrayMascara1 : arrayMascara) {
            System.out.println("valo:"+arrayMascara1);    
        }
        
        System.out.println("VALOR 2:"+valorPosicion);
        System.out.println("VALOR 3:"+arrayMascara[valorPosicion]);
        numero = Integer.parseInt(arrayMascara[valorPosicion]);
        numero++;
        if (controlaGeneracionNumero(arrayMascara[valorPosicion], numero)) {
            return generaCodigoConCeros(numero, arrayMascara[valorPosicion].length());
        } else {
//            return "El numero es mayor al Tamaño no insista::";
            return "off";
        }

    }

    public boolean activaTipoMovimiento(CntEntidad cntEntidad, String tipo, String mascaraNuevo) {
        int nivelObjetoCuenta = 0;
        if (tipo.equals("N")) {
            nivelObjetoCuenta = cntEntidad.getNivel();
            if (nivelObjetoCuenta == 1 && !cntEntidad.getMascaraGenerada().equals(mascaraNuevo)) {
                return true;
            }

        } else {
            nivelObjetoCuenta = cntEntidad.getNivel() - 1;
            if (nivelObjetoCuenta == 1 && !cntEntidad.getMascaraGenerada().equals(mascaraNuevo)) {
                return true;
            }

        }
        return false;
    }

    public int controlaLongitudNumero(CntEntidad cntEntidad, String tipo) {
        int nivelObjetoCuenta1 = 0, numero1 = 0;
        int nivelMascaraAux;
        nivelMascaraAux = generaNivelCodigoCuentasGenerales(cntEntidad.getMascaraGenerada());
        String[] arrayMascara = cntEntidad.getMascaraGenerada().split("-");
        if (tipo.equals("N")) {
            nivelObjetoCuenta1 = cntEntidad.getNivel();
        } else {
            nivelObjetoCuenta1 = cntEntidad.getNivel() - 1;
        }
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjetoCuenta1 == nivelMascaraAux) {
                numero1 = arrayMascara[i].length();

            }
            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return numero1;
    }

    public List<CntEntidad> obtieneHijosPorCntObjetos(CntEntidad cntEntidad) {
        List<CntEntidad> list = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null and j.tipo='" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' "
                + "and j.idEntidadPadre = " + cntEntidad.getIdEntidad());
        if (!list.isEmpty()) {
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    //aumentado para la creación de antecesores de pan de cuentas
    public CntNivel obtienedescripcionNivel(int value) {
        List<CntNivel> listanivel = hibernateTemplate.find(""
                + "select h "
                + "from CntNivel h "
                + "where h.nivel =" + value + " and h.tipoMovimiento='" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "'");
        if (!listanivel.isEmpty()) {
            return listanivel.get(0);
        }
        return null;
    }

    public List<CntEntidad> obtieneHermanosPorCntEntidad(CntEntidad cntEntidad) {
        List<CntEntidad> list = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.nivel = " + cntEntidad.getNivel() + " and j.tipo='" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' "
                + "and j.idEntidadPadre = " + cntEntidad.getIdEntidadPadre() + " order by j.mascaraGenerada ASC");
        if (!list.isEmpty()) {
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntEntidad> cntEntidadesPorGrupoNivelList(String grupoNivel) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select o "
                    + "from CntEntidad o "
                    + "where o.cntMascara.grupoNivel.codigo = '" + grupoNivel + "' "
                    + "and o.fechaBaja is null "
                    + "order by o.mascaraGenerada ASC");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }

    }

    public boolean verificaCuentasGeneralesListLleno(List<ParCuentasGeneralesNivel> parCuentasGeneralesNivelList) {
        for (ParCuentasGeneralesNivel parCuentasGeneralesNivelAux : parCuentasGeneralesNivelList) {
            if (parCuentasGeneralesNivelAux.getNivel() == 0) {
                if (verificaExisteDescripcionDeCuenta(parCuentasGeneralesNivelAux.getCuentasGenerales().getDescripcion())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verificaExisteDescripcionDeCuenta(String nombreCta) {
        if (nombreCta.equals(EnumCuentaGeneral.ACTIVO.name()) || nombreCta.equals(EnumCuentaGeneral.PASIVO.name()) || nombreCta.equals(EnumCuentaGeneral.PATRIMONIO.name()) || nombreCta.equals(EnumCuentaGeneral.INGRESO.name()) || nombreCta.equals(EnumCuentaGeneral.EGRESO.name())) {
            return true;
        }
        return false;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void persistCuentasGeneralesPasanObjetosList(List<ParCuentasGeneralesNivel> cntPlanCuentasList, CntMascara cntMascaras, String usuarioLogeado) throws Exception {
        try {
            boolean activaPatrimonio;
            activaPatrimonio = verificaExistenciaPatrimonio(cntPlanCuentasList);
            ParValor parValorAux;
            for (ParCuentasGeneralesNivel parCuentasGeneralesNivelAux : cntPlanCuentasList) {
                String codigo = parCuentasGeneralesService.generaCodigoCuentasGenrales(cntMascaras.getMascara(), parCuentasGeneralesNivelAux.getNivel());
                int nivelCodigo = parCuentasGeneralesService.generaNivelCodigoCuentasGenerales(codigo);
                CntEntidad cntEntidad = new CntEntidad();
                cntEntidad.setIdEntidadPadre(0L);
                cntEntidad.setCntMascara(cntMascaras);
                cntEntidad.setMascaraGenerada(codigo);
                cntEntidad.setTipo("PCTA");
                cntEntidad.setNivel(nivelCodigo);
                cntEntidad.setUsuarioAlta(usuarioLogeado);
                cntEntidad.setFechaAlta(new Date());
                cntEntidad.setTieneAuxiliar("N");
                cntEntidad.setHabilitaCentroCosto("N");
                cntEntidad.setTieneCentroCosto("N");
                cntEntidad.setHabilitaCentroCosto("N");
                if (parCuentasGeneralesNivelAux.getNivel() != 0) {
                    if (activaPatrimonio && parCuentasGeneralesNivelAux.getCuentasGenerales().getDescripcion().toUpperCase().equals("PASIVO")) {
                        cntEntidad.setDescripcion("PASIVO Y PATRIMONIO");
                        super.persist(cntEntidad);
                    } else {
                        cntEntidad.setDescripcion(parCuentasGeneralesNivelAux.getCuentasGenerales().getDescripcion());
                        super.persist(cntEntidad);
                    }
                }
                parValorAux = parParametricasService.findParValor(parCuentasGeneralesNivelAux.getCuentasGenerales().getCodigo());
                parValorAux.setValor(Integer.toString(parCuentasGeneralesNivelAux.getNivel()));
                parValorAux.setFechaModificacion(new Date());
                parValorAux.setUsuarioModificacion(usuarioLogeado);

                parParametricasService.mergeParValor(parValorAux);
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public boolean verificaExistenciaPatrimonio(List<ParCuentasGeneralesNivel> cntPlanCuentasList) {
        for (ParCuentasGeneralesNivel parCuentasGeneralesNivelAux : cntPlanCuentasList) {
            if (parCuentasGeneralesNivelAux.getCuentasGenerales().getDescripcion().toUpperCase().equals("PATRIMONIO") && parCuentasGeneralesNivelAux.getNivel() == 0) {
                return true;
            }
        }
        return false;
    }

    public List<CntEntidad> listaSuperiorGrupoDeObjetoSeleccionado(CntEntidad cntEntidad) {
        List<CntEntidad> listaAuxiliar = new ArrayList<CntEntidad>();
        List<CntEntidad> listaVolcada = new ArrayList<CntEntidad>();
        CntEntidad cntEntidadAux = cntEntidad;
        while (!cntEntidadAux.getIdEntidadPadre().equals(new Long(0))) {
            cntEntidadAux.setDescripcion(eliminaCaracterDescripcion(cntEntidadAux));
            listaAuxiliar.add(cntEntidadAux);
            cntEntidadAux = (CntEntidad) this.find(CntEntidad.class, cntEntidadAux.getIdEntidadPadre());
        }
        if (cntEntidadAux.getIdEntidadPadre().equals(new Long(0))) {
            listaAuxiliar.add(cntEntidadAux);
        }
        for (int i = listaAuxiliar.size() - 1; i >= 0; i--) {
            listaVolcada.add(listaAuxiliar.get(i));
        }
        return listaVolcada;
    }

    public boolean esNivel1oNivel2(CntEntidad cntEntidad) {
        if (cntEntidad.getNivel() == 1 || cntEntidad.getNivel() == 2) {
            return true;
        }
        return false;
    }

    public String devuelveTipoMovimiento(int tipoMovimiento) {
        if (tipoMovimiento == 1) {
            return EnumTipoMovimiento.DEBE.getCodigo();
        }
        if (tipoMovimiento == 2) {
            return EnumTipoMovimiento.HABER.getCodigo();
        }
        if (tipoMovimiento == 3) {
            return EnumTipoMovimiento.AMBOS.getCodigo();
        }
        if (tipoMovimiento == 4) {
            return EnumTipoMovimiento.NINGUNO.getCodigo();
        }
        return null;
    }

    public String[] obtieneMascaraSeparada(CntEntidad cntEntidad, String tipoNivel) {
        String mascarasDivididasParaVista[] = new String[2];
        String mascaraUno = "";
        String mascaraDos = "";
        Boolean swUno;
        Boolean swDos;
        int posicionCambia;
        String[] arrayMascara = cntEntidad.getMascaraGenerada().split("-");
        posicionCambia = tipoNivel.equals("N") ? (arrayMascara.length - cntEntidad.getNivel()) : arrayMascara.length - (cntEntidad.getNivel() - 1);
        if (posicionCambia > 0 && posicionCambia < arrayMascara.length) {
            swUno = true;
            swDos = false;
            for (int i = 0; i < arrayMascara.length; i++) {
                if (posicionCambia != i && swUno) {
                    mascaraUno = mascaraUno + arrayMascara[i] + "-";
                }
                if (posicionCambia != i && swDos) {
                    mascaraDos = mascaraDos + "-" + arrayMascara[i];
                }
                if (posicionCambia == i) {
                    swUno = false;
                    swDos = true;
                }
            }
            mascarasDivididasParaVista[0] = mascaraUno;
            mascarasDivididasParaVista[1] = mascaraDos;

        } else {
            if (posicionCambia == 0) {
                for (int i = 0; i < arrayMascara.length; i++) {
                    if (posicionCambia != i) {
                        mascaraDos = mascaraDos + "-" + arrayMascara[i];
                    }
                }
                mascarasDivididasParaVista[0] = "";
                mascarasDivididasParaVista[1] = mascaraDos;
            } else {
                if (posicionCambia == arrayMascara.length) {
                    for (int i = 0; i < arrayMascara.length; i++) {
                        if (posicionCambia != i) {
                            mascaraUno = mascaraUno + arrayMascara[i] + "-";
                        }
                    }
                    mascarasDivididasParaVista[0] = mascaraUno;
                    mascarasDivididasParaVista[1] = "";
                }
            }
        }
        return mascarasDivididasParaVista;
    }

    public String generaNumeroSiguienteAutomatico(CntEntidad cntEntidad, String nivel) {
        List<CntEntidad> lista = new ArrayList<CntEntidad>();
        if (nivel.equals("N")) {
            lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntEntidad a where a.idEntidadPadre=" + cntEntidad.getIdEntidadPadre() + " "
                    + "and a.tipo = '"+EnumGruposNivel.PLAN_CUENTAS.getCodigo()+"' "
                    + "order by a.idEntidad DESC");
        } else {
            if (nivel.equals("S")) {
                lista = hibernateTemplate.find(""
                        + "select a "
                        + "from CntEntidad a where a.idEntidadPadre=" + cntEntidad.getIdEntidad() + " "
                        + "and a.tipo = '"+EnumGruposNivel.PLAN_CUENTAS.getCodigo()+"' "
                        + "order by a.idEntidad DESC");
            }
        }
        return !lista.isEmpty() ? generaNumeroSiguiente(lista.get(0), "N") : nivel.equals("N") ? generaNumeroSiguiente(cntEntidad, "N") : generaNumeroSiguiente(cntEntidad, "S");
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void persistMasivoCuentasNivelDosAndUnoBaseMigrada(String nombreUsuarioLogeado) throws Exception {
        cntParametroAutomaticoService.daDeBajaTodosLosParametrosAutomaticos(nombreUsuarioLogeado);
        List<CntEntidad> lista = hibernateTemplate.find(
                "select o from CntEntidad o "
                + "where (o.nivel= 2 or o.nivel=1) "
                + "and o.cntMascara.grupoNivel.codigo='" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' and o.fechaBaja is null");
        for (CntEntidad cntEntidad : lista) {
            ParAjustes parAjustes = (ParAjustes) parParametricasService.find(ParAjustes.class, "SAJU");
            ParTipoRetencion parTiporetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, "SRET");
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
            cntParametroAutomatico.setUsuarioAlta(nombreUsuarioLogeado);
            cntParametroAutomatico.setFechaAlta(new Date());
            try {
                cntParametroAutomaticoService.persistCntParametroAutomatico(cntParametroAutomatico);
            } catch (Exception e) {
                throw e;
            }
        }

    }

    public List<CntEntidad> listaCuentasDeUltimoNivelPorDescripcion(String descripcion) {
        String condicion = descripcion + "%";
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.tipo = '" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' "
                + "and j.nivel = 1 "
                + "and j.descripcion like '" + condicion + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public String generaCodigoCuentasGenrales(String mascara, Integer nivel) {
        String colores = "9-99-999";
        String nivelAux = Integer.toString(nivel);
        String[] arrayMascara = mascara.split("-");
        String mascaraCuentaGeneral = "";
        for (int i = 0; i < arrayMascara.length; i++) {
            String s = arrayMascara[i].replace('9', '0');
            if (mascaraCuentaGeneral.equals("")) {
                mascaraCuentaGeneral = mascaraCuentaGeneral + Integer.toString(nivel);
            } else {
                mascaraCuentaGeneral = mascaraCuentaGeneral + "-" + s;
            }
        }
        return mascaraCuentaGeneral;
    }

    public List<CntEntidad> listaCuentasParaRetencionesAndGrossingUp() {
        String codigoActivo = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "ACT")).getValor() + "%";
        String codigoPasivo = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "PAS")).getValor() + "%";
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.tipo = '" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' "
                + "and j.mascaraGenerada like '" + codigoActivo + "' or j.mascaraGenerada like '" + codigoPasivo + "' order by j.mascaraGenerada ASC");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntEntidad> cntEntidadesParaCentrosDeCostoSoloNivelUnoList() throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntEntidad h "
                    + "where h.cntMascara.grupoNivel.codigo = '" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                    + "and h.fechaBaja is null and h.nivel=1 "
                    + "order by h.mascaraGenerada ASC");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }

    }

    public List<CntEntidad> cntEntidadesParaCentrosDeCostoSoloNivelUnoFiltraList() throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntEntidad h "
                    + "where h.cntMascara.grupoNivel.codigo = '" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                    + "and h.fechaBaja is null and h.nivel<>1 "
                    + "order by h.mascaraGenerada ASC");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }

    }

    public Boolean verfificaExistenciaDeCentroDeCostoAntesDeAdicionar(CntEntidad centrosDeCostoElegidaParaQuitarseAux, List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) {
        Boolean verifica = true;
        for (CntConfiguracionCentrocosto configuracionCentrocosto : listaCntConfiguracionCentrocosto) {
            if (configuracionCentrocosto.getIdCentroCosto().getMascaraGenerada().equals(centrosDeCostoElegidaParaQuitarseAux.getMascaraGenerada())) {
                verifica = false;
                break;
            }
        }
        return verifica;
    }

//    public Boolean verificaEntidadCentroCosto(CntEntidad cntEntidad) throws Exception {
//        String codigoIngreso = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "EGR")).getValor() + "%";
//        String codigoEgreso = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "ING")).getValor() + "%";
//        List<CntEntidad> lista = hibernateTemplate.find("select j "
//                + "from CntEntidad j "
//                + "where j.fechaBaja is null "
//                + "and j.nivel=1 "
//                + "and j.idEntidad='" + cntEntidad.getIdEntidad() + "'"
//                + "and j.tipo = '" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' "
//                + "and j.mascaraGenerada like '" + codigoIngreso + "' or j.mascaraGenerada like '" + codigoEgreso + "' order by j.mascaraGenerada ASC");
//        if (!lista.isEmpty()) {
//            return true;
//        }
//        return false; 
//    }
    //metodo que devuelve fasle si la entidad no tiene centro de costo y si posee cxentro de costo 
    public Boolean verificaEntidadCentroCosto(CntEntidad cntEntidad) throws Exception {
//        String codigoActivo = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "ACT")).getValor() + "%";
//        String codigoPasivo = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "PAS")).getValor() + "%";
//        String codigoPatrimonio = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "PAT")).getValor() + "%";
//        String codigoCuentaAcreedora = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "COA")).getValor() + "%";
//        String codigoOrdenGeneral = ((ParCuentasGenerales) parParametricasService.find(ParCuentasGenerales.class, "COD")).getValor() + "%";

        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.idEntidad = '" + cntEntidad.getIdEntidad() + "'"
                + "and j.nivel = 1 "
                + "and j.tipo = '" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' "
                + "and j.habilitaCentroCosto = 'S' "
                //                + "and (j.mascaraGenerada like '" + codigoActivo + "' or j.mascaraGenerada like '" + codigoPasivo + "' or j.mascaraGenerada like '" + codigoPatrimonio + "' or j.mascaraGenerada like '" + codigoCuentaAcreedora + "' or j.mascaraGenerada like '" + codigoOrdenGeneral + "') "
                + "order by j.mascaraGenerada ASC");
        if (!lista.isEmpty()) {
            return false;
        }
        return true;
    }

    public List<CntConfiguracionCentrocosto> llevaTodosLosCentrosDeCostoNivelUnoASiguienteListaConfiguracion() throws Exception {
        try {
            List<CntConfiguracionCentrocosto> configuracionCentrocostosList = new ArrayList<CntConfiguracionCentrocosto>();
            CntConfiguracionCentrocosto configuracionCentrocosto = new CntConfiguracionCentrocosto();
            for (CntEntidad cntEntidad : cntEntidadesParaCentrosDeCostoSoloNivelUnoList()) {
                CntConfiguracionCentrocosto ccc = (CntConfiguracionCentrocosto) configuracionCentrocosto.clone();
                ccc.setIdCentroCosto(cntEntidad);
                configuracionCentrocostosList.add(ccc);
            }
            return configuracionCentrocostosList;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntEntidad> filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(List<CntConfiguracionCentrocosto> configuracionCentrocostosList) throws Exception {
        try {
            String filtro = "";
            for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : configuracionCentrocostosList) {
                filtro = filtro + " and h.idEntidad<>" + cntConfiguracionCentrocosto.getIdCentroCosto().getIdEntidad();
            }
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntEntidad h "
                    + "where h.cntMascara.grupoNivel.codigo = '" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                    + filtro
                    + "and h.fechaBaja is null "
                    + "order by h.mascaraGenerada ASC");
            return lista;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean validaSumaTotalProcentajeAlCienPorciento(List<CntConfiguracionCentrocosto> configuracionCentrocostosList) throws Exception {
        try {
            BigDecimal sumaPorcentaje = new BigDecimal("0.00");
            for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : configuracionCentrocostosList) {
                if (cntConfiguracionCentrocosto.getPorcentaje() != null) {
                    sumaPorcentaje = sumaPorcentaje.add(cntConfiguracionCentrocosto.getPorcentaje());
                }
            }
            if (sumaPorcentaje.compareTo(new BigDecimal("100.00")) == 0 || sumaPorcentaje.compareTo(new BigDecimal("100.00")) == -1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public BigDecimal SumaTotalProcentajeAlCienPorciento(List<CntConfiguracionCentrocosto> configuracionCentrocostosList) throws Exception {
        try {
            BigDecimal sumaPorcentaje = new BigDecimal("0.00");
            for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : configuracionCentrocostosList) {
                if (cntConfiguracionCentrocosto.getPorcentaje() != null) {
                    sumaPorcentaje = sumaPorcentaje.add(cntConfiguracionCentrocosto.getPorcentaje());
                }
            }
            return sumaPorcentaje;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean verificaExistenciaDeConfiguracionCentrosDeCostoDeUnCntEntidad(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntConfiguracionCentrocosto h "
                    + "where h.idPlanCuenta = " + cntEntidad.getIdEntidad() + " "
                    + "and h.tipo='" + EnumTipoConfiguracionDeCentroDeCosto.DEFINICION.getCodigo() + "' "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    public CntConfiguracionCentrocosto obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidad(CntEntidad cntEntidad, Long idCentroCosto) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntConfiguracionCentrocosto h "
                    + "where h.idPlanCuenta = " + cntEntidad.getIdEntidad() + " "
                    + "and h.tipo='" + EnumTipoConfiguracionDeCentroDeCosto.DEFINICION.getCodigo() + "' and h.idCentroCosto = " + idCentroCosto + " "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? lista.get(0) : null;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntConfiguracionCentrocosto> cargaListadoConfiguracionDesdeUnPlanCuentas(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> configuracionCentrocostosList = new ArrayList<CntConfiguracionCentrocosto>();
            if (verificaExistenciaDeConfiguracionCentrosDeCostoDeUnCntEntidad(cntEntidad)) {
                CntConfiguracionCentrocosto configuracionCentrocosto = new CntConfiguracionCentrocosto();
                for (CntEntidad entidad : cntEntidadesParaCentrosDeCostoSoloNivelUnoList()) {
                    CntConfiguracionCentrocosto ccc = (CntConfiguracionCentrocosto) configuracionCentrocosto.clone();
                    ccc.setIdCentroCosto(entidad);
                    if (obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidad(cntEntidad, entidad.getIdEntidad()) != null) {
                        ccc.setPorcentaje(obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidad(cntEntidad, entidad.getIdEntidad()).getPorcentaje());
                        configuracionCentrocostosList.add(ccc);
                    }
                }
            }
            return configuracionCentrocostosList;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean validaSumaTotalProcentajeAlCienPorcientoParaModificar(List<CntConfiguracionCentrocosto> configuracionCentrocostosList) throws Exception {
        try {
            BigDecimal sumaPorcentaje = new BigDecimal("0.00");
            for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : configuracionCentrocostosList) {
                if (cntConfiguracionCentrocosto.getPorcentaje() != null) {
                    sumaPorcentaje = sumaPorcentaje.add(cntConfiguracionCentrocosto.getPorcentaje());
                }
            }
            if (sumaPorcentaje.compareTo(new BigDecimal("100.00")) == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntEntidad> listaPadresDeCentroDeCosto(CntEntidad centroDeCostoHijo) throws Exception {
        List<CntEntidad> listaDeCuentasPadres = new ArrayList<CntEntidad>();
        Long idCentroDeCosto = centroDeCostoHijo.getIdEntidadPadre();
        CntEntidad centroDeCosto;
        try {
            while (idCentroDeCosto != 0L) {
                centroDeCosto = find(CntEntidad.class, idCentroDeCosto);
                listaDeCuentasPadres.add(centroDeCosto);
                idCentroDeCosto = centroDeCosto.getIdEntidadPadre();
            }
        } catch (Exception e) {
            throw e;
        }
        if (!listaDeCuentasPadres.isEmpty()) {
            return volteaListaDeCuentas(listaDeCuentasPadres);
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntEntidad> volteaListaDeCuentas(List<CntEntidad> listaOriginal) {
        List<CntEntidad> listaVolteada = new ArrayList<CntEntidad>();
        int limite = listaOriginal.size();
        for (int i = 0; i < limite; i++) {
            listaVolteada.add(listaOriginal.get(limite - (i + 1)));
        }
        if (!listaVolteada.isEmpty()) {
            return listaVolteada;
        }
        return listaOriginal;
    }

    public Boolean verificaEntidadConCentroCosto(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntEntidad h "
                    + "where h.idEntidad = " + cntEntidad.getIdEntidad() + " "
                    + "and h.tieneCentroCosto='" + EnumTieneCentroDeCosto.SI.getCodigo() + "' "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

//guarda nueva cuenta general
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCuentaGeneral(CntEntidad cntEntidad, String codigoCuenta) throws Exception {
        try {
            String numeroCuenta[] = cntEntidad.getMascaraGenerada().split("-");
            ParValor parValor = (ParValor) parParametricasService.findParValor(codigoCuenta);
            parValor.setValor(numeroCuenta[0]);
            parValor.setFechaModificacion(new Date());
            parValor.setUsuarioModificacion(cntEntidad.getUsuarioAlta());
            parParametricasService.mergeParValor(parValor);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCuentaGeneralYPlanDeCuentas(CntEntidad cntObjetos, String tipo, CntParametroAutomatico cntParametroAutomatico, String codigoCuenta) throws Exception {
        try {
            persistCntObjetosConAsignacionPorDefinicion(cntObjetos, tipo, cntParametroAutomatico);
            guardaCuentaGeneral(cntObjetos, codigoCuenta);
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean verificaEntidadPadre(CntEntidad cntEntidad) throws Exception {
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.idEntidad='" + cntEntidad.getIdEntidad() + "'"
                + "and j.idEntidadPadre=0 "
                + "and j.tipo = '" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' "
                + "order by j.mascaraGenerada ASC");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<ParPlanCuentas> listaLasCuentasEnUnRango(CntEntidad cuentaInicial, CntEntidad cuentaFinal) throws Exception {
        try {
            List<ParPlanCuentas> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from ParPlanCuentas j "
                    + "where j.nivel=1"
                    + "and (j.mascaraGenerada >= '" + cuentaInicial.getMascaraGenerada() + "' "
                    + "and j.mascaraGenerada <= '" + cuentaFinal.getMascaraGenerada() + "') ");
            if (!lista.isEmpty()) {
                return lista;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public CntEntidad cntEntidadPorMascara(String mascara) throws Exception {
        System.out.println("...mascara " + mascara);
        try {

            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntEntidad j "
                    + "where j.mascaraGenerada = '" + mascara + "'");
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<String> listaDeNumerosDeEntidadesParaReporte(List<ParPlanCuentas> listaDeParPlanCuentas) {
        List<String> listaDePaginas = new ArrayList<String>();
        int numero = 0;
        for (ParPlanCuentas parplanCuentas : listaDeParPlanCuentas) {
            numero = numero + 1;
            listaDePaginas.add("Pag. - " + numero + " - Cuenta: " + parplanCuentas.getMascaraGenerada());
        }
        if (!listaDePaginas.isEmpty()) {
            return listaDePaginas;
        }
        return Collections.EMPTY_LIST;
    }

    //verifica si una entidad tiene auxiliar
    public Boolean verificaEntidadConAuxiliar(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntEntidad h "
                    + "where h.idEntidad = " + cntEntidad.getIdEntidad() + " "
                    + "and h.tieneAuxiliar='" + EnumTieneAuxiliar.SI.getCodigo() + "' "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean verificaEntidadConProyecto(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntEntidad h "
                    + "where h.idEntidad = " + cntEntidad.getIdEntidad() + " "
                    + "and h.tieneProyecto='" + EnumTieneProyecto.SI.getCodigo() + "' "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    //verifica si una cuenta del plan de cuentas tiene asignado algun auxiliar
    public Boolean verificaAuxiliarEntidades() throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select c "
                    + "from CntEntidad c "
                    + "where c.nivel=1"
                    + "and  c.tieneAuxiliar='" + EnumTieneAuxiliar.SI.getCodigo() + "' "
                    + "and c.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }
    //verifica si una entidad del plan de cuentas tiene centro de costos

    public Boolean verificaCentroCostosEntidades() throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select c "
                    + "from CntEntidad c "
                    + "where c.nivel=1"
                    + "and  c.tieneCentroCosto='" + EnumTieneCentroDeCosto.SI.getCodigo() + "' "
                    + "and c.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    //verifica si una un detalle compronbante tiene asignado un proyecto para bloquear el botn de proyectos en datos de empresa
    public Boolean verificaProyectoDetalleComprobante() throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select c.cntEntidad "
                    + "from CntDetalleComprobante c "
                    + "where  c.idProyecto is not null "
                    + "and c.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    //verifica si una entidad tiene asignado un auxiliar
    public Boolean verificaEntidadTieneAuxiliarAsignado(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select c.cntEntidad "
                    + "from CntAuxiliarPorCuenta c "
                    + "where  c.cntEntidad.idEntidad='" + cntEntidad.getIdEntidad() + "'"
                    + " and c.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    //lista los auxiliares asignados a una entidad
    public List<CntAuxiliar> listaDeAuxiliaresPorEntidad(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntAuxiliar> lista = hibernateTemplate.find(""
                    + "select c.cntAuxiliar "
                    + "from CntAuxiliarPorCuenta c "
                    + "where  c.cntEntidad.idEntidad='" + cntEntidad.getIdEntidad() + "' "
                    + "and c.fechaBaja is null ");
            if (!lista.isEmpty()) {
                return lista;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    //verifica si ya existe parametros automaticos nulos para renderizar el boton
    public Boolean verificaEntidadTieneParametrosAutomaticos() throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select c.cntEntidad "
                    + "from CntParametroAutomatico c "
                    + "where  c.tipoMovimiento<>'NING' "
                    + "or c.facturaCompra<>'NING' "
                    + "or c.facturaVenta<>'NING' "
                    + "or c.sinFactura<>'NING' "
                    + "or c.creditoFiscalNoDeducible<>'NING' "
                    + "or c.parTipoRetencion.codigo<>'SRET' "
                    + "or c.parTipoRetencionGrossingUp.codigo<>'SRET' "
                    + "or c.parAjustes.codigo<>'SAJU' "
                    + "or c.tipoCalculoAutomatico<>'NING' "
                    + "and c.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean habilitaEliminacionCentroCosto(CntEntidad cntEntidad) {
        List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDistribucionCentrocosto j "
                + "where j.fechaBaja is null "
                + "and j.cntCentroDeCosto.idEntidad = '" + cntEntidad.getIdEntidad() + "' ");
        if (!lista.isEmpty()) {
            return false;
        }
        return true;
    }

    public String[] verificaYEliminaCentrosDeCosto(CntEntidad cntEntidad) throws Exception {
        String[] mensaje = new String[2];
        if (cntEntidad.getNivel() > 1) {
            List<CntEntidad> lista = listaUltimosDescendientes(cntEntidad);
            if (!lista.isEmpty()) {
                for (CntEntidad cntEntidad2 : lista) {
                    if (!habilitaEliminacionCentroCostoConDistribucion(cntEntidad2)) {
                        mensaje[1] = "No es posible eliminar, verifique que ningún Centro de Costo dependiente esté asignado a una Receta";
                        mensaje[0] = "E";
                        return mensaje;
                    }
                    if (!habilitaEliminacionCentroCosto(cntEntidad2)) {
                        mensaje[1] = "No es posible eliminar, verifique que ningún Centro de Costo dependiente esté asignado a una Transacción";
                        mensaje[0] = "E";
                        return mensaje;
                    }
                }
                eliminaCntEntidadConDescendencia(cntEntidad);
                mensaje[1] = "Se eliminó con éxito el Centro de Costo y sus Dependientes";
                mensaje[0] = "I";
                return mensaje;
            }
        }
        if (habilitaEliminacionCentroCosto(cntEntidad)) {
            if (!habilitaEliminacionCentroCostoConDistribucion(cntEntidad)) {
                mensaje[1] = "No es posible eliminar, verifique que el Centro de Costo no esté asignado a una Receta";
                mensaje[0] = "E";
                return mensaje;
            }
            removeCntObjetos(cntEntidad);
            mensaje[1] = "Se eliminó con éxito el Centro de Costo";
            mensaje[0] = "I";
        } else {
            mensaje[1] = "No es posible eliminar, verifique que el Centro de Costo no esté asignado a ninguna Transacción";
            mensaje[0] = "E";
        }
        return mensaje;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void eliminaCntEntidadConDescendencia(CntEntidad cntEntidad) throws Exception {
        List<CntEntidad> listaDescendientes = listaTodosLosDescendientes(cntEntidad);
        if (!listaDescendientes.isEmpty()) {
            for (CntEntidad cntEntidad1 : listaDescendientes) {
                cntEntidad1.setUsuarioBaja(cntEntidad.getUsuarioBaja());
                cntEntidad1.setFechaBaja(cntEntidad.getFechaBaja());
                removeCntObjetos(cntEntidad1);
            }
        }
        removeCntObjetos(cntEntidad);
    }

    public List<CntEntidad> listaUltimosDescendientes(CntEntidad cntEntidad) {
        String[] mascaraVec = cntEntidad.getMascaraGenerada().split("-");
        String mascaraConsulta = "";
        for (int i = 0; i < ((mascaraVec.length + 1) - cntEntidad.getNivel()); i++) {
            mascaraConsulta = mascaraConsulta + mascaraVec[i] + "-";
        }
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.mascaraGenerada like '" + mascaraConsulta + "%' "
                + "and j.nivel = 1");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntEntidad> listaTodosLosDescendientes(CntEntidad cntEntidad) {
        String[] mascaraVec = cntEntidad.getMascaraGenerada().split("-");
        String mascaraConsulta = "";
        for (int i = 0; i < ((mascaraVec.length + 1) - cntEntidad.getNivel()); i++) {
            mascaraConsulta = mascaraConsulta + mascaraVec[i] + "-";
        }
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.mascaraGenerada like '" + mascaraConsulta + "%' ");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public Boolean habilitaEliminacionCentroCostoConDistribucion(CntEntidad cntEntidad) {
        List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntConfiguracionCentrocosto j "
                + "where j.fechaBaja is null "
                + "and j.idCentroCosto.idEntidad = '" + cntEntidad.getIdEntidad() + "' ");
        if (!lista.isEmpty()) {
            return false;
        }
        return true;
    }

    public Boolean modificaMasivoPlanDeCuentasSoloCampoTieneCentrosDeCosto(CntEntidad entidadSeleccionada) throws Exception {
        try {
            if (verificaSiEnUnRangoDeCuentasTieneAsignadosCentrosDeCosto(entidadSeleccionada)) {
                for (CntEntidad entidad : listaTodosLosDescendientes(entidadSeleccionada)) {
                    entidad.setHabilitaCentroCosto(entidadSeleccionada.getHabilitaCentroCosto());
                    entidad.setFechaModificacion(entidadSeleccionada.getFechaModificacion());
                    entidad.setUsuarioModificacion(entidadSeleccionada.getUsuarioModificacion());
                    mergeCntObjetos(entidad);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean verificaSiEnUnRangoDeCuentasTieneAsignadosCentrosDeCosto(CntEntidad cntEntidad) {
        String[] mascaraVec = cntEntidad.getMascaraGenerada().split("-");
        String mascaraConsulta = "";
        for (int i = 0; i < ((mascaraVec.length + 1) - cntEntidad.getNivel()); i++) {
            mascaraConsulta = mascaraConsulta + mascaraVec[i] + "-";
        }
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntEntidad h "
                + "where h.fechaBaja is null "
                + "and h.mascaraGenerada like '" + mascaraConsulta + "%' "
                + "and h.nivel=1 "
                + "and h.idEntidad IN (select r.idPlanCuenta "
                + "from CntConfiguracionCentrocosto r "
                + "where r.fechaBaja is null)");

        if (!lista.isEmpty()) {
            return false;
        }
        return true;
    }

    //Genera Mascar a apartir del primer registro de la columna mascaraGenrada del documento Excel.
    public String generaMascaraConElRegistroDelExcel(String mascara) {
        String[] arrayMascara = mascara.split("-");
        String mascaraCuentaGeneral = "";
        for (int i = 0; i < arrayMascara.length; i++) {
            if (mascaraCuentaGeneral.equals("")) {
                mascaraCuentaGeneral = convierteDigitosNueve(arrayMascara[i]);
            } else {
                mascaraCuentaGeneral = mascaraCuentaGeneral + "-" + convierteDigitosNueve(arrayMascara[i]);
            }
        }
        return mascaraCuentaGeneral;
    }

    public String convierteDigitosNueve(String parteCodigo) {
        String codigoConvertido = "";
        for (int i = 0; i < parteCodigo.length(); i++) {
            codigoConvertido = codigoConvertido + "9";
        }
        return codigoConvertido;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void saveCntMascarCntNivelesCntPlancuentasCntParametroAutomatico(CntMascara cntMascaras, List<CntNivel> cntNivelesList, List<CntEntidad> listaCntEntidad, String UsuarioLogueado) throws Exception {
        try {
            CntMascara mascara = cntMascarasService.adicionarCntMascara(cntMascaras, cntNivelesList);
            persistCntEntidadListas(listaCntEntidad, mascara);
            persistMasivoCuentasNivelDosAndUnoBaseMigrada(UsuarioLogueado);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void persistCntEntidadListas(List<CntEntidad> listaCntEntidad, CntMascara cntMascara) throws Exception {
        try {
            for (CntEntidad cntEntidad : listaCntEntidad) {
                cntEntidad.setCntMascara(cntMascara);
                persistCntObjetos(cntEntidad);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntParametroAutomatico> listaDeParametrosautomaticosmodificar() {
        List<CntParametroAutomatico> lista = hibernateTemplate.find("select h from CntParametroAutomatico h");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public void cambiafechabaja() throws Exception {
        try {
            List<CntParametroAutomatico> Lista = listaDeParametrosautomaticosmodificar();
            for (CntParametroAutomatico cntParametroAutomatico : Lista) {
                cntParametroAutomatico.setUsuarioModificacion(cntParametroAutomatico.getUsuarioModificacion());
                cntParametroAutomatico.setFechaModificacion(cntParametroAutomatico.getFechaModificacion());
                cntParametroAutomatico.setFechaBaja(null);
                cntParametroAutomatico.setUsuarioBaja(null);
                cntParametroAutomaticoService.mergeCntParametroAutomatico(cntParametroAutomatico);

            }
        } catch (Exception e) {
            throw e;
        }

    }

    public int generaNivelPorMascara(String mascara) {
        String vectorMascara[] = mascara.split("-");
        int nivel = vectorMascara.length + 1;
        for (int i = 0; i < vectorMascara.length; i++) {
            String numero = vectorMascara[i];
            if (Integer.parseInt(numero) != 0) {
                nivel--;
            }
        }
        return nivel;
    }

    public long obtieneIdPadreParaBDMigrada(CntEntidad cntEntidad) {
        int maxNivel = cntEntidad.getMascaraGenerada().split("-").length;
        int nivelPadre = cntEntidad.getNivel() + 1;
        if (cntEntidad.getNivel() == maxNivel) {
            return 0L;
        } else {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntEntidad j "
                    + "where j.fechaBaja is null "
                    + "and j.idEntidad < " + cntEntidad.getIdEntidad() + " "
                    + "and j.nivel = " + nivelPadre + " "
                    + "order by j.idEntidad DESC");
            if (!lista.isEmpty()) {
                return lista.get(0).getIdEntidad();
            } else {
                return 0L;
            }
        }
    }

    public List<CntEntidadPojo> listaCuentasConParametrosAutomaticos() {
        List<CntEntidadPojo> listaDeCuentasConParametros = new ArrayList<CntEntidadPojo>();
        CntEntidadPojo cntEntidadPojo = new CntEntidadPojo();
        CntParametroAutomatico cntParametroAutomatico;
        try {
            List<CntEntidad> listaTodasLasCuentas = cntEntidadesPorGrupoNivelList(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
            if (!listaTodasLasCuentas.isEmpty()) {
                for (CntEntidad cntEntidad : listaTodasLasCuentas) {
//                    CntEntidad cuenta = (CntEntidad)cntEntidad.clone();
                    cntEntidadPojo.setCntEntidad(cntEntidad);
                    cntParametroAutomatico = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
                    cntEntidadPojo.setCntParametroAutomatico(cntParametroAutomatico);
                    CntEntidadPojo pojo = (CntEntidadPojo) cntEntidadPojo.clone();
                    listaDeCuentasConParametros.add(pojo);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CntEntidadesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!listaDeCuentasConParametros.isEmpty()) {
            return listaDeCuentasConParametros;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntEntidadPojo> listaCuentasConParametrosAutomaticosPorDescripcion(String descripcion) {
        List<CntEntidadPojo> listaDeCuentasConParametros = new ArrayList<CntEntidadPojo>();
        CntEntidadPojo cntEntidadPojo = new CntEntidadPojo();
        CntParametroAutomatico cntParametroAutomatico;
        try {
            List<CntEntidad> listaTodasLasCuentas = cntEntidadesPorGrupoYDescripcionNivelList(EnumGruposNivel.PLAN_CUENTAS.getCodigo(), descripcion);
            if (!listaTodasLasCuentas.isEmpty()) {
                for (CntEntidad cntEntidad : listaTodasLasCuentas) {
//                    CntEntidad cuenta = (CntEntidad)cntEntidad.clone();
                    cntEntidadPojo.setCntEntidad(cntEntidad);
                    cntParametroAutomatico = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
                    cntEntidadPojo.setCntParametroAutomatico(cntParametroAutomatico);
                    CntEntidadPojo pojo = (CntEntidadPojo) cntEntidadPojo.clone();
                    listaDeCuentasConParametros.add(pojo);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CntEntidadesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!listaDeCuentasConParametros.isEmpty()) {
            return listaDeCuentasConParametros;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntEntidad> cntEntidadesPorGrupoYDescripcionNivelList(String grupoNivel, String descripcion) throws Exception {
        String consulta = "";
        if (descripcion != null || descripcion.equals("")) {
            consulta = " and o.descripcion like '%" + descripcion + "%' ";
        }
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select o "
                    + "from CntEntidad o "
                    + "where o.cntMascara.grupoNivel.codigo = '" + grupoNivel + "' "
                    + "and o.fechaBaja is null "
                    + "" + consulta + ""
                    + "order by o.mascaraGenerada ASC");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public List<CntPojoParametrizacionAjustesPlanCuentas> cargaListaParaParametrizacionAjustes() {
        List<CntPojoParametrizacionAjustesPlanCuentas> listaPojoAjustes = new ArrayList<CntPojoParametrizacionAjustesPlanCuentas>();
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select o from CntEntidad o "
                + "where o.fechaBaja is null "
                + "order by o.mascaraGenerada ASC");
        for (CntEntidad cntEntidad : lista) {
            CntPojoParametrizacionAjustesPlanCuentas ajustesPlanCuentas = new CntPojoParametrizacionAjustesPlanCuentas();
            ajustesPlanCuentas.setCntEntidad(cntEntidad);
            ajustesPlanCuentas.setParAjustes(EnumAjuste.SIN_AJUSTE.getCodigo());
            listaPojoAjustes.add(ajustesPlanCuentas);
        }
        return listaPojoAjustes;
    }

    public void eliminaPlanDeCuentas(CntEntidad cntEntidad) throws Exception {
        try {
            if (cntParametroAutomaticoService.verificaRelacionCntEntidadConCntParametroAutomatico(cntEntidad) && cntAuxiliaresPorCuentaService.verificaRelacionCntEntidadConCntAuxiliarPorCuenta(cntEntidad) && cntDetalleComprobanteService.verificaRelacionCntEntidadConCntDetalleComprobante(cntEntidad)) {
                removeCntObjetos(cntEntidad);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad eliminaCuentaConSuParametrosAutomatico(CntEntidad cntEntidad) throws Exception {
        try {
            CntParametroAutomatico cntParametroAutomatico = cntParametroAutomaticoService.obtieneCntParametroAutomaticoPorCuenta(cntEntidad);
            cntParametroAutomatico.setFechaBaja(cntEntidad.getFechaBaja());
            cntParametroAutomatico.setUsuarioBaja(cntEntidad.getUsuarioBaja());
            cntParametroAutomaticoService.removeCntParametroAutomatico(cntParametroAutomatico);
            super.remove(cntEntidad);
        } catch (Exception e) {
            throw e;
        }
        return cntEntidad;
    }

    public List<CntEntidad> listaPlanCuentasDescendente(String TipoReporte) {
        String consulta = "";
        List<ParCuentasGenerales> listaCuentasGenerales = parParametricasService.listaTodasLasCuentasGenerales();
        if (TipoReporte.equals("BG")) {
            consulta = "and (j.mascaraGenerada like '" + listaCuentasGenerales.get(0).getValor() + "%' or j.mascaraGenerada like '" + listaCuentasGenerales.get(1).getValor() + "%' or j.mascaraGenerada like '" + listaCuentasGenerales.get(2).getValor() + "%') ";
        } else {
            consulta = "and (j.mascaraGenerada like '" + listaCuentasGenerales.get(3).getValor() + "%' or j.mascaraGenerada like '" + listaCuentasGenerales.get(4).getValor() + "%') ";
        }
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.tipo = 'PCTA' "
                + consulta
                + "order by j.mascaraGenerada DESC");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public Boolean esLaCuentaInicialInferiorALaFinalLibroMayor(CntEntidad cuentaInicial, CntEntidad cuentaFinal) {
        System.out.println("la mascara es::" + cuentaInicial.getMascaraGenerada());
        Long mascaraInicial = Long.parseLong(cuentaInicial.getMascaraGenerada().replace("-", ""));
        Long mascaraFinal = Long.parseLong(cuentaFinal.getMascaraGenerada().replace("-", ""));
        return mascaraInicial <= mascaraFinal;
    }

    public List<EntidadArbolPojo> planDeCuentas() throws Exception {
        List<EntidadArbolPojo> listaCuentas = new ArrayList<EntidadArbolPojo>();
//        Aqui!!!
        return listaCuentas;
    }

    //Listado del plan de cuentas...
    public List<CntEntidad> listaPlanDeCuentasPorMascara(String tipo) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntEntidad a "
                    + "where a.fechaBaja is null "
                    + "and a.tipo = '"
                    + tipo + "' "
                    + "order by a.mascaraGenerada ASC");
            if (!lista.isEmpty()) {
                return lista;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    //Listado del plan de cuentas por idEntidad...
    public List<CntEntidad> listaPlanDeCuentasPoridEntidad(String tipo, Long idEntidadPadre) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntEntidad a "
                    + "where a.fechaBaja is null "
                    + "and a.idEntidadPadre = '"
                    + idEntidadPadre + "' "
                    + "and a.tipo = '"
                    + tipo + "' "
                    + "order by a.mascaraGenerada ASC");
            if (!lista.isEmpty()) {
                return lista;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    //Listado del plan de cuentas por nivel...
    public List<CntEntidad> listaPlanDeCuentasPorNivel(int nivel) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntEntidad a "
                    + "where a.fechaBaja is null "
                    + "and a.nivel = '"
                    + nivel + "' "
                    + "order by a.mascaraGenerada ASC");
            if (!lista.isEmpty()) {
                return lista;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    //Crear entidad pojo Plan de Cuentas...
    public List<EntidadPojo> getListaEntidadPojoByEntidad(CntEntidad cntEntidad, Long idEntidadPadre) throws Exception {
        try {
            List<CntEntidad> listaEntidad = listaPlanDeCuentasPoridEntidad(EnumGruposNivel.PLAN_CUENTAS.getCodigo(), idEntidadPadre);
            List<EntidadPojo> listaEntidadPojo = new ArrayList<EntidadPojo>();
            EntidadPojo entidadPojo;
            for (CntEntidad lista : listaEntidad) {
                entidadPojo = new EntidadPojo();
                entidadPojo.setIdEntidadPojo(lista.getIdEntidad());
                entidadPojo.setDescripcion(lista.getDescripcion());
                entidadPojo.setMascara(lista.getMascaraGenerada());
                entidadPojo.setTipo("HIJ");
                listaEntidadPojo.add(entidadPojo);
            }
            if (!listaEntidadPojo.isEmpty()) {
                return listaEntidadPojo;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    //crear entidad Arbolpojo Plan de Cuentas...
    public List<EntidadArbolPojo> getListaEntidadArbolPojo() throws Exception {
        List<CntMascara> listaTamanio = cntMascarasService.obtieneTananioMascara(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
        List<EntidadArbolPojo> listaEntidadArbolPojo = new ArrayList<EntidadArbolPojo>();
        EntidadArbolPojo entidadArbolPojo;
        List<CntEntidad> listaCntEntidadArbol = listaPlanDeCuentasPorMascara(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
        int valorTM1 = listaTamanio.get(0).getTamanioNivel() - 1;
        int valorTM2 = listaTamanio.get(0).getTamanioNivel() - 2;
        List<EntidadPojo> listaCntEntidadvalorTM1;
        for (CntEntidad cntEntidad : listaCntEntidadArbol) {
            if (listaTamanio.get(0).getTamanioNivel() == cntEntidad.getNivel()) {
                entidadArbolPojo = new EntidadArbolPojo();
                entidadArbolPojo.setIdEntidadPojo(cntEntidad.getIdEntidad());
                entidadArbolPojo.setDescripcion(cntEntidad.getDescripcion());
                entidadArbolPojo.setMascara(cntEntidad.getMascaraGenerada());
                entidadArbolPojo.setTipo("PAD");
                listaCntEntidadvalorTM1 = getListaEntidadPojoByEntidad(cntEntidad, entidadArbolPojo.getIdEntidadPojo());
                if (!listaCntEntidadvalorTM1.isEmpty()) {
                    entidadArbolPojo.setChildren(listaCntEntidadvalorTM1);
                    while (valorTM2 > 0) {
                        List<EntidadPojo> listaCntEntidadvalorTM2;
                        listaCntEntidadvalorTM2 = getListaEntidadPojoByEntidad(cntEntidad, listaCntEntidadvalorTM1.get(0).getIdEntidadPojo());
                        valorTM1 = valorTM2;
                        valorTM2--;
                        if (!listaCntEntidadvalorTM1.isEmpty()) {
                            entidadArbolPojo.setChildren(listaCntEntidadvalorTM1);
                        }
                    }
                }
                listaEntidadArbolPojo.add(entidadArbolPojo);
            }
        }
        if (!listaEntidadArbolPojo.isEmpty()) {
            return listaEntidadArbolPojo;
        }
        return Collections.EMPTY_LIST;
    }

    //Listado del plan de cuentas
    public List<CntEntidad> listaPlanDeCuentasRecursivo(String tipo, int nivel) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntEntidad a "
                    + "where a.fechaBaja is null "
                    + "and a.tipo = '"
                    + tipo + "' "
                    + "and a.nivel = '"
                    + nivel + "' "
                    + "order by a.mascaraGenerada ASC");
            if (!lista.isEmpty()) {
                return lista;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    //Crear entidad pojo Plan de Cuentas Recursivo...
    public List<EntidadPojo> getListaEntidadPojoByEntidadRecursivo(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntMascara> listaTamanio = cntMascarasService.obtieneTananioMascara(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
            List<CntEntidad> listaEntidad = listaPlanDeCuentasRecursivo(EnumGruposNivel.PLAN_CUENTAS.getCodigo(), listaTamanio.get(0).getTamanioNivel());
            List<EntidadPojo> listaEntidadPojo = new ArrayList<EntidadPojo>();
            EntidadPojo entidadPojo;
            for (CntEntidad lista : listaEntidad) {
                entidadPojo = new EntidadPojo();
                entidadPojo.setIdEntidadPojo(lista.getIdEntidad());
                entidadPojo.setDescripcion(lista.getDescripcion());
                entidadPojo.setMascara("");
                entidadPojo.setTipo("ENT");
                listaEntidadPojo.add(entidadPojo);
            }
            if (!listaEntidadPojo.isEmpty()) {
                return listaEntidadPojo;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    //crear entidad Arbolpojo Plan de Cuentas Recursivo...
    public List<EntidadArbolPojo> getListaEntidadArbolPojoRecursivo() throws Exception {
        List<EntidadArbolPojo> listaEntidadArbolPojo = new ArrayList<EntidadArbolPojo>();
        EntidadArbolPojo entidadArbolPojo;
        List<CntEntidad> listaCntEntidadArbol = listaPlanDeCuentasRecursivo(EnumGruposNivel.PLAN_CUENTAS.getCodigo(), 5);
        List<EntidadPojo> listaCntEntidad;
        for (CntEntidad cntEntidad : listaCntEntidadArbol) {
            entidadArbolPojo = new EntidadArbolPojo();
            entidadArbolPojo.setIdEntidadPojo(cntEntidad.getIdEntidad());
            entidadArbolPojo.setDescripcion(cntEntidad.getDescripcion());
            entidadArbolPojo.setMascara("");
            entidadArbolPojo.setTipo("ENT");
            listaCntEntidad = getListaEntidadPojoByEntidadRecursivo(cntEntidad);
            if (!listaCntEntidad.isEmpty()) {
                entidadArbolPojo.setChildren(listaCntEntidad);
            }
            listaEntidadArbolPojo.add(entidadArbolPojo);
        }
        if (!listaEntidadArbolPojo.isEmpty()) {
            return listaEntidadArbolPojo;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntEntidad> obtieneHermanosPorCntEntidadCC(CntEntidad cntEntidad) {
        List<CntEntidad> list = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null "
                + "and j.nivel = " + cntEntidad.getNivel() + " and j.tipo='" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                + "and j.idEntidadPadre = " + cntEntidad.getIdEntidadPadre() + " order by j.mascaraGenerada ASC");
        if (!list.isEmpty()) {
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntEntidad> obtieneHijosPorCntObjetosCC(CntEntidad cntEntidad) {
        List<CntEntidad> list = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.fechaBaja is null and j.tipo='" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                + "and j.idEntidadPadre = " + cntEntidad.getIdEntidad() + " order by j.mascaraGenerada ASC");
        if (!list.isEmpty()) {
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    public Boolean comparaEntidadConDetalleComprobante(CntEntidad cntEntidad, CntDetalleComprobante detalleComprobante) {
        String tipoTransaccion = detalleComprobante.getTransaccionRealizada();
        CntParametroAutomatico parametroAutomatico = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        CntParametroAutomatico parametroAutomaticoActual = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(detalleComprobante.getCntEntidad());
        String codigoObtenido = obtieneCodigoOriginal(tipoTransaccion);
        if (codigoObtenido != null) {
            if (!codigoObtenido.equals(EnumTransaccionRealizada.NINGUNO.getCodigo())) {
                if (!codigoObtenido.equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo())) {
                    if (!codigoObtenido.equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {

                        if (parametroAutomatico.getParTipoRetencion().getCodigo().equals(codigoObtenido)) {
                            return true;
                        } else {
                            if (parametroAutomatico.getParTipoRetencionGrossingUp().getCodigo().equals(codigoObtenido)) {
                                return true;
                            } else {
                                return parametroAutomatico.getParAjustes().getCodigo().equals(codigoObtenido);
                            }
                        }
                    } else {
                        return parametroAutomatico.getFacturaVenta().equals(parametroAutomaticoActual.getFacturaVenta());
                    }
                } else {
                    return parametroAutomatico.getFacturaCompra().equals(parametroAutomaticoActual.getFacturaCompra());
                }
            } else {
                return parametroAutomatico.getSinFactura().equals(parametroAutomaticoActual.getSinFactura());
            }
        } else {
            System.out.println("ERROR no existe el parametro con este tipo:" + tipoTransaccion);
            return false;
        }

    }

    public String obtieneCodigoOriginal(String codigoTransaccion) {
        if (codigoTransaccion.equals(EnumTransaccionRealizada.NINGUNO.getCodigo())) {
            return EnumTransaccionRealizada.NINGUNO.getCodigo();
        } else {
            if (codigoTransaccion.equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo())) {
                return EnumMovimientoFactura.FACTURA_COMPRA.getCodigo();
            } else {
                if (codigoTransaccion.equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
                    return EnumMovimientoFactura.FACTURA_VENTA.getCodigo();
                } else {
                    if (codigoTransaccion.equals(EnumTransaccionRealizada.RETENCION_BIENES.getCodigo()) || codigoTransaccion.equals(EnumTransaccionRealizada.GROSSING_BIENES.getCodigo())) {
                        return EnumTipoRetencion.BIENES.getCodigo();
                    } else {
                        if (codigoTransaccion.equals(EnumTransaccionRealizada.RETENCION_SERVICIOS.getCodigo()) || codigoTransaccion.equals(EnumTransaccionRealizada.GROSSING_SERVICIOS.getCodigo())) {
                            return EnumTipoRetencion.SERVICIOS.getCodigo();
                        } else {
                            if (codigoTransaccion.equals(EnumTransaccionRealizada.RETENCION_RC_IVA.getCodigo()) || codigoTransaccion.equals(EnumTransaccionRealizada.GROSSING_RC_IVA.getCodigo())) {
                                return EnumTipoRetencion.RC_IVA.getCodigo();
                            } else {
                                if (codigoTransaccion.equals(EnumTransaccionRealizada.RETENCION_ALQUILERES.getCodigo()) || codigoTransaccion.equals(EnumTransaccionRealizada.GROSSING_ALQUILERES.getCodigo())) {
                                    return EnumTipoRetencion.ALQUILERES.getCodigo();
                                } else {
                                    if (codigoTransaccion.equals(EnumTransaccionRealizada.RETENCION_REMESAS_AL_EXTERIOR.getCodigo()) || codigoTransaccion.equals(EnumTransaccionRealizada.GROSSING_REMESAS_AL_EXTERIOR.getCodigo())) {
                                        return EnumTipoRetencion.REMESAS_AL_EXTERIOR.getCodigo();
                                    } else {
                                        if (codigoTransaccion.equals(EnumTransaccionRealizada.RETENCION_IUE_IT_IND_EXPORTADOR.getCodigo()) || codigoTransaccion.equals(EnumTransaccionRealizada.GROSSING_IUE_IT_IND_EXPORTADOR.getCodigo())) {
                                            return EnumTipoRetencion.IUE_IT_IND_EXPORTADOR.getCodigo();
                                        } else {
                                            if (codigoTransaccion.equals(EnumAjuste.AJUSTE_RESULTADO_POR_EXPOSICION_INFLACION.getCodigo())) {
                                                return EnumAjuste.AJUSTE_RESULTADO_POR_EXPOSICION_INFLACION.getCodigo();
                                            } else {
                                                if (codigoTransaccion.equals(EnumAjuste.AJUSTE_INFLACION_Y_TENENCIA_DE_BIENES.getCodigo())) {
                                                    return EnumAjuste.AJUSTE_INFLACION_Y_TENENCIA_DE_BIENES.getCodigo();
                                                } else {
                                                    if (codigoTransaccion.equals(EnumAjuste.DIFERENCIA_DE_CAMBIO.getCodigo())) {
                                                        return EnumAjuste.DIFERENCIA_DE_CAMBIO.getCodigo();
                                                    } else {
                                                        if (codigoTransaccion.equals(EnumAjuste.CORRECCION_MONETARIA.getCodigo())) {
                                                            return EnumAjuste.CORRECCION_MONETARIA.getCodigo();
                                                        } else {
                                                            if (codigoTransaccion.equals(EnumAjuste.AJUSTE_DE_CAPITAL.getCodigo())) {
                                                                return EnumAjuste.AJUSTE_DE_CAPITAL.getCodigo();
                                                            } else {
                                                                if (codigoTransaccion.equals(EnumAjuste.AJUSTE_GLOBAL_DEL_PATRIMONIO.getCodigo())) {
                                                                    return EnumAjuste.AJUSTE_GLOBAL_DEL_PATRIMONIO.getCodigo();
                                                                } else {
                                                                    if (codigoTransaccion.equals(EnumAjuste.AJUSTE_RESERVA_PATRIMONIAL.getCodigo())) {
                                                                        return EnumAjuste.AJUSTE_RESERVA_PATRIMONIAL.getCodigo();
                                                                    } else {
                                                                        return null;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
