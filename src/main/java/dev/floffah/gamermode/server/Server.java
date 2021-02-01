package dev.floffah.gamermode.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.floffah.gamermode.config.Config;
import dev.floffah.gamermode.events.EventEmitter;
import dev.floffah.gamermode.events.server.ShutdownEvent;
import dev.floffah.gamermode.server.socket.SocketConnection;
import dev.floffah.gamermode.server.socket.SocketManager;
import dev.floffah.gamermode.visuals.Logger;
import dev.floffah.gamermode.visuals.gui.GuiWindow;
import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public Config conf;
    public int protver = 754;

    SocketManager sock;

    public Server(String[] args) {
        Server.server = this;
        this.args = Arrays.asList(args);
        logger = new Logger(this);
        win = GuiWindow.start(this);

        logger.info(String.format("Running on Java version %s on %s", System.getProperty("java.version"), System.getProperty("os.name")));
        events = new EventEmitter(this);

        om = new ObjectMapper(new YAMLFactory());
        String parent = null;
        try {
            parent = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toUri().getPath();
            if (this.args.contains("-usewd")) {
                parent = System.getProperty("user.dir");
            }
            logger.info(parent);
            File confile = Path.of(parent, "config.yml").toFile();
            if(!confile.exists()) {
                conf = new Config();
                setDefaultConfig();
                om.writeValue(confile, conf);
            }
            conf = om.readValue(confile, Config.class);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        logger.info("Starting socket...");
        sock = new SocketManager(this);
        try {
            sock.start();
        } catch (IOException e) {
            logger.printStackTrace(e);
        }
    }

    public void setDefaultConfig() {
        conf.players = new Config.PlayerConf();
        conf.players.max = 20;
    }

    public void shutDown() throws IOException {
        events.execute(new ShutdownEvent());

        logger.info("Goodbye!");

        for (SocketConnection conn : sock.verified.values()) {
            conn.close();
        }

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
