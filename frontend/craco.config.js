const path = require("path");

module.exports = {
  webpack: {
    alias: {
      "@": path.resolve(__dirname, "src/"),
      "@intl": path.resolve(__dirname, "src/i18n"),
      "@fleetdata": path.resolve(__dirname, "src/fleetdata")
    }
  }
};
