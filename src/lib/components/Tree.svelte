<script lang="ts">
	// @ts-nocheck

	import * as d3 from 'd3';

	/**
	 * ---
	 * 
	 * I took this code from https://www.developer.com/design/creating-a-tree-diagram-with-d3-js/
	 * as a starting point with D3.js hierarchy trees.
	 * Thanks to Robert Gravelle for his great article!
	 * 
	 * ---
	 */

	export let treeData = {
		"name": "Eve",
		"value": 15,
		"type": "black",
		"level": "yellow",
		"children": [
			{
				"name": "Cain",
				"value": 10,
				"type": "grey",
				"level": "red"
			},
			{
				"name": "Seth",
				"value": 10,
				"type": "grey",
				"level": "red",
				"children": [
					{
						"name": "Enos",
						"value": 7.5,
						"type": "grey",
						"level": "purple"
					},
					{
						"name": "Noam",
						"value": 7.5,
						"type": "grey",
						"level": "purple"
					}
				]
			},
			{
				"name": "Abel",
				"value": 10,
				"type": "grey",
				"level": "blue"
			},
			{
				"name": "Awan",
				"value": 10,
				"type": "grey",
				"level": "green",
				"children": [
					{
						"name": "Enoch",
						"value": 7.5,
						"type": "grey",
						"level": "orange"
					}
				]
			},
			{
				"name": "Azura",
				"value": 10,
				"type": "grey",
				"level": "green"
			}
		]
	};

	/*
	count.subscribe((value) => {
		console.log(value);
	});*/

	/**
	 * TODOs:
	 * 	1. create the svg within the content, not the script tag; that's why we have svelte!
	 * 	2. get rid of the typescript errors (might be solved in task 1)
	 *  3. load our disjoint-set forest model with the config parser instead of this dummy data
	 */

	// set the dimensions and margins of the diagram
	const	margin = {top: 20, right: 90, bottom: 30, left: 90},
			width  = 660 - margin.left - margin.right,
			height = 500 - margin.top - margin.bottom;

	// declares a tree layout and assigns the size
	const treemap = d3.tree().size([height, width]);

	//  assigns the data to a hierarchy using parent-child relationships
	let nodes = d3.hierarchy(treeData);
	nodes = treemap(nodes);
</script>

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
						{node.data.name}
					</text>
				</g>
			{/each}
		</g>
	</svg>
</div>

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