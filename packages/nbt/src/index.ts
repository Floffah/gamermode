import "source-map-support/register";
import Parser from "./parser/Parser";
import { resolve } from "path";
import { writeFileSync } from "fs";

export { Parser };

const p = new Parser(resolve(__dirname, "../test/servers.dat"));
writeFileSync(
    resolve(__dirname, "../test/servers.dat.json"),
    JSON.stringify(p.read(), null, 4),
);
