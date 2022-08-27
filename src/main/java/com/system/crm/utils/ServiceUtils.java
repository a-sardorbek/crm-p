package com.system.crm.utils;

import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceUtils {
    public static boolean checkIsNumeric(String id) {
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }
        return true;

    }

    public static Map<String, List<String>> errorsValidation(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        List<String> messageList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            messageList.add(error.getDefaultMessage());

        });
        errors.put("message", messageList);
        return errors;
    }

    public static Map<String, List<String>> errorsValidationConstraint(ConstraintViolationException exception) {
        Map<String, List<String>> errorsConstraint = new HashMap<>();
        List<String> messageList = new ArrayList<>();
        exception.getConstraintViolations().forEach((error) -> {
            messageList.add(error.getMessage());
        });
        errorsConstraint.put("message", messageList);
        return errorsConstraint;
    }
}
