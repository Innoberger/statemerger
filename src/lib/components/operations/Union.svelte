<script lang="ts">
	export let unionFunction: () => void;
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};
</script>

<div class="input-group mb-2">
	<span class="input-group-text">Union</span>
	<input type="text" placeholder="Erster Knoten" bind:value={mergeNodes.first} class="form-control"/>
	<input type="text" placeholder="Zweiter Knoten" bind:value={mergeNodes.second} disabled={!mergeNodes.first} class="form-control"/>
	<button class="btn btn-outline-danger" type="button" on:click={() => mergeNodes = { first: undefined, second: undefined }} disabled={!(mergeNodes?.first || mergeNodes?.second)}>Leeren</button>
	<button class="btn btn-outline-success" type="button" disabled={!(mergeNodes?.first && mergeNodes?.second) || mergeNodes?.first === mergeNodes?.second} on:click="{() => unionFunction()}">Vereinigen</button>  
</div>