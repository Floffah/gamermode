module.exports = {
    root: true,
    parser: "@typescript-eslint/parser",
    plugins: ["workspaces", "@typescript-eslint"],
    extends: [
        "eslint:recommended",
        "plugin:@typescript-eslint/recommended",
        "prettier",
        "prettier/@typescript-eslint",
    ],
    rules: {
        "workspaces/no-relative-imports": "error",
        "workspaces/require-dependency": "warn",
        "workspaces/no-cross-imports": "off",
        "@typescript-eslint/explicit-module-boundary-types": "off",
        "@typescript-eslint/no-explicit-any": "off",
        "no-unused-vars": "off",
        "@typescript-eslint/no-unused-vars": [
            "error",
            {
                argsIgnorePattern: "^_",
                vars: "all",
                args: "after-used",
                ignoreRestSiblings: false,
            },
        ],
        "@typescript-eslint/no-var-requires": "off",
    },
    settings: {
        prettier: true,
    },
    env: {
        node: true,
    },
};
