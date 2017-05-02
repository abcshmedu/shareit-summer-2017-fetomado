package edu.hm.shareit.models;

/**
 * This is the model class representing a disc.
 */
public class Disc extends Medium {

    private String barcode, director;
    private Integer fsk;

    /**
     * Constructs a new disc object.
     *
     * @param title the title of the disc
     * @param barcode the barcode of the disc
     * @param director the director of the disc
     * @param fsk the FSK of the disc
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
}
