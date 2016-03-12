
package com.bap.erp.modelo.entidades.cnf;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "PAR_CUENTAS_DE_AJUSTE",schema="CNF")
@NamedQueries({
    @NamedQuery(name = "ParCuentasDeAjuste.findAll", query = "SELECT p FROM ParCuentasDeAjuste p")})
public class ParCuentasDeAjuste implements Serializable {
   private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO", length = 4, nullable = false)
    private String codigo;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "VALOR")
    private String valor;
    @Column(name = "TIPO_DATO")
    private String tipoDato;

    public ParCuentasDeAjuste() {
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
