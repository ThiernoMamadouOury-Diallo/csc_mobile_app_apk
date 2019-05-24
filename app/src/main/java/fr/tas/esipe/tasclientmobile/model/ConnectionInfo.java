package fr.tas.esipe.tasclientmobile.model;

import fr.tas.esipe.tasclientmobile.infrastructure.ConnectedThread;

public class ConnectionInfo {

    public ConnectedThread connectedThread;
    Boolean isSocketUP;

    public ConnectionInfo(ConnectedThread connectedThread, Boolean isSocketUP) {
        this.connectedThread = connectedThread;
        this.isSocketUP = isSocketUP;
    }

    public ConnectionInfo() {
    }

    public ConnectedThread getConnectedThread() {
        return connectedThread;
    }

    public void setConnectedThread(ConnectedThread connectedThread) {
        this.connectedThread = connectedThread;
    }

    public Boolean getSocketUP() {
        return isSocketUP;
    }

    public void setSocketUP(Boolean socketUP) {
        isSocketUP = socketUP;
    }
}
