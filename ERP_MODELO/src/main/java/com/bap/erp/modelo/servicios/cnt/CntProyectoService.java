package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import java.util.List;

public interface CntProyectoService extends GenericDao<CntProyecto> {

    CntProyecto persistCntProyecto(CntProyecto cntProyecto) throws Exception;

    CntProyecto mergeCntProyecto(CntProyecto cntProyecto) throws Exception;

    void removeCntProyectos(CntProyecto cntProyecto) throws Exception;

    List<CntProyecto> listaCntProyectosOrdenados() throws Exception;

    CntProyecto findCntProyectos(Long idProyecto);

    String generaEspaciosEnDescripcionNivelesSubAndPadre(CntProyecto cntProyecto);

    String generaNumeroSiguienteAutomatico(CntProyecto cntProyecto, String nivel) throws Exception;

    String[] obtieneMascaraSeparada(CntProyecto cntProyecto, String tipoNivel);

    int controlaLongitudNumero(CntProyecto cntProyecto, String tipo);

    String datosRepetidos(CntProyecto cntProyecto);

    Boolean verificaExistenciaDeProyectos(List<CntProyecto> listaProyectos, CntProyecto proyectoSeleccionado);

    Boolean verificaExistenciaDeProyectosParaCrear();

    String datosRepetidosModificacion(CntProyecto cntProyecto);

    Boolean verificaProyectoDetalleComprobante(CntProyecto cntProyecto);

    List<CntProyecto> listaHijosProyecto(CntProyecto cntProyecto) throws Exception;

    void removeProyectosMasHijos(CntProyecto cntProyecto) throws Exception;

    Boolean existeProyecto();

    CntProyecto obtieneCntProyecto(Long idProyecto) throws Exception;
}
