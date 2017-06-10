package edu.hm.shareit.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a class representing some kind of media.
 */
@MappedSuperclass
public class Medium implements Serializable {

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

    /**
     * Sets the title of the medium.
     * @param title the medium's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
