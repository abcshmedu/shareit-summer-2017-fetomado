package edu.hm.shareit.models;

/**
 * This is the model class representing a disc.
 */
public class Disc extends Medium {

	private String barcode, director;
	private int fsk;
	
	/**
	 * Constructs a new disc object.
	 * @param title the title of the disc
	 * @param barcode the barcode of the disc
	 * @param director the director of the disc
	 * @param fsk the FSK of the disc
	 */
	public Disc(String title, String barcode, String director, int fsk) {
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
	 * @return the barcode
	 */
	public  String getBarcode(){
		return barcode;
	}
	
	/**
	 * Returns the director of the disc.
	 * @return the director
	 */
	public String getDirector(){
		return director;
	}
	
	public void setDirector (String director){
	    this.director = director;
	}
	
	/**
	 * Returns the FSK number of the disc.
	 * @return the FSK number
	 */
	public int getFsk(){
		return fsk;
	}
	
	public void setFsk(int fsk) {
	    this.fsk = fsk;  
	}
}
