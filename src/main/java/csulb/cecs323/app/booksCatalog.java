/**
 * A term project for CECS 323 - Database
 *
 * This JPA Books project is done by:
 *                                     Amanuel Reda
 *                                     Sierra Haris
 *                                     Jonathan Kirtland
 *
 *                                     3/20/2022
 *  Note: make sure this property has the same value as provided below with your persistence.xml property
 *       <property name="javax.persistence.schema-generation.database.action" value="none" />
 **/

package csulb.cecs323.app;

/** Import all of the entity classes that we have written for this application. **/
import csulb.cecs323.model.*;

import javax.persistence.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/** Main class booksCatalog*/
public class booksCatalog {
   private EntityManager entityManager;
   private static final Logger LOGGER = Logger.getLogger(booksCatalog.class.getName());
   public booksCatalog(EntityManager manager) {
      this.entityManager = manager;
   }

   /** booksCatalog's main method */
   public static void main(String[] args) {
      LOGGER.fine("Creating EntityManagerFactory and EntityManager");
      EntityManagerFactory factory = Persistence.createEntityManagerFactory("booksCatalog");
      EntityManager manager = factory.createEntityManager();
      /** Create an instance of booksCatalog and store our new EntityManager as an instance variable. **/
      booksCatalog booksCatalog= new booksCatalog(manager);
      /**
       * Note: Please uncomment the next segment of the code when you run the program for the first time
       *       then comment it back. Things to consider for the first run, after uncommenting the segment mentioned above
       *       please comment line 114 - 115 and line 212, so error could be avoided.
       * */
/*
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
      tx.commit();*/

      /**
       * Comment the next two lines of code before you perform a first run, then
       * uncomment it after performing and finishing the first run.
      */
      LOGGER.fine("Begin of Transaction");
      EntityTransaction tx = manager.getTransaction();
      String menu = booksCatalog.showMenu();

      //Since we are doing one case for each run, we only need to start one transaction
      tx.begin();
      switch (menu){
         case "Add Publisher":
            List<Publishers> new_publishers = new ArrayList<Publishers>();
            new_publishers.add(booksCatalog.addPublisher());
            booksCatalog.createEntity(new_publishers);
               booksCatalog.createEntity(new_publishers);
               JOptionPane.showMessageDialog( null,new_publishers.get(0).getName() + " is added to the Publishers table.");
            break;
         case "Add Book":
            List<Books> new_books = new ArrayList<Books>();
            new_books.add(booksCatalog.addBook());
               booksCatalog.createEntity(new_books);
               JOptionPane.showMessageDialog( null,new_books.get(0).getTitle() + " is added to the Books table.");
            break;
         case "Update a Book":
            List<Books> updateBook = new ArrayList<Books>();
            updateBook.add(booksCatalog.updateBook());
            Books findBook = manager.find(Books.class, updateBook.get(0).getISBN());
            findBook.setTitle(updateBook.get(0).getTitle());
            findBook.setYear_published(updateBook.get(0).getYear_published());
            findBook.setPublishers(updateBook.get(0).getPublishers());
            findBook.setAuthoring_entities(updateBook.get(0).getAuthoring_entities());
               manager.merge(findBook);
               JOptionPane.showMessageDialog( null,"Book "+findBook.getISBN() + " is Updated.");
            break;
         case "Delete Book":
            String isbn_to_delete = booksCatalog.deleteBook();
            //System.out.println(isbn_to_delete);
            Books book = manager.find(Books.class,isbn_to_delete);
               manager.remove(book);
               JOptionPane.showMessageDialog( null,"Book "+ isbn_to_delete + " is deleted from Books table.");
            break;
         case "Add Writing Group":
            List<writing_groups> new_writing_group = new ArrayList<writing_groups>();
            new_writing_group.add(booksCatalog.addWritingGroup());
               booksCatalog.createEntity(new_writing_group);
               JOptionPane.showMessageDialog( null,new_writing_group.get(0).getName() + " is added to the writing_groups table.");
            break;
         case "Add Individual Author":
            List<individual_authors> new_individual_author = new ArrayList<individual_authors>();
            new_individual_author.add(booksCatalog.addIndividualAuthor());
               booksCatalog.createEntity(new_individual_author);
               JOptionPane.showMessageDialog( null,new_individual_author.get(0).getName() + " is added to the individual_authors table.");
            break;
         case "Add Ad Hoc Team":
            List<ad_hoc_teams> new_ad_hoc_team = new ArrayList<ad_hoc_teams>();
            new_ad_hoc_team.add(booksCatalog.addAdHocTeam());
               booksCatalog.createEntity(new_ad_hoc_team);
               JOptionPane.showMessageDialog( null,new_ad_hoc_team.get(0).getName() + " is added to the ad_hoc_teams table.");
            break;
         case "Add an Individual Author to an existing Ad Hoc Team":
                booksCatalog.addIndividualAuthorToAdHocTeam();
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

      //After breaking out from a switch case, if there is anything to commit, we commit.
      tx.commit();
      LOGGER.fine("End of Transaction");
   } // End of the main method

   /** Provided by Professor David Brown
    * Create and persist a list of objects to the database.
    * @param entities   The list of entities to persist.  These can be any object that has been
    *                   properly annotated in JPA and marked as "persistable."  I specifically
    *                   used a Java generic so that I did not have to write this over and over.
    */
   public <E> void createEntity(List <E> entities) {
      for (E next : entities) {
         LOGGER.info("Persisting: " + next);
         // Use the booksCatalog entityManager instance variable to get our EntityManager.
         this.entityManager.persist(next);
      }

      /**The auto generated ID (if present) is not passed in to the constructor since JPA will
         generate a value.  So the previous for loop will not show a value for the ID.  But
         now that the Entity has been persisted, JPA has generated the ID and filled that in.**/
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

   /** returns publisher instance that has the given name
    * @param name email of a publisher
    * @return a Publisher object
    */
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

   /** returns publisher instance that has the given email
    * @param email email of a publisher
    * @return a Publisher object
    */
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

    /** returns a publisher instance that has the given phone number
     * @param phone- a phone number of a publisher
     * @return publisher object
     */
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

   /** returns authoring entities instance that has the given email
    * @param email an email of an authoring entities
    * @return authoring entities object
    */
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

   /** A menu that shows the user different options to perform different tasks
    * @return a string (selected menu item)
    */
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
   }//End of showMenu()


   /** returns a Books instance that has the given ISBN
    * @param ISBN ISBN of a book
    * @return Books instance
    */
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
   }//End of getISBN

