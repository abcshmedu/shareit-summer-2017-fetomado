package edu.hm.shareit.resources;

import edu.hm.shareit.services.CopyService;
import edu.hm.shareit.services.CopyServiceImpl;

public class CopyResource {

    private CopyService service;

    CopyResource() {
        service = new CopyServiceImpl();
    }

    CopyResource(CopyService srv) {
        service = srv;
    }

}
