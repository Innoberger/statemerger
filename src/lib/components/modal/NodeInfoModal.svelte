<script lang="ts">
	/**
	 * ---
	 * 
	 * I took some of this code from https://www.eternaldev.com/blog/using-bootstrap-modal-in-svelte/
	 * to handle bootstrap modals with svelte.
	 * Thanks to Sriram Thiagarajan for his great article!
	 * 
	 * ---
	 */
	import { fade, fly } from "svelte/transition";
	import { quintOut } from "svelte/easing";
	import { isUUID } from "class-validator";
	import { selectedCountryStatesForest } from "$lib/stores/selected-country";

	export let open = false;
	export let showBackdrop = true;
	export let unionFunction: () => void;
	export let inSameTree: (first: string, second: string) => boolean;
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};
	export let selectedNode: {
		name: string,
		depth: number,
		root: string,
		path: string[]
	};

	const modalClose = () => {
		open = false;
	}

	function selectNode() {
		mergeNodes.first = selectedNode.name
	}

	function getNodeType(nodeName: string) {
		if ($selectedCountryStatesForest!.states.predecessor[nodeName] === nodeName) return "Bundesland (Root)";
		if (isUUID(nodeName, 4)) return "Hilfsknoten";
		if (!Object.entries($selectedCountryStatesForest!.states.predecessor).find(([_, _predecessor]) => _predecessor === nodeName)) return "Blattknoten";

		return "Stadt oder vereintes Bundesland";
	}
</script>

{#if open}  
	<div class="modal" id="nodeInfoModal" tabindex="-1" role="dialog" aria-labelledby="nodeInfoModalLabel" aria-hidden={false} on:click|self={() => modalClose()}>
		<div class="modal-dialog" role="document" in:fly={{ y: -50, duration: 300 }} out:fly={{ y: -50, duration: 300, easing: quintOut }}>
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="nodeInfoModalLabel">{selectedNode.name}</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" on:click={() => modalClose()}></button>
				</div>
				<div class="modal-body">
					<table class="table table-borderless">
						<tbody>
							<tr>
								<th scope="row">Knotentyp</th>
								<td>{getNodeType(selectedNode.name)}</td>
							</tr>
							<tr>
								<th scope="row">Rang</th>
								<td>{$selectedCountryStatesForest?.states.rank[selectedNode.name]}</td>
							</tr>
							<tr>
								<th scope="row">Bundesland (Root)</th>
								<td>{selectedNode.root}</td>
							</tr>
							<tr>
								<th scope="row">Direkter Vorgänger</th>
								<td>{$selectedCountryStatesForest?.states.predecessor[selectedNode.name]}</td>
							</tr>
							<tr>
								<th scope="row">Ebene / Tiefe</th>
								<td>{selectedNode.depth}</td>
							</tr>
							<tr>
								<th scope="row">Pfad</th>
								<td>
									<ol class="breadcrumb" style="--bs-breadcrumb-divider: '>';">
										{#each selectedNode.path.reverse() as _node}
											<li class="breadcrumb-item">{_node}</li>
										{/each}
									</ol>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" on:click={() => modalClose()}>Schließen</button>
					{#if mergeNodes?.first}
						{#if inSameTree(mergeNodes.first, selectedNode.name)}
							<button type="button" class="btn btn-danger" disabled>
								Vereinigen mit <strong>{mergeNodes.first}</strong> nicht möglich
							</button>
						{:else}
							<button type="button" class="btn btn-success" on:click={() => { modalClose(); mergeNodes.second = selectedNode.name; unionFunction(); }}>
								Vereinigen mit <strong>{mergeNodes.first}</strong>
							</button>
						{/if}
					{:else}
						<button type="button" class="btn btn-primary" on:click={() => { modalClose(); selectNode(); }}>
							zur Vereinigung auswählen
						</button>
					{/if}
				</div>
			</div>
		</div>
	</div>
	{#if showBackdrop}
		<div class="modal-backdrop show" transition:fade={{ duration: 150 }} />
	{/if}
{/if}

<style>
	.modal {
		display: block;
	}
</style>