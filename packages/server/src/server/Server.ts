import { EventEmitter } from "events";
import { AddressInfo, createServer, Server as NetServer, Socket } from "net";
import Connection from "../connections/Connection";
import PlayerStore from "../structure/PlayerStore";

declare interface Server {
    on(event: "listening", listener: (port: number) => void): this;

    on(event: string, listener: () => void): this;
}

class Server extends EventEmitter {
    server: NetServer;

    options: ServerOptions;

    host: string;
    port: number;

    players: PlayerStore;

    constructor(options: ServerOptions) {
        super();
        this.options = options;
    }

    start() {
        this.server = createServer({
            allowHalfOpen: true,
        });

        this.server.on("listening", () => this.listening());
        this.server.on("connection", (s) => this.connection(s));

        this.server.listen({
            host: this.options.server.host,
            port: this.options.server.randomPort ? 0 : this.options.server.port,
        });
    }

    listening() {
        const address: string | AddressInfo | null = this.server.address();
        if (address && typeof address === "object") {
            this.port = address.port;
            this.host = address.address;

            this.emit("listening", this.port);
        } else {
            this.server.close();
            console.log("No address info");
            process.exit(1);
        }
        this.players = new PlayerStore(this);
    }

    connection(s: Socket) {
        new Connection(s, this);
    }
}

interface ServerOptions {
    web?: {
        enable: boolean;
    };
    server: {
        host: string;
        port: number;
        randomPort?: number;
    };
    players: {
        max: number;
        offset?: number;
        showPlayers?: boolean;
    };
    protocol?: {
        debug?: boolean;
    };
}

export default Server;
