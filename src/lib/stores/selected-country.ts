import { writable, type Writable } from 'svelte/store';

export const selectedCountry: Writable<{ [key: string]: string; } | undefined> = writable();