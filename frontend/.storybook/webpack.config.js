const path = require("path");
const webpackOverrides = require("../craco.config").webpack

module.exports = (baseConfig, env, storybookConfig) => {
    /* Set up typescript support */
    storybookConfig.module.rules.push({
        test: /\.(ts|tsx)$/,
        loader: require.resolve('babel-loader'),
        options: {
            presets: [['react-app', { flow: false, typescript: true }]],
        },
    });
    storybookConfig.resolve.extensions.push('.ts', '.tsx');

    /* Set up absolute paths */
    storybookConfig.resolve.alias = {
        ...storybookConfig.resolve.alias,
        ...webpackOverrides.alias
    }
    return storybookConfig
};