package party.threebody.skean.id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasPermission(PERM_NAME,PERM_TYPE)")
public @interface HasPermission {}
//https://nmhblog.wordpress.com/2013/01/27/spring-security-3-1-securing-methods-with-custom-annotations/
