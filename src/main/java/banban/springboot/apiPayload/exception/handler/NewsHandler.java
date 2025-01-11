package banban.springboot.apiPayload.exception.handler;

import banban.springboot.apiPayload.code.BaseErrorCode;
import banban.springboot.apiPayload.exception.GeneralException;

public class NewsHandler extends GeneralException {
    public NewsHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
