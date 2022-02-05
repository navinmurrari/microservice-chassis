package ${package}.api;

import ${package}.exceptions.ValidationException;
import ${package}.model.Error;
import ${package}.model.ErrorResponse;
import ${package}.model.User;
import ${package}.services.UserManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javax.annotation.Generated;

import static ${package}.constants.Constants.*;
import static ${package}.constants.Constants.LANGUAGE;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-02-01T15:26:28.611301400+05:30[Asia/Calcutta]")
@Controller
@Slf4j
@RequestMapping("/")
public class UserApiController implements UserApi {

    private final NativeWebRequest request;

    @Autowired
    private UserManagementService service;

    @Autowired
    public UserApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ofNullable(request);
    }

    @Autowired
    private Environment environment;

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseEntity<Void> createUser(User body) {
        return service.createUser(body);
    }

    @Override
    public ResponseEntity<User> getUserByName(String username) {
        return service.getUserByName(username);
    }

}
