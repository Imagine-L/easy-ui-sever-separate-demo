package top.liubaiblog.admin.exception.user;

/**
 * @author 刘佳俊
 */
public class UserNotPermissionException extends RuntimeException {
    public UserNotPermissionException() {
    }

    public UserNotPermissionException(String message) {
        super(message);
    }
}
