package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedNativeQuery(
        name="IndividualAuthorExists",
        query = "SELECT COUNT(*) " +
                "FROM   AD_HOC_TEAMS " +
                "WHERE  INDIVIDUAL_AUTHORS_EMAIL = ? ",
        resultClass = ad_hoc_teams.class
)
@DiscriminatorValue(value = "AD_HOC_TEAMS")
public class ad_hoc_teams extends authoring_entities{
    @ManyToMany
    @JoinTable(
            name = "AD_HOC_TEAMS_MEMBER",
            joinColumns = {
                    @JoinColumn(name = "ad_hoc_teams_email", referencedColumnName = "email") },
            inverseJoinColumns = {
                    @JoinColumn(name = "individual_authors_email",referencedColumnName = "email")
                    }
    )
    private List<individual_authors> individual_authors = new ArrayList<>();

    public ad_hoc_teams() {super();}

    public ad_hoc_teams(String email, String name, List<individual_authors> individual_authors) {
        super(email, name);
        this.individual_authors = individual_authors;
    }

    public List<individual_authors> getIndividual_authors() {
        return individual_authors;
    }

    public void setIndividual_authors(List<csulb.cecs323.model.individual_authors> individual_authors) {
        this.individual_authors = individual_authors;
    }

    //add individual author to ad_hoc_teams
    public void add_individual_authors(individual_authors individual_authors){
            if(!(this.individual_authors.contains(individual_authors))){
                 this.individual_authors.add(individual_authors);
            }
    }
}
