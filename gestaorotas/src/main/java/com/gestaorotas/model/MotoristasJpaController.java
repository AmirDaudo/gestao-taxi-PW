/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas.model;

import com.gestaorotas.model.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
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
        if (motoristas.getCorridasList() == null) {
            motoristas.setCorridasList(new ArrayList<Corridas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Corridas> attachedCorridasList = new ArrayList<Corridas>();
            for (Corridas corridasListCorridasToAttach : motoristas.getCorridasList()) {
                corridasListCorridasToAttach = em.getReference(corridasListCorridasToAttach.getClass(), corridasListCorridasToAttach.getId());
                attachedCorridasList.add(corridasListCorridasToAttach);
            }
            motoristas.setCorridasList(attachedCorridasList);
            em.persist(motoristas);
            for (Corridas corridasListCorridas : motoristas.getCorridasList()) {
                Motoristas oldIdMotoristaOfCorridasListCorridas = corridasListCorridas.getIdMotorista();
                corridasListCorridas.setIdMotorista(motoristas);
                corridasListCorridas = em.merge(corridasListCorridas);
                if (oldIdMotoristaOfCorridasListCorridas != null) {
                    oldIdMotoristaOfCorridasListCorridas.getCorridasList().remove(corridasListCorridas);
                    oldIdMotoristaOfCorridasListCorridas = em.merge(oldIdMotoristaOfCorridasListCorridas);
                }
            }
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
            Motoristas persistentMotoristas = em.find(Motoristas.class, motoristas.getId());
            List<Corridas> corridasListOld = persistentMotoristas.getCorridasList();
            List<Corridas> corridasListNew = motoristas.getCorridasList();
            List<Corridas> attachedCorridasListNew = new ArrayList<Corridas>();
            for (Corridas corridasListNewCorridasToAttach : corridasListNew) {
                corridasListNewCorridasToAttach = em.getReference(corridasListNewCorridasToAttach.getClass(), corridasListNewCorridasToAttach.getId());
                attachedCorridasListNew.add(corridasListNewCorridasToAttach);
            }
            corridasListNew = attachedCorridasListNew;
            motoristas.setCorridasList(corridasListNew);
            motoristas = em.merge(motoristas);
            for (Corridas corridasListOldCorridas : corridasListOld) {
                if (!corridasListNew.contains(corridasListOldCorridas)) {
                    corridasListOldCorridas.setIdMotorista(null);
                    corridasListOldCorridas = em.merge(corridasListOldCorridas);
                }
            }
            for (Corridas corridasListNewCorridas : corridasListNew) {
                if (!corridasListOld.contains(corridasListNewCorridas)) {
                    Motoristas oldIdMotoristaOfCorridasListNewCorridas = corridasListNewCorridas.getIdMotorista();
                    corridasListNewCorridas.setIdMotorista(motoristas);
                    corridasListNewCorridas = em.merge(corridasListNewCorridas);
                    if (oldIdMotoristaOfCorridasListNewCorridas != null && !oldIdMotoristaOfCorridasListNewCorridas.equals(motoristas)) {
                        oldIdMotoristaOfCorridasListNewCorridas.getCorridasList().remove(corridasListNewCorridas);
                        oldIdMotoristaOfCorridasListNewCorridas = em.merge(oldIdMotoristaOfCorridasListNewCorridas);
                    }
                }
            }
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
            List<Corridas> corridasList = motoristas.getCorridasList();
            for (Corridas corridasListCorridas : corridasList) {
                corridasListCorridas.setIdMotorista(null);
                corridasListCorridas = em.merge(corridasListCorridas);
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
