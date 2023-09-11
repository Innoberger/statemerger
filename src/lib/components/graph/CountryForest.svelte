<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { States } from '$lib/model/states';

	export let stateNames: string[];
	export let stateDivs: { [key: string]: HTMLDivElement };
	export let findRootWithDepthNoPathCompression: (forest: States, node: string) => { root: string | undefined, depth: number, path: string[] };
	export let selectNode: (nodeName: string) => void;
	export let graphSettings: {
		toggleUuidNodeLabels: boolean,
		toggleInnerNodeLabels: boolean,
		toggleNodeRanks: boolean
	}

	/**
	 * Retrieves all forest's predecessor map entries that are part of a single state.
	 * 
	 * @param stateName A state name to filter the predecessor map for.
	 * @returns Filtered predecessor map.
	 */
	function getFilteredPredecessorMap(stateName: string): { [key: string]: string } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest!.states.predecessor)
			.filter(([_name, _]) => findRootWithDepthNoPathCompression($selectedCountryStatesForest!, _name).root === stateName)

		return Object.fromEntries(filteredPredecessors)
	}

	/**
	 * Retrieves all forest's rank map entries that are part of a single state.
	 * 
	 * @param stateName A state name to filter the rank map for.
	 * @returns Filtered rank map.
	 */
	function getFilteredRankMap(stateName: string): { [key: string]: number } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest!.states.rank)
			.filter(([_name, _]) => findRootWithDepthNoPathCompression($selectedCountryStatesForest!, _name).root === stateName)

		return Object.fromEntries(filteredPredecessors)
	}

	/**
	 * Retrieves a map of leave nodes and their depth in the tree, for a single state.
	 * 
	 * @param stateName A state name to filter for.
	 * @returns A map of the state's leave nodes and their depth.
	 */
	function getFilteredLeavesDepthMap(stateName: string): { [key: string]: number } {
		if (!$selectedCountryStatesForest) return {}

		const leavesArray = Object.entries($selectedCountryStatesForest.states.rank)
			.filter(([_name, _rank]) => _rank === 0 && findRootWithDepthNoPathCompression($selectedCountryStatesForest!, _name).root === stateName)
			.map(([_name, _]) => [ _name, findRootWithDepthNoPathCompression($selectedCountryStatesForest!, _name).depth ])

		return Object.fromEntries(leavesArray)
	}
</script>

<div class="container">
	<div class="row justify-content-around">
		{#each stateNames as state}
			<div bind:this={stateDivs[state]} class="col{$selectedCountryStatesForest ? ($selectedCountryStatesForest.states.rank[state] < 3 ? '-xl-6' : '-12') : ''}">
				<StateTree
					predecessorMap={getFilteredPredecessorMap(state)}
					rankMap={getFilteredRankMap(state)}
					leavesDepthMap={getFilteredLeavesDepthMap(state)}
					{graphSettings}
					{selectNode}
				/>
			</div>
		{/each}
	</div>
</div>