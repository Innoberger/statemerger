<script lang="ts">
	//@ts-nocheck
	import { isUUID } from 'class-validator';
	import * as d3 from 'd3';
	import Modal from '../bs-wrapper/Modal.svelte';
	import CountryForest from './CountryForest.svelte';

	type TreeNode = {
		name: string;
		value: number;
		type: string;
		level: string;
		children?: TreeNode[]; // Optional for children
	}

	export let predecessorMap: { [key: string]: string };
	export let rankMap: { [key: string]: number };
	export let leavesDepthMap: { [key: string]: number };
	export let unionFunction: (secondNode: string) => void;
	export let selectedNodes: {
		first: string | undefined;
		second: string | undefined
	};

	let treeData: TreeNode;
	let treemap, margin, width, height;
	let nodes: d3.HierarchyNode<any> | d3.HierarchyNode<unknown>;

	let selectedNode: string;
	let modalOpen: boolean;

	/**
	 * ---
	 * 
	 * I took some of this code from https://www.developer.com/design/creating-a-tree-diagram-with-d3-js/
	 * as a starting point with D3.js hierarchy trees.
	 * Thanks to Robert Gravelle for his great article!
	 * 
	 * ---
	 */

	$: treeData = transformPredecessorMap(predecessorMap)

	// set the dimensions and margins of the diagram
	$: margin = {top: 10, right: getMarginRight(), bottom: 30, left: treeData.name.length * 11 + 15}
	$: width  = 600 - margin.left - margin.right
	$: height = getHeight() - margin.top - margin.bottom

	// declares a tree layout and assigns the size
	$: treemap = d3.tree().size([height, width]);
	
	//  assigns the data to a hierarchy using parent-child relationships
	$: nodes = d3.hierarchy(treeData);
	$: nodes = treemap(nodes)

	function transformPredecessorMap(predecessorMap: { [key: string]: string }): TreeNode {
		function getChildNodes(nodeName: string): TreeNode[] | undefined {
			return Object.entries(predecessorMap).filter(([node, parent]) => {
				return node !== parent && parent === nodeName
			}).map(([node, _]) => {
				return {
					name: node,
					value: 10,
					type: "black",
					level: "lime",
					children: []
				} as TreeNode
			})
		}

		function getRootNode(): TreeNode {
			let name = Object.entries(predecessorMap).find(([node, parent]) => {
				return	parent === node
			})?.[0]

			return {
				name: name,
				value: 15,
				type: "black",
				level: "red",
				children: []
			} as TreeNode
		}

		function setChildNodes(node: TreeNode, root: TreeNode): TreeNode {
			node.children = getChildNodes(node.name)?.map((childNode) => setChildNodes(childNode, root))

			if (node.name !== root.name && node.children?.length) node.level = "orange"

			return node
		}

		let tree: TreeNode = getRootNode()

		tree = setChildNodes(tree, tree)

		return tree
	}

	function transformNodeName(name: string): string {
		return isUUID(name, 4) ? name.split("-")[0] + "-..." : name
	}

	function getMarginRight() {
		const orderedByDepth = Object.entries(leavesDepthMap).sort((a, b) => b[1] - a[1])

		const orderedByNameLength = orderedByDepth
			.filter(([_name, _depth]) => _depth === orderedByDepth[0][1] && !isUUID(_name, 4))
			.sort((a, b) => b[0].length - a[0].length)

		const longestNodeName = orderedByNameLength[0][0]

		return longestNodeName.length * 12 + 20
	}

	function getHeight() {
		return Object.entries(leavesDepthMap).length * 55 + 20
	}

	function openModal(nodeName: string) {
		selectedNode = nodeName;
		modalOpen = true;
	}

	function getNodeType(nodeName: string) {
		if (predecessorMap[nodeName] === nodeName) return "Bundesland (Root)";
		if (isUUID(nodeName, 4)) return "Hilfsknoten";
		if (Object.entries(leavesDepthMap).find(([_name, _]) => _name === nodeName)) return "Blattknoten";

		return "Stadt oder vereintes Bundesland";
	}
</script>

<Modal title={selectedNode} bind:open={modalOpen} {unionFunction} {selectedNodes}>
	<table class="table table-borderless">
		<tbody>
			<tr>
				<th scope="row">Knotentyp</th>
				<td>{getNodeType(selectedNode)}</td>
			</tr>
			<tr>
				<th scope="row">Rang</th>
				<td>{rankMap[selectedNode]}</td>
			</tr>
			<tr>
				<th scope="row">Direkter Vorgänger</th>
				<td>{predecessorMap[selectedNode]}</td>
			</tr>
			<tr>
				<th scope="row">Bundesland (Root)</th>
				<td>{treeData.name}</td>
			</tr>
		</tbody>
	</table>
</Modal>

{#if nodes}
	<div id="tree-container">
		<svg
			class="m-2 p-2"
			id="svg-container"
			width={width + margin.left + margin.right}
			height={height + margin.top + margin.bottom}
			style="border:2px dotted lightgray; border-radius: 10px"
		>
			<g id="svg-group" transform="translate({margin.left},{margin.top})">
				{#each nodes.descendants().slice(1) as edge}
					<path
						class="link"
						style="stroke: {edge.data.level}"
						d="M{edge.y},{edge.x}C{(edge.y + edge.parent.y) / 2},{edge.x} {(edge.y + edge.parent.y) / 2}, {edge.parent.x} {edge.parent.y},{edge.parent.x}"
					/>
				{/each}

				{#each nodes.descendants() as node}
					<!-- svelte-ignore a11y-click-events-have-key-events -->
					<!-- svelte-ignore a11y-no-static-element-interactions -->
					<g
						class="node {node.children ? " node--internal" : " node--leaf"}"
						transform="translate({node.y},{node.x})"
						on:click={() => openModal(node.data.name)}
					>
						<circle
							r={node.data.value}
							style="fill: {node.data.level}"
						/>
						<text
							text-anchor="middle"
							fill="black"
							dy=".33em"
							font-size="60%"
							class="rank"
						>
							{rankMap[node.data.name]}
						</text>
						<text
							dy=".35em"
							x={node.children ? (node.data.value + 5) * -1 : node.data.value + 5}
							y={node.children && node.depth !== 0 ? -(node.data.value + (isUUID(node.data.name, 4) ? -3 : 5)) : 0}
							text-anchor={node.children ? "end" : "start"}
							class="node-descriptor {node.depth === 0 ? "root" : isUUID(node.data.name, 4) ? "dummy" : "real"}"
						>
							{transformNodeName(node.data.name)}
						</text>
					</g>
				{/each}
			</g>
		</svg>
	</div>
{/if}

<style>
	g {
		pointer-events: bounding-box;
	}
	
	.node:hover, .node-descriptor:hover, .node circle:hover {
		pointer-events: bounding-box;
		cursor: pointer;
		stroke: rgb(25, 213, 242);
		fill: rgb(25, 213, 242);
	}

	.node:hover .rank, .rank:hover {
		stroke: none !important;
		fill: black !important;
	}

	.root {
		font-size: 100%;
		font-weight: bold;
	}

	.real {
		font-size: 90%;
	}

	.dummy {
		font-size: 75%;
		fill: gray;
	}

	.node circle {
		stroke: black;
		stroke-width: 2px;
	}

	.link {
		fill: none;
	}
</style>