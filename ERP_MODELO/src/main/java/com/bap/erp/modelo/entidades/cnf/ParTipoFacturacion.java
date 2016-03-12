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
 * @author Administrator
 */
@Entity
@Table(name = "PAR_TIPO_FACTURACION", schema="CNF")
public class ParTipoFacturacion implements Serializable {
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "CODIGO", nullable=false)
    private String codigo;    
    @Column(name = "DESCRIPCION", nullable=false)
    private String descripcion;

    public ParTipoFacturacion() {
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
