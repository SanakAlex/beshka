package com.creato.beshka.exceptions;

import javax.servlet.http.HttpServletResponse;

public class InputErrorException extends AppException {

    private static final String DEFAULT_MESSAGE = "Incorrect input type of type '%s' and params %s";

    public InputErrorException(String className, String fields) {
        super(String.format(DEFAULT_MESSAGE, className, fields));
    }

    @Override
    public int getCode() {
        return HttpServletResponse.SC_CONFLICT;
    }
}
