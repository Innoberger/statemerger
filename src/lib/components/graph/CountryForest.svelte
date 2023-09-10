<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { States } from '$lib/model/states';

	let firstSelectedState: string | undefined;
	let stateNames: string[];

	$: stateNames = getStateNames($selectedCountryStatesForest)

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

	function getFilteredPredecessorMap(stateName: string): { [key: string]: string } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest.states.predecessor)
			.filter(([_name, _]) => findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name)?.root === stateName)

		return Object.fromEntries(filteredPredecessors)
	}

	function getFilteredRankMap(stateName: string): { [key: string]: number } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest.states.rank)
			.filter(([_name, _]) => findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name)?.root === stateName)

		return Object.fromEntries(filteredPredecessors)
	}

	function getFilteredLeavesDepthMap(stateName: string): { [key: string]: number } {
		if (!$selectedCountryStatesForest) return {}

		const leavesArray = Object.entries($selectedCountryStatesForest.states.rank)
			.filter(([_name, _rank]) => _rank === 0 && findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name)?.root === stateName)
			.map(([_name, _]) => [ _name, findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name)?.depth ])

		return Object.fromEntries(leavesArray)
	}

	function unionFunction(state: string) {
		if (undefined === firstSelectedState) {
			alert("1ST union function called by " + state)
			firstSelectedState = state
			return
		}

		if (firstSelectedState === state) {
			alert("union cannot merge a state with itself")
			return
		}

		alert("2ND union function called by " + state + ", first was " + firstSelectedState)

		$selectedCountryStatesForest.union(state, firstSelectedState)
		stateNames = getStateNames($selectedCountryStatesForest)

		firstSelectedState = undefined;
	}

	function getStateNames(states: States) {
		console.log("getStateNames called")

		if (!states) return []

		return Object.entries(states.states.predecessor)
			.filter(([_name, _predecessor]) => _name === _predecessor)
			.map(([_name, _predecessor]) => _name)
	}
</script>
<!-- TODO: I need to alter the functions 
		predecessorMap={getFilteredPredecessorMap(state)}
		rankMap={getFilteredRankMap(state)}
		leavesDepthMap={getFilteredLeavesDepthMap(state)}

to not have a reference from the config as input. Otherwise, this wll not work because I cannot alter the parsed config.
-->

<!-- TODO: Display delected states here -->

{#if stateNames}
	<div class="container">
		<div class="row justify-content-around">
			{#each stateNames as state}
				<div class="col">
					<StateTree
						predecessorMap={getFilteredPredecessorMap(state)}
						rankMap={getFilteredRankMap(state)}
						leavesDepthMap={getFilteredLeavesDepthMap(state)}
						{unionFunction}
					/>
				</div>
			{/each}
		</div>
	</div>
{/if}