package ru.veretennikov.ordersmanagement.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {

    static Map<String, String> getErrors(BindingResult bindingResult, String... ignoredFields) {

        List<String> ignoreList = Arrays.asList(ignoredFields);

        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );

        return bindingResult.getFieldErrors()
                .stream()
                .filter(fieldError -> !ignoreList.contains(fieldError.getField()))
                .collect(collector);
    }

}
