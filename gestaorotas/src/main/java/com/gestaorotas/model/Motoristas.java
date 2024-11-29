package com.gestaorotas.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "motoristas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Motoristas.findAll", query = "SELECT m FROM Motoristas m"),
    @NamedQuery(name = "Motoristas.findById", query = "SELECT m FROM Motoristas m WHERE m.id = :id"),
    @NamedQuery(name = "Motoristas.findByNome", query = "SELECT m FROM Motoristas m WHERE m.nome = :nome"),
    @NamedQuery(name = "Motoristas.findByTelefone", query = "SELECT m FROM Motoristas m WHERE m.telefone = :telefone"),
    @NamedQuery(name = "Motoristas.findByEmail", query = "SELECT m FROM Motoristas m WHERE m.email = :email"),
    @NamedQuery(name = "Motoristas.findBySenha", query = "SELECT m FROM Motoristas m WHERE m.senha = :senha"),
    @NamedQuery(name = "Motoristas.findByMarcaCarro", query = "SELECT m FROM Motoristas m WHERE m.marcaCarro = :marcaCarro"),
    @NamedQuery(name = "Motoristas.findByModeloCarro", query = "SELECT m FROM Motoristas m WHERE m.modeloCarro = :modeloCarro"),
    @NamedQuery(name = "Motoristas.findByMatricula", query = "SELECT m FROM Motoristas m WHERE m.matricula = :matricula"),
    @NamedQuery(name = "Motoristas.findByDisponibilidade", query = "SELECT m FROM Motoristas m WHERE m.disponibilidade = :disponibilidade")
})
public class Motoristas implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "telefone")
    private String telefone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "senha")
    private String senha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "marca_carro")
    private String marcaCarro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "modelo_carro")
    private String modeloCarro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "disponibilidade")
    private String disponibilidade;
    
    @Basic(optional = true)
    @Column(name = "foto1", columnDefinition = "LONGBLOB")
    private byte[] foto1;
    
    @Basic(optional = true)
    @Column(name = "foto2", columnDefinition = "LONGBLOB")
    private byte[] foto2;
    
    @Basic(optional = true)
    @Column(name = "foto3", columnDefinition = "LONGBLOB")
    private byte[] foto3;
    
    @Basic(optional = true)
    @Column(name = "foto4", columnDefinition = "LONGBLOB")
    private byte[] foto4;
    
    @OneToMany(mappedBy = "idMotorista")
    private List<Corridas> corridasList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Motoristas() {
    }

    public Motoristas(Integer id) {
        this.id = id;
    }

    public Motoristas(Integer id, String nome, String telefone, String email, String senha, String marcaCarro, String modeloCarro, String matricula, String disponibilidade) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.marcaCarro = marcaCarro;
        this.modeloCarro = modeloCarro;
        this.matricula = matricula;
        this.disponibilidade = disponibilidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarcaCarro() {
        return marcaCarro;
    }

    public void setMarcaCarro(String marcaCarro) {
        this.marcaCarro = marcaCarro;
    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    public byte[] getFoto1() {
        return foto1;
    }

    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }

    public byte[] getFoto2() {
        return foto2;
    }

    public void setFoto2(byte[] foto2) {
        this.foto2 = foto2;
    }

    public byte[] getFoto3() {
        return foto3;
    }

    public void setFoto3(byte[] foto3) {
        this.foto3 = foto3;
    }

    public byte[] getFoto4() {
        return foto4;
    }

    public void setFoto4(byte[] foto4) {
        this.foto4 = foto4;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Motoristas)) {
            return false;
        }
        Motoristas other = (Motoristas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaorotas.model.Motoristas[ id=" + id + " ]";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    @XmlTransient
    public List<Corridas> getCorridasList() {
        return corridasList;
    }

    public void setCorridasList(List<Corridas> corridasList) {
        this.corridasList = corridasList;
    }
    
}
