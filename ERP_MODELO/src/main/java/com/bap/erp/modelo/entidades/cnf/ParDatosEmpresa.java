package com.bap.erp.modelo.entidades.cnf;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "PAR_DATOS_EMPRESA",schema="CNF")
@NamedQueries({
    @NamedQuery(name = "ParDatosEmpresa.findAll", query = "SELECT p FROM ParDatosEmpresa p")})
public class ParDatosEmpresa implements Serializable {
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO", length = 4, nullable = false)
    private String codigo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private String valor;
    @Column(name = "TIPO_DATO")
    private String tipoDato;

    public ParDatosEmpresa() {
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }
    
}
