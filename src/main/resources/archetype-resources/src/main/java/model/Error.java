package ${package}.model;

import lombok.Data;

@Data
public class Error {
    private String code;
    private String message;
    private String componentName;
}
