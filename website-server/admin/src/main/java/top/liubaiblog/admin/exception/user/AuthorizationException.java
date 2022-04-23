package top.liubaiblog.admin.exception.user;

/**
 * @author 刘佳俊
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
