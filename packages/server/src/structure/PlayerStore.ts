import Server from "../server/Server";
import Player from "./Player";

export default class PlayerStore {
    server: Server;

    players: Map<string, Player> = new Map();
    max: number;

    constructor(server: Server) {
        this.server = server;
        this.max = this.server.options.players.max;
    }
}
