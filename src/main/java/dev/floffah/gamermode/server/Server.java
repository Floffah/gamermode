package dev.floffah.gamermode.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.floffah.gamermode.config.Config;
import dev.floffah.gamermode.events.EventEmitter;
import dev.floffah.gamermode.events.server.ShutdownEvent;
import dev.floffah.gamermode.server.socket.SocketConnection;
import dev.floffah.gamermode.server.socket.SocketManager;
import dev.floffah.gamermode.visuals.Logger;
import dev.floffah.gamermode.visuals.gui.GuiWindow;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class Server {
    public static Server server;

    public GuiWindow win;
    public Logger logger;
    public List<String> args;
    public EventEmitter events;
    public ObjectMapper om;
    public Config conf;
    public int protver = 754;
    public KeyPairGenerator kpg;
    public File confile;

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
            confile = Path.of(parent, "config.yml").toFile();
            if (!confile.exists()) {
                conf = new Config();
                saveConfig();
            }
            loadConfig();
        } catch (URISyntaxException | IOException e) {
            logger.printStackTrace(e);
        }

        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            logger.printStackTrace(e);
        }

        logger.info("Starting socket...");
        sock = new SocketManager(this);
        try {
            sock.start();
        } catch (IOException e) {
            logger.printStackTrace(e);
        }
    }

    public void loadConfig() throws IOException {
        conf = om.readValue(confile, Config.class);
    }

    public void saveConfig() throws IOException {
        om.writeValue(confile, conf);
    }

    public void shutDown() throws IOException {
        events.execute(new ShutdownEvent());

        saveConfig();

        logger.info("Goodbye!");

        for (SocketConnection conn : sock.verified.values()) {
            conn.close();
        }

        win.stop();
        System.exit(0);
    }
}
