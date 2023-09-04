<script>
	import * as d3 from 'd3';
	import { onMount } from 'svelte';

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

	onMount(() => {
		//  assigns the data to a hierarchy using parent-child relationships
		let nodes = d3.hierarchy(treeData);

		// maps the node data to the tree layout
		nodes = treemap(nodes);

		// append the svg object to the body of the page
		// appends a 'group' element to 'svg'
		// moves the 'group' element to the top left margin
		const g = d3.select("#svg-group");

		// adds the links between the nodes
		const link = g.selectAll(".link")
			.data( nodes.descendants().slice(1))
			.enter().append("path")
			.attr("class", "link")
			.style("stroke", d => d.data.level)
			.style("fill", "none")
			.attr("d", d => {
				return "M" + d.y + "," + d.x
					+ "C" + (d.y + d.parent.y) / 2 + "," + d.x
					+ " " + (d.y + d.parent.y) / 2 + "," + d.parent.x
					+ " " + d.parent.y + "," + d.parent.x;
			});

		// adds each node as a group
		const node = g.selectAll(".node")
			.data(nodes.descendants())
			.enter().append("g")
			.attr("class", d => "node" + (d.children ? " node--internal" : " node--leaf"))
			.attr("transform", d => "translate(" + d.y + "," + d.x + ")");

		// adds the circle to the node
		node.append("circle")
			.attr("r", d => d.data.value)
			.style("stroke", d => d.data.type)
			.style("fill", d => d.data.level);

		// adds the text to the node
		node.append("text")
			.attr("dy", ".35em")
			.attr("x", d => d.children ? (d.data.value + 5) * -1 : d.data.value + 5)
			.attr("y", d => d.children && d.depth !== 0 ? -(d.data.value + 5) : d)
			.style("text-anchor", d => d.children ? "end" : "start")
			.text(d => d.data.name);
	});
</script>

<div id="tree-container">
	<svg
		id="svg-container"
		width={width + margin.left + margin.right}
		height={height + margin.top + margin.bottom}
	>
		<g id="svg-group" transform="translate({margin.left},{margin.top})"></g>
	</svg>
</div>

<style>
	body {
		background-color: #eee;
	}

	.node circle {
		fill: #fff;
		stroke: steelblue;
		stroke-width: 3px;
	}

	.node text {
		font: 16px sans-serif;
	}

	.link {
		fill: none;
		stroke: #ccc;
		stroke-width: 2px;
	}
</style>