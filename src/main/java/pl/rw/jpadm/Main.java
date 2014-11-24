package pl.rw.jpadm;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("example");
        EntityManager em = emf.createEntityManager();

        printCustomers(em);

        addCustomer(em, new Customer("60045", "ACME"));
        printCustomers(em);

        addContract(em, "60045", 10000.0);
        printCustomers(em);

        emitInvoices(em);
        emitInvoices(em);
        emitInvoices(em);
        printCustomers(em);

        closeContracts(em);
        printCustomers(em);
    }

    private static void addCustomer(EntityManager em, Customer c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    private static void addContract(EntityManager em, String tin, double amount) {
        em.getTransaction().begin();
        Customer customer = em
                .createQuery("from Customer where tin = :tin", Customer.class)
                .setParameter("tin", tin)
                .getSingleResult();
        Contract contract = customer.createContract(amount);
        em.persist(contract);
        em.getTransaction().commit();
    }

    private static void emitInvoices(EntityManager em) {
        em.getTransaction().begin();
        List<Contract> contracts = em
                .createQuery("from Contract", Contract.class)
                .getResultList();
        for (Contract contract : contracts) {
            Invoice invoice = contract.emitInvoice(1000.0);
            em.persist(invoice);
        }
        em.getTransaction().commit();
    }

    private static void closeContracts(EntityManager em) {
        em.getTransaction().begin();
        List<Contract> contracts = em
                .createQuery("from Contract", Contract.class)
                .getResultList();
        for (Contract contract : contracts)
            contract.close();
        em.getTransaction().commit();
    }

    private static void printCustomers(EntityManager em) {
        List<Customer> cs;
        cs = em.createQuery("from Customer", Customer.class).getResultList();
        System.out.println("RESULT");
        for (Customer ct : cs)
            System.out.println(ct);
        System.out.println("EOF");
    }
}
