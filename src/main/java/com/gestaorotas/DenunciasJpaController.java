/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas;

import com.gestaorotas.exceptions.NonexistentEntityException;
import com.gestaorotas.model.Denuncias;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author asus
 */
public class DenunciasJpaController implements Serializable {

    public DenunciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
public void create(Denuncias denuncias) {
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        Usuarios usuarioId = denuncias.getUsuarioId();
        if (usuarioId != null) {
            usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
            denuncias.setUsuarioId(usuarioId);
        }
        // Use merge em vez de persist
        em.merge(denuncias);
        if (usuarioId != null) {
            usuarioId.getDenunciasList().add(denuncias);
            usuarioId = em.merge(usuarioId);
        }
        em.getTransaction().commit();
    } finally {
        if (em != null) {
            em.close();
        }
    }
}

    public void edit(Denuncias denuncias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Denuncias persistentDenuncias = em.find(Denuncias.class, denuncias.getId());
            Usuarios usuarioIdOld = persistentDenuncias.getUsuarioId();
            Usuarios usuarioIdNew = denuncias.getUsuarioId();
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                denuncias.setUsuarioId(usuarioIdNew);
            }
            denuncias = em.merge(denuncias);
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getDenunciasList().remove(denuncias);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getDenunciasList().add(denuncias);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = denuncias.getId();
                if (findDenuncias(id) == null) {
                    throw new NonexistentEntityException("The denuncias with id " + id + " no longer exists.");
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
            Denuncias denuncias;
            try {
                denuncias = em.getReference(Denuncias.class, id);
                denuncias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The denuncias with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuarioId = denuncias.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getDenunciasList().remove(denuncias);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(denuncias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Denuncias> findDenunciasEntities() {
        return findDenunciasEntities(true, -1, -1);
    }

    public List<Denuncias> findDenunciasEntities(int maxResults, int firstResult) {
        return findDenunciasEntities(false, maxResults, firstResult);
    }

    private List<Denuncias> findDenunciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Denuncias.class));
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

    public Denuncias findDenuncias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Denuncias.class, id);
        } finally {
            em.close();
        }
    }

    public int getDenunciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Denuncias> rt = cq.from(Denuncias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
