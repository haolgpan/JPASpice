package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name = "country")
public class Country implements Serializable {
    @Id
    @Column(name = "id_country")
    int countryId;
    @Column(name = "code", length = 2)
    String code;
    @Column(name = "name", length = 30)
    String name;
    @Column(name = "latitude")
    float latitude;
    @Column(name = "longitud")
    float longitud;

    public Country(int countryId, String code, float latitude,
                   float longitud ,String name) {
        super();
        this.longitud = longitud;
        this.name = name;
        this.latitude = latitude;
        this.code = code;
        this.countryId = countryId;
    }

    public Country() {

    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    @Override
    public String toString() {
        return "Country [id_country=" + countryId + ", nom=" + name + ", code=" + code
                + ", longitudt=" + longitud + ", latitude=" + latitude
                + "]";
    }



}
