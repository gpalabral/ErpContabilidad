/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnf;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "PAR_PARAMETROS_GESTION",schema="CNF")
public class ParParametrosGestion implements Serializable {
    private static final long serialVersionUID = com.bap.erp.modelo.Erp.serialVersionIdErp;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public ParParametrosGestion() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
