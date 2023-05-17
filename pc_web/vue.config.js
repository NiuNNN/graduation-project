'use strict';
// import { targetUrl } from '@/targetUrl.js';
const { defineConfig } = require('@vue/cli-service');

const port = process.env.port || 8080; // dev port

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    historyApiFallback: true,
    allowedHosts: 'all',
    port: port,
    proxy: {
      '/api': {
        target: `http://localhost:80`, // 要代理的目标地址
        // target: `http://niuniu6.natapp1.cc`, // 要代理的目标地址
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
});
