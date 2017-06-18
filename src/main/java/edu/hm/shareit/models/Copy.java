package edu.hm.shareit.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * This class represents a copy of a medium.
 */
@Entity
@JsonPropertyOrder({"medium", "owner"})
public class Copy {

    private static int idCounter = 1;

    @Id
    private int id;
    @ManyToOne
    private Medium medium;
    private String owner;

    /**
     * Constructs a new copy of a medium.
     * @param owner  the owner of the copy
     * @param medium the medium of the copy
     */
    public Copy(String owner, Medium medium) {
        this.owner = owner;
        this.medium = medium;
        id = idCounter;
        idCounter++;
    }

    /**
     * Private constructor needed for Jackson compatibility
     */
    private Copy() {
    }

    /**
     * Returns the medium of the copy.
     * @return the medium
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     * Returns the owner of the copy.
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the copy.
     * @param owner of the copy
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Returns the id of the copy.
     * @return the id
     */
    public int getId() {
        return id;
    }
}
