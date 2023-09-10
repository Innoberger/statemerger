<script lang="ts">
    import StateTree from './StateTree.svelte';
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';
	import type { States } from '$lib/model/states';
	import Modal from '../bs-wrapper/Modal.svelte';
	import { isUUID } from 'class-validator';

	let stateNames: string[] = [];
	let modalOpen: boolean;

	let selectedNode: {
		name: string,
		depth: number,
		root: string
	};

	let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	} = {
		first: undefined,
		second: undefined
	};

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

	function unionFunction(secondNode: string) {
		if (!(mergeNodes && mergeNodes.first)) return;

		mergeNodes.second = secondNode

		$selectedCountryStatesForest.union(mergeNodes.first, mergeNodes.second)
		stateNames = getStateNames($selectedCountryStatesForest)

		mergeNodes = {
			first: undefined,
			second: undefined
		}
	}

	function getStateNames(states: States) {
		console.log("getStateNames called")

		if (!states) return []

		return Object.entries(states.states.predecessor)
			.filter(([_name, _predecessor]) => _name === _predecessor)
			.map(([_name, _predecessor]) => _name)
	}

	function selectNode(nodeName: string) {
		console.log("called")
		let rootNode = findRootWithDepthNoPathCompression($selectedCountryStatesForest, nodeName)!
		selectedNode = {
			name: nodeName,
			depth: rootNode.depth,
			root: rootNode.root
		}
		modalOpen = true;
	}

	function getNodeType(nodeName: string) {
		if ($selectedCountryStatesForest.states.predecessor[nodeName] === nodeName) return "Bundesland (Root)";
		if (isUUID(nodeName, 4)) return "Hilfsknoten";
		if (!Object.entries($selectedCountryStatesForest.states.predecessor).find(([_, _predecessor]) => _predecessor === nodeName)) return "Blattknoten";

		return "Stadt oder vereintes Bundesland";
	}
</script>

<Modal title={selectedNode?.name} bind:open={modalOpen} {unionFunction} {mergeNodes}>
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
</Modal>

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