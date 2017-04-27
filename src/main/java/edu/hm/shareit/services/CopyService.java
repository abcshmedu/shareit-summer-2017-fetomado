package edu.hm.shareit.services;

import edu.hm.shareit.models.Copy;
import edu.hm.shareit.models.Medium;

public interface CopyService {
    ServiceResult addCopy(Copy copy);

    Copy [] getCopys();

    Copy getCopy(int id);

    ServiceResult updateCopy(int id, Copy copy);
}

