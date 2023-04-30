package niuniu.javaweb.exception;

import lombok.Data;
import niuniu.javaweb.utils.result.ResultCode;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.exception
 * @date 2023/2/10 18:43
 */
@Data
public class ParamErrorException extends RuntimeException {

    // 错误码
    private Integer code;

    // 错误消息
    private String msg;

    public ParamErrorException() {
        this((int) ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }
    public ParamErrorException(String msg) {
        this((int) ResultCode.FAILED.getCode(), msg);
    }
    public ParamErrorException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
