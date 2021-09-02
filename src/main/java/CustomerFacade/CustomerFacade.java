package CustomerFacade;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author mathiasjensen
 */
public class CustomerFacade {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        CustomerFacade facade = CustomerFacade.getCustomerFacade(emf);
        
        Customer c1 = facade.addCustomer("Mathias", "Jensen");
        Customer c2 = facade.addCustomer("Mathias", "Filtenborg");
        Customer c3 = facade.addCustomer("August", "Duelund");
        
        System.out.println("Customer 1: " + facade.findCustomerbyID(c1.getId()).getFirstName());
        System.out.println("Customer 2: " + facade.findCustomerbyID(c2.getId()).getLastName());
        System.out.println("Number of customers " + facade.getAllCustomers().size());
    }

    private static EntityManagerFactory emf;
    private static CustomerFacade instance;

    private CustomerFacade() {
        
    }
    
    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }
    
    public Customer addCustomer(String firstName, String lastName){
        Customer customer = new Customer(firstName, lastName);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }
    }

    public Customer findCustomerbyID(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return customer;
        } finally {
            em.close();
        }
    }
    
    public List<Customer> getAllCustomers(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT customer FROM Customer customer", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
