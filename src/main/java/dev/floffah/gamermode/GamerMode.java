package dev.floffah.gamermode;

import dev.floffah.gamermode.server.Server;
import dev.floffah.gamermode.visuals.Logger;

public class GamerMode {
    public static void main(String[] args) {
        try {
            Server server = new Server(args);
        } catch (Exception e) {
            if (Logger.inst != null) {
                Logger.inst.printStackTrace(e);
            } else {
                System.out.println(e);
            }
        }
    }
}
