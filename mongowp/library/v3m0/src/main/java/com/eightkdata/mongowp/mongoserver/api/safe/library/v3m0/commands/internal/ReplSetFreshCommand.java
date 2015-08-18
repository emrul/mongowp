package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal;

import com.eightkdata.mongowp.mongoserver.api.safe.impl.AbstractCommand;
import com.eightkdata.mongowp.mongoserver.api.safe.impl.SimpleArgument;
import com.eightkdata.mongowp.mongoserver.api.safe.impl.SimpleReply;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal.ReplSetFreshCommand.ReplSetFreshArgument;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal.ReplSetFreshCommand.ReplSetFreshReply;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.tools.SimpleReplyMarshaller;
import com.eightkdata.mongowp.mongoserver.api.safe.tools.bson.BsonReaderTool;
import com.eightkdata.mongowp.mongoserver.protocol.MongoWP;
import com.eightkdata.mongowp.mongoserver.protocol.exceptions.MongoServerException;
import com.eightkdata.mongowp.mongoserver.protocol.exceptions.NoSuchKeyException;
import com.eightkdata.mongowp.mongoserver.protocol.exceptions.TypesMismatchException;
import com.google.common.net.HostAndPort;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bson.*;
import org.threeten.bp.Instant;

/**
 *
 */
public class ReplSetFreshCommand extends AbstractCommand<ReplSetFreshArgument, ReplSetFreshReply>{

    public static final ReplSetFreshCommand INSTANCE = new ReplSetFreshCommand();

    private ReplSetFreshCommand() {
        super("replSetFresh");
    }

    @Override
    public Class<? extends ReplSetFreshArgument> getArgClass() {
        return ReplSetFreshArgument.class;
    }

    @Override
    public ReplSetFreshArgument unmarshallArg(BsonDocument requestDoc) throws
            MongoServerException {
        return ReplSetFreshArgument.fromDocument(requestDoc, this);
    }

    @Override
    public Class<? extends ReplSetFreshReply> getReplyClass() {
        return ReplSetFreshReply.class;
    }

    @Override
    public BsonDocument marshallReply(ReplSetFreshReply reply) throws
            MongoServerException {

        BsonDocument result = SimpleReplyMarshaller.marshall(reply);

        result.put("fresher", BsonBoolean.valueOf(reply.isWeAreFresher()));
        if (reply.getInfo() != null) {
            result.put("info", new BsonString(reply.getInfo()));
        }
        result.put("optime", new BsonDateTime(reply.getOpTime().toEpochMilli()));
        result.put("veto", BsonBoolean.valueOf(reply.isDoVeto()));

        return result;
    }

    public static class ReplSetFreshArgument extends SimpleArgument {

        private static final String SET_NAME_FIELD_NAME = "set";
        private static final String WHO_FIELD_NAME = "who";
        private static final String ID_FIELD_NAME = "id";
        private static final String CFG_VER_FIELD_NAME = "cfgver";
        private static final String OPTIME_FIELD_NAME = "optime";

        private final String setName;
        private final HostAndPort who;
        private final int clientId;
        private final long cfgVersion;
        private final Instant opTime;

        public ReplSetFreshArgument(
                ReplSetFreshCommand command,
                String setName,
                HostAndPort who,
                int clientId,
                long cfgVersion,
                Instant opTime) {
            super(command);
            this.setName = setName;
            this.who = who;
            this.clientId = clientId;
            this.cfgVersion = cfgVersion;
            this.opTime = opTime;
        }

        /**
         *
         * @return the name of the set
         */
        public String getSetName() {
            return setName;
        }

        /**
         *
         * @return the host and port of the member that sent the request
         */
        public HostAndPort getWho() {
            return who;
        }

        /**
         *
         * @return the repl set of the member that sent the request
         */
        public int getClientId() {
            return clientId;
        }

        /**
         *
         * @return replSet config version that the member who sent the request
         */
        public long getCfgVersion() {
            return cfgVersion;
        }

        /**
         *
         * @return last optime seen by the member who sent the request
         */
        public Instant getOpTime() {
            return opTime;
        }

        public static ReplSetFreshArgument fromDocument(BsonDocument bson, ReplSetFreshCommand command) throws
                TypesMismatchException, NoSuchKeyException {
            int clientId = BsonReaderTool.getInteger(bson, ID_FIELD_NAME);
            String setName = BsonReaderTool.getString(bson, SET_NAME_FIELD_NAME);
            HostAndPort who = BsonReaderTool.getHostAndPort(bson, WHO_FIELD_NAME);
            long cfgver = BsonReaderTool.getNumeric(bson, CFG_VER_FIELD_NAME).longValue();
            Instant optime = BsonReaderTool.getInstant(bson, OPTIME_FIELD_NAME);

            return new ReplSetFreshArgument(command, setName, who, clientId, cfgver, optime);
        }

    }

    public static class ReplSetFreshReply extends SimpleReply {

        private final String info;
        private final Instant opTime;
        private final boolean weAreFresher;
        private final boolean doVeto;

        public ReplSetFreshReply(
                ReplSetFreshCommand command,
                @Nullable String info,
                @Nonnull Instant opTime,
                boolean weAreFresher,
                boolean doVeto) {
            super(command);
            this.info = info;
            this.opTime = opTime;
            this.weAreFresher = weAreFresher;
            this.doVeto = doVeto;
        }

        public ReplSetFreshReply(
                ReplSetFreshCommand command,
                @Nullable String info,
                @Nonnull Instant opTime,
                boolean weAreFresher,
                boolean doVeto,
                MongoWP.ErrorCode errorCode,
                String errorMessage) {
            super(command, errorCode, errorMessage);
            this.info = info;
            this.opTime = opTime;
            this.weAreFresher = weAreFresher;
            this.doVeto = doVeto;
        }

        public ReplSetFreshReply(
                ReplSetFreshCommand command,
                @Nullable String info,
                @Nonnull Instant opTime,
                boolean weAreFresher,
                boolean doVeto,
                MongoWP.ErrorCode errorCode,
                Object... args) {
            super(command, errorCode, args);
            this.info = info;
            this.opTime = opTime;
            this.weAreFresher = weAreFresher;
            this.doVeto = doVeto;
        }

        @Nullable
        public String getInfo() {
            return info;
        }

        @Nonnull
        public Instant getOpTime() {
            return opTime;
        }

        public boolean isWeAreFresher() {
            return weAreFresher;
        }

        public boolean isDoVeto() {
            return doVeto;
        }

    }

}