package edu.hm.shareit.services;

import edu.hm.shareit.models.Copy;

public interface CopyService {
    ServiceResult addCopy(String owner, String medium);

    Copy[] getCopys();

    Copy getCopy(int id);

    ServiceResult updateCopy(int id, Copy copy);
}

