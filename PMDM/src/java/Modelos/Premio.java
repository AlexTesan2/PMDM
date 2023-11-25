package Modelos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "premios")
@XmlRootElement
@NamedQueries({//aquí crea consultas básicas y a cada una le da un nombre, es un SQL un poco especial.
    @NamedQuery(name = "Premio.findAll", query = "SELECT p FROM Premio p")
    //en esta sentencia cuando quieres recibir un parámetro pones :, el codPremio te lo inventas tú y luego te tienes que acordar;
    , @NamedQuery(name = "Premio.findAllOrdered", query = "SELECT p FROM Premio p ORDER BY p.nomPremio")
    , @NamedQuery(name = "Premio.findAllOrderedL", query = "SELECT p FROM Premio p WHERE p.tipo = 'L' ORDER BY p.nomPremio")
    , @NamedQuery(name = "Premio.findAllOrderedA", query = "SELECT p FROM Premio p WHERE p.tipo = 'A' ORDER BY p.nomPremio")
    , @NamedQuery(name = "Premio.findByCodPremio", query = "SELECT p FROM Premio p WHERE p.codPremio = :codPremio")
    , @NamedQuery(name = "Premio.findByTipo", query = "SELECT p FROM Premio p WHERE p.tipo = :tipo")})
public class Premio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_premio")//en la tabla se llama cod_premio
    private Integer codPremio;//por defecto está encapsulado (private) y no es int sino Integer
    @Basic(optional = false)
    @NotNull // no admite nulos
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "nom_premio")
    private String nomPremio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private Character tipo;
    //relación muchos a muchos tabla premios y libros
    @ManyToMany(mappedBy = "premioList")
    /*
    crea una lista de objetos de la clase libro en lugar de crear una tabla intermedia
    es la manera de resolver la relaición muchos a muchos.
    En la tabla de libros habrá una lista de premios.
    */
    private List<Libro> libroList;
    @JoinColumn(name = "cod_pais", referencedColumnName = "cod_pais")
    //en este caso la relación es que el premio es de un país.
    //por esa razón, es un atributo con un objeto de la clase Pais
    //no es un entero
    @ManyToOne(optional = false)
    private Pais codPais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "premio")
    private List<AutorPremio> autorPremioList;

    public Premio() {
    }

    public Premio(Integer codPremio) {
        this.codPremio = codPremio;
    }

    public Premio(Integer codPremio, String nomPremio, Character tipo) {
        this.codPremio = codPremio;
        this.nomPremio = nomPremio;
        this.tipo = tipo;
    }

    public Integer getCodPremio() {
        return codPremio;
    }

    public void setCodPremio(Integer codPremio) {
        this.codPremio = codPremio;
    }

    public String getNomPremio() {
        return nomPremio;
    }

    public void setNomPremio(String nomPremio) {
        this.nomPremio = nomPremio;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Libro> getLibroList() {
        return libroList;
    }

    public void setLibroList(List<Libro> libroList) {
        this.libroList = libroList;
    }

    public Pais getCodPais() {
        return codPais;
    }

    public void setCodPais(Pais codPais) {
        this.codPais = codPais;
    }

    @XmlTransient
    public List<AutorPremio> getAutorPremioList() {
        return autorPremioList;
    }

    public void setAutorPremioList(List<AutorPremio> autorPremioList) {
        this.autorPremioList = autorPremioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPremio != null ? codPremio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Premio)) {
            return false;
        }
        Premio other = (Premio) object;
        if ((this.codPremio == null && other.codPremio != null) || (this.codPremio != null && !this.codPremio.equals(other.codPremio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Premio[ codPremio=" + codPremio + " ]";
    }
    
}
