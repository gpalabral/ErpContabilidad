/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnf;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "PAR_SEGUNDA_MONEDA", schema="CNF")
public class ParSegundaMoneda implements Serializable {
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public ParSegundaMoneda() {
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
