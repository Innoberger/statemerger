<script lang="ts">
	// @ts-nocheck

	import { page } from '$app/stores';
	import {
		Styles,
		Collapse,
		Navbar,
		NavbarToggler,
		NavbarBrand,
		Nav,
		NavItem,
		NavLink,
		Icon
	} from 'sveltestrap';

	export let data;
	export let currentPage: {
		title: string;
		link: string;
		icon: string;
		description: string;
	};

	$: currentPage = data.pages.find(_page => $page.url.pathname === `/${_page.link}`);

	let isOpen = false;

	function handleUpdate(event) {
		isOpen = event.detail.isOpen;
	}

</script>

<svelte:head>
	<title>{currentPage.title}</title>
	<meta name="description" content={currentPage.description} />
</svelte:head>

<Styles />

<Navbar color="dark" class="sticky-top" dark expand="md">
	<NavbarBrand href="/">StateMerger</NavbarBrand>
	<NavbarToggler on:click={() => (isOpen = !isOpen)} />
	<Collapse {isOpen} navbar expand="md" on:update={handleUpdate}>
		<Nav class="ms-auto" navbar>
			{#each data.pages as _page}
				<NavItem>
					<NavLink active={currentPage.link === _page.link} href="/{_page.link}">
						<Icon name={_page.icon}/>
						<span>{_page.title}</span>
					</NavLink>
				</NavItem>
			{/each}
		</Nav>
	</Collapse>
</Navbar>

<main>{currentPage.title}
	<slot></slot>
</main>
