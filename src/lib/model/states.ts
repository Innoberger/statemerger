import { Forest } from "./forest";

export class States {
    private _states = new Forest();

    public get states() {
        return this._states;
    }

    public makeState(city: string, state: string): void {
        this._states.makeSet(city);
        this._states.makeSet(state);
        this._states.union(city, state);
    }

    /**
     * Merges `recessiveState` and `dominantState` into one state,
     * where `dominantState` will be the root node.
     * Thus, the name of `dominantState` will be kept.
     *
     * @param recessiveState    State to merge, will omit its name.
     * @param dominantState     State to merge, will keep its name.
     */
    public union(recessiveState: string, dominantState: string): void {
        this._states.union(recessiveState, dominantState);
    }

    public findState(city: string): string | undefined {
        return this._states.findSet(city);
    }
}
