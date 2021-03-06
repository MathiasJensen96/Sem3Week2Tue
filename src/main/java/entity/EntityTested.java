/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mathiasjensen
 */
public class EntityTested {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        try {
            Customer c1 = new Customer("Mathias", "Jensen");
            Customer c2 = new Customer("Mathias", "Filtenborg");

            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }
}