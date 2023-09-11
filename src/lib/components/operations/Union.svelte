<script lang="ts">
	export let unionFunction: () => void;
	export let mergeNodes: {
		first: string | undefined;
		second: string | undefined
	};
</script>

<div class="input-group mb-2">
	<span class="input-group-text">Union</span>
	<div class="form-floating">
		<input type="text" id="firstNode" placeholder="Erster Knoten" bind:value={mergeNodes.first} class="form-control"/>
		<label for="firstNode">Erster Knoten</label>
	</div>
	<div class="form-floating">
		<input type="text" id="secondNode" placeholder="Zweiter Knoten" bind:value={mergeNodes.second} class="form-control" disabled={!mergeNodes.first}/>
		<label for="secondNode">Zweiter Knoten</label>
	</div>
	<button class="btn btn-outline-danger" type="button" on:click={() => mergeNodes = { first: undefined, second: undefined }} disabled={!(mergeNodes?.first || mergeNodes?.second)}>Leeren</button>
	<button class="btn btn-outline-success" type="button" disabled={!(mergeNodes?.first && mergeNodes?.second) || mergeNodes?.first === mergeNodes?.second} on:click="{() => unionFunction()}">Vereinigen</button>  
</div>