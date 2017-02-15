package org.seckill.exception;

/**
 * 秒杀相关业务异常
 * Created by zh on 2/14/2017.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
