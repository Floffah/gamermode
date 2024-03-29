package dev.floffah.gamermode.server.socket;

import dev.floffah.gamermode.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SocketManager {
    public Server server;
    public Map<UUID, SocketConnection> verified = new HashMap<>();
    public List<SocketConnection> newconns = new LinkedList<>();
    public ServerSocket sock;

    public SocketManager(Server server) {
        this.server = server;
    }

    public void start() throws IOException {
        sock = new ServerSocket(server.conf.info.port);
        listen();
        server.logger.info("Listening on " + sock.getLocalSocketAddress().toString());
    }

    public void listen() {
        Runnable listener = () -> {
            for (; ; ) {
                try {
                    server.logger.info("Accepting connection...");
                    Socket csock = sock.accept();
                    server.logger.info("Connection accepted");
                    SocketConnection conn = new SocketConnection(this, csock);
                    newconns.add(conn);
                } catch (IOException e) {
                    server.logger.printStackTrace(e);
                }
            }
        };

        server.pool.execute(listener);
    }

    public void stop() throws IOException {
//        in.close();
//        out.close();
//        csock.close();
        server.pool.shutdown();
        sock.close();
    }
}
