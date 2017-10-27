/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.web.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import party.threebody.skean.misc.SkeanException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * map some http status to exceptions
 *
 * @author hzk
 * @see https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * @see http://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 * @since 2017-06-17
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    static Logger getLogger() {
        return LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorBody> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        // @ResponseStatus exception -> re-throw it
        ResponseStatus respSt = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (respSt != null) {
            return handleApiError(e, respSt.code());
        }
        //SkeanException -> 400
        if (e instanceof SkeanException) {
            return handleApiError(e, HttpStatus.BAD_REQUEST);
        }
        //unexpected exception -> 500
        return handleApiError(e, HttpStatus.INTERNAL_SERVER_ERROR);


    }

    protected ModelAndView handleViewError(String url, String errorStack, String errorMessage, String viewName) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", errorStack);
        mav.addObject("url", url);
        mav.addObject("message", errorMessage);
        mav.setViewName(viewName);
        return mav;
    }

    protected ResponseEntity<ApiErrorBody> handleApiError(Exception e, HttpStatus httpStatus) {
        getLogger().error("An exception raised upon a HTTP API (status=" + httpStatus + ").", e);
        ApiErrorBody errBody = new ApiErrorBody(0, e.getLocalizedMessage(), null);
        return new ResponseEntity<ApiErrorBody>(errBody, httpStatus);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorBody> handleConflict(Exception e) {
        return handleApiError(e, HttpStatus.CONFLICT);
    }

}

class ApiErrorBody {

    private int code;
    private String message;
    private List<String> errors;

    public ApiErrorBody(int code, String message, List<String> errors) {
        super();
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}