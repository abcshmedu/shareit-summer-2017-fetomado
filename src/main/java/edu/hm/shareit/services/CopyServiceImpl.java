package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Copy;

import java.util.HashMap;
import java.util.Map;

public class CopyServiceImpl implements CopyService {

    private static Map<Integer, Copy> copys;

    public CopyServiceImpl() {
        if (copys == null) {
            copys = new HashMap<>();
            Book book = new Book("what if?", "Randall Munroe", "978-3-8135-0625-5");
            Copy copy = new Copy("Egon", book);
            copys.put(copy.getId(), copy);
        }
    }


    @Override
    public ServiceResult addCopy(String owner, String medium) {
        return ServiceResult.OK;
    }

    @Override
    public Copy[] getCopys() {
        return copys.values().toArray(new Copy[copys.size()]);
    }

    @Override
    public Copy getCopy(int id) {
        return copys.get(id);
    }

    @Override
    public ServiceResult updateCopy(int id, String owner) {
        return null;
    }
}
