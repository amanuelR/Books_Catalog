package csulb.cecs323.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import java.util.List;

@Entity
@NamedNativeQuery(
        name="ReturnIndividualAuthor",
        query = "SELECT * " +
                "FROM   authoring_entities " +
                "WHERE  AuTHORING_ENTITY_TYPE = 'individual_authors' and name = ? ",
        resultClass = individual_authors.class
)
@DiscriminatorValue(value = "INDIVIDUAL_AUTHORS")
public class individual_authors extends authoring_entities{

    @ManyToMany(mappedBy = "individual_authors")
    List<ad_hoc_teams> ad_hock_teams;


    public individual_authors() {
        super();
    }
    public individual_authors(String email, String name, List<ad_hoc_teams> ad_hock_teams) {
        super(email, name);
        this.ad_hock_teams = ad_hock_teams;
    }


}
