package GL1A.PFA.EquipmentSupervision.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<String> handleException(IllegalStateException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
