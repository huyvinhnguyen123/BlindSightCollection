package Blind.Sight.Commnunity.web.response.mapper;

import Blind.Sight.Commnunity.web.response.common.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

public class ErrorMapper {
    private static final Map<Class<? extends Exception>, CustomErrorResponse> errorMapping = new HashMap<>();

    static {
       errorMapping.put(HttpClientErrorException.Forbidden.class,
               new CustomErrorResponse(HttpStatus.FORBIDDEN, "Forbidden")); // 403
       errorMapping.put(HttpClientErrorException.Unauthorized.class,
               new CustomErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized")); // 401
       errorMapping.put(HttpClientErrorException.BadRequest.class,
               new CustomErrorResponse(HttpStatus.BAD_REQUEST, "Bad request")); // 400
       errorMapping.put(HttpClientErrorException.NotFound.class,
               new CustomErrorResponse(HttpStatus.NOT_FOUND, "Not Found")); // 404
       errorMapping.put(HttpClientErrorException.MethodNotAllowed.class,
               new CustomErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed")); // 405
       errorMapping.put(HttpClientErrorException.UnsupportedMediaType.class,
               new CustomErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type")); // 415
       errorMapping.put(HttpClientErrorException.TooManyRequests.class,
               new CustomErrorResponse(HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests")); // 429

    }

    public static CustomErrorResponse getCustomErrorResponse(Exception ex) {
        CustomErrorResponse customErrorResponse = errorMapping.get(ex.getClass());
        if(customErrorResponse == null) {
            customErrorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
        }
        return  customErrorResponse;
    }

    private ErrorMapper(){}
}
