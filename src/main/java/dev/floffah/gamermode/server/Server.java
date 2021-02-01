package dev.floffah.gamermode.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.floffah.gamermode.events.EventEmitter;
import dev.floffah.gamermode.events.server.ShutdownEvent;
import dev.floffah.gamermode.server.socket.SocketManager;
import dev.floffah.gamermode.visuals.Logger;
import dev.floffah.gamermode.visuals.gui.GuiWindow;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    public static Server server;

    public GuiWindow win;
    public Logger logger;
    public List<String> args;
    public EventEmitter events;
    public ObjectMapper om;

    SocketManager sock;

    public Server(String[] args) {
        Server.server = this;
        this.args = Arrays.asList(args);
        logger = new Logger(this);
        win = GuiWindow.start(this);

        logger.info(String.format("Running on Java version %s on %s", System.getProperty("java.version"), System.getProperty("os.name")));
        events = new EventEmitter(this);

        om = new ObjectMapper(new YAMLFactory());

        logger.info("Starting socket...");
        sock = new SocketManager(this);
        try {
            sock.start();
        } catch (IOException e) {
            logger.printStackTrace(e);
        }
    }

    public void shutDown() {
        events.execute(new ShutdownEvent());

        logger.info("Goodbye!");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.purge();
                win.stop();
                System.exit(0);
            }
        }, 2000);
    }
}
