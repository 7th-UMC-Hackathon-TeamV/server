package banban.springboot.apiPayload.exception.handler;

import banban.springboot.apiPayload.code.BaseErrorCode;
import banban.springboot.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}