<script lang="ts">
	import { selectedCountry } from '$lib/stores/selected-country';
	import CountrySelectorButton from '$lib/components/CountrySelectorButton.svelte';
	import Tree from '$lib/components/Tree.svelte';
</script>

<CountrySelectorButton />

{JSON.stringify($selectedCountry)}

<Tree />