package com.creato.beshka.exceptions;

import javax.servlet.http.HttpServletResponse;

public class EmailExistsException extends AppException {

    @Override
    public int getCode() {
        return HttpServletResponse.SC_CONFLICT;
    }

    @Override
    public String formMessage(){
        return "User with this email already exists";
    }
}
