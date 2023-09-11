<script lang="ts">
	import { selectedCountryStatesForest } from "$lib/stores/selected-country";
	import FindState from "./FindState.svelte";
	import MakeState from "./MakeState.svelte";
	import Union from "./Union.svelte";

	export let findStateFunction: (search: string) => void;
	export let unionFunction: () => void;
	export let makeStateFunction: (city: string, state: string) => boolean;
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};
</script>

<div class="mb-4">
	{#if $selectedCountryStatesForest}
		<FindState {findStateFunction}/>
		<Union bind:mergeNodes={mergeNodes} {unionFunction}/>
	{/if}
	<MakeState {makeStateFunction}/>
</div>