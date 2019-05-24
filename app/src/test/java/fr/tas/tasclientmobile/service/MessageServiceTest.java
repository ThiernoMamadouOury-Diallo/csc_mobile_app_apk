package fr.tas.tasclientmobile.service;

import fr.tas.esipe.tasclientmobile.model.ConnectionInfo;
import fr.tas.esipe.tasclientmobile.infrastructure.ConnectedThread;
import fr.tas.esipe.tasclientmobile.service.MessageService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.nio.charset.Charset;

public class MessageServiceTest {

    ConnectedThread connectedThread;
    ConnectionInfo connectionInfo;
    @Before
    public void setUp(){

        connectionInfo = Mockito.mock(ConnectionInfo.class);

    }

    @Test
    public void  testSendMessage(){
        //Expected
        connectedThread = Mockito.mock(ConnectedThread.class);

        // stubbing appears before the actual execution
        String ClientId_And_CarId="1 AA-000-ZZ";
        byte[] bytes = ClientId_And_CarId.getBytes(Charset.defaultCharset());

        Mockito.when(connectionInfo.getConnectedThread()).thenReturn(connectedThread);
        Mockito.when(connectionInfo.getSocketUP()).thenReturn(true);
        Mockito.when(connectedThread.write(bytes)).thenReturn(true);

        //Actual
        MessageService messageService =new MessageService(connectionInfo);
        Boolean resultat = messageService.sendMessage(bytes);
        Boolean status = connectionInfo.getSocketUP();

        Assert.assertEquals(resultat, true);
        Assert.assertEquals(status, true);
    }

}
