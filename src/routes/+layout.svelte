<script lang="ts">
	import { page } from '$app/stores';

	export let data;
	export let currentPage: {
		title: string;
		external: boolean;
		link: string;
		icon: string;
		description?: string;
		headline?: boolean;
	};

	$: currentPage = data.pages.find((page: { external: any; link: any; }) => !page.external && $page.url.pathname === `/${page.link}`)!;
</script>

<svelte:head>
	<title>{currentPage.title} | StateMerger</title>
	<meta name="description" content={currentPage.description} />
</svelte:head>


{#if currentPage.headline}

	<div class="min-vh-100 root">
		<div class="d-flex flex-column wrapper">
			<nav class="navbar navbar-dark bg-dark sticky-top navbar-expand-lg">
				<div class="container-md">
					<strong><a class="navbar-brand" href="/">StateMerger</a></strong>
					<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
					<div class="collapse navbar-collapse" id="navbarNav">
						<ul class="navbar-nav ms-auto">
							{#each data.pages as page}
								<li class="nav-item">
									<a class="nav-link {currentPage.link === page.link ? 'active' : ''}" target="{page.external ? '_blank' : '' }" aria-current="page" href="{page.external ? '' : '/'}{page.link}">
										<i class="bi-{page.icon}"></i>
										<span>{page.title}</span>
									</a>
								</li>
							{/each}
						</ul>
					</div>
				</div>
			</nav>

			<main class="py-4">
				<div class="container-md">
						<h1>
							<i class="bi-{currentPage.icon}"></i>
							<span>{currentPage.title}</span>
						</h1>
						<hr/>
					<div class="pt-1 text-justify">
						<slot></slot>
					</div>
				</div>
			</main>
		</div>

		<footer class="bg-dark text-secondary py-3">
			<div class="container-md d-flex justify-content-between">
				<div>
					<strong>StateMerger:</strong> Optimierung der Bundesrepublik. Eine Projektarbeit an der <a class="link-secondary" target="_blank" href="https://www.h-ka.de/">Hochschule Karlsruhe</a>.
				</div>
				<div>
					&copy; 2023 Fynn Arnold. Licensed under the <a class="link-secondary" target="_blank" href="https://github.com/Innoberger/statemerger/blob/main/LICENSE">EUPL</a>.
				</div>				
			</div>
		</footer>
	</div>

{:else }

	<slot></slot>

{/if}

<style>
	.root {
		display: flex;
		flex-direction: column;
	}

	.wrapper {
		flex: 1;
	}

	.text-justify {
		text-align: justify;
	}
</style>