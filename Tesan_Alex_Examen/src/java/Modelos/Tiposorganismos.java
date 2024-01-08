/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alext
 */
@Entity
@Table(name = "tiposorganismos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiposorganismos.findAll", query = "SELECT t FROM Tiposorganismos t")
    , @NamedQuery(name = "Tiposorganismos.findByCodTipo", query = "SELECT t FROM Tiposorganismos t WHERE t.codTipo = :codTipo")
    , @NamedQuery(name = "Tiposorganismos.findByNomTipo", query = "SELECT t FROM Tiposorganismos t WHERE t.nomTipo = :nomTipo")})
public class Tiposorganismos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "cod_tipo")
    private String codTipo;
    @Size(max = 50)
    @Column(name = "nom_tipo")
    private String nomTipo;

    public Tiposorganismos() {
    }

    public Tiposorganismos(String codTipo) {
        this.codTipo = codTipo;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public String getNomTipo() {
        return nomTipo;
    }

    public void setNomTipo(String nomTipo) {
        this.nomTipo = nomTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipo != null ? codTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiposorganismos)) {
            return false;
        }
        Tiposorganismos other = (Tiposorganismos) object;
        if ((this.codTipo == null && other.codTipo != null) || (this.codTipo != null && !this.codTipo.equals(other.codTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Tiposorganismos[ codTipo=" + codTipo + " ]";
    }
    
}
