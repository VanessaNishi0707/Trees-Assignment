enum Color {
    RED,
    BLACK
}

public class RBTree {
    public Node root; 

    public RBTree() 
    {
        root = null;
    }

    class Node {
        String[] hawaiian = new String[2];
        String[] english = new String[2];
    
        Color color;
    
        Node left;
        Node right;
        Node parent;
    
        Node(String hSaying, String hMeaning, String eSaying, String eMeaning, Color color) {
            hawaiian[0] = hSaying;
            hawaiian[1] = hMeaning;
            english[0] = eSaying;
            english[1] = eMeaning;
            this.color = color;
        }
    }

    // Checks root
    public void checkOrder() {
        if (root == null) {
            System.out.println("This tree is empty.");
            return;
        }
        checkOrder2(root);
    }

    // Starts traversing down tree
    public void checkOrder2(Node node) {
        if (node == null) {
            return;
        }
        checkOrder2(node.left);
        if (node.color == Color.RED) {
            System.out.println(node.hawaiian[0] + " " + "RED");
        }
        else if (node.color == Color.BLACK) {
            System.out.println(node.hawaiian[0] + " " + "BLACK");
        }
        checkOrder2(node.right);
    }

    // MEMBER 
    // Check if node is a member in tree
    public void member(String saying) {
        if (root == null) {
            System.out.println("This tree is empty.");
            return;
        }
        member2(root, saying);
    }

    // Starts traversing down tree
    public void member2(Node node, String saying) {
        if (node == null) {
            return;
        }
        member2(node.left, saying);
        if (node.hawaiian[0] == saying) {
            System.out.println("This saying is a member!");
            return;
        }
        member2(node.right, saying);
    }

    // FIRST 
    public void first() { // returns first member of tree
        if (root == null) {
            System.out.println("The tree is empty.");;
        }
        else {
            System.out.println("Here's the first saying: " + root.hawaiian[0]);
        }
    }

    public void leftRotate (Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        }
        else if (x == x.parent.left) {
            x.parent.left = y;
        }
        else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate (Node x) {
        Node y = x.left;
        x.left = y.right; 
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        }
        else if (x == x.parent.right) { //if x is right child
            x.parent.right = y;
        }
        else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    // INSERT
    public void insert(String hs, String hm, String es, String em) { //hs=hawaiian saying, hm=hawaiian meaning. es= eng saying, em=eng meaning
        if (root == null) {
            root = new Node(hs, hm, es, em, Color.BLACK);
         }
         else {
            Node z = new Node(hs, hm, es, em, Color.BLACK);
            insert2(z);
         }
         return;
    } 

    public void insert2(Node z) {
        Node x = root;
        Node y = null;
        while (x != null) {
            y = x;
            if (z.hawaiian[0].compareTo(x.hawaiian[0]) < 0) {
                x = x.left;
            }
            else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == null) {
            root = z;
        }
        else if (z.hawaiian[0].compareTo(y.hawaiian[0]) < 0){
            y.left = z;
        }
        else {
            y.right = z;

        }
        z.left = null;
        z.right = null;
        z.color = Color.RED;
        insertFix(z); // causes error when active
    }

    // Not working idk why, list is in order but red black property is not kept
    public void insertFix(Node z) {
        Node y;

        if (z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) { // is z parent a left child
                y = z.parent.parent.right; // y is z uncle
                if (y == null) { // z uncle is NULL
                    if (z == z.parent.right) { // if z is a right child
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
                else if (y.color == Color.BLACK) { // z uncle is black
                    if (z == z.parent.right) { // if z is a right child
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
                
                else if (y.color == Color.RED) { // are z parent & uncle both red? 
                    z.parent.color = Color.BLACK; 
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } 
            } 
            else { // z parent is right child
                y = z.parent.parent.left;
                if (y == null) {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
                else if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                } 
            } 
        }
        
        root.color = Color.BLACK;
    }

    public static void main(String[] args) throws Exception {
        RBTree tree = new RBTree();

        tree.insert("MMM", "XXX", "XXX", "XXX");
        tree.insert("EEE", "XXX", "XXX", "XXX");
        tree.insert("RRR", "XXX", "XXX", "XXX");
        tree.insert("CCC", "XXX", "XXX", "XXX");
        tree.insert("TTT", "XXX", "XXX", "XXX");
        tree.insert("DDD", "XXX", "XXX", "XXX");
        tree.insert("WWW", "XXX", "XXX", "XXX");
        
        tree.checkOrder();
    }
}
