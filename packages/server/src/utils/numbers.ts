// EVERYTHING SHOULD BE BIG ENDIAN

import { read, write } from "ieee754";

// boolean - int between 0 and 1 - 1 byte length

export function writeBool(bool: boolean): Buffer {
    return Buffer.of(bool ? 0x01 : 0x00);
}

export function readBool(buf: Buffer, offset?: number): boolean {
    const nbuf = buf.slice(offset || 0);
    if (nbuf.byteLength >= 1) {
        return nbuf[0] === 0x01;
    } else {
        return false;
    }
}

// byte - int between -128 and 127 - 1 byte length

export function numToByte(num: number): Buffer {
    const buf = Buffer.allocUnsafe(1);
    buf.writeInt8(num);
    return buf;
}

export function byteToNum(buf: Buffer, offset?: number): number {
    const nbuf = buf.slice(offset || 0);
    try {
        return nbuf.readInt8();
    } catch (e) {
        return 0;
    }
}

// unsigned byte - unsigned int between 0 and 255 - 1 byte length

export function numToUByte(num: number): Buffer {
    const buf = Buffer.allocUnsafe(1);
    buf.writeUInt8(num);
    return buf;
}

export function uByteToNum(buf: Buffer, offset?: number): number {
    const nbuf = buf.slice(offset || 0);
    try {
        return nbuf.readUInt8();
    } catch (e) {
        return 0;
    }
}

// short - int between -32768 and 32767 - 2 byte length

export function numToShort(num: number): Buffer {
    const buf = Buffer.allocUnsafe(2);
    buf.writeInt16BE(num);
    return buf;
}

export function shortToNum(buf: Buffer, offset?: number): number {
    const nbuf = buf.slice(offset || 0);
    try {
        return nbuf.readInt16BE();
    } catch (e) {
        return 0;
    }
}

// unsigned short - unsigned int between 0 and 65535 - 2 byte length
// should be used for ports

export function numToUShort(num: number): Buffer {
    const buf = Buffer.allocUnsafe(2);
    buf.writeUInt16BE(num);
    return buf;
}

export function uShortToNum(buf: Buffer, offset?: number): number {
    const nbuf = buf.slice(offset || 0);
    try {
        return nbuf.readUInt16BE();
    } catch (e) {
        return 0;
    }
}

// int - int between -2147483648 and 2147483647 - 4 bytes

export function numToInt(num: number): Buffer {
    const buf = Buffer.allocUnsafe(4);
    buf.writeInt32BE(num);
    return buf;
}

export function intToNum(buf: Buffer, offset?: number): number {
    const nbuf = buf.slice(offset || 0);
    try {
        return nbuf.readUInt32BE();
    } catch (e) {
        return 0;
    }
}

// long - int between -9223372036854775808 and 9223372036854775807 - 8 bytes

export function numToLong(num: number): Buffer {
    const buf = Buffer.allocUnsafe(8);
    buf.writeBigInt64BE(BigInt(num));
    return buf;
}

export function longToNum(buf: Buffer, offset?: number): bigint {
    const nbuf = buf.slice(offset || 0);
    try {
        return nbuf.readBigInt64BE();
    } catch (e) {
        return BigInt(0);
    }
}

// float - single-precision ieee754 floating point number - 4 bytes
// TODO: if someone can remove the usage of the ieee754 package and make an algorithm for this i will pay you with hugs

export function numToFloat(num: number): Buffer {
    const buf = Buffer.allocUnsafe(4);
    write(buf, num, 0, false, 23, 4);
    return buf;
}

export function floatToNum(buf: Buffer, offset?: number): number {
    return read(buf, offset || 0, false, 23, 4);
}

// double - double-precision ieee754 floating point number - 8 bytes
// TODO: if someone can remove the usage of the ieee754 package and make an algorithm for this i will pay you with hugs

export function numToDouble(num: number): Buffer {
    const buf = Buffer.allocUnsafe(8);
    write(buf, num, 0, false, 52, 8);
    return buf;
}

export function doubleToNum(buf: Buffer, offset?: number): number {
    return read(buf, offset || 0, false, 52, 8);
}
