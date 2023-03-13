package controller;

import model.Country;
import model.Product;
import model.Spice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SpiceController {

    private Connection connection;
    private EntityManagerFactory entityManagerFactory;
    private Scanner scanner = new Scanner(System.in);

    private ProductController productController = new ProductController(connection);
    private CountryController countryController = new CountryController(connection);

    public SpiceController(Connection connection) {
        this.connection = connection;
    }

    public SpiceController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * @param spicesFile Aquest String correspon amb l'arxiu on s'emmagatzemen les
     *                   dades de les isntancies de Especia
     * @return ArrayList d'objectes Spice, amb els seus països i la
     * informació de formt de producte.
     */
//    public List<Product> readProductFile(String spiceFile, String countryFile, String productFile)
//            throws IOException {
//        int spiceId, countryId, prodcutId;
//        String name;
//
//        BufferedReader br = new BufferedReader(new FileReader(spiceFile));
//        String linea = "";
//
//        List<Product> productsList = productController.readProductFile(productFile);
//        List<Country> countryList = countryController.readCountryFile(countryFile);
//        List<Spice> spiceList = readSpicesFile(spiceFile,productFile);
//
//        while ((linea = br.readLine()) != null) {
//            StringTokenizer str = new StringTokenizer(linea, ",");
//            spiceId = Integer.parseInt(str.nextToken());
//            countryId = Integer.parseInt(str.nextToken());
//            prodcutId = Integer.parseInt(str.nextToken());
//            name = str.nextToken();
//            System.out.println(spiceId);
//            spiceList.get(spiceId).addSpice(new Spice(spiceId, name,productsList.get(prodcutId) ,countryList.get(countryId)));
//
//        }
//        br.close();
//
//        return productsList;
//    }
    //Method to READ a Spice in a txt format
    public List<Spice> readSpicesFile(String spicesFile, String countryFile, String productFile) throws IOException {
        int spiceId, productId, countryId;
        String name;

        BufferedReader br = new BufferedReader(new FileReader(spicesFile));
        String linea = "";
        List<Country> countryList = countryController.readCountryFile(countryFile);
        List<Product> productList = productController.readProductFile(productFile);
        List<Spice> spicesList = new ArrayList<Spice>();

        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            spiceId = Integer.parseInt(str.nextToken());
            countryId = Integer.parseInt(str.nextToken());
            productId = Integer.parseInt(str.nextToken());
            name = str.nextToken();
            spicesList.add(new Spice(spiceId, name, productList.get(productId), countryList.get(countryId - 1)));
        }
        br.close();
        return spicesList;
    }

    /* Method to CREATE a Spice in the database */
    public void addSpice(Spice spice) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(spice);
        em.getTransaction().commit();
        em.close();
    }

    /* Method to READ all Spices */
    public void listSpices() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Spice> result = em.createQuery("from Spice", Spice.class)
                .getResultList();
        for (Spice spice : result) {
            System.out.println(spice.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    /* Method to UPDATE activity for a Spice */
    public void updateSpice(Integer spiceId, String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Spice spice = em.find(Spice.class, spiceId);
        if (spice != null) {
            spice.setName(name);
            em.merge(spice);
            em.getTransaction().commit();
            em.close();
            System.out.println("Spice amb ID " + spiceId + " actualitzat correctament.");
        } else {
            System.out.println("Spice amb ID " + spiceId + " no trobat.");
        }
    }

    /* Method to DELETE a Spice from the records */
    public void deleteSpice(Integer spiceId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Spice spice = (Spice) em.find(Spice.class, spiceId);
        if(spice != null){
            em.remove(spice);
            em.getTransaction().commit();
            em.close();
            System.out.println("Esborrat la espècia amb ID: " + spiceId);
        }
        else System.out.println("No existeix ID introduït");
    }
    /*Method to show all info of a Spice */
    public void infoSpice() {
        EntityManager em = entityManagerFactory.createEntityManager();
        System.out.println("Introdueix un ID de la spice:");
        int id = scanner.nextInt();
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT s.name, p.format, c.name " +
                        "FROM Spice s " +
                        "JOIN s.product p " +
                        "JOIN s.country c " +
                        "WHERE s.id = :id",
                Object[].class);
        query.setParameter("id", id);
        List<Object[]> resultList = query.getResultList();
        for (Object[] result : resultList) {
            String spiceName = (String) result[0];
            String productFormat = (String) result[1];
            String countryName = (String) result[2];
            System.out.println(spiceName + ", " + productFormat + ", " + countryName);
            System.out.println("----------------------------------------------------");
        }
        if(resultList.isEmpty()) System.out.println("No existeix el ID introduït");
    }

    /* Method to CREATE a Spice into the the table Spice */
    public void insertSpice(int spiceId, String name, Country country, Product product) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Spice spice = em.find(Spice.class, spiceId);
        if (spice != null) {
            System.err.println("Error: Spice amb ID " + spiceId + " ja existeix.");
        } else {
            Spice newSpice = new Spice();
            newSpice.setSpiceId(spiceId);
            newSpice.setName(name);
            newSpice.setCountry(country);
            newSpice.setProduct(product);
            em.persist(newSpice);
            em.getTransaction().commit();
            System.out.println("Spice amb ID " + spiceId + " s'ha afegit correctament.");
        }
        em.close();
    }

    /* Method to filter a Spice by Country */
    public void spiceByCountry(String countryName){
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Spice> query = em.createQuery(
                "SELECT s FROM Spice s WHERE s.country.name = :name",
                Spice.class);
        query.setParameter("name", countryName);
        List<Spice> spices = query.getResultList();

        if (spices.isEmpty()) {
            System.out.println("No s'ha trobat espècias del país " + countryName);
        } else {
            System.out.println("Espècias del país " + countryName + ":");
            for (Spice spice : spices) {
                System.out.println(spice);
            }
        }
        em.close();
    }
    /* Method to filter a Spice by Format */
    public void spiceByFormat(String format){
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Spice> query = em.createQuery(
                "SELECT s FROM Spice s WHERE s.product.format = :name",
                Spice.class);
        query.setParameter("name", format);
        List<Spice> spices = query.getResultList();

        if (spices.isEmpty()) {
            System.out.println("No s'han trobat espècias del format " + format);
        } else {
            System.out.println("Espècias de format " + format + ":");
            for (Spice spice : spices) {
                System.out.println(spice);
            }
        }
        em.close();
    }
    /* Method to DELETE a Spice by Country */
    public void deleteByCountry(String country){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Spice> query = em.createQuery(
                "SELECT s FROM Spice s WHERE s.country.name = :name",
                Spice.class
        );
        query.setParameter("name", country);
        List<Spice> spices = query.getResultList();
        if (spices.isEmpty()) {
            System.out.println("No s'ha trobat espècias del país " + country);
        } else {
            for (Spice spice : spices) {
                em.remove(spice);
            }
            em.getTransaction().commit();
            System.out.println("Espècias del país " + country + " esborrats correctament.");
        }
        em.close();
    }
    /* Method to DELETE a Spice by Format */
    public void deleteByFormat(String format){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Spice> query = em.createQuery(
                "SELECT s FROM Spice s WHERE s.product.format = :format",
                Spice.class
        );
        query.setParameter("format", format);
        List<Spice> spices = query.getResultList();
        if (spices.isEmpty()) {
            System.out.println("No s'han trobat espècias del format " + format + ".");
        } else {
            for (Spice spice : spices) {
                em.remove(spice);
            }
            em.getTransaction().commit();
            System.out.println("Espècias amb format de producte " + format + " esborrats correctament.");
        }
        em.close();
    }

}
