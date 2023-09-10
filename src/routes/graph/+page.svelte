<script lang="ts">
	import CountrySelectorButton from '$lib/components/country-selector/CountrySelectorButton.svelte';
	import CountryForest from '$lib/components/graph/CountryForest.svelte'
</script>


<!-- TODO:
		- Display selected states here
		- Adjust the row/col divs (after some merges use full row instead of one column or so)
		- Button to unselect selectedNodes.first
		- Forms for Make-State, Find-State and Union
		- Settings
			* toggle for show uuid node names
			* toggle for show non-root and non-leave node names
			* toggle for show ranks in node circles
-->


<CountrySelectorButton />
<CountryForest />