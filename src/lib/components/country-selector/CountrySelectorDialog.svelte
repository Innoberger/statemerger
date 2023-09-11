<script lang="ts">
	import type { Config } from '$lib/parser/config';
	import { ConfigParser } from '$lib/parser/config-parser';
	import {
		selectedCountry,
		selectedCountryStatesForest,
		type CountryMeta
	} from '$lib/stores/selected-country';

	export let graphSettings: {
		toggleUuidNodeLabels: boolean,
		toggleInnerNodeLabels: boolean,
		toggleNodeRanks: boolean
	}
	
	let error: string | undefined
	let countryJson: Promise<Config>

	const availableCountries = [
		{
			"code": "de",
			"name": "Deutschland"
		},
		{
			"code": "at",
			"name": "Österreich"
		}
	]

	async function loadCountryJson(countryCode: string) {
		countryJson = fetch(`/countries/${countryCode}.json`).then(async (response) => {
			let responseJson = await response.json()
			makeDisjointSetForest(responseJson)
			return responseJson
		})
	}

	function makeDisjointSetForest(responseJson: Config) {
		$selectedCountryStatesForest = new ConfigParser(JSON.stringify(responseJson)).buildStates()
	}

	function setCountry(country: CountryMeta) {
		if (!availableCountries.find(_country => _country === country)) {
			error = "Das ausgewählte Land ist ungültig."
			return
		}

		loadCountryJson(country.code)
		
		error = undefined
		$selectedCountry = country
	}
</script>

<div class="offcanvas offcanvas-start" tabindex="-1" id="country-selector-dialog" aria-labelledby="country-selector-dialog-label">
	<div class="offcanvas-header">
		<h5 class="offcanvas-title" id="country-selector-dialog-label">Einstellungen</h5>
		<button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
	</div>
	<div class="offcanvas-body">
		<div class="pb-10">
			<h6>Länderauswahl</h6>
			<div>
				<p>Die folgende Liste zeigt die verfügbaren Länder von StateMerger zum jetzigen Zeitpunkt.</p>
				<p>Bitte eines der nachfolgenden Länder auswählen, um zu beginnen.</p>
			</div>
			{#if countryJson}
				{#await countryJson}
					<div class="alert alert-info d-flex align-items-center" role="alert">
						<div class="me-3">
							<div class="spinner-border spinner-border-sm ms-auto" aria-hidden="true"></div>
						</div>
						<div>
							<strong role="status">Loading...</strong>
						</div>
					</div>
				{:then value}
					<div class="alert alert-success d-flex align-items-center" role="alert">
						<div class="me-3">
							<i class="bi-check-circle-fill"></i>
						</div>
						<div>
							Die Länder-Datei zu <strong>{$selectedCountry ? $selectedCountry.name : "unbekannt"}</strong> wurde erfolgreich geladen.
						</div>
					</div>
				{:catch _error}
					<div class="alert alert-danger d-flex align-items-center" role="alert">
						<div class="me-3">
							<i class="bi-exclamation-triangle-fill"></i>
						</div>
						<div>
							Die Länder-Datei zu <strong>{$selectedCountry ? $selectedCountry.name : "unbekannt"}</strong> konnte nicht geladen werden.
						</div>
					</div>
				{/await}
			{:else if !countryJson && !error}
				<div class="alert alert-warning d-flex align-items-center" role="alert">
					<div class="me-3">
						<i class="bi-exclamation-triangle-fill"></i>
					</div>
					<div>
						Sobald ein Land geladen wurde, wird die Ansicht zurückgesetzt und ggf. eigens erstellte Städte und Bundesländer entfernt.
					</div>
				</div>
			{/if}
			{#if error}
				<div class="alert alert-danger d-flex align-items-center" role="alert">
					<div class="me-3">
						<i class="bi-exclamation-triangle-fill"></i>
					</div>
					<div>
						{error}
					</div>
				</div>
			{/if}
			<div class="dropdown mt-3">
				<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" disabled={countryJson===null}>
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
		<hr class="mb-4 mt-4" />
		<div class="mt-12">
			<h6>Anzeigeoptionen</h6>
			<div class="form-check form-switch mb-2">
				<input class="form-check-input" type="checkbox" role="switch" id="toggleUuidNodeLabels" bind:checked={graphSettings.toggleUuidNodeLabels}>
				<label class="form-check-label" for="toggleUuidNodeLabels">Beschriftung von Hilfsknoten mit UUID anzeigen</label>
			</div>
			<div class="form-check form-switch mb-2">
				<input class="form-check-input" type="checkbox" role="switch" id="toggleInnerNodeLabels" bind:checked={graphSettings.toggleInnerNodeLabels}>
				<label class="form-check-label" for="toggleInnerNodeLabels">Beschriftung innerer Knoten anzeigen</label>
			</div>
			<div class="form-check form-switch mb-2">
				<input class="form-check-input" type="checkbox" role="switch" id="toggleNodeRanks" bind:checked={graphSettings.toggleNodeRanks}>
				<label class="form-check-label" for="toggleNodeRanks">Ränge von Knoten anzeigen</label>
			</div>
		</div>
	</div>
</div>

<style>
	.cursor-hover:hover {
		cursor: pointer;
	}
</style>