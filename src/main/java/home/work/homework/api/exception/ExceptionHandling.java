package home.work.homework.api.exception;

import home.work.homework.service.ConflictException;
import home.work.homework.service.NotFoundException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.slf4j.LoggerFactory.getLogger;

@ControllerAdvice
public class ExceptionHandling {

    private static final Logger logger = getLogger(ExceptionHandling.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(produces = "application/json")
    @ResponseBody
    public String handleNotFound(NotFoundException e) {
        logger.error("An exception was caught, HTTP {} code was returned to client \n{}", HttpStatus.NOT_FOUND, e.toString(), e);

        return e.getMessage();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @RequestMapping(produces = "application/json")
    @ResponseBody
    public String handleConflict(ConflictException e) {
        logger.error("An exception was caught, HTTP {} code was returned to client \n{}", HttpStatus.CONFLICT, e.toString(), e);

        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = "application/json")
    @ResponseBody
    public String handleInvalidArgument(MethodArgumentNotValidException e) {
        logger.error("An exception was caught, HTTP {} code was returned to client \n{}", HttpStatus.BAD_REQUEST, e.toString(), e);

        return e.getMessage();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping(produces = "application/json")
    @ResponseBody
    public String accessDenied(AccessDeniedException e) {
        logger.error("An exception was caught, HTTP {} code was returned to client \n{}", HttpStatus.UNAUTHORIZED, e.toString(), e);

        return e.getMessage();
    }
}
