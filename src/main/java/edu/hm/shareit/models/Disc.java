package edu.hm.shareit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;

/**
 * This is the model class representing a disc.
 */
@Entity
@JsonPropertyOrder({"title", "barcode", "director", "fsk"})
@JsonIgnoreProperties({"code"})
public class Disc extends Medium {

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
        this.setCode(barcode);
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
        return getCode();
    }

    /**
     * Sets the barcode of the disc.
     * @param barcode the new barcode
     */
    public void setBarcode(String barcode) {
        setCode(barcode);
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Disc disc = (Disc) o;

        if (getCode() != null ? !getCode().equals(disc.getCode()) : disc.getCode() != null) {
            return false;
        }
        if (director != null ? !director.equals(disc.director) : disc.director != null) {
            return false;
        }
        return fsk != null ? fsk.equals(disc.fsk) : disc.fsk == null;
    }

    @Override
    public int hashCode() {
        int result = getCode() != null ? getCode().hashCode() : 0;
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (fsk != null ? fsk.hashCode() : 0);
        return result;
    }
}
