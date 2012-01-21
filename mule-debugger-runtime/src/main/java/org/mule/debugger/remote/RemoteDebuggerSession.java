package org.mule.debugger.remote;


import org.mule.debugger.server.DebuggerHandler;
import org.mule.debugger.server.DebuggerServerSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RemoteDebuggerSession implements Runnable
{

    protected Socket clientSocket = null;
    protected String serverText = null;
    private final DebuggerHandler handler;
    private static Logger log = Logger.getLogger(RemoteDebuggerSession.class.getName());



    public RemoteDebuggerSession(Socket clientSocket, String serverText, DebuggerHandler handler)
    {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.handler = handler;
    }

    public void run()
    {
        InputStream input = null;
        OutputStream output = null;
        try
        {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();

            if (!handler.isRunning())
            {
                handler.setSessionFactory(new DebuggerServerSessionFactory(output, input));
                handler.start();
                handler.stop();
            } else{
                //response error
            }


        } catch (IOException e)
        {
            log.log(Level.WARNING, "Exception while connecting to client", e);
        } finally
        {

            if (input != null)
            {
                try
                {
                    input.close();
                } catch (IOException e)
                {

                }
            }
            if (output != null)
            {
                try
                {
                    output.close();
                } catch (IOException e)
                {

                }
            }
            try
            {
                clientSocket.close();
            } catch (IOException e)
            {

            }
        }
    }


}
