import 'forest.dart';

class States {
  final Forest _states = Forest();

  /// Creates a new state with the given city and state names,
  /// and merges them into one state.
  void makeState(String city, String state) {
    _states.makeSet(city);
    _states.makeSet(state);
    _states.union(city, state);
  }

  /// Merges two states into one, where `dominantState` becomes
  /// the root state. The name of `dominantState` will be kept.
  ///
  /// @param recessiveState The state to merge, omitting its name.
  /// @param dominantState The state to merge, keeping its name.
  void union(String recessiveState, String dominantState) {
    _states.union(recessiveState, dominantState);
  }

  /// Finds the root state for the given city name.
  ///
  /// @param city The city name to find the root state for.
  /// @return The root state for the given city.
  String? findState(String city) {
    return _states.findSet(city);
  }
}
