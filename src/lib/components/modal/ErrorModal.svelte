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

	const modalClose = () => {
		open = false;
	}
</script>

{#if open}  
	<div class="modal" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorModalLabel" aria-hidden={false} on:click|self={() => modalClose()}>
		<div class="modal-dialog" role="document" in:fly={{ y: -50, duration: 300 }} out:fly={{ y: -50, duration: 300, easing: quintOut }}>
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="errorModalLabel">
						<i class="bi-exclamation-triangle-fill"></i>
						<span>{title}</span>
					</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" on:click={() => modalClose()}></button>
				</div>
				<div class="modal-body">
					<slot></slot>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" on:click={() => modalClose()}>Schließen</button>
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