package org.seckill.exception;

/**
 * 重复秒杀(运行期异常)
 * Created by zh on 2/14/2017.
 */
public class RepeatKillException extends RuntimeException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
