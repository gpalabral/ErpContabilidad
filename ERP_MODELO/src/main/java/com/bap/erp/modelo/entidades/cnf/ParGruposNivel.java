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
 * @author HENRRY
 */
@Entity
@Table(name = "PAR_GRUPOS_NIVEL",schema="CNF")
@NamedQueries({
    @NamedQuery(name = "ParGruposNivel.findAll", query = "SELECT p FROM ParGruposNivel p")})
public class ParGruposNivel implements Serializable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO", length = 4, nullable = false)
    private String codigo;
    @Column(name = "DESCRIPCION",length=100,nullable=false)
    private String descripcion;

    public ParGruposNivel() {
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
