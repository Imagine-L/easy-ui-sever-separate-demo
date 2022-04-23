const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: 80,  // 端口号的配置
        proxy: 'http://localhost:8080'
    },
    lintOnSave: false
})
