<script lang="ts">
	export let makeStateFunction: (city: string, state: string) => boolean;

	let city: string | undefined;
	let state: string | undefined;

	/**
	 * Executes `makeStateFunction` of this component.
	 * If successful, `city` and `state` will be cleared.
	 */
	function execMakeState() {
		if (!makeStateFunction(city ?? "", state ?? "")) return;

		city = undefined;
		state = undefined;
	}
</script>

<div class="input-group mb-2">
	<span class="input-group-text">Make-State</span>
	<div class="form-floating">
		<input type="text" id="city" placeholder="Neue Stadt" bind:value={city} class="form-control"/>
		<label for="city">Neue Stadt</label>
	</div>
	<div class="form-floating">
		<input type="text" id="state" placeholder="Neues Bundesland" bind:value={state} class="form-control" disabled={!city}/>
		<label for="state">Neues Bundesland</label>
	</div>
	<button class="btn btn-outline-danger" type="button" on:click={() => { city = undefined; state = undefined }} disabled={!(city || state)}>Leeren</button>
	<button class="btn btn-outline-success" type="button" disabled={!(city && state) || city === state} on:click="{() => execMakeState()}">Erstellen</button>
</div>