<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryConfigJson, selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { State } from '$lib/parser/state';
	import type { States } from '$lib/model/states';

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
	<div class="container">
		<div class="row">
			{#each $selectedCountryConfigJson.states as state}
				<div class="col">
					<StateTree predecessorMap={getFilteredPredecessorMap(state)} />
				</div>
			{/each}
		</div>
	</div>
{/if}