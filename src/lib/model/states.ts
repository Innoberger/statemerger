import { Forest } from "./forest";

export class States {
    private _states: Forest = new Forest();

    public get states(): Forest {
        return this._states;
    }

    /**
     * Creates a new state tree, containing a `city`
     * pointing to its `state` as root node. 
     * 
     * @param city      Name of the city node.
     * @param state     Name of the state (root) node.
     */
    public makeState(city: string, state: string): void {
        this._states.makeSet(city);
        this._states.makeSet(state);
        this._states.union(city, state);
    }

    /**
     * Merges `recessiveState` and `dominantState` into one state,
     * where `dominantState` will be the root node.
     * Thus, the name of `dominantState` will be kept, but
     * only if union by rank heuristic is not violated.
     *
     * @param recessiveState    State to merge, will omit its name.
     * @param dominantState     State to merge, will keep its name.
     */
    public union(recessiveState: string, dominantState: string): void {
        this._states.union(recessiveState, dominantState);
    }

    /**
     * Finds the state (root node) of a city.
     * 
     * @param city  A city to find its state.
     * @returns     Root node (representing the `state`) of the tree containing `city`, or `undefined` if not found.
     */
    public findState(city: string): string | undefined {
        return this._states.findSet(city);
    }
}
