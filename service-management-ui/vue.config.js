module.exports = {
  devServer: {
    // hot: true,
    // inline: true,
    open: true,
    port: 3000,
    proxy: {
      "/service-request": {
        target: " http://localhost:21000"
      },
      "/tasks": {
        target: " http://localhost:21000"
      },
    }
  },
  runtimeCompiler: true,
  publicPath: "/",
};
