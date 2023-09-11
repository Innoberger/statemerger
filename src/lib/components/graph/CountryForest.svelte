<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { States } from '$lib/model/states';
	import NodeInfoModal from '../modal/NodeInfoModal.svelte';

	export let stateNames: string[];
	export let unionFunction: () => void;
	export let inSameTree: (first: string, second: string) => boolean;
	export let findRootWithDepthNoPathCompression: (forest: States, node: string) => { root: string | undefined, depth: number, path: string[] };
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};

	let modalOpen: boolean = false;

	let selectedNode: {
		name: string,
		depth: number,
		root: string,
		path: string[]
	};

	function getFilteredPredecessorMap(stateName: string): { [key: string]: string } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest.states.predecessor)
			.filter(([_name, _]) => findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name).root === stateName)

		return Object.fromEntries(filteredPredecessors)
	}

	function getFilteredRankMap(stateName: string): { [key: string]: number } {
		const filteredPredecessors = Object.entries($selectedCountryStatesForest.states.rank)
			.filter(([_name, _]) => findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name).root === stateName)

		return Object.fromEntries(filteredPredecessors)
	}

	function getFilteredLeavesDepthMap(stateName: string): { [key: string]: number } {
		if (!$selectedCountryStatesForest) return {}

		const leavesArray = Object.entries($selectedCountryStatesForest.states.rank)
			.filter(([_name, _rank]) => _rank === 0 && findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name).root === stateName)
			.map(([_name, _]) => [ _name, findRootWithDepthNoPathCompression($selectedCountryStatesForest, _name).depth ])

		return Object.fromEntries(leavesArray)
	}

	function selectNode(nodeName: string) {
		let rootNode = findRootWithDepthNoPathCompression($selectedCountryStatesForest, nodeName)
		selectedNode = {
			name: nodeName,
			depth: rootNode.depth,
			root: rootNode.root!,
			path: rootNode.path
		}
		modalOpen = true;
	}
</script>

<!-- TODO:
		- Automatically scroll to a node after Find-State
		- Automatically scroll to merged tree after Union
-->

<NodeInfoModal {selectedNode} bind:open={modalOpen} {unionFunction} bind:mergeNodes={mergeNodes} {inSameTree} />

<div class="container">
	<div class="row justify-content-around">
		{#each stateNames as state}
			<div class="col{$selectedCountryStatesForest.states.rank[state] < 3 ? '-xl-6' : '-12'}">
				<StateTree
					predecessorMap={getFilteredPredecessorMap(state)}
					rankMap={getFilteredRankMap(state)}
					leavesDepthMap={getFilteredLeavesDepthMap(state)}
					{selectNode}
				/>
			</div>
		{/each}
	</div>
</div>