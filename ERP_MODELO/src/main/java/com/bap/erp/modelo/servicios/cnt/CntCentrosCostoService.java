/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;

public interface CntCentrosCostoService extends GenericDao<CntEntidad> {

    String generaCodigoConCeros(int numero, int tamanio);

    String generaCodigoNivelesSubAndPadre(CntEntidad cntCentrosCosto, String tipo);

    int obtieneNiveleCuentaSubAndPadre(CntEntidad cntCentrosCosto, String tipo);

    int controlaLongitudNumero(CntEntidad cntCentrosCosto, String tipo);

    String generaNumeroSiguiente(CntEntidad cntCentrosCosto, String tipo);

    String verificaExistenciaCodigo(String centroCosto);

    String generaCodigoNiveleAndSubNivel(CntEntidad cntCentrosCosto, String tipo, String codigo);

    boolean verificaExistenciaMascaraNivelAndSubNivel(String centroCosto);

    boolean activaTipoMovimientoCombo(CntEntidad cntCentrosCosto);

    boolean verificaExistenciaMascara(CntEntidad cntCentrosCosto);

    CntEntidad persistCntCentroCostoNivelAndSubNivel(CntEntidad cntCentrosCosto) throws Exception;

    CntEntidad mergeCntCentroCostoNivelAndSubNivel(CntEntidad cntCentrosCosto) throws Exception;
    
    CntEntidad removeCntCentroCostoNivelAndSubNivel(CntEntidad cntCentrosCosto) throws Exception;

    String[] allDefinitionCharacterOrNumberCentroCosto(CntEntidad cntCentrosCosto);

    boolean isCharacterCntCentroCosto(CntEntidad cntCentrosCosto, String tipo);

    String generaCodigoConCerosString(String codigo, int tamanio);

    String generaCodigoNiveleInicial();

    String[] obtieneMascaraSeparada(CntEntidad cntEntidad, String tipoNivel);

    String generaNumeroSiguienteAutomatico(CntEntidad cntEntidad, String nivel);
    
    CntEntidad verificaSiEsPadre(CntEntidad centroDeCosto, String nivel, Long idPadre);
    
    public Boolean verificaExistenciaCentrosDeCosto();
    
    public String[] construyeMascaraInicialNivelYSubNivel(String mascara, String nivel);
    
    public String convierteMascaraConCeros(String mascaraOriginal);
    
    Boolean verificaMascara(String mascaraCC);
    
    Boolean verificaMascaraVacio(String mascaraCC);
    
}