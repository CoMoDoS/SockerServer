package Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@NamedQuery(name="Admin.findByEmail", query = "SELECT A FROM Admin A where A.email LIKE :email")
public class Admin {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "incrementor")
    @GenericGenerator(name = "incrementor", strategy = "increment")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private  String email;

    @Column(name = "parola")
    private  String parola;

    @Column(name = "status")
    private String status;

    public Admin(){}

    public Admin(String name, String email, String parola, String status) {
        this.name = name;
        this.email = email;
        this.parola = parola;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", parola='" + parola + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
