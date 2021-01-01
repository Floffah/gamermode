export function numToUByteBuf(num: number): Buffer {
    const buf = Buffer.allocUnsafe(1);
    buf.writeUInt8(num);
    return buf;
}

export function numToByteBuf(num: number): Buffer {
    const buf = Buffer.allocUnsafe(1);
    buf.writeInt8(num);
    return buf;
}

export function numToIntBuf(num: number): Buffer {
    const buf = Buffer.allocUnsafe(4);
    buf.writeInt32BE(num);
    return buf;
}

export function numToLongBuf(num: number): Buffer {
    const buf = Buffer.allocUnsafe(8);
    buf.writeBigInt64BE(BigInt(num));
    return buf;
}

export function numToFloatBuf(num: number): Buffer {
    const buf = Buffer.allocUnsafe(4);
    buf.writeUInt32BE(num);
    return buf;
}

export function numToDoubleBuf(num: number): Buffer {
    const buf = Buffer.allocUnsafe(8);
    buf.writeBigUInt64BE(BigInt(num));
    return buf;
}
