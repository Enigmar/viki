package de.linzn.aiCore.processing.network.readIn;

import de.linzn.aiCore.App;
import de.linzn.javaSocket.server.events.SocketTypeEvent;
import de.linzn.javaSocket.server.interfaces.EventTypes;
import de.linzn.javaSocket.server.interfaces.ITypeListener;

public class ConnectEvent implements ITypeListener {

    private App app;

    public ConnectEvent(App app) {
        this.app = app;
    }

    @Override
    public EventTypes getType() {
        // TODO Auto-generated method stub
        return EventTypes.CONNECT;
    }

    @Override
    public void onTypeEvent(SocketTypeEvent event) {
        App.logger("A new Client connect: " + event.getMessenger().getClientUUID());
    }

}