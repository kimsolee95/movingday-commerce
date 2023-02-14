const bootstrapSassAbstractsImports = require('vue-cli-plugin-bootstrap-vue/sassAbstractsImports.js')
//const { defineConfig } = require('@vue/cli-service')
//module.exports = defineConfig({
//  transpileDependencies: true
//})

module.exports = {
  outputDir: "../src/main/resources/static",  // 빌드 타겟 디렉토리
  devServer: {
    proxy: {
      '/api': {
        // 개발중일때(뷰포트 8080, 스프링포트 8081일때), '/api' 로 들어오면 포트 8081(스프링 서버)로 보낸다
        target: 'http://localhost:8081',
        changeOrigin: true // cross origin 허용
      }
    }
  },
	css: {
		loaderOptions: {
			sass: {
				additionalData: bootstrapSassAbstractsImports.join('\n')
			},
			scss: {
				additionalData: [...bootstrapSassAbstractsImports, ''].join(';\n')
			}
		}
	}
};