<script lang="ts">
	import { selectedCountry } from '$lib/stores/selected-country';
	
	let error: string | undefined;

	const availableCountries = [
		{
			"code": "de",
			"name": "Deutschland"
		}
	]

	function setCountry(country: { [key: string]: string; }) {
		if (!availableCountries.find(_country => _country === country)) {
			error = "Das ausgewählte Land ist ungültig."
			return
		}
		
		error = undefined;
		$selectedCountry = country
	}
</script>

<div class="offcanvas offcanvas-start" tabindex="-1" id="country-selector-dialog" aria-labelledby="country-selector-dialog-label">
	<div class="offcanvas-header">
		<h5 class="offcanvas-title" id="country-selector-dialog-label">Land auswählen</h5>
		<button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
	</div>
	<div class="offcanvas-body">
		<div>
			<p>Die folgende Liste zeigt die verfügbaren Länder von StateMerger zum jetzigen Zeitpunkt.</p>
			<p>Bitte eines der nachfolgenden Länder auswählen, um zu beginnen.</p>			
		</div>
		{#if error}
			<div class="alert alert-danger d-flex align-items-center" role="alert">
				<div class="me-2">
					<i class="bi-exclamation-triangle"></i>
				</div>
				<div>
					{error}
				</div>
			</div>
		{/if}
		<div class="dropdown mt-3">
			<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
				{$selectedCountry ? $selectedCountry.name : "nicht ausgewählt"}
			</button>
			<ul class="dropdown-menu">
				{#each availableCountries as country}
					<!-- svelte-ignore a11y-click-events-have-key-events -->
					<!-- svelte-ignore a11y-no-noninteractive-element-interactions -->
					<li class="dropdown-item cursor-hover" on:click={() => setCountry(country)}>{country.name}</li>
				{/each}
			</ul>
		</div>
	</div>
</div>

<style>
	.cursor-hover:hover {
		cursor: pointer;
	}
</style>