import { encode } from "varint";

export function stringArrayToBuf(strs: string[]): Buffer {
    let buf = Buffer.of();
    for (const str of strs) {
        const s = Buffer.from(str, "utf-8");
        buf = Buffer.of(...buf, ...encode(s.byteLength), ...s);
    }
    return buf;
}

export function stringToBuf(str: string): Buffer {
    const s = Buffer.from(str, "utf-8");
    return Buffer.of(...encode(s.byteLength), ...s);
}
