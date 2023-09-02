<script lang="ts">
	// @ts-nocheck

	import { page } from '$app/stores'

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

	let isOpen = false;
	let currentPage;

	function handleUpdate(event) {
		isOpen = event.detail.isOpen;
	}

	const pages = [
		{
			"title": "Start",
			"link": "",
			"icon": "house-door-fill"
		},
		{
			"title": "Karte",
			"link": "map",
			"icon": "map-fill"
		},
		{
			"title": "Baum",
			"link": "tree",
			"icon": "tree-fill"
		},
		{
			"title": "Hilfe",
			"link": "help",
			"icon": "question-lg"
		}
	]

	$ : currentPage = $page.url.pathname

	function isActive(link: string): Boolean {
		console.log(link + ":" + `/${link}` === currentPage)
		return `/${link}` === currentPage
	}
</script>

<Styles />

<Navbar color="dark" class="sticky-top" dark expand="md">
	<NavbarBrand href="/">StateMerger</NavbarBrand>
	<NavbarToggler on:click={() => (isOpen = !isOpen)} />
	<Collapse {isOpen} navbar expand="md" on:update={handleUpdate}>
		<Nav class="ms-auto" navbar>
			{#each pages as page}
				<NavItem>
					<NavLink active={isActive(page.link)} href="/{page.link}">
						<Icon name={page.icon}/>
						<span>{page.title}</span>
					</NavLink>
				</NavItem>
			{/each}
		</Nav>
	</Collapse>
</Navbar>

<main>
	<slot></slot>
</main>
