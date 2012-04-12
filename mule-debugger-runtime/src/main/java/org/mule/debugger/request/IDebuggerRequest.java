package org.mule.debugger.request;

import org.mule.debugger.commands.ICommand;

import java.io.Serializable;

public interface IDebuggerRequest extends Serializable {

    ICommand createCommand();

    long getRequestId();

}

