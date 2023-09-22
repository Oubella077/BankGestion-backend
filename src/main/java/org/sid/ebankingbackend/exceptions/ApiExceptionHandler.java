package org.sid.ebankingbackend.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = {ApiBaseException.class, IllegalArgumentException.class, IllegalStateException.class, MaxUploadSizeExceededException.class, IOException.class})
    public ResponseEntity<ExceptionResponse> handleapiExceptionHandler(ApiBaseException ex, WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(new ExceptionResponse<ExceptionDetails>(false, details.getMessage()), HttpStatus.OK);
    }


    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationException validationError = new ValidationException();
        validationError.setUrl(request.getDescription(false));

        List<FieldError> fieldError = ex.getBindingResult().getFieldErrors();

        for (FieldError f : fieldError) {
            validationError.addError(f.getDefaultMessage());
        }
        return new ResponseEntity<>(new ExceptionResponse<ValidationException>(false, validationError.getErros().toString()), HttpStatus.OK);
    }


}
