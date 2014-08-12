package com.eits.freemr.configuration.rest.api.infrastructure;

import java.util.HashMap;
import java.util.Map;



import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.interceptors.JSR303ViolationException;
import org.axonframework.eventsourcing.AggregateDeletedException;
import org.axonframework.repository.AggregateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eits.freemr.configuration.domain.interfaces.exception.NotFoundException;

/**
 * The Class AbstractController.
 */
public abstract class AbstractController {

    /** The logger. */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Builds the error response entity.
     * 
     * @param content
     *            the content
     * @param status
     *            the status
     * @return the response entity
     */
    protected ResponseEntity<?> buildErrorResponseEntity(Object content, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        Map<String, Object> error = new HashMap<String, Object>();
        error.put("detail", content);
        return new ResponseEntity<Object>(error, headers, status);
    }

    /**
     * Handle exception.
     * 
     * @param exception
     *            the exception
     * @return the response entity
     */
    @ExceptionHandler(value = CommandExecutionException.class)
    public ResponseEntity<?> handleException(CommandExecutionException exception) {
        Throwable t = exception.getCause();
        logger.error(t.getMessage(), t);
        if (t != null) {
            if ((t instanceof AggregateDeletedException)) {
                return buildErrorResponseEntity(t.getMessage(), HttpStatus.GONE);
            } else if (t instanceof NotFoundException) {
                return buildErrorResponseEntity(((NotFoundException) t).getMessage(), HttpStatus.GONE);
            } else if (t instanceof AggregateNotFoundException) {
                return buildErrorResponseEntity(t.getMessage(), HttpStatus.NOT_FOUND);
            } else if (t instanceof JSR303ViolationException) {
                return buildErrorResponseEntity(((JSR303ViolationException) t).getViolations().toString(), HttpStatus.BAD_REQUEST);
            } 
        }
        return buildErrorResponseEntity(t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Handle exception of gone.
     * 
     * @param exception
     *            the exception
     * @return the response entity
     */
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleExceptionOfGone(NotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(exception.getMessage(), HttpStatus.GONE);
    }

    /**
     * Handle exception of internal server error.
     * 
     * @param exception
     *            the exception
     * @return the response entity
     */
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<?> handleExceptionOfInternalServerError(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle exception.
     * 
     * @param exception
     *            the exception
     * @return the response entity
     */
    @ExceptionHandler(value = AggregateNotFoundException.class)
    public ResponseEntity<?> handleException(AggregateNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
