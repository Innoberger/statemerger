<script lang="ts">
	import { page } from '$app/stores';
	import CountrySelectorDialog from '$lib/components/country-selector/CountrySelectorDialog.svelte';

	export let data;
	export let currentPage: {
		title: string;
		link: string;
		icon: string;
		description: string;
		headline: boolean;
	};

	$: currentPage = data.pages.find(page => $page.url.pathname === `/${page.link}`)!;
</script>

<svelte:head>
	<title>{currentPage.title} | StateMerger</title>
	<meta name="description" content={currentPage.description} />
</svelte:head>


{#if currentPage.headline}

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
							<a class="nav-link {currentPage.link === page.link ? 'active' : ''}" aria-current="page" href="/{page.link}">
								<i class="bi-{page.icon}"></i>
								<span>{page.title}</span>
							</a>
						</li>
					{/each}
				</ul>
			</div>
		</div>
	</nav>

	<main class="py-4 mb-5">
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
	
	<CountrySelectorDialog />

	<footer class="bg-dark text-secondary fixed-bottom py-3 mt-5">
		<div class="container-md">
			<strong>StateMerger:</strong> Optimierung der Bundesrepublik. Eine Projektarbeit an der <a class="link-secondary" href="https://www.h-ka.de/">Hochschule Karlsruhe</a>.
		</div>
	</footer>

{:else }

	<slot></slot>

{/if}

<style>
	.text-justify {
		text-align: justify;
	}
</style>