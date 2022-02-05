package ${package}.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{
    private String code;
    private Object[] params;
    public ValidationException(String message, String code,Object...params) {
        super(message);
        this.code = code;
        this.params = params;
    }

}
