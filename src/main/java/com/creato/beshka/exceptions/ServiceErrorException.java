package com.creato.beshka.exceptions;

import javax.servlet.http.HttpServletResponse;

public class ServiceErrorException extends AppException{

    @Override
    public int getCode(){
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    @Override
    public String formMessage() {
        return "Internal Server Error";
    }
}
