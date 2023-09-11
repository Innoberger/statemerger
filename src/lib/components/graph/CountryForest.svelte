<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { States } from '$lib/model/states';
	import NodeInfoModal from '../modal/NodeInfoModal.svelte';
	import { isUUID } from 'class-validator';

	export let stateNames: string[];
	export let unionFunction: () => void;
	export let findRootWithDepthNoPathCompression: (forest: States, node: string) => { root: string | undefined, depth: number };
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};

	let modalOpen: boolean = false;

	let selectedNode: {
		name: string,
		depth: number,
		root: string
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
			root: rootNode.root!
		}
		modalOpen = true;
	}

	function getNodeType(nodeName: string) {
		if ($selectedCountryStatesForest.states.predecessor[nodeName] === nodeName) return "Bundesland (Root)";
		if (isUUID(nodeName, 4)) return "Hilfsknoten";
		if (!Object.entries($selectedCountryStatesForest.states.predecessor).find(([_, _predecessor]) => _predecessor === nodeName)) return "Blattknoten";

		return "Stadt oder vereintes Bundesland";
	}

	function inSameTree(first: string, second: string): boolean {
		return findRootWithDepthNoPathCompression($selectedCountryStatesForest, first).root === findRootWithDepthNoPathCompression($selectedCountryStatesForest, second).root
	}
</script>

<!-- TODO:
		- Automatically scroll to merged tree after union
-->

<NodeInfoModal title={selectedNode?.name} bind:open={modalOpen} {unionFunction} bind:mergeNodes={mergeNodes} {inSameTree}>
	<table class="table table-borderless">
		<tbody>
			<tr>
				<th scope="row">Knotentyp</th>
				<td>{getNodeType(selectedNode.name)}</td>
			</tr>
			<tr>
				<th scope="row">Rang</th>
				<td>{$selectedCountryStatesForest.states.rank[selectedNode.name]}</td>
			</tr>
			<tr>
				<th scope="row">Bundesland (Root)</th>
				<td>{selectedNode.root}</td>
			</tr>
			<tr>
				<th scope="row">Direkter Vorgänger</th>
				<td>{$selectedCountryStatesForest.states.predecessor[selectedNode.name]}</td>
			</tr>
			<tr>
				<th scope="row">Ebene / Tiefe</th>
				<td>{selectedNode.depth}</td>
			</tr>
		</tbody>
	</table>
</NodeInfoModal>

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