package csulb.cecs323.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "AUTHORING_ENTITY_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
@Table(name = "AUTHORING_ENTITIES")
public abstract class authoring_entities {

    @Id
    @Column(nullable = false, name = "EMAIL" )
    private String email;

    @Column(nullable = false, name = "NAME")
    private String name;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public authoring_entities() {}

    public authoring_entities(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
