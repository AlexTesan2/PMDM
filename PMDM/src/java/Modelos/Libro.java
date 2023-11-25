package Modelos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "libros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Libro.findAll", query = "SELECT l FROM Libro l")
    , @NamedQuery(name = "Libro.findByCodLibro", query = "SELECT l FROM Libro l WHERE l.codLibro = :codLibro")
    , @NamedQuery(name = "Libro.findByNomLibro", query = "SELECT l FROM Libro l WHERE l.nomLibro = :nomLibro")
    , @NamedQuery(name = "Libro.findByAno", query = "SELECT l FROM Libro l WHERE l.ano = :ano")
    , @NamedQuery(name = "Libro.findByPortada", query = "SELECT l FROM Libro l WHERE l.portada = :portada")
    , @NamedQuery(name = "Libro.findByNomArchivo", query = "SELECT l FROM Libro l WHERE l.nomArchivo = :nomArchivo")
    , @NamedQuery(name = "Libro.findByPelicula", query = "SELECT l FROM Libro l WHERE l.pelicula = :pelicula")
    , @NamedQuery(name = "Libro.findByPremio", query = "SELECT l FROM Libro l WHERE l.premioList = :elPremio")
    , @NamedQuery(name = "Libro.findByAutor2", query = "SELECT l FROM Libro l WHERE l.autorList = :elAutor")
    , @NamedQuery(name = "Libro.findByAutor", query = "SELECT l FROM Libro l WHERE l.autorList = :elAutor")})//nos inventamos lo que le damos, un objeto de la clase autor
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_libro")
    private Integer codLibro;
    @Size(max = 100)
    @Column(name = "nom_libro")
    private String nomLibro;
    @Column(name = "ano")
    private Integer ano;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "portada")
    private String portada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nom_archivo")
    private String nomArchivo;
    @Size(max = 30)
    @Column(name = "pelicula")
    private String pelicula;
    @Lob
    @Size(max = 16777215)
    @Column(name = "comentario")
    private String comentario;
    @JoinTable(name = "libro_premios", joinColumns = {
        @JoinColumn(name = "cod_libro", referencedColumnName = "cod_libro")}, inverseJoinColumns = {
        @JoinColumn(name = "cod_premio", referencedColumnName = "cod_premio")})
    @ManyToMany//para resolver la relación muchos a muchos aquí crea también una lista de premios
    private List<Premio> premioList;
    @JoinTable(name = "autor_libro", joinColumns = {
        @JoinColumn(name = "cod_libro", referencedColumnName = "cod_libro")}, inverseJoinColumns = {
        @JoinColumn(name = "cod_autor", referencedColumnName = "cod_autor")})
    @ManyToMany
    private List<Autor> autorList;

    public Libro() {
    }

    public Libro(Integer codLibro) {
        this.codLibro = codLibro;
    }

    public Libro(Integer codLibro, String portada, String nomArchivo) {
        this.codLibro = codLibro;
        this.portada = portada;
        this.nomArchivo = nomArchivo;
    }

    public Integer getCodLibro() {
        return codLibro;
    }

    public void setCodLibro(Integer codLibro) {
        this.codLibro = codLibro;
    }

    public String getNomLibro() {
        return nomLibro;
    }

    public void setNomLibro(String nomLibro) {
        this.nomLibro = nomLibro;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getNomArchivo() {
        return nomArchivo;
    }

    public void setNomArchivo(String nomArchivo) {
        this.nomArchivo = nomArchivo;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @XmlTransient
    public List<Premio> getPremioList() {
        return premioList;
    }

    public void setPremioList(List<Premio> premioList) {
        this.premioList = premioList;
    }

    @XmlTransient
    public List<Autor> getAutorList() {
        return autorList;
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codLibro != null ? codLibro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Libro)) {
            return false;
        }
        Libro other = (Libro) object;
        if ((this.codLibro == null && other.codLibro != null) || (this.codLibro != null && !this.codLibro.equals(other.codLibro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Libro[ codLibro=" + codLibro + " ]";
    }
}
