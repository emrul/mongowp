
package com.eightkdata.mongowp.mongoserver.api.safe.impl;

import com.eightkdata.mongowp.mongoserver.api.safe.Command;

/**
 *
 */
public abstract class AbstractCommand<Arg, Result> implements Command<Arg, Result> {

    private final String commandName;

    public AbstractCommand(String commandName) {
        this.commandName = commandName;
    }
    
    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public boolean isAdminOnly() {
        return false;
    }

    @Override
    public boolean isSlaveOk() {
        return false;
    }

    @Override
    public boolean isSlaveOverrideOk() {
        return false;
    }

    @Override
    public boolean canChangeReplicationState() {
        return false;
    }

    @Override
    public boolean shouldAffectCommandCounter() {
        return true;
    }

    @Override
    public boolean isAllowedOnMaintenance() {
        return true;
    }

    @Override
    public boolean isReadyToReplyResult(Result r) {
        return false;
    }

    @Override
    public String toString() {
        return getCommandName();
    }
}