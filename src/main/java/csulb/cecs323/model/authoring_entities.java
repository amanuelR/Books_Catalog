package csulb.cecs323.model;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "AUTHORING_ENTITY_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
@Table(name = "AUTHORING_ENTITIES")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "ReturnAuthoringEntities",
                query = "SELECT * " +
                        "FROM   AUTHORING_ENTITIES " +
                        "WHERE  EMAIL = ? ",
                resultClass = authoring_entities.class
        ),
        @NamedNativeQuery(
                name = "ReturnAuthoringEntitiesEmail",
                query = "SELECT EMAIL " +
                        "FROM   AUTHORING_ENTITIES ",
                resultClass = authoring_entities.class
        ),
        @NamedNativeQuery(
                name = "ReturnAuthoringEntityType",
                query = "SELECT AUTHORING_ENTITY_TYPE " +
                        "FROM AUTHORING_ENTITIES ",
                resultClass = authoring_entities.class
        )
})
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

    /** authoring_entities's default constructor */
    public authoring_entities() {}

    /**  an authoring_entities constructor that takes two parameters(attributes) */
    public authoring_entities(String email, String name) {
        this.email = email;
        this.name = name;
    }//End of constructor

    @Override
    public String toString() {
        return " \n Entity Type: " + getClass().getAnnotation(DiscriminatorValue.class).value() + "\n"
                + " Email: " + email + "\n" +
                " Name: " + name + "\n";
    }//End of toString()
}//End of authoring_entities class
