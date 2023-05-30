'use strict';
const path = require('path');
const { defineConfig } = require('@vue/cli-service');
const port = process.env.port || 8080; // dev port

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = defineConfig({
  publicPath: '/',
  outputDir: '/PRHMS-pc',
  assetsDir: 'static',
  transpileDependencies: true,
  devServer: {
    historyApiFallback: true,
    allowedHosts: 'all',
    port: port,
    host: '0.0.0.0',
    proxy: {
      '/api': {
        // target: `http://192.168.31.75:80`, // 要代理的目标地址
        target: `http://niuniu6.natapp1.cc`, // 要代理的目标地址
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  },
  configureWebpack: {
    name: `出租公屋管理系统 | PRHMS`,
    resolve: {
      alias: {
        '@': resolve('src')
      }
    },
    //配置需要排除的包
    externals: {
      vue: 'Vue',
      echarts: 'echarts',
      axios: 'axios'
    }
  }
});
