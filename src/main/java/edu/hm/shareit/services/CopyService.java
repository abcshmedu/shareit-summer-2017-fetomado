package edu.hm.shareit.services;

import edu.hm.shareit.models.Copy;

public interface CopyService {
    ServiceResult addCopy(String owner, String medium);

    Copy[] getCopies();

    Copy getCopy(int id);

    ServiceResult updateCopy(int id, String owner);
}

