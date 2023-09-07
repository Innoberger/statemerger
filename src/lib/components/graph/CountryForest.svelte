<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryConfigJson, selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { State } from '$lib/parser/state';
	import type { States } from '$lib/model/states';

    const predecessorMap = {
		"Stuttgart": "Baden-Württemberg",
		"Baden-Württemberg": "Baden-Württemberg",
		"Mannheim": "7e86eb1f-b49f-494d-a7db-a51cf1bddce4",
		"7e86eb1f-b49f-494d-a7db-a51cf1bddce4": "Baden-Württemberg",
		"Karlsruhe": "a47871fd-485a-45c0-8c3a-300f83eade7b",
		"a47871fd-485a-45c0-8c3a-300f83eade7b": "Baden-Württemberg"
	};

    function findRootNoPathCompression(forest: States, node: string): string | undefined {
        /*
		 * Recursively walk up the tree,
		 * but do not apply path compression.
		 * This is for visualization purposes only
		 */
        if (node !== forest.states.predecessor[node]) {
            return findRootNoPathCompression(forest, forest.states.predecessor[node]!)!;
        }

        return forest.states.predecessor[node];
    }

	function getFilteredPredecessorMap(state: State): { [key: string]: string } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest.states.predecessor)
			.filter(([_name, _]) => findRootNoPathCompression($selectedCountryStatesForest, _name) === state.name)

		return Object.fromEntries(filteredPredecessors)
	}
</script>

{#if $selectedCountryConfigJson?.states}
    {#each $selectedCountryConfigJson.states as state}
        <StateTree predecessorMap={getFilteredPredecessorMap(state)} />
    {/each}
{/if}