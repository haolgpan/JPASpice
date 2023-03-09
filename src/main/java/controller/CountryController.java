package controller;


import model.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CountryController {

    private Connection connection;
    private EntityManagerFactory entityManagerFactory;

    public CountryController(Connection connection) {
        this.connection = connection;
    }

    public CountryController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }
    /**
     * @param filename Aquest String correspon amb l'arxiu on s'emmagatzemen les
     *                 dades de les instancies de Country
     * @throws IOException <dt><b>Preconditions:</b>
     *                     <dd>
     *                     filename<>nil </br> llistaCountry == nil
     *                     <dt><b>Postconditions:</b>
     *                     <dd>
     *                     llistaCountry<>nil
     */

    //Method to READ a Country in a txt format
    public List<Country> readCountryFile(String filename) throws IOException {
        int id;
        String code, country;
        float longitud, latitude;
        List<Country> countrieslist = new ArrayList<Country>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String linea = "";
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, ",");
            id = Integer.parseInt(str.nextToken());
            code = str.hasMoreTokens() ? str.nextToken() : "";
            longitud = str.hasMoreTokens() ? Float.parseFloat(str.nextToken()) : 0.0f;
            latitude = str.hasMoreTokens() ? Float.parseFloat(str.nextToken()) : 0.0f;
            country = str.hasMoreTokens() ? str.nextToken() : "";
            countrieslist.add(new Country(id, code, latitude, longitud, country));

        }
        br.close();
        return countrieslist;
    }

    public void printCountries(ArrayList<Country> countriesList) {
        for (int i = 0; i < countriesList.size(); i++) {
            System.out.println(countriesList.get(i).toString());
        }
    }


    /* Method to CREATE a Country in the database */
    public void addCountry(Country country) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Country countryExists = (Country) em.find(Country.class, country.getCountryId());
        em.merge(country);
        em.getTransaction().commit();
        em.close();
    }


    /* Method to READ all Country */
    public void listCountries() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Country> result = em.createQuery("from Country", Country.class)
                .getResultList();
        for (Country country : result) {
            System.out.println(country.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    /* Method to UPDATE activity for a country */
    public void updateCountry(Integer countryId, String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Country country = (Country) em.find(Country.class, countryId);
        if (country != null) {
            country.setName(name);
            em.merge(country);
            em.getTransaction().commit();
            em.close();
            System.out.println("País amb ID " + countryId + " actualitzat correctament.");
        } else {
            System.out.println("País amb ID " + countryId + " no trobat.");
        }
    }

    /* Method to DELETE a country from the records */
    public void deleteCountry(Integer countryId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Country country = (Country) em.find(Country.class, countryId);
        em.remove(country);
        em.getTransaction().commit();
        em.close();
    }

}
