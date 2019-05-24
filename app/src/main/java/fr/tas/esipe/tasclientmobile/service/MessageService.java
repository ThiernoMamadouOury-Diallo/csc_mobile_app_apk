package fr.tas.esipe.tasclientmobile.service;

import fr.tas.esipe.tasclientmobile.model.ConnectionInfo;

public class MessageService {
    ConnectionInfo connectionInfo;

    public MessageService(ConnectionInfo connectionInfo) {

        this.connectionInfo = connectionInfo;

    }

    public Boolean sendMessage(byte[] bytes){
        Boolean isSend;
        isSend = connectionInfo.getConnectedThread().write(bytes);

        return isSend;
    }
}
