/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas;

import com.gestaorotas.exceptions.NonexistentEntityException;
import com.gestaorotas.model.Corridas;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.gestaorotas.model.Motoristas;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author asus
 */
public class CorridasJpaController implements Serializable {

    public CorridasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Corridas corridas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Motoristas idMotorista = corridas.getIdMotorista();
            if (idMotorista != null) {
                idMotorista = em.getReference(idMotorista.getClass(), idMotorista.getId());
                corridas.setIdMotorista(idMotorista);
            }
            Usuarios idCliente = corridas.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getId());
                corridas.setIdCliente(idCliente);
            }
            em.persist(corridas);
            if (idMotorista != null) {
                idMotorista.getCorridasList().add(corridas);
                idMotorista = em.merge(idMotorista);
            }
            if (idCliente != null) {
                idCliente.getCorridasList().add(corridas);
                idCliente = em.merge(idCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Corridas corridas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corridas persistentCorridas = em.find(Corridas.class, corridas.getId());
            Motoristas idMotoristaOld = persistentCorridas.getIdMotorista();
            Motoristas idMotoristaNew = corridas.getIdMotorista();
            Usuarios idClienteOld = persistentCorridas.getIdCliente();
            Usuarios idClienteNew = corridas.getIdCliente();
            if (idMotoristaNew != null) {
                idMotoristaNew = em.getReference(idMotoristaNew.getClass(), idMotoristaNew.getId());
                corridas.setIdMotorista(idMotoristaNew);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getId());
                corridas.setIdCliente(idClienteNew);
            }
            corridas = em.merge(corridas);
            if (idMotoristaOld != null && !idMotoristaOld.equals(idMotoristaNew)) {
                idMotoristaOld.getCorridasList().remove(corridas);
                idMotoristaOld = em.merge(idMotoristaOld);
            }
            if (idMotoristaNew != null && !idMotoristaNew.equals(idMotoristaOld)) {
                idMotoristaNew.getCorridasList().add(corridas);
                idMotoristaNew = em.merge(idMotoristaNew);
            }
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getCorridasList().remove(corridas);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getCorridasList().add(corridas);
                idClienteNew = em.merge(idClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = corridas.getId();
                if (findCorridas(id) == null) {
                    throw new NonexistentEntityException("The corridas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corridas corridas;
            try {
                corridas = em.getReference(Corridas.class, id);
                corridas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The corridas with id " + id + " no longer exists.", enfe);
            }
            Motoristas idMotorista = corridas.getIdMotorista();
            if (idMotorista != null) {
                idMotorista.getCorridasList().remove(corridas);
                idMotorista = em.merge(idMotorista);
            }
            Usuarios idCliente = corridas.getIdCliente();
            if (idCliente != null) {
                idCliente.getCorridasList().remove(corridas);
                idCliente = em.merge(idCliente);
            }
            em.remove(corridas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Corridas> findCorridasEntities() {
        return findCorridasEntities(true, -1, -1);
    }

    public List<Corridas> findCorridasEntities(int maxResults, int firstResult) {
        return findCorridasEntities(false, maxResults, firstResult);
    }

    private List<Corridas> findCorridasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Corridas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Corridas findCorridas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Corridas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCorridasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Corridas> rt = cq.from(Corridas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
