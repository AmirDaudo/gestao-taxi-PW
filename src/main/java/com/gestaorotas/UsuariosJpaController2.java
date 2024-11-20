/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas;

import com.gestaorotas.exceptions.NonexistentEntityException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.gestaorotas.model.Corridas;
import java.util.ArrayList;
import java.util.List;
import com.gestaorotas.model.Denuncias;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author asus
 */
public class UsuariosJpaController2 implements Serializable {

    public UsuariosJpaController2(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getCorridasList() == null) {
            usuarios.setCorridasList(new ArrayList<Corridas>());
        }
        if (usuarios.getDenunciasList() == null) {
            usuarios.setDenunciasList(new ArrayList<Denuncias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Corridas> attachedCorridasList = new ArrayList<Corridas>();
            for (Corridas corridasListCorridasToAttach : usuarios.getCorridasList()) {
                corridasListCorridasToAttach = em.getReference(corridasListCorridasToAttach.getClass(), corridasListCorridasToAttach.getId());
                attachedCorridasList.add(corridasListCorridasToAttach);
            }
            usuarios.setCorridasList(attachedCorridasList);
            List<Denuncias> attachedDenunciasList = new ArrayList<Denuncias>();
            for (Denuncias denunciasListDenunciasToAttach : usuarios.getDenunciasList()) {
                denunciasListDenunciasToAttach = em.getReference(denunciasListDenunciasToAttach.getClass(), denunciasListDenunciasToAttach.getId());
                attachedDenunciasList.add(denunciasListDenunciasToAttach);
            }
            usuarios.setDenunciasList(attachedDenunciasList);
            em.persist(usuarios);
            for (Corridas corridasListCorridas : usuarios.getCorridasList()) {
                Usuarios oldIdClienteOfCorridasListCorridas = corridasListCorridas.getIdCliente();
                corridasListCorridas.setIdCliente(usuarios);
                corridasListCorridas = em.merge(corridasListCorridas);
                if (oldIdClienteOfCorridasListCorridas != null) {
                    oldIdClienteOfCorridasListCorridas.getCorridasList().remove(corridasListCorridas);
                    oldIdClienteOfCorridasListCorridas = em.merge(oldIdClienteOfCorridasListCorridas);
                }
            }
            for (Denuncias denunciasListDenuncias : usuarios.getDenunciasList()) {
                Usuarios oldUsuarioIdOfDenunciasListDenuncias = denunciasListDenuncias.getUsuarioId();
                denunciasListDenuncias.setUsuarioId(usuarios);
                denunciasListDenuncias = em.merge(denunciasListDenuncias);
                if (oldUsuarioIdOfDenunciasListDenuncias != null) {
                    oldUsuarioIdOfDenunciasListDenuncias.getDenunciasList().remove(denunciasListDenuncias);
                    oldUsuarioIdOfDenunciasListDenuncias = em.merge(oldUsuarioIdOfDenunciasListDenuncias);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            List<Corridas> corridasListOld = persistentUsuarios.getCorridasList();
            List<Corridas> corridasListNew = usuarios.getCorridasList();
            List<Denuncias> denunciasListOld = persistentUsuarios.getDenunciasList();
            List<Denuncias> denunciasListNew = usuarios.getDenunciasList();
            List<Corridas> attachedCorridasListNew = new ArrayList<Corridas>();
            for (Corridas corridasListNewCorridasToAttach : corridasListNew) {
                corridasListNewCorridasToAttach = em.getReference(corridasListNewCorridasToAttach.getClass(), corridasListNewCorridasToAttach.getId());
                attachedCorridasListNew.add(corridasListNewCorridasToAttach);
            }
            corridasListNew = attachedCorridasListNew;
            usuarios.setCorridasList(corridasListNew);
            List<Denuncias> attachedDenunciasListNew = new ArrayList<Denuncias>();
            for (Denuncias denunciasListNewDenunciasToAttach : denunciasListNew) {
                denunciasListNewDenunciasToAttach = em.getReference(denunciasListNewDenunciasToAttach.getClass(), denunciasListNewDenunciasToAttach.getId());
                attachedDenunciasListNew.add(denunciasListNewDenunciasToAttach);
            }
            denunciasListNew = attachedDenunciasListNew;
            usuarios.setDenunciasList(denunciasListNew);
            usuarios = em.merge(usuarios);
            for (Corridas corridasListOldCorridas : corridasListOld) {
                if (!corridasListNew.contains(corridasListOldCorridas)) {
                    corridasListOldCorridas.setIdCliente(null);
                    corridasListOldCorridas = em.merge(corridasListOldCorridas);
                }
            }
            for (Corridas corridasListNewCorridas : corridasListNew) {
                if (!corridasListOld.contains(corridasListNewCorridas)) {
                    Usuarios oldIdClienteOfCorridasListNewCorridas = corridasListNewCorridas.getIdCliente();
                    corridasListNewCorridas.setIdCliente(usuarios);
                    corridasListNewCorridas = em.merge(corridasListNewCorridas);
                    if (oldIdClienteOfCorridasListNewCorridas != null && !oldIdClienteOfCorridasListNewCorridas.equals(usuarios)) {
                        oldIdClienteOfCorridasListNewCorridas.getCorridasList().remove(corridasListNewCorridas);
                        oldIdClienteOfCorridasListNewCorridas = em.merge(oldIdClienteOfCorridasListNewCorridas);
                    }
                }
            }
            for (Denuncias denunciasListOldDenuncias : denunciasListOld) {
                if (!denunciasListNew.contains(denunciasListOldDenuncias)) {
                    denunciasListOldDenuncias.setUsuarioId(null);
                    denunciasListOldDenuncias = em.merge(denunciasListOldDenuncias);
                }
            }
            for (Denuncias denunciasListNewDenuncias : denunciasListNew) {
                if (!denunciasListOld.contains(denunciasListNewDenuncias)) {
                    Usuarios oldUsuarioIdOfDenunciasListNewDenuncias = denunciasListNewDenuncias.getUsuarioId();
                    denunciasListNewDenuncias.setUsuarioId(usuarios);
                    denunciasListNewDenuncias = em.merge(denunciasListNewDenuncias);
                    if (oldUsuarioIdOfDenunciasListNewDenuncias != null && !oldUsuarioIdOfDenunciasListNewDenuncias.equals(usuarios)) {
                        oldUsuarioIdOfDenunciasListNewDenuncias.getDenunciasList().remove(denunciasListNewDenuncias);
                        oldUsuarioIdOfDenunciasListNewDenuncias = em.merge(oldUsuarioIdOfDenunciasListNewDenuncias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<Corridas> corridasList = usuarios.getCorridasList();
            for (Corridas corridasListCorridas : corridasList) {
                corridasListCorridas.setIdCliente(null);
                corridasListCorridas = em.merge(corridasListCorridas);
            }
            List<Denuncias> denunciasList = usuarios.getDenunciasList();
            for (Denuncias denunciasListDenuncias : denunciasList) {
                denunciasListDenuncias.setUsuarioId(null);
                denunciasListDenuncias = em.merge(denunciasListDenuncias);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
