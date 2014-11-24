package pl.rw.jpadm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Customer extends BaseEntity<Integer,Customer> {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(length = 20)
    private String tin;
    @Column(length = 50)
    private String name;
    @OneToMany(mappedBy = "customer")
    private List<Contract> contracts = new ArrayList<Contract>();

    protected Customer() {
    }

    public Customer(String tin, String name) {
        this.tin = tin;
        this.name = name;
    }

    public Contract createContract(double amount) {
        Contract c = new Contract(new Date(), amount, this);
        contracts.add(c);
        return c;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", tin='" + tin + '\'' +
                ", name='" + name + '\'' +
                ", contracts=" + contracts +
                '}';
    }
}
