package com.creato.beshka.exceptions;

import javax.servlet.http.HttpServletResponse;

public class NoSuchEntityException extends AppException {

    private static final String DEFAULT_MESSAGE = "No entity of type '%s' and params %s";
    private static final String NO_ENTITY_OF_TYPE = "No entity of type '%s'";

    public NoSuchEntityException(String className){
        super(String.format(NO_ENTITY_OF_TYPE , className));
    }

    public NoSuchEntityException(String className, String params){
        super(String.format(DEFAULT_MESSAGE, className, params));
    }

    public int getCode() {
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
