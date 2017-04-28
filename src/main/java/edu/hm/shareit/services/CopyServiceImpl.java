package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Copy;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
        MediaServiceImpl tmpMsr = new MediaServiceImpl();
        Copy copy;
        Medium mediumToCopy = null;
        String regexISBN = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$";
        String regexBarcode = "^[1-9][0-9]{8,14}$";
        Pattern patternISBN = Pattern.compile(regexISBN);
        Pattern patternBarcode = Pattern.compile(regexBarcode);
        if (patternISBN.matcher(medium).matches()) {
            mediumToCopy = tmpMsr.getBook(medium);
        } else if (patternBarcode.matcher(medium).matches()) {
            mediumToCopy = tmpMsr.getDisc(medium);
        }
        if (mediumToCopy == null) {
            return ServiceResult.NOT_FOUND;
        }
        copy = new Copy(owner, mediumToCopy);
        copys.put(copy.getId(), copy);
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
