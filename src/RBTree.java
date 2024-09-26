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

    // Checks if nodes have been inserted alphabetically within the tree. 
    // Does not check if red-black properties are maintained.
    public void checkOrder() { // Checks root
        if (root == null) {
            System.out.println("This tree is empty.");
            return;
        }
        checkOrder2(root);
    }

    public void checkOrder2(Node node) { // Traverses down tree recurisvely
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

    // MEMBER: Prints result from member2
    public void member(String saying) {
        if (root == null) {
            System.out.println("This tree is empty.");
            return;
        }
        if (member2(root, saying) == null) {
            System.out.println("The saying: " + saying + " is NOT a member!");
        }
        else if (member2(root, saying) != null) {
            System.out.println("The saying: " + saying + " is a member!");
        }
    }

    // Searches tree for match using divide-and-conquer, returns node if a match is found
    public Node member2(Node node, String saying) {
        if ((node == null) || (node.hawaiian[0] == saying)) {
            return node;
        }
        if (node.hawaiian[0].compareTo(saying) < 0) { // if node.hawaiian[0] comes before saying alphabetically
            return member2(node.right, saying); // search left side
        }

        return member2(node.left, saying); // else, search right side
    }

    // FIRST: Finds leftmost node which in this case contains the saying that comes first alphabetically
    // Traverses down the leftmost path until it reaches a node who's left child is null
    public void first() { // returns first member of tree
        Node x = root;
        Node y = null;

        if (root == null) {
            System.out.println("The tree is empty.");;
        }
        else {
            while (x != null) {
                y = x;
                x = x.left;
            }
            System.out.println("Here's the first saying: ");
            System.out.println("Hawaiian saying: " + y.hawaiian[0]);
            System.out.println("Hawaiian meaning: " + y.hawaiian[1]);
            System.out.println("English saying: " + y.english[0]);
            System.out.println("English meaning: " + y.english[1] + "\n");
        }
    }

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
        System.out.println("Here's the last saying: ");
        System.out.println("Hawaiian saying: " + current.hawaiian[0]);
        System.out.println("Hawaiian meaning: " + current.hawaiian[1]);
        System.out.println("English saying: " + current.english[0]);
        System.out.println("English meaning: " + current.english[1] + "\n");
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

    // Auxillary functions for insert
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

    // INSERT: inserts a node into the proper position and maintains red-black properties
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

    // Traverses down a simple path to find the proper place for the new node
    // Halves the search area with each iteration
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

        while (z.parent.color == Color.RED) {
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
            if (z.parent == null) {
                return;
            }
        }
        
        root.color = Color.BLACK;
    }

    // MEHUA
    // Prints the strings returned by Mehua
    public void MeHua(String word) {
        ArrayList<String> sayingsWithWord = new ArrayList<>();
        int i;
        
        MeHuaHelper(root, word, sayingsWithWord);

        if (sayingsWithWord.size() == 0) {
            System.out.println("No sayings contain this word!");
        }
        else {
            System.out.println("Here are the sayings that contain " + word + ": ");
            for (i = 0; i < sayingsWithWord.size(); i++) {
                System.out.println(sayingsWithWord.get(i));
            }
        }
        System.out.println("\n");
    }

    // Returns arraylist of all sayings that contain the given word
    public void MeHuaHelper(Node node, String word, ArrayList<String> sayingsWithWord) {
        if (node == null) {
            return;
        }
        if (node.hawaiian[0].contains(word)) {
            sayingsWithWord.add(node.hawaiian[0]);
        }
        MeHuaHelper(node.left, word, sayingsWithWord);
        MeHuaHelper(node.right, word, sayingsWithWord);
    }

    public static void main(String[] args) throws Exception {
        RBTree tree = new RBTree();

        tree.insert("E kaupē aku nō i ka hoe a kō mai.", "XXX", "Put forward the paddle and draw it back", "Go on with the task that is started and finish it.");
        tree.insert("Hoʻokahi nō lā o ka malihini.", "XXX", "A stranger only for a day.", "After the first day as a guest, one must help with the work.");
        tree.insert("Kūlia i ka nuʻu.", "XXX", "Strive to reach the highest.", "Strive to do your best.");
        tree.insert("Ma ka hana ka ʻike.", "XXX", "In working one learns.", "Knowledge can be acquired by doing.");
        tree.insert("Na ka ʻeleu miki.", "XXX", "(The prize) goes to the quick one", "Similar to the saying, “The early bird gets the worm.”");
        tree.insert("ʻAʻa i ka hula, waiho i ka hilahila i ka hale.", "XXX", "When one wants to dance the hula, bashfulness should be left at home.", "Don’t be shy. Participate! This does not just pertain to hula. This can be used in all situations\n" + //
                        "when one is apprehensive in participating in an activity");
        tree.insert("ʻIke i ke au nui me ke au iki.", "XXX", "Knows the big currents and the little currents.", "Is very well versed");
        tree.insert("Ua hala ʻē ka Puʻulena.", "XXX", "The Puʻulena wind of Puna has passed.", "Too late! Your chance has passed. Be quick next time.");

        tree.checkOrder();
        tree.first(); 
        tree.last();
        tree.withWord("the");
        tree.MeHua("ka");

        tree.insert("I maikaʻi ke kalo i ka ʻohā.", "XXX", "The goodness of the taro is judged by the young plant it produces", "Parents are often judged by the behavior of their children.");
        tree.checkOrder();
    }
}
