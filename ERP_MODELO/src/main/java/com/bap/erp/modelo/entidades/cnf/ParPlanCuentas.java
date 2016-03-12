
package com.bap.erp.modelo.entidades.cnf;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PAR_PLAN_CUENTAS",schema="CNF")
public class ParPlanCuentas implements Serializable {
 private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @Basic(optional = false)
    @Column(name = "id_entidad")
    private long idEntidad;
    @Basic(optional = false)
    @Column(name = "mascara_generada")
    private String mascaraGenerada;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "nivel")
    private int nivel;

    public ParPlanCuentas() {
    }

    public long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getMascaraGenerada() {
        return mascaraGenerada;
    }

    public void setMascaraGenerada(String mascaraGenerada) {
        this.mascaraGenerada = mascaraGenerada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
}
