<script lang="ts">
	import { page } from '$app/stores';

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
	<title>{currentPage.title}</title>
	<meta name="description" content={currentPage.description} />
</svelte:head>

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
		{#if currentPage.headline}
			<h1>{currentPage.title}</h1>
			<hr/>
		{/if}
		<div class="pt-1">
			<slot></slot>
		</div>
    </div>
</main>

<footer class="bg-dark text-secondary fixed-bottom py-3 mt-5">
	<div class="container-md">
		<strong>StateMerger:</strong> Optimierung der Bundesrepublik. Eine Projektarbeit an der <a class="link-secondary" href="https://www.h-ka.de/">Hochschule Karlsruhe</a>.
	</div>
</footer>