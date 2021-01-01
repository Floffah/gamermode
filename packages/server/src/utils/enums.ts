export function getAllWithValue(e: any, val: any): string[] {
    const es = [];

    for (const key of Object.keys(e)) {
        if (e[key] === val) {
            es.push(key);
        }
    }

    return es;
}
