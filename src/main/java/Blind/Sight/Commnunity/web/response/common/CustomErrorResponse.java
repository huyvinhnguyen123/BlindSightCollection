package Blind.Sight.Commnunity.web.response.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomErrorResponse {
    private int status;
    private LocalDateTime timestamp;
    private String error;
    private String message;
    private String path;

    private HttpStatus httpStatus;

    public CustomErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public CustomErrorResponse(int status) {
        this.status = status;
        this.message = "Unexpected error";
    }

    public CustomErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public CustomErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CustomErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public CustomErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
