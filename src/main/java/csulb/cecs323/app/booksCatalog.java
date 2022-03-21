/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

// Import all of the entity classes that we have written for this application.
import csulb.cecs323.model.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class booksCatalog {
   private EntityManager entityManager;
   private static final Logger LOGGER = Logger.getLogger(booksCatalog.class.getName());
   public booksCatalog(EntityManager manager) {
      this.entityManager = manager;
   }

   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("booksCatalog");
      EntityManager manager = factory.createEntityManager();
      // Create an instance of booksCatalog and store our new EntityManager as an instance variable.
      booksCatalog booksCatalog= new booksCatalog(manager);

      List<Publishers> publishers = new ArrayList<Publishers>();
      List<Books> books = new ArrayList<Books>();
      List<writing_groups> writing_groups = new ArrayList<writing_groups>();
      List<individual_authors> individual_authors = new ArrayList<individual_authors>();
      List<ad_hoc_teams> ad_hoc_teams = new ArrayList<ad_hoc_teams>();

      //add writing_groups objects
      //String email, String name, String head_writer, Integer year_formed
      writing_groups.add(new writing_groups("proudlywritters@writters.com","Proudly Wrtters","Alan James",1980));
      writing_groups.add(new writing_groups("storytellers@writters.com","Storytellers","Emma Joe",1978));
      writing_groups.add(new writing_groups("Penmaniacs@writters.com","Penmaniacs","Emerson Fabio",2000));

      //add individual_authors objects
      individual_authors.add(new individual_authors("agathachristie@hotmail.com", "Agatha Christie",null ));
      individual_authors.add(new individual_authors("julesverne@hotmail.com","Jules Verne",null));
      individual_authors.add(new individual_authors("enidblyton@gmail.com","Enid Blyton",null));

      //add ad_hoc_teams objects
      ad_hoc_teams.add(new ad_hoc_teams("justicefortheinnocents@adhocteams.com","Justice For The Innocents",null));
      ad_hoc_teams.add(new ad_hoc_teams("abetterecosystem@adhocteams.com","A Better Ecosystem",null));
      ad_hoc_teams.add(new ad_hoc_teams("stationone@adhocteams.com", "Station One", null));
      // publishers.add(publishersForm());

      //add publishers(String name, String email, String phone)

      publishers.add(new Publishers("Penguin Random House","newaccount@penguinrandomhouse.com","866-761-6685"));
      publishers.add(new Publishers("Hachette Book Group","hachette.books@hbgusa.com","800-759-0190"));
      publishers.add(new Publishers("Simon & Schuster","cservice@simonandschuster.co.in","800-223-2336"));


      //String isbn = JOptionPane.showInputDialog("ISBN: ");
      //String title = JOptionPane.showInputDialog("Publisher Email: ");
      //String year_published = JOptionPane.showInputDialog("Publisher Phone Number: ");

      // Any changes to the database need to be done within a transaction.
      // See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions

      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();

      tx.begin();

      booksCatalog.createEntity(writing_groups);
      booksCatalog.createEntity(individual_authors);
      booksCatalog.createEntity(ad_hoc_teams);
      booksCatalog.createEntity(publishers);

      tx.commit();
      LOGGER.fine("End of Transaction");

      //we need to add the books after the AUTHORING_ENTITIES and PUBLISHERS tables are populated
      //add Books(String ISBN, String title, Integer year_published,  authoring_entities authoring_entities, Publishers publishers)
      books.add(new Books("0-3403-6000-3", "The Future of Cryptocurrency", 2016,booksCatalog.getAuthoringEntitiesEmail("storytellers@writters.com"),booksCatalog.getName("Hachette Book Group")));
      books.add(new Books("0-5362-5597-0", "The Ultimate AI", 2011,booksCatalog.getAuthoringEntitiesEmail("julesverne@hotmail.com"),booksCatalog.getName("Penguin Random House")));
      books.add(new Books("0-6093-8620-4", "Why Do You Code",2007,booksCatalog.getAuthoringEntitiesEmail("justicefortheinnocents@adhocteams.com"),booksCatalog.getName("Simon & Schuster")));


      //add individual_authors to an existing ad_hoc_teams
      ad_hoc_teams.get(0).add_individual_authors(individual_authors.get(0));
      ad_hoc_teams.get(1).add_individual_authors(individual_authors.get(1));

      tx.begin();
      booksCatalog.createEntity(ad_hoc_teams);
      booksCatalog.createEntity(books);
      tx.commit();

      String menu = booksCatalog.showMenu();
      switch (menu){
         case "Add Publisher":
            List<Publishers> new_publishers = new ArrayList<Publishers>();
            new_publishers.add(booksCatalog.addPublisher());
            booksCatalog.createEntity(new_publishers);
            tx.begin();
               booksCatalog.createEntity(new_publishers);
            tx.commit();
            break;
         case "Add Book":
            List<Books> new_books = new ArrayList<Books>();
            new_books.add(booksCatalog.addBook());
            tx.begin();
               booksCatalog.createEntity(new_books);
            tx.commit();
            break;
         case "Update a Book":
            List<Books> updateBook = new ArrayList<Books>();
            updateBook.add(booksCatalog.updateBook());
            Books findBook = manager.find(Books.class, updateBook.get(0).getISBN());
            findBook.setTitle(updateBook.get(0).getTitle());
            findBook.setYear_published(updateBook.get(0).getYear_published());
            findBook.setPublishers(updateBook.get(0).getPublishers());
            findBook.setAuthoring_entities(updateBook.get(0).getAuthoring_entities());
            tx.begin();
               manager.merge(findBook);
            tx.commit();
            break;
         case "Delete Book":
            String isbn_to_delete = booksCatalog.deleteBook();
            //System.out.println(isbn_to_delete);
            Books book = manager.find(Books.class,isbn_to_delete);
            tx.begin();
               manager.remove(book);
            tx.commit();
            break;
         case "Add Writing Group":
            List<writing_groups> new_writing_group = new ArrayList<writing_groups>();
            new_writing_group.add(booksCatalog.addWritingGroup());
            tx.begin();
               booksCatalog.createEntity(new_writing_group);
            tx.commit();
            break;
         case "Add Individual Author":
            List<individual_authors> new_individual_author = new ArrayList<individual_authors>();
            new_individual_author.add(booksCatalog.addIndividualAuthor());
            tx.begin();
               booksCatalog.createEntity(new_individual_author);
            tx.commit();
            break;
         case "Add Ad Hoc Team":
            List<ad_hoc_teams> new_ad_hoc_team = new ArrayList<ad_hoc_teams>();
            new_ad_hoc_team.add(booksCatalog.addAdHocTeam());
            tx.begin();
               booksCatalog.createEntity(new_ad_hoc_team);
            tx.commit();
            break;
         case "Add an Individual Author to an existing Ad Hoc Team":
            tx.begin();
                booksCatalog.addIndividualAuthorToAdHocTeam();
            tx.commit();
            break;
         case "Show Information About a Publisher":
            booksCatalog.showPublisherInfo();
            break;
         case "Show Information About a Book":
            booksCatalog.showBookInfo();
            break;
         case "Show Information About a Writing Group":
            booksCatalog.showWritingGroupInfo();
            break;
         case "Show Information About an Individual Author":
            booksCatalog.showIndividualAuthorInfo();
            break;
         case "Show Information About an Ad Hoc Team":
            booksCatalog.showAdHocTeamInfo();
            break;
         case "List Primary Keys of Book":
            booksCatalog.displayBooksPKs();
            break;
         case "List Primary Keys of Publishers":
            booksCatalog.displayPublishersPKs();
            break;
         case "List Primary Keys of Authoring Entities":
            booksCatalog.displayAuthoringEntities();
            break;
      }// End of the switch statement

   } // End of the main method

   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the booksCatalog entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

  /* public individual_authors getEmail (String name) {
      // Run the native query that we defined in the individual_authors entity to find the right email.
      List<individual_authors> authors = this.entityManager.createNamedQuery("ReturnIndividualAuthor",
              individual_authors.class).setParameter(1, name).getResultList();
      if (authors.size() == 0) {
         // Invalid author name passed in.
         return null;
      } else {
         // Return the individual_author object that they asked for.
         return authors.get(0);
      }
   }*/
   /// End of the getStyle method

   public Publishers getName(String name){
      // Run the native query that we defined in the publisher entity to find the right style.
      List<Publishers> publisher = this.entityManager.createNamedQuery("ReturnPublisher",
              Publishers.class).setParameter(1, name).getResultList();
      //publisher.get(0).toString();
      if (publisher.size() == 0) {
         // Invalid publisher name passed in.
         return null;
      } else {
         // Return the publisher object that they asked for.
         return publisher.get(0);
      }
   }// End of the getName method
   public Publishers getEmail(String email){
      // Run the native query that we defined in the publisher entity to find the right style.
      List<Publishers> publisher = this.entityManager.createNamedQuery("ReturnPublisherEmail",
              Publishers.class).setParameter(1, email).getResultList();
      //publisher.get(0).toString();
      if (publisher.size() == 0) {
         // Invalid publisher name passed in.
         return null;
      } else {
         // Return the publisher object that they asked for.
         return publisher.get(0);
      }
   }// End of the getName method
   public Publishers getPhone(String phone){
      // Run the native query that we defined in the publisher entity to find the right style.
      List<Publishers> publisher = this.entityManager.createNamedQuery("ReturnPublisherPhone",
              Publishers.class).setParameter(1, phone).getResultList();
      //publisher.get(0).toString();
      if (publisher.size() == 0) {
         // Invalid publisher name passed in.
         return null;
      } else {
         // Return the publisher object that they asked for.
         return publisher.get(0);
      }
   }// End of the getName method


   public authoring_entities getAuthoringEntitiesEmail(String email){
      // Run the native query that we defined in the authoring_entities entity to find the right style.
      List<authoring_entities> authoring_entities = this.entityManager.createNamedQuery("ReturnAuthoringEntities",
              authoring_entities.class).setParameter(1, email).getResultList();
      if (authoring_entities.size() == 0) {
         // Invalid  authoring_entities email passed in.
         return null;
      } else {
         // Return the  authoring_entities object that they asked for.
         return authoring_entities.get(0);
      }
   }// End of the getAuthoringEntitiesEmail method

   //menu
   public String showMenu(){
      Object[] options = {"Add Publisher", "Add Book", "Update a Book" ,
                          "Delete Book","Add Writing Group", "Add Individual Author",
                          "Add Ad Hoc Team", "Add an Individual Author to an existing Ad Hoc Team",
                          "Show Information About a Publisher", "Show Information About a Book",
                          "Show Information About a Writing Group", "Show Information About an Individual Author",
                          "Show Information About an Ad Hoc Team", "List Primary Keys of Book",
                          "List Primary Keys of Publishers", "List Primary Keys of Authoring Entities"};
      Object selectionObject = JOptionPane.showInputDialog(null, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
      String selectionString = selectionObject.toString();
      return selectionString;
   }

   //get the ISBN of a book
   public Books getISBN(String ISBN){
      // Run the native query that we defined in the Books entity to find the right style.
      List<Books> books = this.entityManager.createNamedQuery("ReturnBookISBN",
              Books.class).setParameter(1, ISBN).getResultList();
      if (books.size() == 0) {
         // Invalid  Books ISBN passed in.
         return null;
      } else {
         // Return the Books object that they asked for.
         return books.get(0);
      }
   }

   //get the title of a book
   public Books getTitle(String title){
      // Run the native query that we defined in the Books entity to find the right style.
      List<Books> books = this.entityManager.createNamedQuery("ReturnBookTitle",
              Books.class).setParameter(1, title).getResultList();
      if (books.size() == 0) {
         // Invalid  Books title passed in.
         return null;
      } else {
         // Return the Books object that they asked for.
         return books.get(0);
      }
   }
   //add new book
   public Books addBook(){

      String ISBN = JOptionPane.showInputDialog("ISBN: ");
      //check ISBN is not in the database
      while(getISBN(ISBN) != null){
         ISBN = JOptionPane.showInputDialog("Already Exists, Try Another ISBN: ");
      }
      String title = JOptionPane.showInputDialog("Title: ");
      //check title is unique
      while(getTitle(title) != null){
         title = JOptionPane.showInputDialog("Already Exists, Try Another Title: ");
      }
      Integer year_published = Integer.valueOf(JOptionPane.showInputDialog("Year Published: "));

      List<String> name = entityManager.createQuery("Select p.name from Publishers p").getResultList();
      Object[] publishers = name.toArray();

      String publisher_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Publisher: ", JOptionPane.PLAIN_MESSAGE, null, publishers, publishers[0]));

      List<String> authoring_entities_email = entityManager.createQuery("Select a.email from authoring_entities a").getResultList();
      Object[] email = authoring_entities_email.toArray();
      String AUTHORING_ENTITY_TYPE = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Authoring Entities: ", JOptionPane.PLAIN_MESSAGE, null, email, email[0]));


      Books new_book = new Books(ISBN,title,year_published,getAuthoringEntitiesEmail(AUTHORING_ENTITY_TYPE),getName(publisher_name));

      return new_book;
   }
   //add new publisher
   public Publishers addPublisher(){

      String name = JOptionPane.showInputDialog("Publisher Name: ");
      //check if the name is not already in the database
      while(getName(name) != null){
         name = JOptionPane.showInputDialog("Already Exists, Try Another Name: ");
      }

      String email = JOptionPane.showInputDialog("Publisher Email: ");
      //check if the email is not already in the database
      while(getName(email) != null){
         email = JOptionPane.showInputDialog("Already Exists, Try Another Email: ");
      }

      String phone = JOptionPane.showInputDialog("Publisher Phone: ");
      //check if the phone is not already in the database
      while(getName(phone) != null){
         phone = JOptionPane.showInputDialog("Already Exists, Try Another Phone: ");
      }

      Publishers new_publisher = new Publishers(name,email,phone);

      return new_publisher;
   }

   //update a book
   public Books updateBook(){
      List<String> isbns = entityManager.createQuery("Select b.ISBN from Books b").getResultList();
      Object[] books = isbns.toArray();

      String books_to_be_updated = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Book: ", JOptionPane.PLAIN_MESSAGE, null, books, books[0]));

      List<Books> booksList = this.entityManager.createNamedQuery("ReturnBookISBN",
              Books.class).setParameter(1, books_to_be_updated).getResultList();

      String title = JOptionPane.showInputDialog("New Title: ", booksList.get(0).getTitle());
      Integer year_published = Integer.valueOf(JOptionPane.showInputDialog("New Year Published: ",booksList.get(0).getYear_published()));

      List<String> publisher_name = entityManager.createQuery("Select p.name from Publishers p").getResultList();
      Object[] publishers = publisher_name.toArray();

      String new_publisher_name = JOptionPane.showInputDialog(null, "Choose", "Menu",JOptionPane.PLAIN_MESSAGE, null,publishers,booksList.get(0).getPublishers()).toString();

      List<String> authoring_entities_email = entityManager.createQuery("Select ae.email from authoring_entities ae").getResultList();
      Object[] authors = authoring_entities_email.toArray();

      String new_ae_email = JOptionPane.showInputDialog(null, "Choose", "Menu",JOptionPane.PLAIN_MESSAGE, null,authors,booksList.get(0).getAuthoring_entities().getEmail()).toString();


      Books update = new Books(booksList.get(0).getISBN(), title, year_published,getAuthoringEntitiesEmail(new_ae_email),getName(new_publisher_name));

      return update;
      //title -> current -> edit
      //authoring_entites -> update

      //set the default value to the current value for each attributes
   }
   //delete a book
   public String deleteBook(){
      List<String> isbns = entityManager.createQuery("Select b.ISBN from Books b").getResultList();
      Object[] books = isbns.toArray();

      String book_to_delete = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Book: ", JOptionPane.PLAIN_MESSAGE, null, books, books[0]));

      return book_to_delete;
   }
   //add writing group
   public writing_groups addWritingGroup(){
      String name = JOptionPane.showInputDialog("Writing Group Name: ");
      String email = JOptionPane.showInputDialog("Writing Group Email: ");
      while(getAuthoringEntitiesEmail(email) != null){
         email = JOptionPane.showInputDialog("Already Exists, Try Another Email: ");
      }
      String head_writer = JOptionPane.showInputDialog("Writing Group Head Writer: ");
      Integer year_formed = Integer.valueOf(JOptionPane.showInputDialog("Writing Group Year Formed: "));

      writing_groups new_writing_groups = new writing_groups(name,email,head_writer,year_formed);

      return new_writing_groups;
   }
   //add individual author
   public individual_authors addIndividualAuthor(){
      String name = JOptionPane.showInputDialog("Individual Author Name: ");
      String email = JOptionPane.showInputDialog("Individual Author Email: ");
      while(getAuthoringEntitiesEmail(email) != null){
         email = JOptionPane.showInputDialog("Already Exists, Try Another Email: ");
      }

      individual_authors new_individual_author = new individual_authors(name,email,null);
      return new_individual_author;
   }
   //add ad hoc team
   public ad_hoc_teams addAdHocTeam(){
      String name = JOptionPane.showInputDialog("Ad Hoc Team Name: ");
      String email = JOptionPane.showInputDialog("Ad Hoc Team Email: ");
      while(getAuthoringEntitiesEmail(email) != null){
         email = JOptionPane.showInputDialog("Already Exists, Try Another Email: ");
      }

      ad_hoc_teams new_ad_hoc_team = new ad_hoc_teams(name,email,null);
      return new_ad_hoc_team;
   }

   public void addIndividualAuthorToAdHocTeam(){

      List<String> name = entityManager.createQuery("Select ia.name from individual_authors ia").getResultList();
      Object[] authorSel = name.toArray();

      String individual_author_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Individual Author: ", JOptionPane.PLAIN_MESSAGE, null, authorSel, authorSel[0]));

      List<individual_authors> authors = this.entityManager.createNamedQuery("ReturnIndividualAuthor",
              individual_authors.class).setParameter(1, individual_author_name).getResultList();

      List<String> team = entityManager.createQuery("Select aht.name from ad_hoc_teams aht").getResultList();
      Object[] ad_hoc_team = team.toArray();
      String teamSel = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Ad Hoc Team: ", JOptionPane.PLAIN_MESSAGE, null, ad_hoc_team, ad_hoc_team[0]));

      List<ad_hoc_teams> adHocTeams = this.entityManager.createNamedQuery("ReturnAdHocTeam",
              ad_hoc_teams.class).setParameter(1, teamSel).getResultList();

      adHocTeams.get(0).add_individual_authors(authors.get(0));

   }
   public void showPublisherInfo(){
      List<String> name = entityManager.createQuery("Select p.name from Publishers p").getResultList();
      Object[] publishers = name.toArray();

      String publisher_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Publisher: ", JOptionPane.PLAIN_MESSAGE, null, publishers, publishers[0]));

      List<Publishers> publisherss = this.entityManager.createNamedQuery("ReturnPublisher",
              Publishers.class).setParameter(1, publisher_name).getResultList();

      Publishers p = publisherss.get(0);
      String publisher_info = p.toString();

      JOptionPane.showMessageDialog(null,"Publisher Info: " + publisher_info);
   }

   public void showBookInfo(){
      List<String> title = entityManager.createQuery("Select b.title from Books b").getResultList();
      Object[] books = title.toArray();

      String book_title = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Book: ", JOptionPane.PLAIN_MESSAGE, null, books, books[0]));

      List<Books> bookss = this.entityManager.createNamedQuery("ReturnBookTitle",
              Books.class).setParameter(1, book_title).getResultList();

      Books b = bookss.get(0);
      String book_info = b.toString();

      JOptionPane.showMessageDialog(null,"Book Info: " + book_info);

   }

   public void showWritingGroupInfo(){
      List<String> names = entityManager.createQuery("Select w.name from writing_groups w").getResultList();
      Object[] writing_groups = names.toArray();

      String wg_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Writing Group: ", JOptionPane.PLAIN_MESSAGE, null, writing_groups, writing_groups[0]));

      List<writing_groups> writingGroups = this.entityManager.createNamedQuery("ReturnWritingGroup",
              writing_groups.class).setParameter(1, wg_name).getResultList();

      writing_groups writingGroups1 = writingGroups.get(0);
      String writing_group_info = writingGroups1.toString();

      JOptionPane.showMessageDialog(null,"Writing Group Info: " + writing_group_info);

   }

   public void showIndividualAuthorInfo(){
      List<String> author_names = entityManager.createQuery("Select i.name from individual_authors i").getResultList();
      Object[] individual_authors = author_names.toArray();

      String ind_author_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Writing Group: ", JOptionPane.PLAIN_MESSAGE, null, individual_authors, individual_authors[0]));

      List<individual_authors> individualAuthors = this.entityManager.createNamedQuery("ReturnIndividualAuthor",
              individual_authors.class).setParameter(1,ind_author_name).getResultList();

      individual_authors authors = individualAuthors.get(0);
      String author_info = authors.toString();

      JOptionPane.showMessageDialog(null,"Individual Author Info: " + author_info);

   }

   public void showAdHocTeamInfo(){
      List<String> team_names = entityManager.createQuery("Select i.name from ad_hoc_teams i").getResultList();
      Object[] ad_hoc_team_names = team_names.toArray();

      String sel_team_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Writing Group: ", JOptionPane.PLAIN_MESSAGE, null, ad_hoc_team_names, ad_hoc_team_names[0]));

      List<ad_hoc_teams> adHocTeams = this.entityManager.createNamedQuery("ReturnAdHocTeam",
              ad_hoc_teams.class).setParameter(1,sel_team_name).getResultList();

      ad_hoc_teams  team= adHocTeams.get(0);
      String team_info = team.toString();

      JOptionPane.showMessageDialog(null,"Ad Hoc Team Info: " + team_info);

   }

   public void displayBooksPKs(){
      List<String> isbn = entityManager.createQuery("Select b.ISBN from Books b").getResultList();
      List<String> title = entityManager.createQuery("Select b.title from Books b").getResultList();
      String rows = "";
      for(int i = 0; i < isbn.size(); i++){
         rows = rows + "<tr><td>"+isbn.get(i)+"</td><td>"+title.get(i)+"</td></tr>";

        // rows = rows + isbn.get(i) + "                     " + title.get(i) + "\n";
      }
      JOptionPane.showMessageDialog(null,"<html><table border = 1><tr color = #FF0000><td>Primary Key </td><td>Title</td></tr>"+rows+"</table></html>");
   }
   public void displayPublishersPKs(){
      List<String> publisher_name = entityManager.createQuery("Select p.name from Publishers p").getResultList();

      String rows = "";
      for(int i = 0; i < publisher_name.size(); i++){
         rows = rows + "<tr><td>" + publisher_name.get(i) + "</td></tr>";
      }
      JOptionPane.showMessageDialog(null,"<html><table border = 1><tr color = #FF0000><td>Primary Keys(Publishers)</td></tr>"+rows+"</table></html>");
   }

   public void displayAuthoringEntities(){
      List<String> emails = entityManager.createQuery("Select ae.email from authoring_entities ae").getResultList();
      //List<String> type = entityManager.createQuery("Select ae from authoring_entities ae ").getResultList();
     // List<authoring_entities> authoring_entities = this.entityManager.createNamedQuery("ReturnAuthoringEntityType",
           //   authoring_entities.class).getResultList();
      String rows = "";
      for(int i = 0; i < emails.size(); i++){
        // rows = rows + String.format(emails.get(i),getAuthoringEntitiesEmail(emails.get(i)).getClass().getAnnotation(DiscriminatorValue.class).value());
        rows = rows + "<tr><td>"+emails.get(i)+"</td><td>"+getAuthoringEntitiesEmail(emails.get(i)).getClass().getAnnotation(DiscriminatorValue.class).value()+"</td></tr>";
         //rows = rows  + emails.get(i) + "                                           " + getAuthoringEntitiesEmail(emails.get(i)).getClass().getAnnotation(DiscriminatorValue.class).value() + "\n";
      }
      //rows = rows + "</table></html>";
      JOptionPane.showMessageDialog(null,"<html><table border = 1><tr color = #FF0000><td>Primary Key</td><td>Type</td></tr>"+rows+"</table></html>" );

   }
}// End of booksCatalog class
