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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A simple application to demonstrate how to persist an object in JPA.
 * <p>
 * This is for demonstration and educational purposes only.
 * </p>
 * <p>
 *     Originally provided by Dr. Alvaro Monge of CSULB, and subsequently modified by Dave Brown.
 * </p>
 */
public class booksCatalog {
   /**
    * You will likely need the entityManager in a great many functions throughout your application.
    * Rather than make this a global variable, we will make it an instance variable within the booksCatalog
    * class, and create an instance of booksCatalog in the main.
    */
   private EntityManager entityManager;

   /**
    * The Logger can easily be configured to log to a file, rather than, or in addition to, the console.
    * We use it because it is easy to control how much or how little logging gets done without having to
    * go through the application and comment out/uncomment code and run the risk of introducing a bug.
    * Here also, we want to make sure that the one Logger instance is readily available throughout the
    * application, without resorting to creating a global variable.
    */
   private static final Logger LOGGER = Logger.getLogger(booksCatalog.class.getName());

   /**
    * The constructor for the booksCatalog class.  All that it does is stash the provided EntityManager
    * for use later in the application.
    * @param manager    The EntityManager that we will use.
    */
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

      //String email, String name, String head_writer, Integer year_formed
      writing_groups.add(new writing_groups("proudlywritters@writters.com","Proudly Wrtters","Alan James",1980));
      writing_groups.add(new writing_groups("storytellers@writters.com","Storytellers","Emma Joe",1978));
      writing_groups.add(new writing_groups("Penmaniacs@writters.com","Penmaniacs","Emerson Fabio",2000));

      individual_authors.add(new individual_authors("agathachristie@hotmail.com", "Agatha Christie",null ));
      individual_authors.add(new individual_authors("julesverne@hotmail.com","Jules Verne",null));
      individual_authors.add(new individual_authors("enidblyton@gmail.com","Enid Blyton",null));

      ad_hoc_teams.add(new ad_hoc_teams("justicefortheinnocents@adhocteams.com","Justice For The Innocents",null));
      ad_hoc_teams.add(new ad_hoc_teams("abetterecosystem@adhocteams.com","A Better Ecosystem",null));
      ad_hoc_teams.add(new ad_hoc_teams("stationone@adhocteams.com", "Station One", null));
      // publishers.add(publishersForm());

      ad_hoc_teams.get(0).setIndividual_authors(booksCatalog.getEmail("Jules Verne"));


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

      tx.commit();
      LOGGER.fine("End of Transaction");





   } // End of the main method

   /**
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

      // The auto generated ID (if present) is not passed in to the constructor since JPA will
      // generate a value.  So the previous for loop will not show a value for the ID.  But
      // now that the Entity has been persisted, JPA has generated the ID and filled that in.
      for (E next : entities) {
         LOGGER.info("Persisted object after flush (non-null id): " + next);
      }
   } // End of createEntity member method

   /**
    * Think of this as a simple map from a String to an instance of auto_body_styles that has the
    * same name, as the string that you pass in.  To create a new Cars instance, you need to pass
    * in an instance of auto_body_styles to satisfy the foreign key constraint, not just a string
    * representing the name of the style.
    * @paramname       The name of the autobody style that you are looking for.
    * @return           The auto_body_styles instance corresponding to that style name.
    */
   public List<individual_authors> getEmail (String name) {
      // Run the native query that we defined in the auto_body_styles entity to find the right style.
      List<individual_authors> authors = this.entityManager.createNamedQuery("ReturnIndividualAuthor",
              individual_authors.class).setParameter(1, name).getResultList();
      if (authors.size() == 0) {
         // Invalid style name passed in.
         return null;
      } else {
         // Return the individual_author object that they asked for.
         return (List<individual_authors>) authors.get(0);
      }
   }
   /// End of the getStyle method

   public static Publishers publishersForm(){
      String name = JOptionPane.showInputDialog("Publisher Name: ");
      String email = JOptionPane.showInputDialog("Publisher Email: ");
      String phone = JOptionPane.showInputDialog("Publisher Phone Number: ");

      Publishers publisher = new Publishers(name,email,phone);
      return publisher;
   }


} // End of booksCatalog class
