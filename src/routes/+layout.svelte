<script lang="ts">
	// @ts-nocheck

	import { page } from '$app/stores';

	export let data;
	export let currentPage: {
		title: string;
		link: string;
		icon: string;
		description: string;
	};

	$: currentPage = data.pages.find(page => $page.url.pathname === `/${page.link}`);
</script>

<svelte:head>
	<title>{currentPage.title}</title>
	<meta name="description" content={currentPage.description} />
</svelte:head>

<!--
<Styles />

<Navbar color="dark" class="sticky-top" dark expand="md">
	<NavbarBrand href="/">StateMerger</NavbarBrand>
	<NavbarToggler on:click={() => (isOpen = !isOpen)} />
	<Collapse {isOpen} navbar expand="md" on:update={handleUpdate}>
		<Nav class="ms-auto" navbar>
			{#each data.pages as page}
				<NavItem>
					<NavLink active={currentPage.link === page.link} href="/{page.link}">
						<Icon name={page.icon}/>
						<span>{page.title}</span>
					</NavLink>
				</NavItem>
			{/each}
		</Nav>
	</Collapse>
</Navbar>
-->

<nav class="navbar navbar-dark bg-dark sticky-top navbar-expand-lg">
	<div class="container-fluid">
		<a class="navbar-brand" href="/">StateMerger</a>
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

<main>
	<i class="0-circle"></i>
	<h2>{currentPage.title}</h2>
	<slot></slot>
</main>