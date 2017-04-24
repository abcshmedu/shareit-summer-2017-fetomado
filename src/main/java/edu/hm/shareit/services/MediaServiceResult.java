 package edu.hm.shareit.services;

import javax.ws.rs.core.Response;

public enum MediaServiceResult {
    OK(Response.Status.OK.getStatusCode(), ""),
    BAD_REQUEST(Response.Status.BAD_REQUEST.getStatusCode(), "BAD_REQUEST!!!"),
    NOT_FOUND(Response.Status.NOT_FOUND.getStatusCode(), "NOT_FOUND!!!");

    private int code;
    private String detail;

    MediaServiceResult(int code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public int getStatus() {
        return code;
    }
    
    public String getDetail() {
        return detail;
    }

}
