import 'package:statemerger/model/states.dart';
import 'package:test/test.dart';

void main() {
  group('States Tests', () {
    late States states;

    setUp(() {
      states = States();
    });

    test('test makeState creates a new forest with two nodes', () {
      states.makeState('someCity', 'someState');

      expect(states.states.predecessor.length, 2);
      expect(states.states.predecessor['someCity'], 'someState');
      expect(states.states.predecessor['someState'], 'someState');

      expect(states.states.rank.length, 2);
      expect(states.states.rank['someCity'], 0);
      expect(states.states.rank['someState'], 1);
    });

    test('test union merges two states and updates root node', () {
      states.makeState('cityA', 'stateA');
      states.makeState('cityB', 'stateB');

      states.union('stateB', 'stateA');

      expect(states.findState('cityA'), 'stateA');
      expect(states.findState('cityB'), 'stateA');
    });

    test('test union merges two states and updates ranks', () {
      states.makeState('cityA', 'stateA');
      states.makeState('cityB', 'stateB');

      states.union('stateB', 'stateA');

      expect(states.states.rank['cityA'], 0);
      expect(states.states.rank['cityB'], 0);
      expect(states.states.rank['stateA'], 2);
      expect(states.states.rank['stateB'], 1);
    });

    test('test union by rank', () {
      states.makeState('cityA', 'stateA');
      states.makeState('cityB', 'stateB');
      states.makeState('cityC', 'stateC');

      states.union('stateB', 'stateA');

      states.union('stateA', 'stateC');

      expect(states.states.predecessor['stateA'], 'stateA');
      expect(states.states.predecessor['stateB'], 'stateA');
      expect(states.states.predecessor['stateC'], 'stateA');
    });

    test('test union is always merging root nodes', () {
      states.makeState('someCity', 'someState');
      states.makeState('anotherCity', 'anotherState');

      expect(states.states.predecessor['someCity'], 'someState');
      expect(states.states.predecessor['someState'], 'someState');
      expect(states.states.predecessor['anotherCity'], 'anotherState');
      expect(states.states.predecessor['anotherState'], 'anotherState');

      states.union('someCity', 'anotherCity');

      expect(states.states.predecessor['someCity'], 'someState');
      expect(states.states.predecessor['someState'], 'anotherState');
      expect(states.states.predecessor['anotherCity'], 'anotherState');
      expect(states.states.predecessor['anotherState'], 'anotherState');
    });

    test('test findState with path compression', () {
      states.makeState('cityA', 'stateA');
      states.makeState('cityB', 'stateB');
      states.makeState('cityC', 'stateC');

      states.union('stateB', 'stateA');
      states.union('stateC', 'stateB');

      expect(states.states.predecessor['cityC'], 'stateC');
      expect(states.findState('cityC'), 'stateA');
      expect(states.states.predecessor['cityC'], 'stateA');
    });

    test('test findState fails for unknown city', () {
      expect(
          () => states.findState('nonExistingCity'), throwsA(isA<Exception>()));
    });
  });
}
