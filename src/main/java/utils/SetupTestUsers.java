package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test123");
    User admin = new User("admin", "test123");
    User both = new User("user_admin", "test123");

    Car car1 = new Car("Lynet","Mercedes","Series 3","2018","Rolex","Silver",new ArrayList<>(), new ArrayList<>());
    Car car2 = new Car("Bravo","BMW","MX3","2020","DC","Black",new ArrayList<>(), new ArrayList<>());
    Car car3 = new Car("test","test","test","test","test","test",new ArrayList<>(), new ArrayList<>());

    Driver driver1 = new Driver("James Brown","1997","amateur","male", user, car1);
    Driver driver2 = new Driver("Anna West", "2001", "professional", "female",admin, car2);
    Driver driver3 = new Driver("test", "test", "test", "test",both, car3);

    Race race1 = new Race("Nas", "Miami", "27-06-22", "210", new ArrayList<>());
    Race race2 = new Race("Le Mans", "Nice", "17-08-22", "210", new ArrayList<>());

    car1.addDriver(driver1);
    car2.addDriver(driver2);
    car3.addDriver(driver3);

    race1.addCar(car1);
    race1.addCar(car2);
    race2.addCar(car1);

    car1.addRace(race1);
    car1.addRace(race2);
    car2.addRace(race1);

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
    {
      throw new UnsupportedOperationException("You have not changed the passwords");
    }

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);

    em.persist(userRole);
    em.persist(adminRole);

    em.persist(admin);
    em.persist(both);
    em.persist(user);

    em.persist(driver1);
    em.persist(driver2);
    em.persist(driver3);
    em.persist(car1);
    em.persist(car2);
    em.persist(car3);
    em.persist(race1);
    em.persist(race2);

    em.getTransaction().commit();

    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test123"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
