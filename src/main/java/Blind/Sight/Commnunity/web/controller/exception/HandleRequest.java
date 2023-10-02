package Blind.Sight.Commnunity.web.controller.exception;

import Blind.Sight.Commnunity.exception.CustomException;
import Blind.Sight.Commnunity.web.response.common.CustomErrorResponse;
import Blind.Sight.Commnunity.web.response.common.ResponseDto;

import Blind.Sight.Commnunity.web.response.mapper.ErrorMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
public class HandleRequest extends ResponseEntityExceptionHandler {

    /**
     * handle bad request for error validate input
     *
     * @param fieldErrors   - input list field error
     * @param bindingResult - input binding result
     * @return -  bad request body
     */
    @ExceptionHandler(ValidationException.class)
    public static ResponseEntity<ResponseDto<Object>> validateRequest(HttpStatus httpStatus,
                                                                    Map<String, String> fieldErrors,
                                                                      BindingResult bindingResult) {
        // show fields and message error
        for(FieldError fieldError: bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        }
        // return dto response status, message and data
        ResponseDto<Object> responseDto = ResponseDto.build().withHttpStatus(httpStatus)
                .withMessage("Bad Request")
                .withData(fieldErrors);
        return ResponseEntity.badRequest().body(responseDto);
    }

    /**
     * Handle all errors response
     *
     * @param ex - exception
     * @return - response
     */
    @ExceptionHandler(Exception.class)
    public static ResponseEntity<ResponseDto<Object>> handleAllExceptions(CustomException ex) {
        CustomErrorResponse customErrorResponse = ErrorMapper.getCustomErrorResponse(ex);
        ResponseDto<Object> responseDto = ResponseDto.build()
                .withHttpStatus(customErrorResponse.getHttpStatus())
                .withMessage(customErrorResponse.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(responseDto);
    }
}
