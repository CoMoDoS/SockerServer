package Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "related")
@NamedQuery(name="Related.findByIdFrom", query = "FROM Related as cas")
public class Related
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "incrementor")
    @GenericGenerator(name = "incrementor", strategy = "increment")
    private int id;

    @Column(name = "id_from")
    private int idFrom;

    @Column(name = "id_to")
    private int idTo;
    public Related(){}

    public Related(int idFrom, int idTo) {
        this.idFrom = idFrom;
        this.idTo = idTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(int idFrom) {
        this.idFrom = idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }

    @Override
    public String toString() {
        return "Related{" +
                "id=" + id +
                ", idFrom=" + idFrom +
                ", idTo=" + idTo +
                '}';
    }
}
