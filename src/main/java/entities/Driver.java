package entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@NamedQuery(name = "driver.deleteAllRows", query = "DELETE from Driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String dummy;

    public Driver() {
    }

    public Driver(String dummy) {
        this.dummy = dummy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    
}
