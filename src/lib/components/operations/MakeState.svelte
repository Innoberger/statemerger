<script lang="ts">
	export let makeStateFunction: (city: string, state: string) => boolean;

	let city: string | undefined;
	let state: string | undefined;

	function execMakeState() {
		if (!makeStateFunction(city ?? "", state ?? "")) return;

		city = undefined;
		state = undefined;
	}
</script>

<div class="input-group mb-2">
	<span class="input-group-text">Make-State</span>
	<input type="text" placeholder="Neue Stadt" bind:value={city} class="form-control"/>
	<input type="text" placeholder="Neues Bundesland" bind:value={state} disabled={!city} class="form-control"/>
	<button class="btn btn-outline-danger" type="button" on:click={() => { city = undefined; state = undefined }} disabled={!(city || state)}>Leeren</button>
	<button class="btn btn-outline-success" type="button" disabled={!(city && state) || city === state} on:click="{() => execMakeState()}">Erstellen</button>
</div>