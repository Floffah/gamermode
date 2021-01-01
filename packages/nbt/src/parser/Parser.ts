/**
 * this is very broken and very unfinish
 * TODO: fix and finish
 */

import { readFileSync } from "fs";
import { decode } from "varint";

export default class Parser {
    filepath: string;
    buf: Buffer;

    data = {};

    constructor(filepath: string) {
        this.filepath = filepath;
    }

    read(): { [k: string]: any } {
        try {
            this.buf = readFileSync(this.filepath);
        } catch (e) {
            return {};
        }

        this.data = this.readCompound(this.buf);

        return this.data;
    }

    readCompound(
        buf: Buffer,
        full?: Buffer,
        start?: number,
    ): { [k: string]: any } {
        let obj = {};
        let nextbyte = 0;

        const n = (n: number) => (nextbyte = nextbyte + n);

        if (buf[0] === 10) {
            n(1);
            const namelen = buf.slice(nextbyte, nextbyte + 2).readUInt16BE();
            n(2);
            const name = buf
                .slice(nextbyte, nextbyte + namelen)
                .toString("utf-8");
            n(namelen);

            while (nextbyte <= buf.byteLength) {
                const type = decode(buf.slice(nextbyte, nextbyte + 1));

                if (type === 9) {
                    const [list, bytes] = this.readList(
                        buf.slice(nextbyte),
                        buf,
                        (start || 0) + nextbyte,
                    );
                    obj = {
                        ...obj,
                        ...list,
                    };
                    n(bytes + 1);
                } // else {
                break;
                //}
            }

            const final: { [k: string]: any } = {};
            final[name] = obj;
            return final;
        }
        return {};
    }

    readList(
        buf: Buffer,
        full?: Buffer,
        start?: number,
    ): [{ [k: string]: any[] }, number] {
        const list: any[] = [];

        let nextbyte = 0;
        const n = (n: number) => (nextbyte = nextbyte + n);

        n(1);
        const namelen = buf.slice(nextbyte, nextbyte + 2).readUInt16BE();
        n(2);
        const name = buf.slice(nextbyte, nextbyte + namelen).toString("utf-8");
        n(namelen);
        const datatype = buf[nextbyte];
        n(1);
        const items = buf.slice(nextbyte, nextbyte + 4).readInt32BE();
        n(4);

        for (let i = 0; i < items; i++) {
            if (datatype === 10) {
                const [obj, bytes] = this.readNoneCompound(
                    buf.slice(nextbyte),
                    full,
                    start ? start + nextbyte : undefined,
                );
                n(bytes + 1);
                list.push(obj);
            }
        }

        const final: { [k: string]: any[] } = {};
        final[name] = list;

        return [final, nextbyte - 1];
    }

    readNoneCompound(
        buf: Buffer,
        full?: Buffer,
        start?: number,
    ): [{ [k: string]: any }, number] {
        const obj: { [k: string]: any } = {};

        let more = true;
        let nextbyte = 0;
        const n = (n: number) => (nextbyte = nextbyte + n);
        while (more) {
            if (buf[nextbyte] === 0) {
                more = false;
                break;
            } else {
                const type = buf[nextbyte];
                if (type === 8) {
                    const [name, val, bytes] = this.readString(
                        buf.slice(nextbyte),
                        full,
                        start ? start + nextbyte : undefined,
                    );
                    n(bytes + 1);
                    obj[name] = val;
                } else if (type === 1) {
                    const [name, val, bytes] = this.readByte(
                        buf.slice(nextbyte),
                        full,
                        start ? start + nextbyte : undefined,
                    );
                    n(bytes + 1);
                    obj[name] = val;
                } else {
                    more = false;
                }
            }
        }

        return [obj, nextbyte - 1];
    }

    readByte(
        buf: Buffer,
        full?: Buffer,
        start?: number,
    ): [string, number, number] {
        let name = "";
        let val = 0;
        let nextbyte = 0;
        const n = (n: number) => (nextbyte = nextbyte + n);

        n(1);
        const namelen = buf.slice(nextbyte, nextbyte + 2).readUInt16BE();
        n(2);
        name = buf.slice(nextbyte, nextbyte + namelen).toString("utf-8");
        n(namelen);
        val = buf.slice(nextbyte, nextbyte + 1).readInt8();

        return [
            name,
            val,
            nextbyte -
                (full ? (full[(start || 0) + nextbyte] == 0 ? 0 : 1) : 1),
        ];
    }

    readString(
        buf: Buffer,
        full?: Buffer,
        start?: number,
    ): [string, string, number] {
        let name = "";
        let val = "";
        let nextbyte = 0;
        const n = (n: number) => (nextbyte = nextbyte + n);

        n(1);
        const namelen = buf.slice(nextbyte, nextbyte + 2).readUInt16BE();
        n(2);
        name = buf.slice(nextbyte, nextbyte + namelen).toString("utf-8");
        n(namelen);
        const vallen = buf.slice(nextbyte, nextbyte + 2).readUInt16BE();
        n(2);
        val = buf.slice(nextbyte, nextbyte + vallen).toString("utf-8");
        n(vallen);
        console.log(name, vallen, name === "icon" ? "0" : val);

        return [
            name,
            val,
            nextbyte -
                (full ? (full[(start || 0) + nextbyte] == 0 ? 0 : 1) : 1),
        ];
    }
}
