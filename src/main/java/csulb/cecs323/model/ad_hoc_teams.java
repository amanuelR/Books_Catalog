package csulb.cecs323.model;

import javax.persistence.*;
import javax.swing.*;
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
@NamedNativeQuery(
        name="ReturnAdHocTeam",
        query = "SELECT * " +
                "FROM   AUTHORING_ENTITIES " +
                "WHERE  NAME = ? ",
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

    public void setIndividual_authors(List<individual_authors> individual_authors) {
        this.individual_authors = individual_authors;
    }


    /** Adds an individual author to an ad hoc team after checking if that instance of individual author
     *  is not part of the team currently
     * @param individual_authors an individual author instance that is going to be added to ad hoc team
     * @return a boolean value to tell us if the publisher is added or not
     */
    public boolean add_individual_authors(individual_authors individual_authors){
            boolean flag = false;
            if(!(this.individual_authors.contains(individual_authors))){
                 this.individual_authors.add(individual_authors);
                  flag = true;
            }
            else{
                JOptionPane.showMessageDialog( null,"Individual Author Already in the Team.");
            }
            return flag;
    }//End of add_individual_authors
}//End of ad_hoc_teams class