   /** returns an instance of a book that has the given title
    * @param title the title of the book that we are getting an instance of
    * @return Books instance
    */
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
   }//End of getTitle

   /** returns a Books instance that the user wants to add to the database
    * @return a Books instance that the user wants to add to the database
    */
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

      //create the object and return the instance of the new book
      Books new_book = new Books(ISBN,title,year_published,getAuthoringEntitiesEmail(AUTHORING_ENTITY_TYPE),getName(publisher_name));

      return new_book;
   }//End of addBook()

   /** returns an object of Publishers that the user wants to add to the database
    * @return a new Publishers object
    */
   public Publishers addPublisher(){

      String publisher_name = JOptionPane.showInputDialog("Publisher Name: ");
      //check if the name is not already in the database
      while(getName(publisher_name) != null){
         publisher_name  = JOptionPane.showInputDialog("Already Exists, Try Another Name: ");
      }

      String publisher_email = JOptionPane.showInputDialog("Publisher Email: ");
      //check if the email is not already in the database
      while(getEmail(publisher_email) != null){
         publisher_email = JOptionPane.showInputDialog("Already Exists, Try Another Email: ");
      }

      String publisher_phone = JOptionPane.showInputDialog("Publisher Phone: ");
      //check if the phone is not already in the database
      while(getPhone(publisher_phone) != null){
         publisher_phone = JOptionPane.showInputDialog("Already Exists, Try Another Phone: ");
      }

      Publishers new_publisher = new Publishers(publisher_name ,publisher_email,publisher_phone);

      return new_publisher;
   }//End of addPublisher()

   /** Updates an existing book
    * @return an existing Books object that the user modified its data
    */
   public Books updateBook(){
      //list of books ISBN
      List<String> isbns = entityManager.createQuery("Select b.ISBN from Books b").getResultList();
      Object[] books = isbns.toArray();

      //selected book that the user wants to update
      String books_to_be_updated = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Book: ", JOptionPane.PLAIN_MESSAGE, null, books, books[0]));

      //holds the object of the book that the user wants to update
      List<Books> booksList = this.entityManager.createNamedQuery("ReturnBookISBN",
              Books.class).setParameter(1, books_to_be_updated).getResultList();

      String title = JOptionPane.showInputDialog("New Title: ", booksList.get(0).getTitle());

      //a list that holds all the current Books objects that are in our database
      // except the one that has the same title as the book we are trying to update
      List<Books> book_exists = this.entityManager.createNamedQuery("ReturnBookTitleForUpdate",
              Books.class).setParameter(1, title).getResultList();
      //Check if there is a book with the title that the user is inputting


      while (!book_exists.isEmpty()) {
            title = JOptionPane.showInputDialog("Already Exists, Try Another Title: ");
            book_exists.clear();
      }

      Integer year_published = Integer.valueOf(JOptionPane.showInputDialog("New Year Published: ",booksList.get(0).getYear_published()));


      List<String> publisher_name = entityManager.createQuery("Select p.name from Publishers p").getResultList();
      Object[] publishers = publisher_name.toArray();

      String new_publisher_name = JOptionPane.showInputDialog(null, "Choose", "Menu",JOptionPane.PLAIN_MESSAGE, null,publishers,booksList.get(0).getPublishers()).toString();

      List<String> authoring_entities_email = entityManager.createQuery("Select ae.email from authoring_entities ae").getResultList();
      Object[] authors = authoring_entities_email.toArray();

      String new_ae_email = JOptionPane.showInputDialog(null, "Choose", "Menu",JOptionPane.PLAIN_MESSAGE, null,authors,booksList.get(0).getAuthoring_entities().getEmail()).toString();


      Books update = new Books(booksList.get(0).getISBN(), title, year_published,getAuthoringEntitiesEmail(new_ae_email),getName(new_publisher_name));

      return update;
   } //End of updateBook()

   /** returns ISBN of a book that a user wants to delete
    * @return  an ISBN(String) of a book
    */
   public String deleteBook(){
      //list of books ISBN
      List<String> isbns = entityManager.createQuery("Select b.ISBN from Books b").getResultList();
      Object[] books = isbns.toArray();

      //selected book's ISBN that the user wants to delete
      String book_to_delete = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Book: ", JOptionPane.PLAIN_MESSAGE, null, books, books[0]));

      return book_to_delete;
   }// End of deleteBook()

   /** returns a new writing object that a user wants to add to the database
    * @return a new writing group object
    */
   public writing_groups addWritingGroup(){
      String name = JOptionPane.showInputDialog("Writing Group Name: ");
      String email = JOptionPane.showInputDialog("Writing Group Email: ");
      while(getAuthoringEntitiesEmail(email) != null){
         email = JOptionPane.showInputDialog("Already Exists, Try Another Email: ");
      }
      String head_writer = JOptionPane.showInputDialog("Writing Group Head Writer: ");
      Integer year_formed = Integer.valueOf(JOptionPane.showInputDialog("Writing Group Year Formed: "));

      writing_groups new_writing_groups = new writing_groups(email,name,head_writer,year_formed);

      return new_writing_groups;
   }//End of addWritingGroup()

   /** returns a new individual author that a user wants to add to the database
    * @return a new individual author object
    */
   public individual_authors addIndividualAuthor(){

      String individual_author_name = JOptionPane.showInputDialog("Individual Author Name: ");
      String individual_author_email = JOptionPane.showInputDialog("Individual Author Email: ");

      //check if the individual author doesn't exist in our database
      while(getAuthoringEntitiesEmail(individual_author_email) != null){
         individual_author_email = JOptionPane.showInputDialog("Already Exists, Try Another Email: ");
      }
      //create a new object of individual author and return it
      individual_authors new_individual_author = new individual_authors(individual_author_email,individual_author_name,null);
      return new_individual_author;
   }//End of addIndividualAuthor()

   /** returns a new ad_hoc_team that a user wants to add to the database
    * @return a new ad_hoc_team object
    */
   public ad_hoc_teams addAdHocTeam(){

      String ad_hoc_team_name = JOptionPane.showInputDialog("Ad Hoc Team Name: ");
      String ad_hoc_team_email = JOptionPane.showInputDialog("Ad Hoc Team Email: ");

      //check if the ad hoc team doesn't exist in our database
      while(getAuthoringEntitiesEmail(ad_hoc_team_email) != null){
         ad_hoc_team_email = JOptionPane.showInputDialog("Already Exists, Try Another Email: ");
      }

      //create an ad_hoc_team object and return it
      ad_hoc_teams new_ad_hoc_team = new ad_hoc_teams(ad_hoc_team_email,ad_hoc_team_name,null);
      return new_ad_hoc_team;
   }// End of addAdHocTeam()

   /**
    * Adds an individual author to an existing ad hoc team
    */
   public void addIndividualAuthorToAdHocTeam(){
      //list of individual authors name
      List<String> name = entityManager.createQuery("Select ia.name from individual_authors ia").getResultList();
      Object[] authorSelectionChoice = name.toArray();

      //Selected individual author that the user wants to add to an ad hoc team
      String selected_individual_author_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Individual Author: ", JOptionPane.PLAIN_MESSAGE, null, authorSelectionChoice, authorSelectionChoice[0]));

      //holds the individual author object that the user wants to add to an ad hoc team
      List<individual_authors> authors = this.entityManager.createNamedQuery("ReturnIndividualAuthor",
              individual_authors.class).setParameter(1, selected_individual_author_name).getResultList();

      //list of ad hoc teams
      List<String> team = entityManager.createQuery("Select aht.name from ad_hoc_teams aht").getResultList();
      Object[] ad_hoc_team = team.toArray();

      //the selected ad hoc team that the individual author is going to be added to
      String selected_ad_hoc_team = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Ad Hoc Team: ", JOptionPane.PLAIN_MESSAGE, null, ad_hoc_team, ad_hoc_team[0]));

      //holds the ad hoc team object that the individual author is going to be added to
      List<ad_hoc_teams> adHocTeams = this.entityManager.createNamedQuery("ReturnAdHocTeam",
              ad_hoc_teams.class).setParameter(1, selected_ad_hoc_team).getResultList();
      //adding the individual author to an ad hoc team
      if(adHocTeams.get(0).add_individual_authors(authors.get(0)))
         JOptionPane.showMessageDialog( null, "Individual Author is added to an Ad Hoc Team.");
   }//End of addIndividualAuthorToAdHocTeam()

   /**
    * Shows Information about a publisher that a user wants to see
    */
   public void showPublisherInfo(){
      //a list of publishers name
      List<String> name = entityManager.createQuery("Select p.name from Publishers p").getResultList();
      Object[] publishers = name.toArray();

      //the selected publisher name
      String selected_publisher_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Publisher: ", JOptionPane.PLAIN_MESSAGE, null, publishers, publishers[0]));

      //this list holds the publisher object that the user wants to see
      List<Publishers> publisherss = this.entityManager.createNamedQuery("ReturnPublisher",
              Publishers.class).setParameter(1, selected_publisher_name).getResultList();

      Publishers p = publisherss.get(0);
      String publisher_info = p.toString();

      JOptionPane.showMessageDialog(null,"Publisher Info: " + publisher_info);
   }//End of showPublisherInfo()


   /**
    * shows Information about a book that a user wants to see
    */
   public void showBookInfo(){
      //list of books title
      List<String> title = entityManager.createQuery("Select b.title from Books b").getResultList();
      Object[] books = title.toArray();

      String selected_book_title = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Book: ", JOptionPane.PLAIN_MESSAGE, null, books, books[0]));

      //holds the book object that the user wants to see
      List<Books> bookss = this.entityManager.createNamedQuery("ReturnBookTitle",
              Books.class).setParameter(1, selected_book_title).getResultList();

      Books b = bookss.get(0);
      String book_info = b.toString();

      JOptionPane.showMessageDialog(null,"Book Info: " + book_info);

   }//End of showBookInfo()

   /**
    * Show information about a Writing Group instance that the user likes to see
    */
   public void showWritingGroupInfo(){
      //list of writing groups name
      List<String> names = entityManager.createQuery("Select w.name from writing_groups w").getResultList();
      Object[] writing_groups = names.toArray();

      // the name of the selected writing group that the user wants to see information of
      String selected_writing_group_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Writing Group: ", JOptionPane.PLAIN_MESSAGE, null, writing_groups, writing_groups[0]));

      List<writing_groups> writingGroups = this.entityManager.createNamedQuery("ReturnWritingGroup",
              writing_groups.class).setParameter(1, selected_writing_group_name).getResultList();

      writing_groups writingGroups1 = writingGroups.get(0);
      String writing_group_info = writingGroups1.toString();

      JOptionPane.showMessageDialog(null,"Writing Group Info: " + writing_group_info);

   }//End of showWritingGroupInfo()

   /**
    * Show information of an Individual Author that user likes to see
    */
   public void showIndividualAuthorInfo(){
      //list of all individual authors names
      List<String> author_names = entityManager.createQuery("Select i.name from individual_authors i").getResultList();
      Object[] individual_authors = author_names.toArray();

      //the name of the selected ad_hoc_teams that the user wants to see information of
      String selected_author_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Individual Authors: ", JOptionPane.PLAIN_MESSAGE, null, individual_authors, individual_authors[0]));

      List<individual_authors> individualAuthors = this.entityManager.createNamedQuery("ReturnIndividualAuthor",
              individual_authors.class).setParameter(1,selected_author_name).getResultList();

      individual_authors authors = individualAuthors.get(0);
      String author_info = authors.toString();

      JOptionPane.showMessageDialog(null,"Individual Author Info: " + author_info);

   }//End of showIndividualAuthorInfo()

   /**
    * Show Information of an Ad Hoc Team Object
    */
   public void showAdHocTeamInfo(){

      //list of all ad_hoc_teams names
      List<String> team_names = entityManager.createQuery("Select i.name from ad_hoc_teams i").getResultList();
      Object[] ad_hoc_team_names = team_names.toArray();

      //the name of the selected ad_hoc_teams that the user wants to see information of
      String selected_team_name = String.valueOf(JOptionPane.showInputDialog(null, "Choose", "Writing Group: ", JOptionPane.PLAIN_MESSAGE, null, ad_hoc_team_names, ad_hoc_team_names[0]));

      List<ad_hoc_teams> adHocTeams = this.entityManager.createNamedQuery("ReturnAdHocTeam",
              ad_hoc_teams.class).setParameter(1,selected_team_name).getResultList();

      ad_hoc_teams  team= adHocTeams.get(0);
      String team_info = team.toString();

      JOptionPane.showMessageDialog(null,"Ad Hoc Team Info: " + team_info);

   }//End of showAdHocTeamInfo()

   /**
    * Shows all primary keys of the existing records in Books
    */
   public void displayBooksPKs(){
      //list of all the ISBNs of books that are in our database
      List<String> isbn = entityManager.createQuery("Select b.ISBN from Books b").getResultList();

      //list of all the books title that are in our database
      List<String> title = entityManager.createQuery("Select b.title from Books b").getResultList();
      String rows = "";
      for(int i = 0; i < isbn.size(); i++){
         rows = rows + "<tr><td>"+isbn.get(i)+"</td><td>"+title.get(i)+"</td></tr>";
      }
      JOptionPane.showMessageDialog(null,"<html><table border = 1><tr color = #FF0000><td>Primary Key </td><td>Title</td></tr>"+rows+"</table></html>");
   }//End of displayBooksPKs

   /**
    * Shows all primary keys of the existing records in Publishers
    */
   public void displayPublishersPKs(){
      //a list of publishers name (Primary Keys)
      List<String> publisher_name = entityManager.createQuery("Select p.name from Publishers p").getResultList();

      //rows represent a collection of all the primary keys(name) of publishers
      String rows = "";
      for(int i = 0; i < publisher_name.size(); i++){
         rows = rows + "<tr><td>" + publisher_name.get(i) + "</td></tr>";
      }
      JOptionPane.showMessageDialog(null,"<html><table border = 1><tr color = #FF0000><td>Primary Keys(Publishers)</td></tr>"+rows+"</table></html>");
   }//End of displayPublishersPKs()

   /** Shows all primary keys and authoring entity types of the existing records in AUTHORING_ENTITIES */
   public void displayAuthoringEntities(){
      //a list of authoring_entities emails (Primary Keys)
      List<String> emails = entityManager.createQuery("Select ae.email from authoring_entities ae").getResultList();
      String rows = "";
      for(int i = 0; i < emails.size(); i++){                     //returns the discriminator value of the email its referenced for
          rows = rows + "<tr><td>"+emails.get(i)+"</td><td>"+getAuthoringEntitiesEmail(emails.get(i)).getClass().getAnnotation(DiscriminatorValue.class).value()+"</td></tr>";
      }
      JOptionPane.showMessageDialog(null,"<html><table border = 1><tr color = #FF0000><td>Primary Key</td><td>Type</td></tr>"+rows+"</table></html>" );

   }//End of displayAuthoringEntities()
}// End of booksCatalog class
