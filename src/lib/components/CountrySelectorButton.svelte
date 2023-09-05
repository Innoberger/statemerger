<script lang="ts">
	import { selectedCountry } from '$lib/stores/selected-country';
</script>

<div class="input-group mb-3">
	<button class="btn btn-outline-primary" type="button" id="select-country-dialog-trigger-button" data-bs-toggle="offcanvas" data-bs-target="#country-selector-dialog" aria-controls="country-selector-dialog">
		Land {$selectedCountry ? "ändern" : "auswählen..."}
	</button>
	{#if $selectedCountry}
		<input type="text" class="form-control" aria-label="Ausgewähltes Land" aria-describedby="select-country-dialog-trigger-button" value={$selectedCountry.name} readonly>
	{:else}
		<input type="text" class="form-control" aria-label="Ausgewähltes Land" aria-describedby="select-country-dialog-trigger-button" placeholder="nicht ausgewählt" readonly>
	{/if}
</div>
