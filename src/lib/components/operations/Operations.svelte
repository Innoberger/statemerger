<script lang="ts">
	import { selectedCountryStatesForest } from "$lib/stores/selected-country";
	import Union from "./Union.svelte";

	export let unionFunction: () => void;
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};
</script>

<div class="mb-5">
	{#if $selectedCountryStatesForest}
		<Union bind:mergeNodes={mergeNodes} {unionFunction}/>
	{/if}
</div>