
package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.pojos;

import com.eightkdata.mongowp.mongoserver.protocol.exceptions.BadValueException;
import com.eightkdata.mongowp.mongoserver.protocol.exceptions.NoSuchKeyException;
import com.eightkdata.mongowp.mongoserver.protocol.exceptions.TypesMismatchException;
import com.eightkdata.mongowp.mongoserver.api.safe.tools.bson.BsonDocumentBuilder;
import com.eightkdata.mongowp.mongoserver.api.safe.tools.bson.BsonField;
import com.eightkdata.mongowp.mongoserver.api.safe.tools.bson.BsonReaderTool;
import com.google.common.net.HostAndPort;
import javax.annotation.Nullable;
import org.bson.*;

/**
 *
 */
public class HeartbeatInfo {
    
    private static final BsonField<Boolean> CHECK_EMPTY_FIELD_NAME = BsonField.create("checkEmpty");
    private static final BsonField<Long> PROTOCOL_VERSION_FIELD_NAME = BsonField.create("pv");
    private static final BsonField<Long> CONFIG_VERSION_FIELD_NAME = BsonField.create("v");
    private static final BsonField<Long> SENDER_ID_FIELD_NAME = BsonField.create("fromId");
    private static final BsonField<String> SET_NAME_FIELD_NAME = BsonField.create("replSetHeartbeat");
    private static final BsonField<String> SENDER_HOST_FIELD_NAME = BsonField.create("from");
    
    private final Boolean checkEmpty;
    private final long protocolVersion;
    private final long configVersion;
    private final Long senderId;
    private final String setName;
    private final HostAndPort senderHost;

    public HeartbeatInfo(
            long protocolVersion, 
            long configVersion, 
            String setName, 
            HostAndPort senderHost,
            @Nullable Long senderId, 
            @Nullable Boolean checkEmpty) {
        this.checkEmpty = checkEmpty;
        this.protocolVersion = protocolVersion;
        this.configVersion = configVersion;
        this.senderId = senderId;
        this.setName = setName;
        this.senderHost = senderHost;
    }
    
    public boolean isCheckEmpty() {
        return checkEmpty;
    }

    public long getProtocolVersion() {
        return protocolVersion;
    }

    public long getConfigVersion() {
        return configVersion;
    }

    @Nullable
    public Long getSenderId() {
        return senderId;
    }

    public String getSetName() {
        return setName;
    }

    public HostAndPort getSenderHost() {
        return senderHost;
    }
    
    public static HeartbeatInfo unmarshall(BsonDocument bson) throws TypesMismatchException, NoSuchKeyException, BadValueException {
        BsonReaderTool.checkOnlyHasFields(
                "ReplSetHeartbeatArgs", 
                bson, 
                CHECK_EMPTY_FIELD_NAME.getFieldName(),
                PROTOCOL_VERSION_FIELD_NAME.getFieldName(),
                CONFIG_VERSION_FIELD_NAME.getFieldName(),
                SENDER_ID_FIELD_NAME.getFieldName(),
                SET_NAME_FIELD_NAME.getFieldName(),
                SENDER_HOST_FIELD_NAME.getFieldName()
        );
        Boolean checkEmpty = null;
        if (bson.containsKey(CHECK_EMPTY_FIELD_NAME.getFieldName())) {
            checkEmpty = BsonReaderTool.getBoolean(bson, CHECK_EMPTY_FIELD_NAME);
        }
        long protocolVersion = BsonReaderTool.getLong(bson, PROTOCOL_VERSION_FIELD_NAME);
        long configVersion = BsonReaderTool.getLong(bson, CONFIG_VERSION_FIELD_NAME);
        Long senderId = null;
        if (bson.containsKey(SENDER_ID_FIELD_NAME.getFieldName())) {
            senderId = BsonReaderTool.getLong(bson, SENDER_ID_FIELD_NAME);
        }
        String setName = BsonReaderTool.getString(bson, SET_NAME_FIELD_NAME);
        
        String senderHostString = BsonReaderTool.getString(bson, SENDER_HOST_FIELD_NAME, null);
        HostAndPort senderHost = senderHostString != null ? HostAndPort.fromString(senderHostString) : null;
        
        return new HeartbeatInfo(protocolVersion, configVersion, setName, senderHost, senderId, checkEmpty);
    }
    
    public BsonDocument marshall() {
        BsonDocumentBuilder builder = new BsonDocumentBuilder();
        builder.append(SET_NAME_FIELD_NAME, setName);
        builder.append(PROTOCOL_VERSION_FIELD_NAME, protocolVersion);
        builder.append(CONFIG_VERSION_FIELD_NAME, configVersion);
        if (senderHost != null) {
            builder.append(SENDER_HOST_FIELD_NAME, senderHost.toString());
        }
        else {
            builder.append(SENDER_HOST_FIELD_NAME, "");
        }
        
        if (senderId != null) {
            builder.append(SENDER_ID_FIELD_NAME, senderId);
        }
        if (checkEmpty != null) {
            builder.append(CHECK_EMPTY_FIELD_NAME, checkEmpty);
        }
        return builder.build();
    }

}