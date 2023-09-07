<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryConfigJson, selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { State } from '$lib/parser/state';
	import type { States } from '$lib/model/states';

    function findRootWithDepthNoPathCompression(forest: States, node: string): { root: string, depth: number } | undefined {
        /*
		 * Recursively walk up the tree,
		 * but do not apply path compression.
		 * This is for visualization purposes only!
		 */
        if (node !== forest.states.predecessor[node]) {
			let _root = findRootWithDepthNoPathCompression(forest, forest.states.predecessor[node]!)!
            return { root: _root.root, depth: _root.depth + 1}
        }

        return { root: forest.states.predecessor[node], depth: 0};
    }

	function getFilteredPredecessorMap(state: State): { [key: string]: string } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest.states.predecessor)
			.filter(([_name, _]) => findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name)?.root === state.name)

		return Object.fromEntries(filteredPredecessors)
	}

	function getFilteredRankMap(state: State): { [key: string]: number } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest.states.rank)
			.filter(([_name, _]) => findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name)?.root === state.name)

		return Object.fromEntries(filteredPredecessors)
	}

	function getFilteredLeavesDepthMap(state: State): { [key: string]: number } {
		if (!$selectedCountryStatesForest) return {}

		const leavesArray = Object.entries($selectedCountryStatesForest.states.rank)
			.filter(([_name, _rank]) => _rank === 0 && findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name)?.root === state.name)
			.map(([_name, _]) => [ _name, findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name)?.depth ])

		return Object.fromEntries(leavesArray)
	}
</script>

{#if $selectedCountryConfigJson?.states}
	<div class="container">
		<div class="row justify-content-around">
			{#each $selectedCountryConfigJson.states as state}
				<div class="col">
					<StateTree predecessorMap={getFilteredPredecessorMap(state)} rankMap={getFilteredRankMap(state)} leavesDepthMap={getFilteredLeavesDepthMap(state)} />
				</div>
			{/each}
		</div>
	</div>
{/if}