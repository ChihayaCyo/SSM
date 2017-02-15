package org.seckill.exception;

/**
 * 秒杀关闭后秒杀异常
 * Created by zh on 2/14/2017.
 */
public class SeckillCloseException extends RuntimeException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
