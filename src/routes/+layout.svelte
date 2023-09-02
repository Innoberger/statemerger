<script>
	// @ts-nocheck

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
</script>

<Styles />

<Navbar color="dark" dark expand="md">
	<NavbarBrand href="/">StateMerger</NavbarBrand>
	<NavbarToggler on:click={() => (isOpen = !isOpen)} />
	<Collapse {isOpen} navbar expand="md" on:update={handleUpdate}>
		<Nav class="ms-auto" navbar>
			{#each pages as page}
			<NavItem>
				<NavLink href="/{page.link}">
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
