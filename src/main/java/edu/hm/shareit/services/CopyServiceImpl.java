package edu.hm.shareit.services;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Copy;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;
import edu.hm.shareit.persistence.Persistence;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This is the implementation of the CopyService interface. It does the work
 * behind the /copies/ resource.
 */
public class CopyServiceImpl implements CopyService {

    @Inject
    private Persistence persist;

    @Override
    public ServiceResult addCopy(String owner, String medium) {
        Medium mediumToCopy = null;
        String regexISBN = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$";
        String regexBarcode = "^[1-9][0-9]{8,14}$";
        Pattern patternISBN = Pattern.compile(regexISBN);
        Pattern patternBarcode = Pattern.compile(regexBarcode);
        if (patternISBN.matcher(medium).matches()) {
            mediumToCopy = persist.get(Book.class, medium);
        } else if (patternBarcode.matcher(medium).matches()) {
            mediumToCopy = persist.get(Disc.class, medium);
        }
        if (mediumToCopy == null) {
            return ServiceResult.NOT_FOUND;
        }
        Copy copy = new Copy(owner, mediumToCopy);
        persist.add(copy);
        return ServiceResult.OK;
    }

    @Override
    public Copy[] getCopies() {
        List<Copy> copies = persist.getAll(Copy.class);
        return copies.toArray(new Copy[copies.size()]);
    }

    @Override
    public Copy getCopy(int id) {
        if (persist.exist(Copy.class, id)) {
            return persist.get(Copy.class, id);
        } else {
            return null;
        }
    }

    @Override
    public ServiceResult updateCopy(int id, String owner) {
        if (!persist.exist(Copy.class, id)) {
            return ServiceResult.NOT_FOUND;
        }
        if ((owner == null || owner.equals(""))) {
            return ServiceResult.BAD_REQUEST;
        }
        Copy copyToEdit = persist.get(Copy.class, id);
        if (!owner.equals("")) {
            copyToEdit.setOwner(owner);
        }
        persist.update(copyToEdit);
        return ServiceResult.OK;
    }

}
