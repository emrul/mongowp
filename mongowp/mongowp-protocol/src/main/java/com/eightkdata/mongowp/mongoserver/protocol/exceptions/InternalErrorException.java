
package com.eightkdata.mongowp.mongoserver.protocol.exceptions;

import com.eightkdata.mongowp.mongoserver.protocol.MongoWP.ErrorCode;

/**
 *
 */
public class InternalErrorException extends MongoServerException {
    private static final long serialVersionUID = 1L;

    public InternalErrorException(String customMessage) {
        super(customMessage, ErrorCode.INTERNAL_ERROR);
    }

    public InternalErrorException(String customMessage, Throwable cause) {
        super(customMessage, cause, ErrorCode.INTERNAL_ERROR);
    }

    public InternalErrorException(Object... args) {
        super(ErrorCode.INTERNAL_ERROR, args);
    }

    public InternalErrorException(Throwable cause, Object... args) {
        super(cause, ErrorCode.INTERNAL_ERROR, args);
    }

}