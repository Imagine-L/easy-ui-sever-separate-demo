package top.liubaiblog.admin.exception.user;

/**
 * @author 刘佳俊
 */
public class PasswordMistakeException extends RuntimeException {
    public PasswordMistakeException() {
    }

    public PasswordMistakeException(String message) {
        super(message);
    }
}
