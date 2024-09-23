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
        if (this.root == null) {
            System.out.println("This tree is empty.");
            return;
        }
        checkOrder2(this.root);
    }

    // Starts traversing down tree
    public void checkOrder2(Node node) {
        if (node == null) {
            return;
        }
        checkOrder2(node.left);
        System.out.println(node.hawaiian[0] + " ");
        checkOrder2(node.right);
    }

    public void leftRotate (Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
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
            this.root = y;
        }
        else if (x == x.parent.right) {
            x.parent.right = y;
        }
        else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void insert(String hs, String hm, String es, String em) { //hs=hawaiian saying, hm=hawaiian meaning. es= eng saying, em=eng meaning
        if (this.root == null) {
            this.root = new Node(hs, hm, es, em, Color.BLACK);
         }
         else {
            Node z = new Node(hs, hm, es, em, Color.BLACK);
            insert2(z);
         }
    } 

    public void insert2(Node z) {
        Node x = this.root;
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
            this.root = z;
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
    }

    public static void main(String[] args) throws Exception {
        RBTree tree = new RBTree();

        tree.insert("BBB", "XXX", "XXX", "XXX");
        tree.insert("GGG", "XXX", "XXX", "XXX");
        tree.insert("ABA", "XXX", "XXX", "XXX");
        tree.insert("BBA", "XXX", "XXX", "XXX");
        tree.insert("CCC", "XXX", "XXX", "XXX");
        tree.insert("YYY", "XXX", "XXX", "XXX");
        tree.insert("AAA", "XXX", "XXX", "XXX");
        tree.checkOrder();
    }
}
