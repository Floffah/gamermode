package dev.floffah.gamermode.visuals;

import dev.floffah.gamermode.server.Server;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.Timestamp;

public class Logger {
    Server main;

    public Logger(Server main) {
        this.main = main;
    }

    public void info(String... message) {
        log(getFormat("info") + String.join(" ", message) + "\n");
    }

    public void err(String ...message) {
        err(true, message);
    }
    public void err(boolean dosout, String... message) {
        log(getFormat("err") + String.join(" ", message) + "\n", dosout);
    }

    public void printStackTrace(Exception e) {
        if (main.win != null && main.win.loaded) {
            String[] stack = ExceptionUtils.getStackFrames(e);
            for (String s : stack) {
                err(false, s);
            }
        }
        e.printStackTrace();
    }

    String getFormat(String type) {
        return String.format("[%s] [%s] [%s]: ", new Timestamp(System.currentTimeMillis()), Thread.currentThread().getName(), type);
    }

    public void log(String message) {
        this.log(message, true);
    }

    public void log(String message, boolean dosout) {
        if (main.win != null && main.win.loaded) {
            main.win.log(message);
        }
        if(dosout) System.out.print(message);
    }
}
