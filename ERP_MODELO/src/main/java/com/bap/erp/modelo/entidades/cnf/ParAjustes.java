
package com.bap.erp.modelo.entidades.cnf;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "PAR_AJUSTES", schema="CNF")

public class ParAjustes implements Serializable {
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO", nullable=false)
    private String codigo;
    @Column(name = "DESCRIPCION", nullable=false)
    private String descripcion;
    @Column(name = "TIPO_DATO")
    private String tipoDato;

    public ParAjustes() {
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

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }
    
}
