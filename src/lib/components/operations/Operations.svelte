<script lang="ts">
	import { selectedCountryStatesForest } from "$lib/stores/selected-country";
	import FindState from "./FindState.svelte";
	import Union from "./Union.svelte";

	export let findStateFunction: (search: string) => void;
	export let unionFunction: () => void;
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};
</script>

<div class="mb-5">
	{#if $selectedCountryStatesForest}
		<FindState {findStateFunction}/>
		<Union bind:mergeNodes={mergeNodes} {unionFunction}/>
	{/if}
</div>