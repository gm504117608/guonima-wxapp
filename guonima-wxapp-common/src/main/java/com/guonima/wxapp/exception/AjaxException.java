package com.guonima.wxapp.exception;

/**
 * @ClassName: AjaxException
 * @Description: ajax异常, 针对ajax请求处理的Exception
 */
public class AjaxException extends RuntimeException {
    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public AjaxException() {
        super();
    }

    public AjaxException(String message) {
        super(message);
    }

    public AjaxException(Throwable cause) {
        super(cause);
    }

    public AjaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public AjaxException(String message, Throwable cause,
                         boolean enableSuppression,
                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
