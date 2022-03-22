package csulb.cecs323.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import java.util.List;

@Entity
@NamedNativeQuery(
        name="ReturnIndividualAuthor1",
        query = "SELECT * " +
                "FROM   authoring_entities " +
                "WHERE  AuTHORING_ENTITY_TYPE = 'individual_authors' and name = ? ",
        resultClass = individual_authors.class
)
@NamedNativeQuery(
        name="ReturnIndividualAuthor",
        query = "SELECT * " +
                "FROM   authoring_entities " +
                "WHERE  name = ? ",
        resultClass = individual_authors.class
)
@DiscriminatorValue(value = "INDIVIDUAL_AUTHORS")
public class individual_authors extends authoring_entities{

    @ManyToMany(mappedBy = "individual_authors")
    List<ad_hoc_teams> ad_hock_teams;


    /** individual_authors's default constructor*/
    public individual_authors() {
        super();
    }

    /** individual_authors's constructor that takes three parameters(attributes)*/
    public individual_authors(String email, String name, List<ad_hoc_teams> ad_hock_teams) {
        super(email, name);
        this.ad_hock_teams = ad_hock_teams;
    }
}
