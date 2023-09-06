<script lang="ts">
	import { selectedCountryStatesForest } from '$lib/stores/selected-country';
	import { isUUID } from 'class-validator';
	import * as d3 from 'd3';

	interface TreeNode {
		name: string;
		value: number;
		type: string;
		level: string;
		children?: TreeNode[]; // Optional for children
	}

	/**
	 * ---
	 * 
	 * I took this code from https://www.developer.com/design/creating-a-tree-diagram-with-d3-js/
	 * as a starting point with D3.js hierarchy trees.
	 * Thanks to Robert Gravelle for his great article!
	 * 
	 * ---
	 */

	let treeData;
	let nodes = undefined;

	function transformPredecessorMap(predecessorMap: { [key: string]: string }): TreeNode {
		function getChildNodes(nodeName: string): TreeNode[] | undefined {
			return Object.entries(predecessorMap).filter(([node, parent]) => {
				return node !== parent && parent === nodeName
			}).map(([node, _]) => {
				return {
					name: node,
					value: 10,
					type: "black",
					level: "lime"
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
				level: "red"
			} as TreeNode
		}

		function setChildNodes(node: TreeNode): TreeNode {
			node.children = getChildNodes(node.name)?.map((childNode) => setChildNodes(childNode))

			return node
		}

		let tree: TreeNode = getRootNode()

		tree = setChildNodes(tree)

		return tree
	}

	const predecessorMap = {
		"Stuttgart": "Baden-Württemberg",
		"Baden-Württemberg": "Baden-Württemberg",
		"Mannheim": "7e86eb1f-b49f-494d-a7db-a51cf1bddce4",
		"7e86eb1f-b49f-494d-a7db-a51cf1bddce4": "Baden-Württemberg",
		"Karlsruhe": "a47871fd-485a-45c0-8c3a-300f83eade7b",
		"a47871fd-485a-45c0-8c3a-300f83eade7b": "Baden-Württemberg"
	};

	// set the dimensions and margins of the diagram
	const	margin = {top: 20, right: 100, bottom: 30, left: 200},
			width  = 600 - margin.left - margin.right,
			height = 400 - margin.top - margin.bottom;

	// declares a tree layout and assigns the size
	const treemap = d3.tree().size([height, width]);
	
	selectedCountryStatesForest.subscribe((value) => {
		if (!(value && value.states && value.states.predecessor)) return;
		refreshTree()
	});
	
	function refreshTree() {
		treeData = transformPredecessorMap(predecessorMap)
		//  assigns the data to a hierarchy using parent-child relationships
		nodes = d3.hierarchy(treeData);

		//
		nodes = treemap(nodes)
	}

	function transformNodeName(name: string): string {
		return isUUID(name, 4) ? name.split("-")[0] + "-..." : name
	}
</script>

{#if nodes}
	<div id="tree-container">
		<svg
			id="svg-container"
			width={width + margin.left + margin.right}
			height={height + margin.top + margin.bottom}
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
						on:click={() => alert(node.data.name)}
					>
						<circle
							r={node.data.value}
							style="fill: {node.data.level}"
						/>
						<text
							dy=".35em"
							x={node.children ? (node.data.value + 5) * -1 : node.data.value + 5}
							y={node.children && node.depth !== 0 ? -(node.data.value + 5) : node}
							text-anchor={node.children ? "end" : "start"}
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
	
	.node:hover {
		pointer-events: bounding-box;
		cursor: pointer;
		stroke: rgb(25, 213, 242);
		fill: rgb(25, 213, 242);
	}

	.node circle:hover {
		stroke: rgb(25, 213, 242);
		fill: rgb(25, 213, 242);
	}

	.node text:hover {
		stroke: rgb(25, 213, 242);
		fill: rgb(25, 213, 242);
	}


	.node circle {
		stroke: black;
		stroke-width: 2px;
	}

	.link {
		fill: none;
	}

	.link:hover {
		cursor: pointer;
		stroke-width: 2px;
	}
</style>