<script lang="ts">
	import { selectedCountry, selectedCountryStatesForest } from '$lib/stores/selected-country';
</script>

<div class="input-group mb-4">
	<span class="input-group-text">Land</span>
	{#if $selectedCountry}
		<input type="text" class="form-control" aria-label="Ausgewähltes Land" aria-describedby="select-country-dialog-trigger-button" value={$selectedCountry.name} readonly>
	{:else}
		<input type="text" class="form-control" aria-label="Ausgewähltes Land" aria-describedby="select-country-dialog-trigger-button" placeholder="nicht ausgewählt" readonly>
	{/if}
	<button class="btn btn-outline-primary" type="button" id="select-country-dialog-trigger-button" data-bs-toggle="offcanvas" data-bs-target="#country-selector-dialog" aria-controls="country-selector-dialog">
		Einstellungen
	</button>
	{#if $selectedCountryStatesForest}
		<button class="btn btn-outline-danger" type="button" on:click={() => { $selectedCountry = undefined; $selectedCountryStatesForest = undefined; }}>
			Graph leeren
		</button>
	{/if}
</div>
