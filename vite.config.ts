import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vitest/config';
import copy from 'rollup-plugin-copy'

export default defineConfig({
	plugins: [
		sveltekit(),
		copy({
			targets: [
				{
					src: 'node_modules/bootstrap/dist/**/*',
					dest: 'static/vendor/bootstrap'
				},
				{
					src: 'node_modules/bootstrap-icons/**/*',
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
