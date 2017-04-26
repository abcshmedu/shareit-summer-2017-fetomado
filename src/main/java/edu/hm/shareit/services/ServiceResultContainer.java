package edu.hm.shareit.services;

import edu.hm.shareit.services.ServiceResult;

public class ServiceResultContainer {
    private ServiceResult result;

    public String getDetail() {
        return result.getDetail();
    }

    public int getCode() {
        return result.getStatus();
    }

    public ServiceResultContainer(ServiceResult sr) {
        result = sr;
    }
}
