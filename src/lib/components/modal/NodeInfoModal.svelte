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

	export let open = false;
	export let showBackdrop = true;
	export let title = "Modal title";
	export let unionFunction: () => void;
	export let inSameTree: (first: string, second: string) => boolean;
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};

	const modalClose = () => {
		open = false;
	}

	function selectNode() {
		mergeNodes.first = title
	}
</script>

{#if open}  
	<div class="modal" id="nodeInfoModal" tabindex="-1" role="dialog" aria-labelledby="nodeInfoModalLabel" aria-hidden={false} on:click|self={() => modalClose()}>
		<div class="modal-dialog" role="document" in:fly={{ y: -50, duration: 300 }} out:fly={{ y: -50, duration: 300, easing: quintOut }}>
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="nodeInfoModalLabel">{title}</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" on:click={() => modalClose()}></button>
				</div>
				<div class="modal-body">
					<slot></slot>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" on:click={() => modalClose()}>Schließen</button>
					{#if mergeNodes?.first}
						{#if inSameTree(mergeNodes.first, title)}
							<button type="button" class="btn btn-danger" disabled>
								Vereinigen mit <strong>{mergeNodes.first}</strong> nicht möglich
							</button>
						{:else}
							<button type="button" class="btn btn-success" on:click={() => { modalClose(); mergeNodes.second = title; unionFunction(); }}>
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