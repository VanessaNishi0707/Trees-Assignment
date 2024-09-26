import java.util.ArrayList;

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

    //-------------------------------------added code start----------------------//
    // LAST
public void last() {
    if (root == null) {
        System.out.println("The tree is empty.");
        return;
    }
    Node current = root;
    while (current.right != null) {
        current = current.right;
    }
    System.out.println("Here's the last saying: " + current.hawaiian[0]);
}

// PREDECESSOR
public Node predecessor(Node node) {
    if (node == null) return null;
    if (node.left != null) {
        Node current = node.left;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
    Node parent = node.parent;
    while (parent != null && node == parent.left) {
        node = parent;
        parent = parent.parent;
    }
    return parent;
}

// SUCCESSOR
public Node successor(Node node) {
    if (node == null) return null;
    if (node.right != null) {
        Node current = node.right;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    Node parent = node.parent;
    while (parent != null && node == parent.right) {
        node = parent;
        parent = parent.parent;
    }
    return parent;
}

// WITH WORD
public void withWord(String word) {
    ArrayList<String> sayingsWithWord = findWithWord(root, word);
    if (sayingsWithWord.isEmpty()) {
        System.out.println("No sayings contain this word!");
    } else {
        System.out.println("Here are the sayings that contain " + word + ": " + sayingsWithWord);
    }
}

// Helper method to find sayings containing the specified word
private ArrayList<String> findWithWord(Node node, String word) {
    ArrayList<String> sayingsWithWord = new ArrayList<>();
    if (node != null) {
        sayingsWithWord.addAll(findWithWord(node.left, word));
        if (node.hawaiian[0].contains(word)) {
            sayingsWithWord.add(node.hawaiian[0]);
        }
        sayingsWithWord.addAll(findWithWord(node.right, word));
    }
    return sayingsWithWord;
}
//-----------------------------------added code end-------------------------//

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
        insertFix(z);
    }

    // Restores red-black properties
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

    // Prints strings returned by Mehua
    public void printMeHua(String word) {
        ArrayList<String> sayingsWithWord = MeHua(root, word);

        if (sayingsWithWord.size() == 0) {
            System.out.println("No sayings contain this word!");
        }
        else {
            int i;
            System.out.println("Here are the sayings that contain " + word + ": ");
            for (i = 0; i < sayingsWithWord.size(); i++) {
                System.out.println(sayingsWithWord.get(i));
            }
        }
    }

    // Returns array of all sayings that contain word
    //WORK IN PROGRESS DOESNT WORK YET
    public static ArrayList<String> MeHua(Node node, String word) {
        ArrayList<String> sayingsWithWord = new ArrayList<String>();
        if (node == null) {
        }
        MeHua(node.left, word);
        if (node.hawaiian[0].contains(word)) {
            sayingsWithWord.add(node.hawaiian[0]);
        }
        MeHua(node.right, word);

        return sayingsWithWord;
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
        tree.insert("WEWF", "XXX", "XXX", "XXX");
        tree.insert("QCD", "XXX", "XXX", "XXX");
        tree.insert("DFD", "XXX", "XXX", "XXX");
        tree.insert("AAD", "XXX", "XXX", "XXX");
        tree.insert("GRF", "XXX", "XXX", "XXX");
        tree.insert("QEEF", "XXX", "XXX", "XXX");
        tree.insert("AFE", "XXX", "XXX", "XXX");
        
        tree.checkOrder();
        tree.first();
    }
}
