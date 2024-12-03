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
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "corridas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Corridas.findAll", query = "SELECT c FROM Corridas c"),
    @NamedQuery(name = "Corridas.findById", query = "SELECT c FROM Corridas c WHERE c.id = :id"),
    @NamedQuery(name = "Corridas.findByDataHora", query = "SELECT c FROM Corridas c WHERE c.dataHora = :dataHora"),
    @NamedQuery(name = "Corridas.findByPontoPartida", query = "SELECT c FROM Corridas c WHERE c.pontoPartida = :pontoPartida"),
    @NamedQuery(name = "Corridas.findByDestino", query = "SELECT c FROM Corridas c WHERE c.destino = :destino"),
    @NamedQuery(name = "Corridas.findByPreco", query = "SELECT c FROM Corridas c WHERE c.preco = :preco"),
    @NamedQuery(name = "Corridas.findByStatus", query = "SELECT c FROM Corridas c WHERE c.status = :status")})
public class Corridas implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cliente_nome")
    private String clienteNome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pickup")
    private String pickup;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "destino")
    private String destino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "distancia")
    private double distancia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_estimado")
    private double valorEstimado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "status")
    private String status;
    @Column(name = "data_hora_solicitacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraSolicitacao;
    @JoinColumn(name = "motorista_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Motoristas motoristaId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ponto_partida")
    private String pontoPartida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "preco")
    private BigDecimal preco;
    @JoinColumn(name = "id_motorista", referencedColumnName = "id")
    @ManyToOne
    private Motoristas idMotorista;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne
    private Usuarios idCliente;

    public Corridas() {
    }

    public Corridas(Integer id) {
        this.id = id;
    }

    public Corridas(Integer id, Date dataHora, String pontoPartida, String destino, BigDecimal preco, String status) {
        this.id = id;
        this.dataHora = dataHora;
        this.pontoPartida = pontoPartida;
        this.destino = destino;
        this.preco = preco;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getPontoPartida() {
        return pontoPartida;
    }

    public void setPontoPartida(String pontoPartida) {
        this.pontoPartida = pontoPartida;
    }


    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }


    public Motoristas getIdMotorista() {
        return idMotorista;
    }

    public void setIdMotorista(Motoristas idMotorista) {
        this.idMotorista = idMotorista;
    }

    public Usuarios getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Usuarios idCliente) {
        this.idCliente = idCliente;
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
        if (!(object instanceof Corridas)) {
            return false;
        }
        Corridas other = (Corridas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestaorotas.model.Corridas[ id=" + id + " ]";
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(Date dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public Motoristas getMotoristaId() {
        return motoristaId;
    }

    public void setMotoristaId(Motoristas motoristaId) {
        this.motoristaId = motoristaId;
    }
    
}
