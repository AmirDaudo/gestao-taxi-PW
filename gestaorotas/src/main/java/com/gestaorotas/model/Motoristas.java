/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
    @NamedQuery(name = "Motoristas.findByDisponibilidade", query = "SELECT m FROM Motoristas m WHERE m.disponibilidade = :disponibilidade"),
    @NamedQuery(name = "Motoristas.findByStatus", query = "SELECT m FROM Motoristas m WHERE m.status = :status"),
    @NamedQuery(name = "Motoristas.findByBloqueado", query = "SELECT m FROM Motoristas m WHERE m.bloqueado = :bloqueado")})
public class Motoristas implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 20)
    @Column(name = "telefone")
    private String telefone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "senha")
    private String senha;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "marca_carro")
    private String marcaCarro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "modelo_carro")
    private String modeloCarro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "disponibilidade")
    private String disponibilidade;
    @Size(max = 255)
    @Column(name = "foto1")
    private String foto1;
    @Size(max = 255)
    @Column(name = "foto2")
    private String foto2;
    @Size(max = 255)
    @Column(name = "foto3")
    private String foto3;
    @Size(max = 255)
    @Column(name = "foto4")
    private String foto4;
    @Size(max = 10)
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "motoristaId")
    private List<Corridas> corridasList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "bloqueado")
    private Boolean bloqueado;

    public Motoristas() {
    }

    public Motoristas(Integer id) {
        this.id = id;
    }

    public Motoristas(Integer id, String nome, String telefone, String email, String senha, String marcaCarro, String modeloCarro, String matricula, String disponibilidade, String status) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.marcaCarro = marcaCarro;
        this.modeloCarro = modeloCarro;
        this.matricula = matricula;
        this.disponibilidade = disponibilidade;
        this.status = status;
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


    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
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

    public Object getCorridasList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
/*
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
*/
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

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public void setFoto3(String foto3) {
        this.foto3 = foto3;
    }

    public String getFoto4() {
        return foto4;
    }

    public void setFoto4(String foto4) {
        this.foto4 = foto4;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
/*
    @XmlTransient
    public List<Corridas> getCorridasList() {
        return corridasList;
    }

    public void setCorridasList(List<Corridas> corridasList) {
        this.corridasList = corridasList;
    }
    */
}
