package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @Column(name = "id_product")
    private int productId;
    @Column(name = "format", length = 30)
    private String format;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id_product", referencedColumnName = "id_product")
//    private List<Spice> spices = new ArrayList<Spice>();

    public Product(int productId, String format) {
        super();
        this.productId = productId;
        this.format = format;
    }

    public Product() {
        super();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

//    public void addSpice(Spice spice) {
//        spices.add(spice);
//    }
//
//    public Spice getSpice(int i) {
//        return spices.get(i);
//    }
//
//    public List<Spice> getSpices() {
//        return spices;
//    }
//
//    public void setSpice(List<Spice> spices) {
//        this.spices = spices;
//    }


    @Override
    public String toString() {
        String result = "Producte [id_producte=" + productId + ",format=" + format  + "]";

//        result += "\n Llista de productes: [ \n";
//
//        for (Spice s : spices) {
//            result += "\t";
//            result += s.toString();
//            result += "\n";
//        }
//
//        result += "] \n";

        return result;
    }

}

