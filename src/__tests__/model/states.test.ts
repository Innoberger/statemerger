import { describe, beforeEach, expect, test } from 'vitest';
import { States } from '$lib/model/states';

describe('States', () => {
  let states: States;

  beforeEach(() => {
    states = new States();
  });

  test('test makeState creates a new forest with two nodes', () => {
    states.makeState('someCity', 'someState');

    expect(Object.keys(states.states.predecessor).length).toBe(2);
    expect(states.states.predecessor['someCity']).toBe('someState');
    expect(states.states.predecessor['someState']).toBe('someState');

    expect(Object.keys(states.states.rank).length).toBe(2);
    expect(states.states.rank['someCity']).toBe(0);
    expect(states.states.rank['someState']).toBe(1);
  });

  test('test union merges two states and updates root node', () => {
    states.makeState('cityA', 'stateA');
    states.makeState('cityB', 'stateB');

    states.union('stateB', 'stateA');

    expect(states.findState('cityA')).toBe('stateA');
    expect(states.findState('cityB')).toBe('stateA');
  });

  test('test union merges two states and updates ranks', () => {
    states.makeState('cityA', 'stateA');
    states.makeState('cityB', 'stateB');

    states.union('stateB', 'stateA');

    expect(states.states.rank['cityA']).toBe(0);
    expect(states.states.rank['cityB']).toBe(0);
    expect(states.states.rank['stateA']).toBe(2);
    expect(states.states.rank['stateB']).toBe(1);
  });

  test('test union by rank', () => {
    states.makeState('cityA', 'stateA');
    states.makeState('cityB', 'stateB');
    states.makeState('cityC', 'stateC');

    states.union('stateB', 'stateA');

    // Instead of using stateC as the new root node,
    // it chooses stateA as the new root node,
    // because higher ranks will always win over dominantState.
    states.union('stateA', 'stateC');

    expect(states.states.predecessor['stateA']).toBe('stateA');
    expect(states.states.predecessor['stateB']).toBe('stateA');
    expect(states.states.predecessor['stateC']).toBe('stateA');
  });

  test('test union is always merging root nodes', () => {
    states.makeState('someCity', 'someState');
    states.makeState('anotherCity', 'anotherState');

    expect(states.states.predecessor['someCity']).toBe('someState');
    expect(states.states.predecessor['someState']).toBe('someState');
    expect(states.states.predecessor['anotherCity']).toBe('anotherState');
    expect(states.states.predecessor['anotherState']).toBe('anotherState');

    states.union('someCity', 'anotherCity');

    expect(states.states.predecessor['someCity']).toBe('someState');
    expect(states.states.predecessor['someState']).toBe('anotherState');
    expect(states.states.predecessor['anotherCity']).toBe('anotherState');
    expect(states.states.predecessor['anotherState']).toBe('anotherState');
  });

  test('test findState with path compression', () => {
    states.makeState('cityA', 'stateA');
    states.makeState('cityB', 'stateB');
    states.makeState('cityC', 'stateC');

    states.union('stateB', 'stateA');
    states.union('stateC', 'stateB');

    expect(states.states.predecessor['cityC']).toBe('stateC');
    expect(states.findState('cityC')).toBe('stateA');
    expect(states.states.predecessor['cityC']).toBe('stateA');
  });

  test('test findState fails for unknown city', () => {
    expect(states.findState('nonExistingCity')).toBe(undefined);
  });

});
