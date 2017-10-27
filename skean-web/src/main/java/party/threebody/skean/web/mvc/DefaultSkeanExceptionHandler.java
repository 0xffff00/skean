/*
 * Copyright 2017. https://github.com/0xffff00
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

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import party.threebody.skean.misc.SkeanException;
import party.threebody.skean.misc.SkeanInvalidArgumentException;
import party.threebody.skean.misc.SkeanNotImplementedException;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * map some http status to exceptions
 *
 * @author hzk
 * @since 2.1
 */
@RestControllerAdvice
public class DefaultSkeanExceptionHandler {

    static final Map<Class<? extends Exception>, HttpStatus> EX2HTTP = new LinkedHashMap<>();

    static {
        EX2HTTP.put(SkeanInvalidArgumentException.class, HttpStatus.BAD_REQUEST);
        EX2HTTP.put(SkeanNotImplementedException.class, HttpStatus.NOT_IMPLEMENTED);
        EX2HTTP.put(SkeanException.class, HttpStatus.BAD_REQUEST);
    }

    protected Map<Class<? extends Exception>, HttpStatus> getExceptionToHttpStatusMap() {
        return EX2HTTP;
    }

    @ExceptionHandler(SkeanException.class)
    ResponseEntity<?> handleAll(HttpServletRequest req, Exception e) {
        return handleFromMapOrAnnotations(req, e);
    }

    protected ResponseEntity<?> handleFromMapOrAnnotations(HttpServletRequest req, Exception e) {
        // find in EX2HTTP
        for (Class<? extends Exception> exClass : getExceptionToHttpStatusMap().keySet()) {
            if (exClass.isInstance(e)) {
                HttpStatus httpStatus = getExceptionToHttpStatusMap().get(exClass);
                return handle0(req, e, httpStatus);
            }
        }
        // find @ResponseStatus annotated Exception
        ResponseStatus respSt = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (respSt != null) {
            return handle0(req, e, respSt.code());
        }
        return handle0(req, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<ApiErrorInfo> handle0(HttpServletRequest req, Exception e, HttpStatus httpStatus) {
        ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
        apiErrorInfo.setStatus(httpStatus.value());
        apiErrorInfo.setError(httpStatus.getReasonPhrase());
        apiErrorInfo.setMessage(e.getMessage());
        apiErrorInfo.setTimestamp(System.currentTimeMillis());
        apiErrorInfo.setPath(req.getContextPath());
        apiErrorInfo.setDevException(e.getClass().getName());
        return new ResponseEntity<ApiErrorInfo>(apiErrorInfo, httpStatus);
    }


}
