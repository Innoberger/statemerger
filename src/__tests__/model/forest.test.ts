import { Forest } from '../../lib/model/forest';

describe('Forest', () => {
  let forest: Forest;

  beforeEach(() => {
    forest = new Forest();
  });

  test('test makeSet creates a new set', () => {
    forest.makeSet('someElement');

    expect(Object.keys(forest.predecessor).length).toBe(1);
    expect(forest.predecessor['someElement']).toBe('someElement');

    expect(Object.keys(forest.rank).length).toBe(1);
    expect(forest.rank['someElement']).toBe(0);
  });

  test('test union merges two sets and updates root node', () => {
    forest.makeSet('someElement');
    forest.makeSet('anotherElement');

    forest.union('someElement', 'anotherElement');

    expect(forest.predecessor['someElement']).toBe('anotherElement');
    expect(forest.predecessor['anotherElement']).toBe('anotherElement');
  });

  test('test union merges two sets and updates ranks', () => {
    forest.makeSet('someElement');
    forest.makeSet('anotherElement');

    forest.union('someElement', 'anotherElement');

    expect(forest.rank['someElement']).toBe(0);
    expect(forest.rank['anotherElement']).toBe(1);
  });

  test('test union by rank', () => {
    forest.makeSet('1stElement');
    forest.makeSet('2ndElement');
    forest.makeSet('3rdElement');

    forest.union('2ndElement', '1stElement');

    // Instead of using 3rdElement as the new root node,
    // it uses 1stElement as the new root node,
    // because higher ranks will always win over dominantNode.
    forest.union('1stElement', '3rdElement');

    expect(forest.predecessor['1stElement']).toBe('1stElement');
    expect(forest.predecessor['2ndElement']).toBe('1stElement');
    expect(forest.predecessor['3rdElement']).toBe('1stElement');
  });

  test('test union is always merging root nodes', () => {
    forest.makeSet('1stElement');
    forest.makeSet('2ndElement');
    forest.makeSet('3rdElement');
    forest.makeSet('4thElement');

    expect(forest.predecessor['1stElement']).toBe('1stElement');
    expect(forest.predecessor['2ndElement']).toBe('2ndElement');
    expect(forest.predecessor['3rdElement']).toBe('3rdElement');
    expect(forest.predecessor['4thElement']).toBe('4thElement');

    forest.union('2ndElement', '1stElement');
    forest.union('4thElement', '3rdElement');

    expect(forest.predecessor['1stElement']).toBe('1stElement');
    expect(forest.predecessor['2ndElement']).toBe('1stElement');
    expect(forest.predecessor['3rdElement']).toBe('3rdElement');
    expect(forest.predecessor['4thElement']).toBe('3rdElement');

    forest.union('3rdElement', '1stElement');

    expect(forest.predecessor['1stElement']).toBe('1stElement');
    expect(forest.predecessor['2ndElement']).toBe('1stElement');
    expect(forest.predecessor['3rdElement']).toBe('1stElement');
    expect(forest.predecessor['4thElement']).toBe('3rdElement');
  });

  test('test findSet with path compression', () => {
    forest.makeSet('1stElement');
    forest.makeSet('2ndElement');
    forest.makeSet('3rdElement');

    forest.union('2ndElement', '1stElement');

    expect(forest.predecessor['3rdElement']).toBe('3rdElement');

    forest.union('3rdElement', '2ndElement');

    expect(forest.findSet('3rdElement')).toBe('1stElement');
    expect(forest.predecessor['3rdElement']).toBe('1stElement');
  });

  test('test findSet fails for unknown element', () => {
    expect(() => {
      forest.findSet('nonExistingElement');
    }).toThrow();
  });

  test('test toString provides correct representation', () => {
    forest.makeSet('someElement');
    forest.makeSet('anotherElement');

    forest.union('someElement', 'anotherElement');
    const expected =
      'Forest(predecessor={"someElement":"anotherElement","anotherElement":"anotherElement"}, rank={"someElement":0,"anotherElement":1})';
    expect(forest.toString()).toBe(expected);
  });
});
