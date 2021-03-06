package dev.floffah.gamermode.visuals;

import dev.floffah.gamermode.server.Server;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.Timestamp;

public class Logger {
    public static Logger inst;

    Server main;

    public Logger(Server main) {
        this.main = main;
        Logger.inst = this;
    }

    public void info(String... message) {
        log(getFormat("info") + String.join(" ", message) + "\n");
    }

    public void warn(String... message) {
        log(getFormat("warn") + String.join(" ", message) + "\n");
    }

    public void debug(String... message) {
        if (main.conf.debug.debugLog) {
            log(getFormat("debug") + String.join(" ", message) + "\n");
        }
    }

    public void debugwarn(String... message) {
        if (main.conf.debug.debugLog) {
            log(getFormat("debugwarn") + String.join(" ", message) + "\n");
        }
    }

    public void err(String... message) {
        err(true, message);
    }

    public void err(boolean dosout, String... message) {
        log(getFormat("err") + String.join(" ", message) + "\n", dosout);
    }

    public void printStackTrace(Exception e) {
        String[] stack = ExceptionUtils.getStackFrames(e);
        for (String s : stack) {
            err(!main.conf.debug.debugLog, s);
        }
        if (main.conf.debug.debugLog) e.printStackTrace();
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
        if (dosout) System.out.print(message);
    }
}
