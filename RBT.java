public class RBT {
    private Node root;
    private int size;

    /* CONSTRUCTOR */
    public RBT() {
	this.root = null;
	this.size = 0;
    }

    /* PUBLIC METHODS */

    /***
     *insert a new key into tree
     *or update the count if the key already exists
     */
    public void put(int key) {
	if(root == null) {
	    root = new Node(key);
	    size++;
	}
	else 
	    root = put(key, root);
	root.setColor(Node.BLACK);
    }
    
    /***
     *get the count associated with the given key;
     *return 0 if key doesn't exist in the tree
     */
    public int get(int key) {
        Node x = root;
        while(x != null) {
            if(key == x.key()) 
            return x.count();
            if(key > x.key())
            x = x.right();
            else
            x = x.left();
        }
        return 0;
    }

    /***
     *get the color of a node
     ***/
    public String getColor(int key) {
        Node x = get(key, root);
        if(x == null)
            return null;
        if(x.isRed())
            return "RED";
        return "BLACK";
    }

    /***
     *return true if the tree
     *is empty and false 
     *otherwise
     */
    public boolean isEmpty() {
	    return root == null;
    }

    /***
     *return the number of Nodes
     *in the tree
     */
    public int size() {
	    return size;
    }

    /***
     *returns the height of the tree
     */
    public int height() {
	    return height(root);
    }

    /***
     *returns the height of node 
     *with given key in the tree;
     *return -1 if the key does
     *not exist in the tree
     */
    public int height(int key) {
	Node x = get(key, root);
	return height(x);
    }

    /***
     *returns true if the key is in the tree
     *and false otherwise
     ***/
    public boolean contains(int key) {
        Node x = get (key, root);
        if(x != null)
            return true;
        return false;
    }


     /***
     *return the depth of the node
     *with the given key in the tree;
     *return -1 if the key does not exist
     *in the tree
     ***/
    public int depth(int key) {
        return depth(root, key, 0);
    }

    private int depth(Node node, int key, int currDepth) {
        if (node == null) {
            return -1;
        }
        if (key == node.key()) {
            return currDepth;
        }
        else if (key < node.key()) {
            return depth(node.left(), key, currDepth+1);
        }
        else {
            return depth(node.right(), key, currDepth + 1);
        }
    }

    /***
     *return the size of the subtree 
     *rooted at the given key
     ***/
    public int size(int key) {
       Node curr = get(key, root);
       if (curr == null) {
        return -1;
       }
       return curr.N();
    }

     /***
     *return the minimum key
     ***/
    public int min() throws EmptyTreeException {
        if (root == null) {
            throw new EmptyTreeException();
        }
        Node minNode = findMinRecursive(root);
        return minNode.key();
    }

    private Node findMinRecursive(Node node) {
        if (node.left() == null) {
            return node;
        }
        return findMinRecursive(node.left());
    }
     /***
     *return the maximum key
     ***/
    public int max() throws EmptyTreeException {
        if (root == null) {
            throw new EmptyTreeException();
        }
        Node maxNode = findMaxRecursive(root);
        return maxNode.key();
    }

    private Node findMaxRecursive(Node node) {
        if (node.right() == null) {
            return node;
        }
        return findMaxRecursive(node.right());
    }

    /***
     *return the largest key
     *that is less than or equal
     *to the parameter
     ***/
    public int floor(int key) throws KeyDoesNotExistException {
        Node x = root;
        Node best = null;
        while (x != null) {
            if (key == x.key()) {
                return x.key();
            } else if (key > x.key()) {
                best = x;
                x = x.right();
            } else {
                x = x.left();
            }
        }
        if (best != null) {
            return best.key();
        }
        throw new KeyDoesNotExistException();
    }

     /***
     *return the smallest key
     *that is greater than or equal
     *to the parameter
     ***/
    public int ceil(int key) throws KeyDoesNotExistException {
        Node x = root;
        Node best = null;
        while (x != null) {
            if (key == x.key()) {
                return x.key();
            } else if (key < x.key()) {
                best = x;
                x = x.left();
            } else {
                x = x.right();
            }
        }
        if (best != null) {
            return best.key();
        }
        throw new KeyDoesNotExistException();
    }

    /***
     *return the number of keys
     *that are less than the parameter
     ***/
    public int rank(int key) {
        return rank(key, root);
    }

    private int rank(int key, Node root) {
        if (root == null) {
            return -1;
        }
        if (root.key() == key) {
        if (root.left() != null) {
            return root.left().N();
        }
        else {
            return 0;
        }
        }
        if (key > root.key()) {
            if (root.left() != null) {
                return root.left().N() + 1 + rank(key, root.right());
            }
            else {
                return 1 + rank(key, root.right());
            }
        }
        else if (key < root.key()) {
            return rank(key, root.left());
        }
        return -1;
    }

    /***
     *return the key at the given rank
     ***/
    public int select(int rank) throws NoSuchRankException {
        if (rank < 0 || rank >= size)  {
            throw new NoSuchRankException();
        }
        return select(rank, root).key();
    }

    private Node select(int rank, Node x) {
        if (x == null) {
            return null;
        }

        int left = 0;
        if (x.left() != null) {
            left = x.left().N();
        }
        if (left>rank) {
            return select(rank, x.left());
        }
        else if (left<rank) {
            return select(rank-left-1, x.right());
        }
        else {
            return x;
        }
    }

    /***
     *return the number of keys in [lo...hi]
     ***/
    public int size(int lo, int hi) {
        return rank(hi) - rank(lo) + 1;
    }

    /***
     *return a String representation of the tree
     *level by level
     ***/
    public String toString(){

        if (root == null) {
            return "[]";
        }
    String finalString = "";
    Queue<Node> queue = new Queue<>();
    queue.enqueue(root);

    while (!queue.isEmpty()) {
        try {
            Node curr = queue.dequeue();
            finalString += curr.toString() + " ";

            if (curr.left() != null) {
                queue.enqueue(curr.left());
            }

            if (curr.right() != null) {
                queue.enqueue(curr.right());
            }
        } catch (EmptyQueueException e) {
        }
    }
    finalString = finalString.trim();
    return finalString;
}

    /* PRIVATE METHODS */

    /***
     *return the height of x
     *or -1 if x is null
     */
    private int height(Node x) {
	if(x == null)
	    return -1;
	return x.height();
    }

    /***
     *recursive helper method for insert
     */
    private Node put(int key, Node x) {
        if(key == x.key()) {
            x.incCount();
        } else if (key > x.key()) {
            if(x.right() == null) {
            x.setRight(new Node(key));
            size++;
            } else {
            x.setRight(put(key, x.right()));
            }
        } else {
            if(x.left() == null) {
            x.setLeft(new Node(key));
            size++;
            } else {
            x.setLeft(put(key, x.left()));
            }
        }
        x = balance(x);
        x.setHeight(Math.max(height(x.left()), height(x.right())) + 1);
        x.setN(N(x.left()) + N(x.right()) + 1);
        return x;
    }

    private int N(Node x){
        if (x == null)
            return 0;
        return x.N();
    }

    

    /***
     *recursive method for getting Node 
     *with given key
     */
    private Node get(int key, Node x) {
	if(x == null)
	    return null;
	if(key == x.key())
	    return x;
	else if(key > x.key())
	    return get(key, x.right());
	return get(key, x.left());
    }

    /***
     *rotate left
     ***/
    private Node rotateLeft(Node h) {
	Node x = h.right();
	h.setRight(x.left());
	x.setLeft(h);
	x.setColor(h.color());
	h.setColor(Node.RED);
	x.setHeight(h.height());
	h.setHeight(1 + Math.max(height(h.left()), height(h.right())));

    int N = 1;
    if (h.left() != null) {
        N += h.left().N();
    }
    if (h.right() != null) {
        N += h.right().N();
    }
    h.setN(N);

	return x;
    }

    /***
     *rotate right
     ***/
    private Node rotateRight(Node h) {
	Node x = h.left();
	h.setLeft(x.right());
	x.setRight(h);
	x.setColor(h.color());
	h.setColor(Node.RED);
	x.setHeight(h.height());
	h.setHeight(1 + Math.max(height(h.left()), height(h.right())));

    int N = 1;
    if (h.left() != null) {
        N += h.left().N();
    }
    if (h.right() != null) {
        N += h.right().N();
    }
    h.setN(N);
	return x;
    }

    /***
     *color flip
     ***/
    private void colorFlip(Node h) {
	h.setColor(Node.RED);
	h.left().setColor(Node.BLACK);
	h.right().setColor(Node.BLACK);
	if(h == root)
	    h.setColor(Node.BLACK);
    }

    /***
     *balance
     ***/
    private Node balance(Node h) {
	if(h.right() != null && h.right().isRed() && h.right().right() != null && h.right().right().isRed()) {
	    h = rotateLeft(h);
	    colorFlip(h);
	}
	else if(h.left() != null && h.right() != null && h.left().isRed() && h.right().isRed())
	    colorFlip(h);
	else if(h.left() != null && h.left().isRed())
	    h = rotateRight(h);
	return h;
    }
}    
