package com.senior.cyber.frmk.common.simulator;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;

public class InternalResponseEntity implements Serializable {

    private HttpStatusCode statusCode;

    private HttpHeaders headers;

    private Object body;

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

}