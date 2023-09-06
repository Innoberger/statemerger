<script lang="ts">
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';
	import CountrySelectorButton from '$lib/components/CountrySelectorButton.svelte';
	import Tree from '$lib/components/Tree.svelte';
</script>

<CountrySelectorButton />

{JSON.stringify($selectedCountryStatesForest)}

<Tree />