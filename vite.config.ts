import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vitest/config';
import copy from 'rollup-plugin-copy'

export default defineConfig({
	plugins: [
		sveltekit(),
		copy({
			targets: [
				{
					src: 'node_modules/bootstrap/dist/css/bootstrap.min.css*',
					dest: 'static/vendor/bootstrap/css'
				},
				{
					src: 'node_modules/bootstrap/dist/js/bootstrap.bundle.min.js*',
					dest: 'static/vendor/bootstrap/js'
				},
				{
					src: 'node_modules/bootstrap-icons/*',
					dest: 'static/vendor/bootstrap-icons'
				},
			]
		})
	],

	test: {
		include: ['src/**/*.{test,spec}.{js,ts}']
	},

	css: {
		devSourcemap: true,
		preprocessorOptions: {
			scss: {
				additionalData: '@use "src/variables.scss" as *;'
			}
		}
	}
});
