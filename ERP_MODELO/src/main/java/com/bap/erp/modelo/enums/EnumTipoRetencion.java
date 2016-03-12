/*,
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;
/**
 *
 * @author Administrador
 */
public enum EnumTipoRetencion {

    SIN_RETENCION("SRET"),
    BIENES("BIEN"),
    SERVICIOS("SERV"),
    RC_IVA("RIVA"),
    ALQUILERES("ALQU"),
    REMESAS_AL_EXTERIOR("REXT"),
    IUE_IT_IND_EXPORTADOR("IUIT");
    private String codigo;

    private EnumTipoRetencion(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
