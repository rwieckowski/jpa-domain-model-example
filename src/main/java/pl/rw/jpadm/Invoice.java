package pl.rw.jpadm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity
public class Invoice extends BaseEntity<Integer,Invoice> {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private Date maturityDate;
    @Column
    double amount;
    @ManyToOne
    Contract contract;

    protected Invoice() {
    }

    public Invoice(Date maturityDate, double amount, Contract contract) {
        this.maturityDate = maturityDate;
        this.amount = amount;
        this.contract = contract;
    }

    public void remove() {
        contract.removeInvoice(this);
        contract = null;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public boolean sameAs(Invoice other) {
        return Objects.equals(id, this.id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", maturityDate=" + maturityDate +
                ", amount=" + amount +
                ", contract=" + (contract != null ? contract.id() : "null") +
                '}';
    }
}
