import controller.CountryController;
import controller.ProductController;
import controller.SpiceController;
import controller.TableController;
import database.ConnectionFactory;
import model.Country;
import model.Product;
import model.Spice;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static SessionFactory sessionFactoryObj;
/*
  private static SessionFactory buildSessionFactory() {
    // Creating Configuration Instance & Passing Hibernate Configuration File
    Configuration configObj = new Configuration();
    configObj.configure("hibernate.cfg.xml");

    // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
    ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

    // Creating Hibernate SessionFactory Instance
    sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
    return sessionFactoryObj;
  } */

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();

        } catch (HibernateException he) {
            System.out.println("Session Factory creation failure");
            throw he;
        }
    }

    public static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("JPASpice");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }

    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList();

        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection c = connectionFactory.connect();

//    SessionFactory sessionFactory = buildSessionFactory();
        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
        //sessionObj = buildSessionFactory().openSession();


        CountryController countryController = new CountryController(c, entityManagerFactory);
        SpiceController spiceController = new SpiceController(c, entityManagerFactory);
        ProductController productController = new ProductController(c, entityManagerFactory);
        TableController tableController = new TableController(c,entityManagerFactory);
        Scanner scanner = new Scanner(System.in);
        List<Country> countries = null;
        List<Product> productes = null;
        List<Spice> spices = null;
        String name = null;
        try {
            countries = countryController.readCountryFile("src/main/resources/country.txt");
            productes = productController.readProductFile("src/main/resources/product.txt");
            spices = spiceController.readSpicesFile("src/main/resources/especias.txt", "src/main/resources/country.txt", "src/main/resources/product.txt");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//
        int id = 0;
        int id_prod = 0;
        int id_spice = 0;
        Menu menu = new Menu();
        int opcio;
        opcio = menu.mainMenu();
        while (opcio != 0) {
            switch (opcio) {
                case 0:
                    System.out.println("Adeu!!");
                    System.exit(1);
                    break;
                case 1:
                    try {
                        // countryController.printAutors(countryController.readAuthorsFile("src/main/resources/autors.txt"));
                        // for (Author a : countries) {
                        //   countryController.addAuthor(a);
                        // }
                        // productController.printProduct(productController.readProductFile("src/main/resources/revistes.txt"));
                        // productController.printProduct();
//                         System.out.println("Revistes llegides des del fitxer");
//                    for (int i = 0; i < spices.size(); i++) {
//                        System.out.println(spices.get(i).toString()+"\n");
//                        for (int j = 0; j < spices.get(i).getSpices().size(); j++) {
//                            Country country = countries.get(i).getCountry();
//                            countryController.addCountry(country);
//
//                            System.out.println("PAÍS:");
//                            System.out.println(country);
//
//                            Spice spice = spices.get(i);
//                            spice.setCountry(country);
//
//                            System.out.println("LA ESPECIA:");
//                            System.out.println(spice);
//
//                            spiceController.addSpice(spice);
//                        }
//
//                        spiceController.addSpice(spices.get(i));
//                    }
                        for (Product p : productes) {
                            System.out.println(p);
                            productController.addProduct(p);
                        }
                        for (Country country : countries) {
                            System.out.println(country);
                            countryController.addCountry(country);
                        }

                        for (Spice s : spices) {
                            System.out.println(s);
                            spiceController.addSpice(s);
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try{
                        tableController.deleteContent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        tableController.addTable();
                    }catch (Exception e){
                        System.out.println("Les taules ja estan creades");
                    }
                    break;
                case 4:
                    try {
                        tableController.deleteTable();
                    }catch (Exception e){
                        System.out.println("No hi ha taules per eliminar");
                    }
                    break;
                case 5:
                    try {
                        spiceController.infoSpice();
                        break;
                    }catch (Exception e){
                        System.out.println("No hi ha especias per mostrar");
                    }
                case 6:
                    spiceController.listSpices();
                    System.out.println("Introdueix Id de la espècia a esborrar: ");
                    id = scanner.nextInt();
                    try {
                        spiceController.deleteSpice(id);
                        spiceController.listSpices();
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                case 7:
                    spiceController.listSpices();
                    System.out.println("Introdueix Id de la espècia a actualitzar: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        System.out.println("Introdueix el nou nom.");
                        name = scanner.nextLine();
                        spiceController.updateSpice(id,name);
                        spiceController.listSpices();
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                case 8:
                    spiceController.listSpices();
                    try {
                        System.out.println("Introdueix un Id per la nova espècia: ");
                        id_spice = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Introdueix el nom de la espècia: ");
                        name = scanner.nextLine();
                        countryController.listCountries();
                        System.out.println("Introdueix id del país: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                    }catch (InputMismatchException e){
                        System.out.println("Entrada de dades incorrectes");
                    }
                    while (id < 0 || id > 224) {
                        System.out.println("No existeix el país seleccionat, torna a introduir un ID país correcte");
                        id = scanner.nextInt();
                        scanner.nextLine();
                    }
                    productController.listProducts();
                    System.out.println("Introdueix id del format de producte: ");
                    id_prod = scanner.nextInt();
                    scanner.nextLine();
                    while (id_prod < 0 || id_prod > 24) {
                        System.out.println("No existeix el format seleccionat, torna a introduir un ID pformat correcte");
                        id_prod = scanner.nextInt();
                        scanner.nextLine();
                    }
                    try {
                        spiceController.insertSpice(id_spice,name, countries.get(id), productes.get(id_prod));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                case 9:
                    spiceController.listSpices();
                    break;
                case 10:
                    countryController.printCountries(countries);
                    break;
                case 11:
                    productController.printProduct(productes);
                    break;
                case 12:
                    System.out.println("Introdueix Id del país a actualitzar: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        System.out.println("Introdueix el nou nom.");
                        name = scanner.nextLine();
                        countryController.updateCountry(id,name);
                        countryController.listCountries();
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                case 13:
                    System.out.println("Introdueix Id del format a actualitzar: ");
                    id_prod = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        System.out.println("Introdueix el nou format.");
                        name = scanner.nextLine();
                        productController.updateProduct(id_prod,name);
                        countryController.listCountries();
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                case 14:
                    countryController.listCountries();
                    System.out.println("Introdueix Id del país a esborrar: ");
                    id = scanner.nextInt();
                    try {
                        countryController.deleteCountry(id);
                        countryController.listCountries();
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                case 15:
                    productController.listProducts();
                    id = scanner.nextInt();
                    try {
                        productController.deleteProduct(id);
                        productController.listProducts();
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                default:
//                    System.out.println("No existeix la opció escollida.");
                    break;
            }
            opcio = menu.mainMenu();
        }
    }
}
/*
    static User userObj;
    static Session sessionObj;
    static SessionFactory sessionFactoryObj;

    private static SessionFactory buildSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");

        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }

    public static void main(String[] args) {
        System.out.println(".......Hibernate Maven Example.......\n");
        try {
            sessionObj = buildSessionFactory().openSession();
            sessionObj.beginTransaction();

            for(int i = 101; i <= 105; i++) {
                userObj = new User();
                userObj.setUserid(i);
                userObj.setUsername("Editor " + i);
                userObj.setCreatedBy("Administrator");
                userObj.setCreatedDate(new Date());

                sessionObj.save(userObj);
            }
            System.out.println("\n.......Records Saved Successfully To The Database.......\n");

            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
        } catch(Exception sqlException) {
            if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }
    }
 */
