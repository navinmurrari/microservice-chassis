package ${package}.api;

import ${package}.exceptions.ValidationException;
import ${package}.model.Error;
import ${package}.model.ErrorResponse;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import static ${package}.constants.Constants.*;
import static org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT;
@Slf4j
@ControllerAdvice
public class ExceptionHandlers {
    @Autowired
    private NativeWebRequest request;
    @Autowired
    private Environment environment;
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e){
       ErrorResponse errorResponse = new ErrorResponse();
       errorResponse.setLanguage(getLanguage());
       errorResponse.setStatus(FAILED);
       errorResponse.setTimeStamp(ISO_8601_EXTENDED_DATETIME_FORMAT.format(new Date()));
       Error error = new Error();
       error.setCode(e.getCode());
       error.setMessage(getMessageFromBundle(e));
       error.setComponentName(environment.getProperty(COMPONENT_NAME));
       errorResponse.setErrors(Lists.newArrayList(error));
       return ResponseEntity.badRequest().body(errorResponse);
    }

    private String getLanguage() {
        return Optional.ofNullable(request.getHeader(LANGUAGE)).orElse("en");
    }

    private String getMessageFromBundle(ValidationException ve) {
        Locale locale;
        try {
            locale = Locale.forLanguageTag(getLanguage());
        }catch (Exception e){
            log.error("invalid language",e);
            locale = Locale.getDefault();
        }
        return messageSource.getMessage(ve.getCode(),ve.getParams(),locale);
    }
}
