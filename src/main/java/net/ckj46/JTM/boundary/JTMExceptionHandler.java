package net.ckj46.JTM.boundary;

import net.ckj46.JTM.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JTMExceptionHandler {
    @ExceptionHandler ({NotFoundException.class})
    public ResponseEntity notFoundHandler(Exception e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
