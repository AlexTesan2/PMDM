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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alext
 */
@Entity
@Table(name = "paises")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paises.findAll", query = "SELECT p FROM Paises p")
    , @NamedQuery(name = "Paises.findByCodigo", query = "SELECT p FROM Paises p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Paises.findByCodPais", query = "SELECT p FROM Paises p WHERE p.codPais = :codPais")
    , @NamedQuery(name = "Paises.findByCodPaisTelefono", query = "SELECT p FROM Paises p WHERE p.codPaisTelefono = :codPaisTelefono")
    , @NamedQuery(name = "Paises.findByNomPais", query = "SELECT p FROM Paises p WHERE p.nomPais = :nomPais")
    , @NamedQuery(name = "Paises.findByNomPaisIngles", query = "SELECT p FROM Paises p WHERE p.nomPaisIngles = :nomPaisIngles")
    , @NamedQuery(name = "Paises.findBySubContinente", query = "SELECT p FROM Paises p WHERE p.subContinente = :subContinente")
    , @NamedQuery(name = "Paises.findByCodRegion", query = "SELECT p FROM Paises p WHERE p.codRegion = :codRegion")})
public class Paises implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Size(max = 2)
    @Column(name = "cod_pais")
    private String codPais;
    @Size(max = 4)
    @Column(name = "cod_pais_telefono")
    private String codPaisTelefono;
    @Size(max = 100)
    @Column(name = "nom_pais")
    private String nomPais;
    @Size(max = 255)
    @Column(name = "nom_pais_ingles")
    private String nomPaisIngles;
    @Size(max = 3)
    @Column(name = "sub_continente")
    private String subContinente;
    @Column(name = "cod_region")
    private Integer codRegion;

    public Paises() {
    }

    public Paises(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getCodPaisTelefono() {
        return codPaisTelefono;
    }

    public void setCodPaisTelefono(String codPaisTelefono) {
        this.codPaisTelefono = codPaisTelefono;
    }

    public String getNomPais() {
        return nomPais;
    }

    public void setNomPais(String nomPais) {
        this.nomPais = nomPais;
    }

    public String getNomPaisIngles() {
        return nomPaisIngles;
    }

    public void setNomPaisIngles(String nomPaisIngles) {
        this.nomPaisIngles = nomPaisIngles;
    }

    public String getSubContinente() {
        return subContinente;
    }

    public void setSubContinente(String subContinente) {
        this.subContinente = subContinente;
    }

    public Integer getCodRegion() {
        return codRegion;
    }

    public void setCodRegion(Integer codRegion) {
        this.codRegion = codRegion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paises)) {
            return false;
        }
        Paises other = (Paises) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Paises[ codigo=" + codigo + " ]";
    }
    
}
