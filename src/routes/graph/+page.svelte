<script lang="ts">
	import ErrorModal from '$lib/components/modal/ErrorModal.svelte';
	import CountrySelectorButton from '$lib/components/country-selector/CountrySelectorButton.svelte';
	import CountryForest from '$lib/components/graph/CountryForest.svelte'
	import NodeInfoModal from '$lib/components/modal/NodeInfoModal.svelte';
	import Operations from '$lib/components/operations/Operations.svelte';
	import { States } from '$lib/model/states';
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';
	import CountrySelectorDialog from '$lib/components/country-selector/CountrySelectorDialog.svelte';


	let stateNames: string[] = [];
	let stateDivs: { [key: string]: HTMLDivElement } = {};
	let nodeCount: number;
	let nodeInfoModal = false;

	let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	} = {
		first: undefined,
		second: undefined
	};

	let graphSettings: {
		toggleUuidNodeLabels: boolean,
		toggleInnerNodeLabels: boolean,
		toggleNodeRanks: boolean,
		scrollOnFindState: boolean
	} = {
		toggleUuidNodeLabels: false,
		toggleInnerNodeLabels: false,
		toggleNodeRanks: false,
		scrollOnFindState: false
	}

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
		root: string,
		path: string[]
	};

	$: stateNames = getStateNames($selectedCountryStatesForest!);
	$: nodeCount = countNodes(stateNames);

	/**
	 * Finds the root (and other information) of a `node` in a `forest`.
	 * 
	 * Recursively walks up the tree, but does not apply path compression.
	 * This is for visualization purposes only!
	 * 
	 * @param forest States object to find the root in.
	 * @param node A node name to be searched.
	 * @returns An object containing the `root` name (or undefined if node is not found), the node's `depth` and a `path` from the root to the node.
	 */
	function findRootWithDepthNoPathCompression(forest: States, node: string): { root: string | undefined, depth: number, path: string[] } {
		if (node !== forest.states.predecessor[node]) {
			let _root = findRootWithDepthNoPathCompression(forest, forest.states.predecessor[node]!)!
			_root.path.push(node)
			return { root: _root.root, depth: _root.depth + 1, path: _root.path }
		}

		return { root: forest.states.predecessor[node], depth: 0, path: [node] };
	}

	/**
	 * Retrieves the state names form the state's predecessor map.
	 * 
	 * @param states States object to extract the state names.
	 * @returns A string list of state names.
	 */
	function getStateNames(states: States): string[] {
		if (!states) return []

		return Object.entries(states.states.predecessor)
			.filter(([_name, _predecessor]) => _name === _predecessor)
			.map(([_name, _predecessor]) => _name)
	}

	/**
	 * Checks if the two nodes `first` and `second` are in the same tree.
	 * 
	 * @param first A node.
	 * @param second Another node.
	 * 
	 * @returns Boolean indicating if `first` and `second` are part of the same tree.
	 */
	function inSameTree(first: string, second: string): boolean {
		return findRootWithDepthNoPathCompression($selectedCountryStatesForest!, first).root === findRootWithDepthNoPathCompression($selectedCountryStatesForest!, second).root
	}

	/**
	 * Function to search for the root, which UI elements can call.
	 * 
	 * @param search A node to be looked for.
	 */
	function findStateFunction(search: string) {
		let result = $selectedCountryStatesForest!.findState(search!);

		// Only to retrieve depth and path of the root
		let nodeInfo = findRootWithDepthNoPathCompression($selectedCountryStatesForest!, search)

		if (undefined === result) {
			errorModal = { title: "404 Not Found", error: "Die gesuchte Stadt bzw. der gesuchte Knoten wurde in keinem Bundesland gefunden.", open: true };
			return;
		}

		selectedNode = {
			name: search,
			depth: nodeInfo.depth,
			root: nodeInfo.root!,
			path: nodeInfo.path
		}

		if (graphSettings.scrollOnFindState) scrollIntoView(nodeInfo.root!);

		nodeInfoModal = true
		stateNames = getStateNames($selectedCountryStatesForest!);
	}

	/**
	 * Function to merge the two selected nodes in `mergeNodes`. This function should only be called by UI elements.
	 */
	function unionFunction() {
		if (!(mergeNodes && mergeNodes.first && mergeNodes.second)) {
			errorModal = { title: "Union fehlgeschlagen", error: "Erster und zweiter Knoten müssen ausgewählt werden.", open: true }
			return;
		}

		if (mergeNodes.first === mergeNodes.second) {
			errorModal = { title: "Union fehlgeschlagen", error: "Union eines Knotens mit sich selbst nicht möglich.", open: true }
			return;
		}

		let firstRoot = findRootWithDepthNoPathCompression($selectedCountryStatesForest!, mergeNodes.first).root
		let secondRoot = findRootWithDepthNoPathCompression($selectedCountryStatesForest!, mergeNodes.second).root

		if (undefined === firstRoot || undefined === secondRoot) {
			errorModal = { title: "Union fehlgeschlagen", error: "Erster oder zweiter Knoten existiert nicht.", open: true }
			return;
		}

		if (firstRoot === secondRoot) {
			errorModal = { title: "Union fehlgeschlagen", error: "Union innerhalb eines Baumes nicht möglich.", open: true }
			return;
		}

		$selectedCountryStatesForest!.union(mergeNodes.first, mergeNodes.second)
		stateNames = getStateNames($selectedCountryStatesForest!)

		mergeNodes = {
			first: undefined,
			second: undefined
		}
	}

	/**
	 * Function to create a new `state` containing only a `city`. This function should only be called by UI elements.
	 * 
	 * @param city A city name.
	 * @param state A state name.
	 * @returns Operation was successful or not.
	 */
	function makeStateFunction(city: string, state: string): boolean {
		if (city === state) {
			errorModal = { title: "Make-State fehlgeschlagen", error: "Stadt und Bundesland dürfen nicht gleich benannt werden.", open: true }
			return false;
		}

		if (!$selectedCountryStatesForest) $selectedCountryStatesForest = new States()

		if (undefined !== findRootWithDepthNoPathCompression($selectedCountryStatesForest, city).root ||
			undefined !== findRootWithDepthNoPathCompression($selectedCountryStatesForest, state).root) {
			errorModal = { title: "Make-State fehlgeschlagen", error: "Stadt oder Bundesland existieren bereits.", open: true }
			return false;
		}

		$selectedCountryStatesForest.makeState(city, state)

		// To trigger a re-render, we must trigger an update of stateNames
		stateNames = getStateNames($selectedCountryStatesForest)
		return true;
	}

	/**
	 * Counts the amount of nodes inside the forest.
	 * 
	 * @param states Needed array of stateNames, otherwise this function will not be invoked on data updates.
	 * @returns Amount of nodes.
	 */
	function countNodes(states: string[]): number {
		if (!(states && $selectedCountryStatesForest)) return 0;

		return Object.entries($selectedCountryStatesForest.states.predecessor).length;
	}

	/**
	 * Selects a node insie the forest and opens the NodeInfoModal.
	 * 
	 * @param nodeName Name of the node to be selected.
	 */
	function selectNode(nodeName: string) {
		let rootNode = findRootWithDepthNoPathCompression($selectedCountryStatesForest!, nodeName)
		selectedNode = {
			name: nodeName,
			depth: rootNode.depth,
			root: rootNode.root!,
			path: rootNode.path
		}
		nodeInfoModal = true;
	}

	/**
	 * Scrolls a tree into focus.
	 * 
	 * @param rootName Name of the tree's root.
	 */
	function scrollIntoView(rootName: string) {
		stateDivs[rootName].scrollIntoView({
			behavior: 'smooth'
		});
	}
