export default class Player {
    name: string;
    uuid: string; // recognised by the protocol
    formatuuid: string; // the more human-readible uuid that is the only version the uuid package supports

    position: Position;
}

export interface Position {
    x: number;
    y: number;
    z: number;
    yaw: number;
    pitch: number;
}
