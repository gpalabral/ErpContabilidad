package com.bap.erp.modelo.entidades.cnf;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "PAR_TIPOS_MOVIMIENTO", schema="CNF")
@NamedQueries({
    @NamedQuery(name = "ParTiposMovimiento.findAll", query = "SELECT p FROM ParTiposMovimiento p")})
public class ParTiposMovimiento implements Serializable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO",nullable=false)
    private String codigo;
    @Column(name = "DESCRIPCION",nullable=false)
    private String descripcion;

    public ParTiposMovimiento() {
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
