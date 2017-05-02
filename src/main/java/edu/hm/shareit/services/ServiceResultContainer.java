package edu.hm.shareit.services;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Objects from this class encapsulate a ServiceResult to allow serialisation with Jackson.
 */
@JsonPropertyOrder({"code", "detail"})
public class ServiceResultContainer {
    private ServiceResult result;

    /**
     * Returns the detail text of the encapsulated result.
     * @return the detail text
     */
    public String getDetail() {
        return result.getDetail();
    }

    /**
     * Returns the status code of the encapsulated result.
     * @return the status code
     */
    public int getCode() {
        return result.getStatus();
    }

    /**
     * Constructs a new ServiceResultContainer encapsulating a given ServiceResult.
     * @param sr the ServiceResult to encapsulate.
     */
    public ServiceResultContainer(ServiceResult sr) {
        result = sr;
    }
}
