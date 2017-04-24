package edu.hm.shareit.models;

/**
 * This is a class representing some kind of media.
 */
public class Medium {

    private String title;

    /**
     * Constructs a new medium.
     * @param title the title of the medium
     */
    public Medium(String title) {
        this.title = title;
    }

    /**
     * Returns the title of the medium.
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title=title;
    }

}
