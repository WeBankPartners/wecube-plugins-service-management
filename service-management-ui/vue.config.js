module.exports = {
  devServer: {
    // hot: true,
    // inline: true,
    open: true,
    port: 3000,
    proxy: {
      "/service-mgmt": {
        target: " http://localhost:21000"
      },
      "/service-management": {
        target: " http://localhost:21000"
      },
      "/users": {
        target: " http://localhost:21000"
      },
    }
  },
  runtimeCompiler: true,
  publicPath: "/",
  productionSourceMap: false,
  chainWebpack: config => {
    const img = config.module.rule("images");
    img.uses.clear();
    img
      .use("url-loader")
      .loader("url-loader")
      .options({ limit: 1000000 });

    const svg = config.module.rule("svg");
    svg.uses.clear();
    svg.uses.clear();
    svg
      .use("url-loader")
      .loader("url-loader")
      .options({ limit: 1000000 });
    config.when(process.env.PLUGIN === "plugin", config => {
      config
        .entry("app")
        .clear()
        .add("./src/main-plugin.js"); //作为插件时
    });
  },
  configureWebpack: config => {
    if (process.env.PLUGIN === "plugin") {
      config.optimization.splitChunks = {}
    }
  }
};
