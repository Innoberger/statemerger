import type { Config } from './config';
import { States } from '$lib/model/states';
import { v4 as uuidv4 } from 'uuid';

export class ConfigParser {
    private readonly config: Config;

    /**
     * Set the raw JSON data for this config instance.
     * 
     * @param configData    JSON data to be parsed as disjoint-set forest data structure.
     */
    constructor(configData: string) {
        this.config = JSON.parse(configData);
    }

    /**
     * Parses the config data from JSON into disjoint-set forest data structure for states and cities.
     * 
     * @returns A disjoint-set forest data structure representing states and cities.
     */
    public buildStates(): States {
        const states = new States();

        /*
         * We process each configured state.
         */
        this.config.states?.forEach(state => {
            const stateName = state.name!;

            /*
             * For every state, we process every configured city.
             */
            state.cities?.forEach((city, cityIndex) => {
                if (cityIndex === 0) {
                    /*
                     * The 1st city (index = 0) is defined as the capital city of a state.
                     * We say it will be assigned to the "real" state instead of a dummy city.
                     */
                    states.makeState(city, stateName);
                } else {
                    /*
                     * For every other element, a dummy state for each city is created.
                     * Only the first in the list will get a pointer to it's 'real' state.
                     *
                     * UUID is used to ensure that every dummy state is disjoint (unique).
                     */
                    const dummyState = uuidv4();
                    states.makeState(city, dummyState);


                    /*
                     * Now we can merge the dummyState of cityName with the real state.
                     * Keep in mind that the second argument will set the node name.
                     * At this point it would not matter if we used dummyState as first argument
                     * or city, because union will always link the root node of the tree.
                     * But for better understanding, we use the dummyState.
                     */
                    states.union(dummyState, stateName);
                }
            });
        });

        return states;
    }
}
