package top.liubaiblog.admin.exception_handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.liubaiblog.admin.exception.user.UserNotPermissionException;

/**
 * @author 刘佳俊
 */
@ControllerAdvice
public class PermissionExceptionHandler {

    @ExceptionHandler({UserNotPermissionException.class})
    public ResponseEntity<String> userNotPermissionExceptionHandler(Exception e) {
        return ResponseEntity.status(405).body(e.getMessage());
    }

}
