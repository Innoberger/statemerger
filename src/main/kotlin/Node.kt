/**
 * Node class for a disjoint-set forest.
 * Each node points to a `predecessor` node,
 * has a `rank` and can store `data`.
 *
 * The root node does not contain a predecessor,
 * so it points to / contains `null`.
 *
 * @param predecessor   Preceding tree node, can be `null`
 * @param rank          Upper bound on the node height
 * @param data          Optional stored data, can be `null`
 */
class Node (
    var predecessor: Node? = null,
    var rank: Int,
    val representative: Any? = null
): NodeRepresentative(representative)