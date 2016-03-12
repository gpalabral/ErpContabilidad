/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntDistribucionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.iknow.utils.ObjectUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

/**
 *
 * @author HENRRY
 */
public class CntDistribucionCentroCostoServiceImpl extends GenericDaoImpl<CntDistribucionCentrocosto> implements CntDistribucionCentroCostoService {

    @Autowired
    private CntEntidadesService cntEntidadesService;
    @Autowired
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @Autowired
    private CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService;

    public List<CntDistribucionCentrocosto> listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(CntDetalleComprobante cntDetalleComprobante, Boolean porDefinicion, String codigoReceta) throws Exception {
        List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList = new ArrayList<CntDistribucionCentrocosto>();
        CntDistribucionCentrocosto cntDistribucionCentrocosto = new CntDistribucionCentrocosto();
        List<CntConfiguracionCentrocosto> listaParaDefinicionYAlternativa = new ArrayList<CntConfiguracionCentrocosto>();
        if (porDefinicion && codigoReceta.equals("0")) {
            listaParaDefinicionYAlternativa = cntConfiguracionCentroCostoService.listaCentrosDeCostoPorDefinicion(cntDetalleComprobante.getCntEntidad());
        } else if (!porDefinicion && !codigoReceta.equals("0")) {
            listaParaDefinicionYAlternativa = cntConfiguracionCentroCostoService.listaCentrosDeCostoAlternativa(codigoReceta);
        }
        if (!listaParaDefinicionYAlternativa.isEmpty()) {
            for (CntConfiguracionCentrocosto configuracionCentroCosto : listaParaDefinicionYAlternativa) {
                CntDistribucionCentrocosto distribucionCentrocosto = (CntDistribucionCentrocosto) cntDistribucionCentrocosto.clone();
                distribucionCentrocosto.setIdConfiguracionCentrocosto(configuracionCentroCosto.getIdConfiguracionCentrocosto());
                distribucionCentrocosto.setCntDetalleComprobante(cntDetalleComprobante);
                distribucionCentrocosto.setCntCentroDeCosto(configuracionCentroCosto.getIdCentroCosto());
                distribucionCentrocosto.setEstado(EnumEstado.PENDIENTE.getCodigo());
                distribucionCentrocosto.setPorcentaje(configuracionCentroCosto.getPorcentaje());
                distribucionCentrocosto.setMonto(obtieneMontoDesdeElPorcentaje(cntDetalleComprobante.getDebe() != null ? cntDetalleComprobante.getDebe() : cntDetalleComprobante.getHaber(), distribucionCentrocosto.getPorcentaje()));
                cntDistribucionCentrocostoList.add(distribucionCentrocosto);
            }

            List<CntDistribucionCentrocosto> cntDistribucionCentrocostoListFinal = new ArrayList<CntDistribucionCentrocosto>();
            Long idEntidadPadreAnterior = 0L;
            CntDistribucionCentrocosto nuevo = new CntDistribucionCentrocosto();
            for (CntDistribucionCentrocosto cntDistribucionCentrocostoNivelUno : cntDistribucionCentrocostoList) {
                if (!cntDistribucionCentrocostoNivelUno.getCntCentroDeCosto().getIdEntidadPadre().equals(idEntidadPadreAnterior)) {
                    for (CntEntidad centrosDeCosto : cntEntidadesService.listaPadresDeCentroDeCosto(cntDistribucionCentrocostoNivelUno.getCntCentroDeCosto())) {
                        CntDistribucionCentrocosto distribucionParaCCPadre = (CntDistribucionCentrocosto) nuevo.clone();
                        distribucionParaCCPadre.setEstado(EnumEstado.PENDIENTE.getCodigo());
                        distribucionParaCCPadre.setCntCentroDeCosto(centrosDeCosto);
                        distribucionParaCCPadre.setCntDetalleComprobante(cntDetalleComprobante);
                        cntDistribucionCentrocostoListFinal.add(distribucionParaCCPadre);
                    }
                }
                idEntidadPadreAnterior = cntDistribucionCentrocostoNivelUno.getCntCentroDeCosto().getIdEntidadPadre();
                cntDistribucionCentrocostoListFinal.add(cntDistribucionCentrocostoNivelUno);
            }
            return cntDistribucionCentrocostoListFinal;
        } else {
            try {
                for (CntEntidad centroDeCosto : cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
                    CntDistribucionCentrocosto distribucionCentrocosto = (CntDistribucionCentrocosto) cntDistribucionCentrocosto.clone();
                    distribucionCentrocosto.setEstado(EnumEstado.PENDIENTE.getCodigo());
                    distribucionCentrocosto.setCntCentroDeCosto(centroDeCosto);
                    distribucionCentrocosto.setCntDetalleComprobante(cntDetalleComprobante);
                    cntDistribucionCentrocostoList.add(distribucionCentrocosto);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        if (!cntDistribucionCentrocostoList.isEmpty()) {
            return cntDistribucionCentrocostoList;
        }
        return Collections.EMPTY_LIST;
    }

    public Boolean verificaSumatoriaDeProcentajesYMontosParaDistribucionCC(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList) throws Exception {
        try {
            BigDecimal sumaPorcentaje = new BigDecimal("0.00");
            for (CntDistribucionCentrocosto distribucionCentrocosto : cntDistribucionCentrocostoList) {
                sumaPorcentaje = sumaPorcentaje.add(distribucionCentrocosto.getPorcentaje());
            }
            if (sumaPorcentaje.equals(new BigDecimal("100.00"))) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    public List<CntDistribucionCentrocosto> obtieneAutomaticamenteElProcentajesYMontosParaDistribucionCC(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList, BigDecimal montoTotal) throws Exception {
        try {
            for (CntDistribucionCentrocosto cntDistribucionCentrocosto : cntDistribucionCentrocostoList) {
                if (cntDistribucionCentrocosto.getCntCentroDeCosto().getNivel() == 1) {
                    if (cntDistribucionCentrocosto.getMonto() != null) {
                        BigDecimal valorP = ((cntDistribucionCentrocosto.getMonto().multiply(new BigDecimal("100"))).divide(montoTotal, 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        cntDistribucionCentrocosto.setPorcentaje(valorP);
                    }
                    if (cntDistribucionCentrocosto.getPorcentaje() != null) {
                        BigDecimal valorM = ((montoTotal.multiply(cntDistribucionCentrocosto.getPorcentaje())).divide(new BigDecimal("100"), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);

                        cntDistribucionCentrocosto.setMonto(valorM);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return cntDistribucionCentrocostoList;
    }

    public BigDecimal obtieneMontoDesdeElPorcentaje(BigDecimal montoTotal, BigDecimal porcentaje) {
        BigDecimal valorMonto = ((montoTotal.multiply(porcentaje)).divide(new BigDecimal("100"), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
        return valorMonto;
    }

    public BigDecimal obtienePorcentajeDesdeElMonto(BigDecimal montoTotal, BigDecimal monto) {
        BigDecimal valorPorcentaje = ((monto.multiply(new BigDecimal("100"))).divide(montoTotal, 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
        return valorPorcentaje;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaListaDeCntDistribucionCentroCosto(List<CntDistribucionCentrocosto> cntDistribucionCentrocostosList, String usuarioLogueado) throws Exception {
        Date fechaActual = new Date();
        for (CntDistribucionCentrocosto cntDistribucionCentrocosto : cntDistribucionCentrocostosList) {
            if (cntDistribucionCentrocosto.getMonto() != null && cntDistribucionCentrocosto.getMonto().compareTo(BigDecimal.ZERO) == 1) {
                try {
                    cntDistribucionCentrocosto.setUsuarioAlta(usuarioLogueado);
                    cntDistribucionCentrocosto.setFechaAlta(fechaActual);
                    cntDistribucionCentrocosto.setEstado(EnumEstado.PENDIENTE.getCodigo());
                    persistCntDistribucionCentrocosto(cntDistribucionCentrocosto);
                } catch (Exception ex) {
                    Logger.getLogger(CntDistribucionCentroCostoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        /*Desde aqui*/
        CntDetalleComprobante cntDetalleComprobantePadre = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDistribucionCentrocostosList.get(0).getCntDetalleComprobante().getIdDetalleComprobante());
        List<CntDistribucionCentrocosto> listaDistribucionCentrocostos = listaDistribucionCentroCostoPorDetalleComprobante(cntDetalleComprobantePadre);
        List<CntDetalleComprobante> listaDetallesComprobantesHijos = cntDetalleComprobanteService.listaHijosPorPadreParaDelete(cntDetalleComprobantePadre);
        CntDistribucionCentrocosto cntDistribucionCentrocostoOriginal = new CntDistribucionCentrocosto();
        for (CntDetalleComprobante cntDetalleComprobante : listaDetallesComprobantesHijos) {
            if (cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
                for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaDistribucionCentrocostos) {
                    CntDistribucionCentrocosto cntDistribucionCentrocostoTemporal = (CntDistribucionCentrocosto) cntDistribucionCentrocostoOriginal.clone();
                    cntDistribucionCentrocostoTemporal.setCntDetalleComprobante(cntDetalleComprobante);
                    cntDistribucionCentrocostoTemporal.setCntCentroDeCosto(cntDistribucionCentrocosto.getCntCentroDeCosto());
                    cntDistribucionCentrocostoTemporal.setEstado(cntDistribucionCentrocosto.getEstado());
                    cntDistribucionCentrocostoTemporal.setPorcentaje(cntDistribucionCentrocosto.getPorcentaje());
                    cntDistribucionCentrocostoTemporal.setMonto(obtieneMontoDesdeElPorcentaje(cntDetalleComprobante.getDebe() != null ? cntDetalleComprobante.getDebe() : cntDetalleComprobante.getHaber(), cntDistribucionCentrocosto.getPorcentaje()));
                    cntDistribucionCentrocostoTemporal.setUsuarioAlta(cntDistribucionCentrocosto.getUsuarioAlta());
                    cntDistribucionCentrocostoTemporal.setFechaAlta(cntDistribucionCentrocosto.getFechaAlta());
                    persistCntDistribucionCentrocosto(cntDistribucionCentrocostoTemporal);
                }
            }
        }
        if (cntDetalleComprobantePadre.getIdAntecesor() != null) {
            CntDetalleComprobante detalleAntecesor;
            if (cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobantePadre.getIdAntecesor()) == null) {
                detalleAntecesor = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobantePadre.getIdDetalleComprobante());
            } else {
                detalleAntecesor = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobantePadre.getIdAntecesor());
            }
            List<CntDistribucionCentrocosto> listaCntDistribucionCC = listaDistribucionCentroCostoPorDetalleComprobante(detalleAntecesor);
            if (!listaCntDistribucionCC.isEmpty()) {
                for (CntDistribucionCentrocosto cntDistribucionCentrocosto1 : listaCntDistribucionCC) {
                    cntDistribucionCentrocosto1.setUsuarioModificacion(usuarioLogueado);
                    cntDistribucionCentrocosto1.setFechaModificacion(fechaActual);
                    cntDistribucionCentrocosto1.setUsuarioBaja(usuarioLogueado);
                    cntDistribucionCentrocosto1.setFechaBaja(fechaActual);
                    removeCntDistribucionCentroCosto(cntDistribucionCentrocosto1);
                }
            }
            for (CntDetalleComprobante detalleComprobanteHijos : cntDetalleComprobanteService.listaHijosPorPadreParaDelete(detalleAntecesor)) {
                List<CntDistribucionCentrocosto> listaCntDistribucionCC2 = listaDistribucionCentroCostoPorDetalleComprobante(detalleComprobanteHijos);
                if (!listaCntDistribucionCC2.isEmpty()) {
                    for (CntDistribucionCentrocosto cntDistribucionCentrocosto2 : listaCntDistribucionCC2) {
                        cntDistribucionCentrocosto2.setUsuarioModificacion(usuarioLogueado);
                        cntDistribucionCentrocosto2.setFechaModificacion(fechaActual);
                        cntDistribucionCentrocosto2.setUsuarioBaja(usuarioLogueado);
                        cntDistribucionCentrocosto2.setFechaBaja(fechaActual);
                        removeCntDistribucionCentroCosto(cntDistribucionCentrocosto2);
                    }
                }
            }
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDistribucionCentrocosto persistCntDistribucionCentrocosto(CntDistribucionCentrocosto cntDistribucionCentrocosto) throws Exception {
        try {
            super.persist(cntDistribucionCentrocosto);
        } catch (Exception e) {
            throw e;
        }
        return cntDistribucionCentrocosto;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDistribucionCentrocosto mergeCntDistribucionCentrocosto(CntDistribucionCentrocosto cntDistribucionCentrocosto) throws Exception {
        try {
            super.merge(cntDistribucionCentrocosto);
        } catch (Exception e) {
            throw e;
        }
        return cntDistribucionCentrocosto;
    }

    public BigDecimal obtieneSumaDeMontosListaCntDistribucionCentroCosto(List<CntDistribucionCentrocosto> listaCntDistribucionCentrocostos) {
        BigDecimal totalMonto = new BigDecimal(BigInteger.ZERO);
        for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaCntDistribucionCentrocostos) {
            if (cntDistribucionCentrocosto.getMonto() != null) {
                totalMonto = totalMonto.add(cntDistribucionCentrocosto.getMonto());
            }
        }
        return totalMonto;
    }

    public BigDecimal obtieneSumaDePorcentajesListaCntDistribucionCentroCosto(List<CntDistribucionCentrocosto> listaCntDistribucionCentrocostos) {
        BigDecimal totalPorcentaje = new BigDecimal(BigInteger.ZERO);
        for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaCntDistribucionCentrocostos) {
            if (cntDistribucionCentrocosto.getPorcentaje() != null) {
                totalPorcentaje = totalPorcentaje.add(cntDistribucionCentrocosto.getPorcentaje());
            }
        }
        return totalPorcentaje;
    }

    public BigDecimal encuentraRestoDistribucionCentroDeCostos(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList, BigDecimal montoTotal) {
        BigDecimal resto = montoTotal.subtract(obtieneSumaDeMontosListaCntDistribucionCentroCosto(cntDistribucionCentrocostoList));
        return resto;
    }

    public BigDecimal encuentraRestoPorcentajeDistribucionCentroDeCostos(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList, BigDecimal montoTotal) {
        BigDecimal resto = montoTotal.subtract(obtieneSumaDePorcentajesListaCntDistribucionCentroCosto(cntDistribucionCentrocostoList));
        return resto;
    }

    public List<CntDistribucionCentrocosto> listaDistribucionCentroCostoPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(
                    "select j "
                    + "from CntDistribucionCentrocosto j "
                    + "where j.cntDetalleComprobante.idDetalleComprobante = '" + cntDetalleComprobante.getIdDetalleComprobante() + "' ");
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            throw e;
        }
        return Collections.EMPTY_LIST;
    }

    //modificado del listado para distribucion de centro de costos
    public List<CntDistribucionCentrocosto> obtieneListaDistribucionCentrocostoPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDistribucionCentrocosto h "
                + "where h.fechaBaja is not null and h.cntDetalleComprobante.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante());
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void cambiaEstadoDistribucionCentrocostoModificacion(CntDetalleComprobante detalleComprobante) throws Exception {
        try {
            List<CntDistribucionCentrocosto> centrocostoList = obtieneListaDistribucionCentrocostoPorDetalleComprobante(detalleComprobante);
            if (centrocostoList != null) {
                for (CntDistribucionCentrocosto centrocosto : centrocostoList) {
                    centrocosto.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                    centrocosto.setUsuarioModificacion(detalleComprobante.getUsuarioModificacion());
                    centrocosto.setFechaModificacion(detalleComprobante.getFechaModificacion());
                    mergeCntDistribucionCentrocosto(centrocosto);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntDistribucionCentrocosto> listaDistribucionCCParaModificacion(CntDetalleComprobante cntDetalleComprobante, List<CntDistribucionCentrocosto> listaOriginal) throws Exception {
        CntDetalleComprobante cntDetalleComprobanteAntecedente = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor());
        BigDecimal montoTotal;
        if (cntDetalleComprobante.getDebe() != null) {
            montoTotal = cntDetalleComprobante.getDebe();
        } else {
            montoTotal = cntDetalleComprobante.getHaber();
        }
        try {
            if (!listaOriginal.isEmpty()) {
                List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(""
                        + "select j "
                        + "from CntDistribucionCentrocosto j "
                        + "where j.cntDetalleComprobante.idDetalleComprobante = '" + cntDetalleComprobanteAntecedente.getIdDetalleComprobante() + "'");
                for (CntDistribucionCentrocosto cntDistribucionCentrocostoOriginal : listaOriginal) {
                    for (CntDistribucionCentrocosto cntDistribucionCentrocosto : lista) {
                        if (cntDistribucionCentrocostoOriginal.getCntCentroDeCosto().getIdEntidad().equals(cntDistribucionCentrocosto.getCntCentroDeCosto().getIdEntidad())) {
                            cntDistribucionCentrocostoOriginal.setPorcentaje(cntDistribucionCentrocosto.getPorcentaje());
                            cntDistribucionCentrocostoOriginal.setMonto(obtieneMontoDesdeElPorcentaje(montoTotal, cntDistribucionCentrocosto.getPorcentaje()));
                        }
                    }
                }
                return listaOriginal;
            }
        } catch (Exception e) {
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntDistribucionCentroCosto(CntDistribucionCentrocosto cntDistribucionCentrocosto) throws Exception {
        try {
            super.remove(cntDistribucionCentrocosto);
        } catch (Exception j) {
            throw j;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void deleteCntDistribucionCentroCosto(CntDistribucionCentrocosto cntDistribucionCentrocosto) throws Exception {
        try {
            super.delete(cntDistribucionCentrocosto);
        } catch (Exception h) {
            throw h;
        }
    }

    public List<CntDistribucionCentrocosto> listaDistribucionCCPorDetalleComprobanteConFechaBaja(CntDetalleComprobante cntDetalleComprobante) {
        List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(
                "select j "
                + "from CntDistribucionCentrocosto j "
                + "where j.cntDetalleComprobante.idDetalleComprobante = '" + cntDetalleComprobante.getIdDetalleComprobante() + "' ");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void modificaDistribucionCentroDeCostoPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante, String usuarioLogueado, Date fechaAlta) throws Exception {
        List<CntDistribucionCentrocosto> listaDistribucionCC = obtieneListaDistribucionCentrocostoPorDetalleComprobante(cntDetalleComprobante);
        if (!listaDistribucionCC.isEmpty()) {
            modificaFechaYUsuarioDistribucionPorDistribucion(listaDistribucionCC.get(0), usuarioLogueado, fechaAlta);
            for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaDistribucionCC) {
                cntDistribucionCentrocosto.setFechaAlta(fechaAlta);
                cntDistribucionCentrocosto.setUsuarioAlta(usuarioLogueado);
                cntDistribucionCentrocosto.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                mergeCntDistribucionCentrocosto(cntDistribucionCentrocosto);
            }
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void modificaFechaYUsuarioDistribucionPorDistribucion(CntDistribucionCentrocosto cntDistribucionCentrocosto, String usuarioLogueado, Date fechaAlta) throws Exception {
        if (cntDistribucionCentrocosto.getCntDetalleComprobante().getIdAntecesor() != null) {
            CntDetalleComprobante cntDetalleComprobanteAntecesor = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDistribucionCentrocosto.getCntDetalleComprobante().getIdAntecesor());
            List<CntDistribucionCentrocosto> listaDistribucionModificados = listaDistribucionCCPorDetalleComprobanteConFechaBaja(cntDetalleComprobanteAntecesor);
            if (!listaDistribucionModificados.isEmpty()) {
                for (CntDistribucionCentrocosto cntDistribucionCentrocosto1 : listaDistribucionModificados) {
                    cntDistribucionCentrocosto1.setFechaModificacion(fechaAlta);
                    cntDistribucionCentrocosto1.setUsuarioModificacion(usuarioLogueado);
                    cntDistribucionCentrocosto1.setFechaBaja(fechaAlta);
                    cntDistribucionCentrocosto1.setUsuarioBaja(usuarioLogueado);
                    mergeCntDistribucionCentrocosto(cntDistribucionCentrocosto1);
                }
            }
            List<CntDetalleComprobante> listaDeHijosDeAntecesor = cntDetalleComprobanteService.listaHijosPorPadreParaDelete(cntDetalleComprobanteAntecesor);
            for (CntDetalleComprobante cntDetalleComprobante : listaDeHijosDeAntecesor) {
                List<CntDistribucionCentrocosto> listaDistribucionDeHijosModificados = listaDistribucionCCPorDetalleComprobanteConFechaBaja(cntDetalleComprobante);
                if (!listaDistribucionDeHijosModificados.isEmpty()) {
                    for (CntDistribucionCentrocosto cntDistribucionCentrocosto2 : listaDistribucionDeHijosModificados) {
                        cntDistribucionCentrocosto2.setFechaModificacion(fechaAlta);
                        cntDistribucionCentrocosto2.setUsuarioModificacion(usuarioLogueado);
                        cntDistribucionCentrocosto2.setFechaBaja(fechaAlta);
                        cntDistribucionCentrocosto2.setUsuarioBaja(usuarioLogueado);
                        mergeCntDistribucionCentrocosto(cntDistribucionCentrocosto2);
                    }
                }
            }
        }
    }

    public Boolean verificaValoresListaCentroCostos(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) {
        for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : listaCntConfiguracionCentrocosto) {
            if (cntConfiguracionCentrocosto.getPorcentaje() == null) {
                return false;
            }
        }
        return true;
    }

    public Boolean verificaRelacionCentroCostoConCntDistribucionCentrocosto(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntDistribucionCentrocosto h "
                    + "where h.cntCentroDeCosto.idEntidad=" + cntEntidad.getIdEntidad() + " and h.fechaBaja is null");
            return lista.isEmpty();
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean existeUnComprobanteConCentroDeCosto() {
        List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDistribucionCentrocosto j "
                + "where j.fechaBaja is null");
        return !lista.isEmpty();
    }
}
