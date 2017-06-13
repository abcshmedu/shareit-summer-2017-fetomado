package edu.hm.shareit.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

/**
 * This is the model class representing a disc.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonPropertyOrder({"title","barcode","director","fsk"})
public class Disc extends Medium {

    @Id private String barcode;
    private String director;
    private Integer fsk;

    /**
     * Constructs a new disc object.
     * @param title    the title of the disc
     * @param barcode  the barcode of the disc
     * @param director the director of the disc
     * @param fsk      the FSK of the disc
     */
    public Disc(String title, String barcode, String director, Integer fsk) {
        super(title);
        this.barcode = barcode;
        this.director = director;
        this.fsk = fsk;
    }

    /**
     * Private constructor needed for Jackson compatibility
     */
    private Disc() {
        super("");
    }

    /**
     * Returns the barcode number of the disc.
     * @return barcode unique identifier of the disc
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Returns the director of the disc.
     * @return director of the disc
     */
    public String getDirector() {
        return director;
    }

    /**
     * Sets the director of the disc.
     * @param director of the disc
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Returns the FSK number of the disc.
     * @return fsk of the disc
     */
    public Integer getFsk() {
        return fsk;
    }

    /**
     * Sets the FSK number of the disc.
     * @param fsk of the disc
     */
    public void setFsk(Integer fsk) {
        this.fsk = fsk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disc disc = (Disc) o;

        if (barcode != null ? !barcode.equals(disc.barcode) : disc.barcode != null) return false;
        if (director != null ? !director.equals(disc.director) : disc.director != null) return false;
        return fsk != null ? fsk.equals(disc.fsk) : disc.fsk == null;
    }

    @Override
    public int hashCode() {
        int result = barcode != null ? barcode.hashCode() : 0;
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (fsk != null ? fsk.hashCode() : 0);
        return result;
    }
}
