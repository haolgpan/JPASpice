package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Connection;

public class TableController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;

    public TableController(Connection connection) {
        this.connection = connection;
    }

    public TableController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addTable() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        // create the table
        em.createNativeQuery("CREATE TABLE country\n" +
                "(\n" +
                "  id_country integer NOT NULL,\n" +
                "  code character varying(2),\n" +
                "  latitude float,\n" +
                "  longitud float,\n" +
                "  name character varying(100) NOT NULL,\n" +
                "  CONSTRAINT pk_country PRIMARY KEY (id_country)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE product\n" +
                "(\n" +
                "  id_product integer NOT NULL,\n" +
                "  format character varying(30) NOT NULL,\n" +
                "  CONSTRAINT pk_product PRIMARY KEY (id_product),\n" +
                "  CONSTRAINT uk_format UNIQUE (format)\n" +
                ");\n" +
                "\n" +
                "\n" +
                "CREATE TABLE spices (\n" +
                "    id_spice int,\n" +
                "    id_country integer NOT NULL,\n" +
                "    id_product integer NOT NULL,\n" +
                "    name character varying(100) NOT NULL,\n" +
                "      CONSTRAINT fk_art_pais FOREIGN KEY (id_country)\n" +
                "          REFERENCES country (id_country) MATCH SIMPLE\n" +
                "          ON UPDATE NO ACTION ON DELETE NO ACTION,\n" +
                "      CONSTRAINT fk_articles_prodcut FOREIGN KEY (id_product)\n" +
                "          REFERENCES product (id_product) MATCH SIMPLE\n" +
                "          ON UPDATE NO ACTION ON DELETE NO ACTION,\n" +
                "      CONSTRAINT uk_spices UNIQUE (name)\n" +
                ");\n").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
    public void deleteTable(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DROP TABLE spices; DROP TABLE country; DROP TABLE product;").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
    public void deleteContent(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE spices CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE country CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE product CASCADE").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

}
