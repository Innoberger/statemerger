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

	function findRootWithDepthNoPathCompression(forest: States, node: string): { root: string | undefined, depth: number, path: string[] } {
		/*
		* Recursively walk up the tree,
		* but do not apply path compression.
		* This is for visualization purposes only!
		*/
		if (node !== forest.states.predecessor[node]) {
			let _root = findRootWithDepthNoPathCompression(forest, forest.states.predecessor[node]!)!
			_root.path.push(node)
			return { root: _root.root, depth: _root.depth + 1, path: _root.path }
		}

		return { root: forest.states.predecessor[node], depth: 0, path: [node] };
	}

	function getStateNames(states: States): string[] {
		if (!states) return []

		return Object.entries(states.states.predecessor)
			.filter(([_name, _predecessor]) => _name === _predecessor)
			.map(([_name, _predecessor]) => _name)
	}

	function inSameTree(first: string, second: string): boolean {
		return findRootWithDepthNoPathCompression($selectedCountryStatesForest!, first).root === findRootWithDepthNoPathCompression($selectedCountryStatesForest!, second).root
	}

	function findStateFunction(search: string) {
		let result = $selectedCountryStatesForest!.findState(search!);
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
		stateNames = getStateNames($selectedCountryStatesForest)
		return true;
	}

	function countNodes(states: string[]): number {
		if (!(states && $selectedCountryStatesForest)) return 0;

		return Object.entries($selectedCountryStatesForest.states.predecessor).length;
	}

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
			Um zu beginnen, bitte zunächst ein Land auswählen oder mit Make-State erstellen.
		{/if}
	</div>
</div>

<CountryForest {stateNames} bind:stateDivs={stateDivs} {findRootWithDepthNoPathCompression} {graphSettings} {selectNode}/>