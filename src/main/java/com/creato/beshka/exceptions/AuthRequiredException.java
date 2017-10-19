package com.creato.beshka.exceptions;

import javax.servlet.http.HttpServletResponse;

public class AuthRequiredException extends AppException {

    @Override
    public int getCode(){
        return HttpServletResponse.SC_UNAUTHORIZED;
    }

    @Override
    public String formMessage() {
        return "Authorization exception occurs";
    }
}
