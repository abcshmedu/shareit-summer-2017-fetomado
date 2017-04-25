package edu.hm.shareit.models;

/**
 * This class represents a copy of a medium.
 */
public class Copy {

    private Medium medium;
    private String owner;

    /**
     * Constructs a new copy of a medium.
     * @param owner the owner of the copy
     * @param medium the medium of the copy
     */
    public Copy(String owner, Medium medium) {
        this.owner = owner;
        this.medium = medium;
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
}
