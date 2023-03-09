package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "spices")
public class Spice implements Serializable {
    @Id
    @Column(name = "id_spice")
    int spiceId;
    @Column(name = "name", length = 100)
    String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country")
    public Country country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    public Product product;

    public Spice(int spiceId, String name, Product product, Country country) {
        super();
        this.spiceId = spiceId;
        this.name = name;
        this.country = country;
        this.product = product;
    }

    public Spice() {
        super();

    }

    public int getSpiceId() {
        return spiceId;
    }

    public void setSpiceId(int spiceId) {
        this.spiceId = spiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
//    public void addSpice(Spice spice) {
//        spices.add(spice);
//    }
//    public List<Spice> getSpices() {
//        return spices;
//    }
//
//    public void setSpice(List<Spice> spices) {
//        this.spices = spices;
//    }

    @Override
    public String toString() {
        return "Spice{" +
                "spiceId=" + spiceId +
                ", name='" + name + '\'' +
                ", country=" + country.toString() +
                ", product=" + product.toString() +
                '}';
    }
}
