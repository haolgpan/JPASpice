package controller;


import model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductController {

    private Connection connection;
    private EntityManagerFactory entityManagerFactory;

    public ProductController(Connection connection) {
        this.connection = connection;
    }

    public ProductController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * @param filename Aquest String correspon amb l'arxiu on s'emmagatzemen les
     *                 dades de les instancies de Revista
     * @throws IOException <dt><b>Preconditions:</b>
     *                     <dd>
     *                     filename<>nil </br> llistaRevistes == nil
     *                     <dt><b>Postconditions:</b>
     *                     <dd>
     *                     llistaRevistes<>nil
     */

    public List<Product> readProductFile(String filename) throws IOException {
        int prodcutId;
        String format;
        List<Product> productList = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String linea = "";
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            prodcutId = Integer.parseInt(str.nextToken());
            format = str.nextToken();
            productList.add(new Product(prodcutId, format));

        }
        br.close();
        System.out.println("m:" + productList);
        return productList;
    }

    public void printProduct(ArrayList<Product> productsList) {
        for (int i = 0; i < productsList.size(); i++) {
            System.out.println(productsList.get(i).toString());
        }
    }

    /* Method to CREATE a Product  in the database */
    public void addProduct(Product product) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
        em.close();
    }

    /* Method to READ all Prodcut */
    public void listProducts() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Product> result = em.createQuery("from Product", Product.class)
                .getResultList();



        for (Product product : result) {
            System.out.println(product.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    /* Method to UPDATE activity for a Product */
    public void updateProduct(Integer productId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Product product = (Product) em.find(Product.class, productId);
        em.merge(product);
        em.getTransaction().commit();
        em.close();
    }

    /* Method to DELETE a Product from the records */
    public void deleteProduct(Integer productId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Product product = (Product) em.find(Product.class, productId);
        em.remove(product);
        em.getTransaction().commit();
        em.close();
    }
}
