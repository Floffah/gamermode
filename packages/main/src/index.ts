import "source-map-support/register";
import { Server } from "@gamermode/server";

const s = new Server({
    server: {
        host: "127.0.0.1",
        port: 25565,
    },
    players: {
        max: 50,
        showPlayers: true,
    },
    protocol: {
        debug: true,
    },
});

s.start();
