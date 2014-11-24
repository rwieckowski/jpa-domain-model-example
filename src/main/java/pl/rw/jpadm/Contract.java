package pl.rw.jpadm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Contract extends BaseEntity<Integer,Contract> {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private Date start;
    @Column
    private double amount;
    @Column
    private boolean active;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "contract")
    private List<Invoice> invoices = new ArrayList<Invoice>();

    protected Contract() {
    }

    public Contract(Date start, double amount, Customer customer) {
        this.start = start;
        this.amount = amount;
        this.customer = customer;
        this.active = true;
    }

    public Invoice emitInvoice(double amount) {
        this.amount -= amount;
        Invoice invoice = new Invoice(new Date(), amount, this);
        invoices.add(invoice);
        return invoice;
    }

    void removeInvoice(Invoice invoice) {
        amount += invoice.amount;
        invoices.remove(invoice);
    }

    public void close() {
        active = false;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", start=" + start +
                ", amount=" + amount +
                ", active=" + active +
                ", customer=" + customer.id() +
                ", invoices=" + invoices +
                '}';
    }
}
