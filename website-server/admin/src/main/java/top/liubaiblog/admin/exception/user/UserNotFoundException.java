package top.liubaiblog.admin.exception.user;

/**
 * @author 刘佳俊
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
