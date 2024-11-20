/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas;

import com.gestaorotas.exceptions.NonexistentEntityException;
import com.gestaorotas.model.Motoristas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author asus
 */
public class MotoristasJpaController implements Serializable {

    public MotoristasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Motoristas motoristas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(motoristas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Motoristas motoristas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            motoristas = em.merge(motoristas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = motoristas.getId();
                if (findMotoristas(id) == null) {
                    throw new NonexistentEntityException("The motoristas with id " + id + " no longer exists.");
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
            Motoristas motoristas;
            try {
                motoristas = em.getReference(Motoristas.class, id);
                motoristas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The motoristas with id " + id + " no longer exists.", enfe);
            }
            em.remove(motoristas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Motoristas> findMotoristasEntities() {
        return findMotoristasEntities(true, -1, -1);
    }

    public List<Motoristas> findMotoristasEntities(int maxResults, int firstResult) {
        return findMotoristasEntities(false, maxResults, firstResult);
    }

    private List<Motoristas> findMotoristasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Motoristas.class));
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

    public Motoristas findMotoristas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Motoristas.class, id);
        } finally {
            em.close();
        }
    }

    public int getMotoristasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Motoristas> rt = cq.from(Motoristas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
