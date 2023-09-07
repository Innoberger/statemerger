<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryConfigJson, selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { State } from '$lib/parser/state';

    const predecessorMap = {
		"Stuttgart": "Baden-Württemberg",
		"Baden-Württemberg": "Baden-Württemberg",
		"Mannheim": "7e86eb1f-b49f-494d-a7db-a51cf1bddce4",
		"7e86eb1f-b49f-494d-a7db-a51cf1bddce4": "Baden-Württemberg",
		"Karlsruhe": "a47871fd-485a-45c0-8c3a-300f83eade7b",
		"a47871fd-485a-45c0-8c3a-300f83eade7b": "Baden-Württemberg"
	};

	function getIsolatedPredecessorMap(state: State): { [key: string]: string } {
		return predecessorMap
	}
</script>

{#if $selectedCountryConfigJson?.states}
    {#each $selectedCountryConfigJson.states as state}
        <StateTree predecessorMap={getIsolatedPredecessorMap(state)} />
    {/each}
{/if}