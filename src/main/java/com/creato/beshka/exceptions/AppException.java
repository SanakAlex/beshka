package com.creato.beshka.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.MessageSource;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends Exception {

    private int code;

    AppException() {
        super("AppException");
    }

    AppException(String message) {
        super(message);
    }

    /**
     * override this method to create custom messages for users
     * @return converted message
     */
    public String formMessage(){
        return getMessage();
    }

    /**
     * override this method to create a list of errors for user
     * @return list of errors
     */
    public List<String> formListErrors(MessageSource messageSource, String locale){
        return null;
    }
}
