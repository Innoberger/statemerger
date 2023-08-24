import 'package:statemerger/model/forest.dart';
import 'package:test/test.dart';

void main() {
  group('Forest Tests', () {
    late Forest forest;

    setUp(() {
      forest = Forest();
    });

    test('test makeSet creates a new set', () {
      forest.makeSet('someElement');

      expect(forest.predecessor.length, 1);
      expect(forest.predecessor['someElement'], 'someElement');

      expect(forest.rank.length, 1);
      expect(forest.rank['someElement'], 0);
    });

    test('test union merges two sets and updates root node', () {
      forest.makeSet('someElement');
      forest.makeSet('anotherElement');

      forest.union('someElement', 'anotherElement');

      expect(forest.predecessor['someElement'], 'anotherElement');
      expect(forest.predecessor['anotherElement'], 'anotherElement');
    });

    test('test union merges two sets and updates ranks', () {
      forest.makeSet('someElement');
      forest.makeSet('anotherElement');

      forest.union('someElement', 'anotherElement');

      expect(forest.rank['someElement'], 0);
      expect(forest.rank['anotherElement'], 1);
    });

    test('test union by rank', () {
      forest.makeSet('1stElement');
      forest.makeSet('2ndElement');
      forest.makeSet('3rdElement');

      forest.union('2ndElement', '1stElement');

      forest.union('1stElement', '3rdElement');

      expect(forest.predecessor['1stElement'], '1stElement');
      expect(forest.predecessor['2ndElement'], '1stElement');
      expect(forest.predecessor['3rdElement'], '1stElement');
    });

    test('test union is always merging root nodes', () {
      forest.makeSet('1stElement');
      forest.makeSet('2ndElement');
      forest.makeSet('3rdElement');
      forest.makeSet('4thElement');

      expect(forest.predecessor['1stElement'], '1stElement');
      expect(forest.predecessor['2ndElement'], '2ndElement');
      expect(forest.predecessor['3rdElement'], '3rdElement');
      expect(forest.predecessor['4thElement'], '4thElement');

      forest.union('2ndElement', '1stElement');
      forest.union('4thElement', '3rdElement');

      expect(forest.predecessor['1stElement'], '1stElement');
      expect(forest.predecessor['2ndElement'], '1stElement');
      expect(forest.predecessor['3rdElement'], '3rdElement');
      expect(forest.predecessor['4thElement'], '3rdElement');

      forest.union('3rdElement', '1stElement');

      expect(forest.predecessor['1stElement'], '1stElement');
      expect(forest.predecessor['2ndElement'], '1stElement');
      expect(forest.predecessor['3rdElement'], '1stElement');
      expect(forest.predecessor['4thElement'], '3rdElement');
    });

    test('test findSet with path compression', () {
      forest.makeSet('1stElement');
      forest.makeSet('2ndElement');
      forest.makeSet('3rdElement');

      forest.union('2ndElement', '1stElement');

      forest.union('3rdElement', '2ndElement');

      expect(forest.findSet('3rdElement'), '1stElement');
      expect(forest.predecessor['3rdElement'], '1stElement');
    });

    test('test findSet fails for unknown element', () {
      expect(() => forest.findSet('nonExistingElement'),
          throwsA(isA<Exception>()));
    });

    test('test toString provides correct representation', () {
      forest.makeSet('someElement');
      forest.makeSet('anotherElement');

      forest.union('someElement', 'anotherElement');
      final expected =
          'Forest(predecessor: {someElement: anotherElement, anotherElement: anotherElement}, rank: {someElement: 0, anotherElement: 1})';
      expect(forest.toString(), expected);
    });
  });
}
