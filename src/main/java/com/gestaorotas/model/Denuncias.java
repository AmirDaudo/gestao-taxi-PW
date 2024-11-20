/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "denuncias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Denuncias.findAll", query = "SELECT d FROM Denuncias d"),
    @NamedQuery(name = "Denuncias.findById", query = "SELECT d FROM Denuncias d WHERE d.id = :id"),
    @NamedQuery(name = "Denuncias.findByUsuarioNome", query = "SELECT d FROM Denuncias d WHERE d.usuarioNome = :usuarioNome"),
    @NamedQuery(name = "Denuncias.findByUsuarioEmail", query = "SELECT d FROM Denuncias d WHERE d.usuarioEmail = :usuarioEmail"),
    @NamedQuery(name = "Denuncias.findByData", query = "SELECT d FROM Denuncias d WHERE d.data = :data")})
public class Denuncias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "usuarioNome")
    private String usuarioNome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "usuarioEmail")
    private String usuarioEmail;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "mensagem")
    private String mensagem;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "usuarioId", referencedColumnName = "id")
    @ManyToOne
    private Usuarios usuarioId;

    public Denuncias() {
    }

    public Denuncias(Integer id) {
        this.id = id;
    }

    public Denuncias(Integer id, String usuarioNome, String usuarioEmail, String mensagem) {
        this.id = id;
        this.usuarioNome = usuarioNome;
        this.usuarioEmail = usuarioEmail;
        this.mensagem = mensagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuarios getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuarios usuarioId) {
        this.usuarioId = usuarioId;
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
        if (!(object instanceof Denuncias)) {
            return false;
        }
        Denuncias other = (Denuncias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaorotas.model.Denuncias[ id=" + id + " ]";
    }
    
}
