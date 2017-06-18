package edu.hm.shareit.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

/**
 * This is a class representing some kind of media.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Medium implements Serializable {

    @Id
    private String code;
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

    /**
     * Returns the identifying code of this medium.
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the identifying code of this medium.
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
