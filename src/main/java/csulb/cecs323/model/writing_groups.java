package csulb.cecs323.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(
        name = "ReturnWritingGroup",
        query = "SELECT * " +
                "FROM   AUTHORING_ENTITIES " +
                "WHERE  NAME = ? ",
        resultClass = writing_groups.class
)
@DiscriminatorValue(value = "WRITING_GROUPS")
public class writing_groups extends authoring_entities{
    @Column(name = "HEAD_WRITER")
    private String head_writer;

    @Column(name = "YEAR_FORMED")
    private Integer year_formed;

    public String getHead_writer() {
        return head_writer;
    }

    public void setHead_writer(String head_writer) {
        this.head_writer = head_writer;
    }

    public Integer getYear_formed() {
        return year_formed;
    }

    public void setYear_formed(Integer year_formed) {
        this.year_formed = year_formed;
    }

    public writing_groups() { super(); }

    public writing_groups(String email, String name, String head_writer, Integer year_formed) {
        super(email, name);
        this.head_writer = head_writer;
        this.year_formed = year_formed;
    }

    @Override
    public String toString() {
        return  super.toString() +
                " Head Writer: " + head_writer + "\n" +
                " Year Formed=" + year_formed;
    }
}
