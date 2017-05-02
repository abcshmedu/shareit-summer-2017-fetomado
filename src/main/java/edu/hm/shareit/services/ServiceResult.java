package edu.hm.shareit.services;

import javax.ws.rs.core.Response;

/**
 * This enum contains all valid response-codes.
 */
public enum ServiceResult {
    OK(Response.Status.OK.getStatusCode(), ""),
    BAD_REQUEST(Response.Status.BAD_REQUEST.getStatusCode(), "Fehlerhafte Eingabe!"),
    DUPLICATE(Response.Status.BAD_REQUEST.getStatusCode(), "Dieses Medium existiert bereits!"),
    NOT_FOUND(Response.Status.NOT_FOUND.getStatusCode(), "Medium nicht gefunden!");

    private int code;
    private String detail;

    /**
     * Constructs a new ServiceResult instance.
     * @param code   HTTP-Response-Code
     * @param detail Info about response
     */
    ServiceResult(int code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    /**
     * Returns the object's code.
     * @return code HTTP-Response code
     */
    public int getStatus() {
        return code;
    }

    /**
     * Returns the object's detail.
     * @return detail info about the response
     */
    public String getDetail() {
        return detail;
    }

}
