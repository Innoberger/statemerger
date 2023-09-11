<script lang="ts">
	export let findStateFunction: (search: string) => void;

	let search: string | undefined


</script>

<div class="input-group mb-2">
	<span class="input-group-text">Find-State</span>
	<div class="form-floating">
		<input type="text" id="search" placeholder="Exakte Bezeichnung einer Stadt oder eines Knotens" bind:value={search} class="form-control"/>
		<label for="search">Exakte Bezeichnung einer Stadt oder eines Knotens</label>
	</div>
	<button class="btn btn-outline-danger" type="button" on:click={() => search = undefined} disabled={!search}>Leeren</button>
	<button class="btn btn-outline-success" type="button" on:click={() => { findStateFunction(search ?? ""); search = undefined; }} disabled={!search}>Suchen</button>
</div>