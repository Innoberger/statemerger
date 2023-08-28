export class Forest {
    private _predecessor: { [key: string]: string; } = {};
    private _rank: { [key: string]: number; } = {};

    public get predecessor(): { [key: string]: string; } {
        return this._predecessor;
    }

    public get rank(): { [key: string]: number; } {
        return this._rank;
    }

    public makeSet(x: string): void {
        this._predecessor[x] = x;
        this._rank[x] = 0;
    }

    /**
     * Merges the root nodes of `recessiveNode`
     * and `dominantNode` into one node, where
     * `dominantNode` will be the new root node.
     * Thus, the key/name of `dominantNode`'s
     * root node will be kept if union by rank is not violated.
     *
     * @param recessiveNode Node to merge, will omit its name.
     * @param dominantNode  Node to merge, will keep its name.
     */
    public union(recessiveNode: string, dominantNode: string): void {
        this.link(this.findSet(recessiveNode)!, this.findSet(dominantNode)!);
    }

    /**
     * Links `recessiveNode` and `dominantNode` into one node,
     * where `dominantNode` will be the new root node.
     * Thus, the key/name of `dominantNode` will be kept.
     *
     * But: Union by rank will always win over `dominantNode`.
     *
     * @param recessiveNode Node to link, will omit its name.
     * @param dominantNode  Node to link, will keep its name.
     */
    private link(recessiveNode: string, dominantNode: string): void {
        if (this._rank[recessiveNode]! > this._rank[dominantNode]!) {
            this._predecessor[dominantNode] = recessiveNode;
            return;
        }

        this._predecessor[recessiveNode] = dominantNode;

        if (this._rank[recessiveNode] === this._rank[dominantNode]) {
            this._rank[dominantNode] = this._rank[dominantNode]! + 1;
        }
    }

    /**
     * Finds the root node of a tree.
     * 
     * @param x     An element of a tree.
     * @returns     Root node of the tree containing `x`, or `undefined` if not found.
     */
    public findSet(x: string): string | undefined {
        // Path compression
        if (x !== this._predecessor[x]) {
            this._predecessor[x] = this.findSet(this._predecessor[x]!)!;
        }

        return this._predecessor[x];
    }
}