</script>

<ErrorModal title={errorModal.title} bind:open={errorModal.open}>
    {errorModal.error}
</ErrorModal>

<NodeInfoModal {selectedNode} bind:open={nodeInfoModal} {unionFunction} bind:mergeNodes={mergeNodes} {inSameTree} />

<CountrySelectorButton />
<CountrySelectorDialog bind:graphSettings={graphSettings} />

<Operations bind:mergeNodes={mergeNodes} {findStateFunction} {unionFunction} {makeStateFunction}/>

<div class="alert alert-info d-flex align-items-center mb-5" role="alert">
	<div class="me-3">
		<i class="bi-info-circle-fill"></i>
	</div>
	<div>
		{#if $selectedCountryStatesForest}
			{#if stateNames.length === 1}
				Es befindet sich <strong>ein Bundesland</strong> mit <strong>{nodeCount} Knoten</strong> in der Datenstruktur.
			{:else}
				Es befinden sich <strong>{stateNames.length} Bundesländer</strong> mit insgesamt <strong>{nodeCount} Knoten</strong> in der Datenstruktur.
			{/if}
		{:else}
			Um zu beginnen, bitte zunächst ein Land auswählen oder mit <strong>Make-State</strong> erstellen.
		{/if}
	</div>
</div>

<CountryForest {stateNames} bind:stateDivs={stateDivs} {findRootWithDepthNoPathCompression} {graphSettings} {selectNode}/>