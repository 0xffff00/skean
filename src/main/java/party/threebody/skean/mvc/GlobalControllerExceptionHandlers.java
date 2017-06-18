package party.threebody.skean.mvc;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 切入非本项目写的异常，翻译到对应的HTTP状态码
 * 
 * @author hzk
 * @since 2017-06-17
 */
@ControllerAdvice
public class GlobalControllerExceptionHandlers {

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void handleConflict() {
	}

}