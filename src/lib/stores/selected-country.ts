import { writable, type Writable } from 'svelte/store';

export const selectedCountry: Writable<{ [key: string]: string; } | undefined> = writable();
export const selectedCountryJson: Writable<any> = writable();