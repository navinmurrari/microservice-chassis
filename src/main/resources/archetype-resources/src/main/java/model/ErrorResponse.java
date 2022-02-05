package ${package}.model;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private String status;
    private String language;
    private String timeStamp;
    private String traceId;
    private String step;
    private List<Error> errors;
}
