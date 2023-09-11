import type { States } from '$lib/model/states';
import type { Config } from '$lib/parser/config';
import { writable, type Writable } from 'svelte/store';

/*
 * Type definition of country metadata
 */
export type CountryMeta = {
    code: string,
    name: string
}

export const selectedCountry: Writable<CountryMeta | undefined> = writable();
export const selectedCountryStatesForest: Writable<States> = writable();