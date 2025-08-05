package com.shop.online.exception;


import com.shop.online.model.response.ApplicationErrorResponseDto;
import com.shop.online.model.response.APINsbResponseError;
import com.shop.online.utils.ErrorCodesUtil;
import com.shop.online.utils.StringRanDom;
import com.shop.online.utils.TemplateApiErrorEnum;
import com.shop.online.utils.constants.APIConstants;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceApiExceptionHandler {

    private static final String EXCEPTION = "exception";
    private static final String PATTERN = "^requestDto\\.(.+)$";
    private static final String INVALID_DATE_FORMAT = "Invalid date format.";
    private MessageSource messageSource;

    @ExceptionHandler(ServiceApiException.class)
    public ResponseEntity<APINsbResponseError> handleBadRequestException(ServiceApiException ex) {
        this.logErrorException(ex, HttpStatus.BAD_REQUEST.toString());
        APINsbResponseError apiResponseError = APINsbResponseError.builder()
                .code(ex.getCode())
                .message(!StringUtils.isEmpty(ex.getMessage()) ? ex.getMessage() : APIConstants.ERROR_UNKNOWN_MSG)
                .build();
        return new ResponseEntity<>(apiResponseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<APINsbResponseError> handleDataException(Exception ex) {
        APINsbResponseError apiResponseError = APINsbResponseError.builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SuppressWarnings("java:S2637")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationErrorResponseDto handleMethodNotValidException(MethodArgumentNotValidException ex) {

        final ApplicationErrorResponseDto error = new ApplicationErrorResponseDto(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase());

        for (final FieldError field : ex.getBindingResult().getFieldErrors()) {
            final String name = field.getField().replaceAll(PATTERN, "$1");
            error.addErrorKeyAndObjectDetail(name, field.getDefaultMessage());
        }

        return error;
    }

    @ExceptionHandler({JsonParseException.class, JsonMappingException.class, MismatchedInputException.class,
            DateTimeParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationErrorResponseDto handleJsonParseMappingException(Exception ex) {

        String message = ex.getMessage().length() > 120 ? ex.getMessage().substring(0, 120) : ex.getMessage();
        String field = EXCEPTION;

        if (ex.getMessage().contains("Date")) {
            message = ErrorCodesUtil.getMessage(messageSource, TemplateApiErrorEnum.ISO_DATE_FORMAT_CHECK,
                    INVALID_DATE_FORMAT);
        }

        if (ex.getCause() instanceof JsonMappingException) {
            final JsonMappingException mappingExc = (JsonMappingException) ex.getCause();

            if (!mappingExc.getPath().isEmpty() && mappingExc.getPath().size() > 1) {
                field = mappingExc.getPath().get(1).getFieldName();
            }
        }

        return generateErrorResponseDto(field, message);
    }

    private ApplicationErrorResponseDto generateErrorResponseDto(final String field, String message) {
        final ApplicationErrorResponseDto error = new ApplicationErrorResponseDto(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase());

        error.addErrorKeyAndObjectDetail(field, message);

        return error;
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationErrorResponseDto handleMethodNotValidException(MissingPathVariableException ex) {

        final ApplicationErrorResponseDto error = new ApplicationErrorResponseDto(
                String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST.getReasonPhrase());

        error.addErrorKeyAndObjectDetail(EXCEPTION, "miss path param :" + ex.getVariableName());

        return error;
    }

    /**
     * fog error exception
     *
     * @param ex         Exception
     * @param httpStatus String
     */
    private void logErrorException(Exception ex, String httpStatus) {
        try {
            JsonObject json = new JsonObject();
            String stacktrace = ExceptionUtils.getStackTrace(ex);
            stacktrace = StringRanDom.formatSpaceString(stacktrace);
            json.addProperty("HttpStatus", httpStatus);
            json.addProperty("stacktrace", stacktrace);
            log.error(StringRanDom.formatSpaceString(json.toString()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
