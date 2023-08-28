import { describe, beforeEach, expect, test } from 'vitest';
import { ConfigParser } from '$lib/parser/config-parser'

describe('ConfigParser', () => {
  test('test buildStates constructs correct state-city relationships', () => {
    const configData = {
      states: [
        {
          name: 'StateA',
          cities: ['CityA1', 'CityA2']
        },
        {
          name: 'StateB',
          cities: ['CityB1', 'CityB2']
        }
      ]
    };

    const configParser = new ConfigParser(JSON.stringify(configData));
    const states = configParser.buildStates();

    /*
     * verify the state-city relationships
     */
    expect(states.findState('CityA1')).toBe('StateA');
    expect(states.findState('CityA2')).toBe('StateA');
    expect(states.findState('CityB1')).toBe('StateB');
    expect(states.findState('CityB2')).toBe('StateB');
  });

  test('test buildStates with city name equals another state\'s name', () => {
    const configData = {
      states: [
        {
          name: 'StateA',
          cities: ['Hello']
        },
        {
          name: 'Hello',
          cities: ['CityB']
        }
      ]
    };

    const configParser = new ConfigParser(JSON.stringify(configData));
    const states = configParser.buildStates();

    /*
     * When a city got the name of another state it will just point to itself.
     * This is usually a risky choice! (unhappy flow)
     */
    expect(states.findState('Hello')).toBe('Hello');
  });

  test('test buildStates with on different states contain equal city', () => {
    const configData = {
      states: [
        {
          name: 'StateA',
          cities: ['SameCity']
        },
        {
          name: 'StateB',
          cities: ['SameCity']
        }
      ]
    };

    const configParser = new ConfigParser(JSON.stringify(configData));
    const states = configParser.buildStates();


    /*
     * When different states got the same city, the last in config list is taken.
     * This is usually a risky choice! (unhappy flow)
     */
    expect(states.findState('SameCity')).toBe('StateB');
  });
});
