import type { States } from '$lib/model/states';
import { writable, type Writable } from 'svelte/store';

export const selectedCountry: Writable<{ [key: string]: string; } | undefined> = writable();
export const selectedCountryJson: Writable<Promise<any>> = writable();
export const selectedCountryStatesForest: Writable<States> = writable();