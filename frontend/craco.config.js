const path = require("path");

const aliases = {
    "@": "src/",
    "@intl": "src/i18n",
    "@fleetdata": "src/fleetdata"
}

const webpackAliases = Object.keys(aliases).reduce(
    (obj, alias) => ({
        ...obj,
        [alias]: path.resolve(__dirname, aliases[alias])
    }),
    {}
)

const jestAliases = Object.keys(aliases).reduce(
    (obj, alias) => (
        // if it's a singular prefix, require a slash '/' after it
        // to ensure there are no other packages using it
        (alias.length == 1)
            ? {
                ...obj,
                [`^${alias}/(.*)$`]: `<rootDir>/${aliases[alias]}/$1`
            }
        : {
            ...obj,
            [`^${alias}(.*)$`]: `<rootDir>/${aliases[alias]}$1`
        }),
    {}
)

module.exports = {
    webpack: {
        alias: {
            ...webpackAliases
        }
    },
    jest: {
        configure: {
            moduleNameMapper: {
                ...jestAliases
            },
            collectCoverageFrom: [
                "src/**/*.{js,jsx,ts,tsx}",
                "!node_modules/**/*",
                "!src/setupProxy.js",
                "!src/serviceWorker.ts",
                "!src/fleetdata/**/*",
                "!src/i18n/components/**/*"
            ]
        }
    }
};
