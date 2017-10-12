package party.threebody.skean.web;

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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 切入非本项目写的异常，翻译到对应的HTTP状态码
 * 
 * @author hzk
 * @since 2017-06-17
 * @see https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * @see http://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiErrorBody> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

		// If the exception is annotated with @ResponseStatus re-throw it and
		// let the framework handle it

		ResponseStatus respSt = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
		if (respSt != null) {
			return handleApiError(e, respSt.code());
		} else {
			return handleApiError(e, HttpStatus.BAD_REQUEST);
		}

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

	static Logger getLogger() {
		return LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
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

	public ApiErrorBody(int code, String message, List<String> errors) {
		super();
		this.code = code;
		this.message = message;
		this.errors = errors;
	}

}