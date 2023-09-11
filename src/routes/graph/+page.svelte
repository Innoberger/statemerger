<script lang="ts">
	import ErrorModal from '$lib/components/modal/ErrorModal.svelte';
	import CountrySelectorButton from '$lib/components/country-selector/CountrySelectorButton.svelte';
	import CountryForest from '$lib/components/graph/CountryForest.svelte'
	import NodeInfoModal from '$lib/components/modal/NodeInfoModal.svelte';
	import Operations from '$lib/components/operations/Operations.svelte';
	import type { States } from '$lib/model/states';
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';


	let stateNames: string[] = [];
	let nodeInfoModal = false;

	let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	} = {
		first: undefined,
		second: undefined
	};

	let errorModal: {
		title: string,
		error: string,
		open: boolean
	} = {
        title: "Fehler",
        error: "",
        open: false
    }

	let selectedNode: {
		name: string,
		depth: number,
		root: string
	};

	$: stateNames = getStateNames($selectedCountryStatesForest);

	function findRootWithDepthNoPathCompression(forest: States, node: string): { root: string | undefined, depth: number } {
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

	function getStateNames(states: States): string[] {
		if (!states) return []

		return Object.entries(states.states.predecessor)
			.filter(([_name, _predecessor]) => _name === _predecessor)
			.map(([_name, _predecessor]) => _name)
	}

	function inSameTree(first: string, second: string): boolean {
		return findRootWithDepthNoPathCompression($selectedCountryStatesForest, first).root === findRootWithDepthNoPathCompression($selectedCountryStatesForest, second).root
	}

	function findStateFunction(search: string) {
		let result = $selectedCountryStatesForest.findState(search!);
		let nodeInfo = findRootWithDepthNoPathCompression($selectedCountryStatesForest, search)

		if (undefined === result) {
			errorModal = { title: "404 Not Found", error: `Die gesuchte Stadt bzw. der gesuchte Knoten wurden in keinem Bundesland gefunden.`, open: true };
			return;
		}

		selectedNode = {
			name: search,
			depth: nodeInfo.depth,
			root: nodeInfo.root!
		}

		nodeInfoModal = true
		stateNames = getStateNames($selectedCountryStatesForest);
	}

	function unionFunction() {
		if (!(mergeNodes && mergeNodes.first && mergeNodes.second)) {
			errorModal = { title: "Vereinigung fehlgeschlagen", error: "Erster und zweiter Knoten müssen ausgewählt werden.", open: true }
			return;
		}

		let firstRoot = findRootWithDepthNoPathCompression($selectedCountryStatesForest, mergeNodes.first).root
		let secondRoot = findRootWithDepthNoPathCompression($selectedCountryStatesForest, mergeNodes.second).root

		if (undefined === firstRoot || undefined === secondRoot) {
			errorModal = { title: "Vereinigung fehlgeschlagen", error: "Erster oder zweiter Knoten existiert nicht.", open: true }
			return;
		}

		if (firstRoot === secondRoot) {
			errorModal = { title: "Vereinigung fehlgeschlagen", error: "Vereinigung innerhalb eines Baumes nicht möglich.", open: true }
			return;
		}

		$selectedCountryStatesForest.union(mergeNodes.first, mergeNodes.second)
		stateNames = getStateNames($selectedCountryStatesForest)

		mergeNodes = {
			first: undefined,
			second: undefined
		}
	}
</script>

<!-- TODO:
		- Display selected nodes here
		- Display the amount of trees in the forest here
		- Button to unselect selectedNodes.first
		- Forms for Make-State, Find-State and Union
		- Settings
			* toggle for show uuid node names
			* toggle for show non-root and non-leave node names
			* toggle for show ranks in node circles
-->

<ErrorModal title={errorModal.title} bind:open={errorModal.open}>
    {errorModal.error}
</ErrorModal>

<NodeInfoModal {selectedNode} bind:open={nodeInfoModal} {unionFunction} bind:mergeNodes={mergeNodes} {inSameTree} />

<CountrySelectorButton />


<Operations bind:mergeNodes={mergeNodes} {findStateFunction} {unionFunction}/>

{#if !$selectedCountryStatesForest}
	<div class="alert alert-info d-flex align-items-center" role="alert">
		<div class="me-3">
			<i class="bi-info-circle-fill"></i>
		</div>
		<div>
			Um zu beginnen, bitte zunächst ein Land auswählen oder mit Make-State erstellen.
		</div>
	</div>
{/if}

<CountryForest bind:mergeNodes={mergeNodes} {stateNames} {unionFunction} {inSameTree} {findRootWithDepthNoPathCompression}/>