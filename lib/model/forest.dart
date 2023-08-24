class Forest {
  final Map<String, String> _predecessor = {};
  final Map<String, int> _rank = {};

  /// Creates a new set containing a single element `x`.
  ///
  /// @param x The element to create a set for.
  void makeSet(String x) {
    _predecessor[x] = x;
    _rank[x] = 0;
  }

  /// Merges the root nodes of `recessiveNode` and `dominantNode`
  /// into one node, where `dominantNode` will be the new root node.
  /// The key/name of `dominantNode`'s root node will be kept.
  ///
  /// @param recessiveNode The node to merge, omitting its name.
  /// @param dominantNode The node to merge, keeping its name.
  void union(String recessiveNode, String dominantNode) {
    link(findSet(recessiveNode)!, findSet(dominantNode)!);
  }

  /// Links `recessiveNode` and `dominantNode` into one node,
  /// where `dominantNode` will be the new root node.
  /// The key/name of `dominantNode` will be kept.
  ///
  /// However, union by rank will always take precedence over dominantNode.
  ///
  /// @param recessiveNode The node to link, omitting its name.
  /// @param dominantNode The node to link, keeping its name.
  void link(String recessiveNode, String dominantNode) {
    if (_rank[recessiveNode]! > _rank[dominantNode]!) {
      _predecessor[dominantNode] = recessiveNode;
      return;
    }

    _predecessor[recessiveNode] = dominantNode;

    if (_rank[recessiveNode] == _rank[dominantNode]) {
      _rank[dominantNode] = _rank[dominantNode]! + 1;
    }
  }

  /// Finds the root node for the given element `x`.
  ///
  /// Performs path compression to optimize future find operations.
  ///
  /// @param x The element to find the root node for.
  /// @return The root node for the given element.
  String? findSet(String x) {
    if (x != _predecessor[x]) {
      _predecessor[x] = findSet(_predecessor[x]!)!;
    }

    return _predecessor[x];
  }

  @override
  String toString() {
    return 'Forest(predecessor: $_predecessor, rank: $_rank)';
  }
}
