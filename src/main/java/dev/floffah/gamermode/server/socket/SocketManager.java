package dev.floffah.gamermode.server.socket;

import dev.floffah.gamermode.server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SocketManager {
    int port = 25565;

    ServerSocket sock;

    public Server server;
    Thread lthread;

    Map<UUID, SocketConnection> verified = new HashMap<>();
    List<SocketConnection> newconns = new LinkedList<>();

    public SocketManager(Server server) {
        this.server = server;
    }

    public void start() throws IOException {
        sock = new ServerSocket(port);
        listen();
    }

    public void listen() {
        Runnable listener = () -> {
            while(true) {
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

        lthread = new Thread(listener);
        lthread.start();
    }

    public void stop() throws IOException {
//        in.close();
//        out.close();
//        csock.close();
        sock.close();
    }
}
