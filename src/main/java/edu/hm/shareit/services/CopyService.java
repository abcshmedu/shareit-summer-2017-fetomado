package edu.hm.shareit.services;

import edu.hm.shareit.models.Copy;

/**
 * Interface for the service managing copies of mediums.
 */
public interface CopyService {

    /**
     * Add a new copy of a given medium.
     * @param owner  the owner of the copy
     * @param medium the medium of which a copy should be created
     * @return the result of the operation
     */
    ServiceResult addCopy(String owner, String medium);

    /**
     * Returns all copies that exist in the service.
     * @return an array with all copies
     */
    Copy[] getCopies();

    /**
     * Get a specific copy.
     * @param id the ID of the requested copy
     * @return the copy is it exists, null otherwise
     */
    Copy getCopy(int id);

    /**
     * Update the data of a specific copy.
     * @param id the ID of the copy to update
     * @param owner the new owner of the copy
     * @return the result of the operation
     */
    ServiceResult updateCopy(int id, String owner);
}

