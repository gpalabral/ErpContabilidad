package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnf.ParRecetasDistribucionCentroCosto;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumContextosParaVistas;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumTipoConfiguracionDeCentroDeCosto;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntConfiguracionCentroCostoServiceImpl extends GenericDaoImpl<CntConfiguracionCentrocosto> implements CntConfiguracionCentroCostoService {

    @Autowired
    private CntEntidadesService cntEntidadesService;
    @Autowired
    private ParParametricasService parParametricasService;

    public List<CntConfiguracionCentrocosto> listaConbinadaCentrosDeCostoConConfiguracionDeCentrosDeCostoList(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        List<CntConfiguracionCentrocosto> cntConfiguracionCentroCostoList = new ArrayList<CntConfiguracionCentrocosto>();
        CntConfiguracionCentrocosto cntConfiguracionCentrocosto = new CntConfiguracionCentrocosto();
        try {
            for (CntEntidad centroDeCosto : cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
                CntConfiguracionCentrocosto configuracionCentrocosto = (CntConfiguracionCentrocosto) cntConfiguracionCentrocosto.clone();
                configuracionCentrocosto.setIdCentroCosto(centroDeCosto);
                configuracionCentrocosto.setIdPlanCuenta(cntDetalleComprobante.getCntEntidad().getIdEntidad());
                configuracionCentrocosto.setTipo(EnumTipoConfiguracionDeCentroDeCosto.LIBRE.getCodigo());
                cntConfiguracionCentroCostoList.add(configuracionCentrocosto);
            }

        } catch (Exception e) {
            throw e;
        }
        return cntConfiguracionCentroCostoList;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntConfiguracionCentrocosto persistCntConfiguracionCentroCosto(CntConfiguracionCentrocosto cntConfiguracionCentroCosto) throws Exception {
        try {
            super.persist(cntConfiguracionCentroCosto);
        } catch (Exception e) {
            throw e;
        }
        return cntConfiguracionCentroCosto;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntConfiguracionCentrocosto mergeCntConfiguracionCentroCosto(CntConfiguracionCentrocosto cntConfiguracionCentroCosto) throws Exception {
        try {
            super.merge(cntConfiguracionCentroCosto);
        } catch (Exception e) {
            throw e;
        }
        return cntConfiguracionCentroCosto;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntConfiguracionCentrocosto removeCntConfiguracionCentroCosto(CntConfiguracionCentrocosto cntConfiguracionCentroCosto) throws Exception {
        try {
            super.remove(cntConfiguracionCentroCosto);
        } catch (Exception e) {
            throw e;
        }
        return cntConfiguracionCentroCosto;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCntConfiguracionCentroCostoDefinicion(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentroCosto, String usuario, CntEntidad cuentaElegida) throws Exception {
        try {

            Date fechaAlta = new Date();
//        cuentaElegida.setTieneCentroCosto(EnumTieneCentroDeCosto.SI.getCodigo());
            cuentaElegida.setFechaAlta(fechaAlta);
            cuentaElegida.setUsuarioAlta(usuario);
            cntEntidadesService.mergeCntObjetos(cuentaElegida);
            for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : listaCntConfiguracionCentroCosto) {
                cntConfiguracionCentrocosto.setTipo(EnumTipoConfiguracionDeCentroDeCosto.DEFINICION.getCodigo());
                cntConfiguracionCentrocosto.setFechaAlta(fechaAlta);
                cntConfiguracionCentrocosto.setUsuarioAlta(usuario);
                cntConfiguracionCentrocosto.setIdPlanCuenta(cuentaElegida.getIdEntidad());
                cntConfiguracionCentrocosto.setCodigoDistribucionCentroCosto(null);
                persistCntConfiguracionCentroCosto(cntConfiguracionCentrocosto);
//            cuentaElegida.setTieneCentroCosto(EnumConfirmacion.SI.getCodigo());
                cntEntidadesService.mergeCntObjetos(cuentaElegida);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCntConfiguracionCentroCostoAlternativa(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentroCosto, String usuario, ParRecetasDistribucionCentroCosto receta) throws Exception {
        Date fechaAlta = new Date();
        ParValor recetaAGuardar = new ParValor();
        recetaAGuardar.setCodigo(parParametricasService.ultimoRegistroRecetaParValor().toString());
        recetaAGuardar.setDescripcion(receta.getDescripcion());
        recetaAGuardar.setContexto(EnumContextosParaVistas.RECETA.getCodigo());
        recetaAGuardar.setFechaAlta(fechaAlta);
        recetaAGuardar.setUsuarioAlta(usuario);
        recetaAGuardar = parParametricasService.persistParValor(recetaAGuardar);
        for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : listaCntConfiguracionCentroCosto) {
            cntConfiguracionCentrocosto.setTipo(EnumTipoConfiguracionDeCentroDeCosto.ALTERNATIVA.getCodigo());
            cntConfiguracionCentrocosto.setFechaAlta(fechaAlta);
            cntConfiguracionCentrocosto.setUsuarioAlta(usuario);
            cntConfiguracionCentrocosto.setCodigoDistribucionCentroCosto(Long.parseLong(recetaAGuardar.getCodigo()));
            cntConfiguracionCentrocosto.setIdPlanCuenta(null);
            persistCntConfiguracionCentroCosto(cntConfiguracionCentrocosto);
        }
    }

    public List<CntConfiguracionCentrocosto> obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidadParaDefinicion(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntConfiguracionCentrocosto h "
                    + "where h.idPlanCuenta = " + cntEntidad.getIdEntidad() + " "
                    + "and h.tipo='" + EnumTipoConfiguracionDeCentroDeCosto.DEFINICION.getCodigo() + "' "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void modificarCntConfiguracionCentroCostoDefinicion(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentroCosto, String usuario, CntEntidad cuentaElegida) throws Exception {
        try {
            Date fechaAlta = new Date();
            for (CntConfiguracionCentrocosto configuracionCentrocosto : obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidadParaDefinicion(cuentaElegida)) {
                configuracionCentrocosto.setFechaModificacion(fechaAlta);
                configuracionCentrocosto.setUsuarioModificacion(usuario);
                configuracionCentrocosto.setFechaBaja(fechaAlta);
                configuracionCentrocosto.setUsuarioBaja(usuario);
                mergeCntConfiguracionCentroCosto(configuracionCentrocosto);
            }
            for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : listaCntConfiguracionCentroCosto) {
                cntConfiguracionCentrocosto.setTipo(EnumTipoConfiguracionDeCentroDeCosto.DEFINICION.getCodigo());
                cntConfiguracionCentrocosto.setFechaAlta(fechaAlta);
                cntConfiguracionCentrocosto.setUsuarioAlta(usuario);
                cntConfiguracionCentrocosto.setIdPlanCuenta(cuentaElegida.getIdEntidad());
                cntConfiguracionCentrocosto.setCodigoDistribucionCentroCosto(null);
                persistCntConfiguracionCentroCosto(cntConfiguracionCentrocosto);
            }
        } catch (Exception e) {
        }
    }

    public List<CntConfiguracionCentrocosto> listaCentrosDeCostoPorDefinicion(CntEntidad cuenta) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntConfiguracionCentrocosto j "
                    + "where j.fechaBaja is null "
                    + "and j.idPlanCuenta = '" + cuenta.getIdEntidad() + "' "
                    + "order by j.idCentroCosto.idEntidadPadre ASC");
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            throw e;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntConfiguracionCentrocosto> listaCentrosDeCostoAlternativa(String codigoReceta) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntConfiguracionCentrocosto j "
                    + "where j.fechaBaja is null "
                    + "and j.codigoDistribucionCentroCosto = '" + Long.parseLong(codigoReceta) + "' "
                    + "order by j.idCentroCosto.idEntidadPadre ASC");
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            throw e;
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

    public List<CntConfiguracionCentrocosto> cargaListadoConfiguracionParaRecetasDesdeUnPlanCuentas(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> configuracionCentrocostosList = new ArrayList<CntConfiguracionCentrocosto>();
            if (verificaExistenciaDeConfiguracionCentrosDeCostoParaRecetaDeUnCntEntidad(parRecetasDistribucionCentroCosto)) {
                CntConfiguracionCentrocosto configuracionCentrocosto = new CntConfiguracionCentrocosto();
                for (CntEntidad entidad : cntEntidadesParaCentrosDeCostoSoloNivelUnoList()) {
                    CntConfiguracionCentrocosto ccc = (CntConfiguracionCentrocosto) configuracionCentrocosto.clone();
                    ccc.setIdCentroCosto(entidad);
                    if (obtieneListaConfiguracionCentrosDeCostoParaRecetaDesdeCntEntidad(parRecetasDistribucionCentroCosto, entidad.getIdEntidad()) != null) {
                        ccc.setPorcentaje(obtieneListaConfiguracionCentrosDeCostoParaRecetaDesdeCntEntidad(parRecetasDistribucionCentroCosto, entidad.getIdEntidad()).getPorcentaje());
                        configuracionCentrocostosList.add(ccc);
                    }
                }
            }
            return configuracionCentrocostosList;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean verificaExistenciaDeConfiguracionCentrosDeCostoParaRecetaDeUnCntEntidad(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntConfiguracionCentrocosto h "
                    + "where h.codigoDistribucionCentroCosto = '" + parRecetasDistribucionCentroCosto.getCodigo() + "' "
                    + "and h.tipo='" + EnumTipoConfiguracionDeCentroDeCosto.ALTERNATIVA.getCodigo() + "' "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? true : false;
        } catch (Exception e) {
            throw e;
        }
    }

    public CntConfiguracionCentrocosto obtieneListaConfiguracionCentrosDeCostoParaRecetaDesdeCntEntidad(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto, Long idCentroCosto) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntConfiguracionCentrocosto h "
                    + "where h.codigoDistribucionCentroCosto = '" + parRecetasDistribucionCentroCosto.getCodigo() + "' "
                    + "and h.tipo='" + EnumTipoConfiguracionDeCentroDeCosto.ALTERNATIVA.getCodigo() + "' and h.idCentroCosto = " + idCentroCosto + " "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? lista.get(0) : null;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void modificarCntConfiguracionCentroCostoAlternativa(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentroCosto, String usuario, ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) throws Exception {
        try {
            Date fechaAlta = new Date();
            for (CntConfiguracionCentrocosto configuracionCentrocosto : obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidadParaAlternativa(parRecetasDistribucionCentroCosto)) {
                configuracionCentrocosto.setFechaModificacion(fechaAlta);
                configuracionCentrocosto.setUsuarioModificacion(usuario);
                configuracionCentrocosto.setFechaBaja(fechaAlta);
                configuracionCentrocosto.setUsuarioBaja(usuario);
                mergeCntConfiguracionCentroCosto(configuracionCentrocosto);
            }
            for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : listaCntConfiguracionCentroCosto) {
                cntConfiguracionCentrocosto.setTipo(EnumTipoConfiguracionDeCentroDeCosto.ALTERNATIVA.getCodigo());
                cntConfiguracionCentrocosto.setFechaAlta(fechaAlta);
                cntConfiguracionCentrocosto.setUsuarioAlta(usuario);
                cntConfiguracionCentrocosto.setCodigoDistribucionCentroCosto(Long.parseLong(parRecetasDistribucionCentroCosto.getCodigo()));
                cntConfiguracionCentrocosto.setIdPlanCuenta(null);
                persistCntConfiguracionCentroCosto(cntConfiguracionCentrocosto);

            }
            ParValor recetaAGuardar = parParametricasService.findParValor(parRecetasDistribucionCentroCosto.getCodigo());
            recetaAGuardar.setDescripcion(parRecetasDistribucionCentroCosto.getDescripcion());
            recetaAGuardar.setFechaModificacion(fechaAlta);
            recetaAGuardar.setUsuarioModificacion(usuario);
            parParametricasService.mergeParValor(recetaAGuardar);
        } catch (Exception e) {
        }
    }

    public List<CntConfiguracionCentrocosto> obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidadParaAlternativa(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) throws Exception {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntConfiguracionCentrocosto h "
                    + "where h.codigoDistribucionCentroCosto = '" + parRecetasDistribucionCentroCosto.getCodigo() + "' "
                    + "and h.tipo='" + EnumTipoConfiguracionDeCentroDeCosto.ALTERNATIVA.getCodigo() + "' "
                    + "and h.fechaBaja is null ");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }
}
