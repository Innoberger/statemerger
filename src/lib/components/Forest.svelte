<script>
    import Tree from './Tree.svelte';
	import { selectedCountry, selectedCountryConfigJson, selectedCountryStatesForest } from '$lib/stores/selected-country';

    const predecessorMap = {
		"Stuttgart": "Baden-Württemberg",
		"Baden-Württemberg": "Baden-Württemberg",
		"Mannheim": "7e86eb1f-b49f-494d-a7db-a51cf1bddce4",
		"7e86eb1f-b49f-494d-a7db-a51cf1bddce4": "Baden-Württemberg",
		"Karlsruhe": "a47871fd-485a-45c0-8c3a-300f83eade7b",
		"a47871fd-485a-45c0-8c3a-300f83eade7b": "Baden-Württemberg"
	};
</script>

<Tree {predecessorMap} />