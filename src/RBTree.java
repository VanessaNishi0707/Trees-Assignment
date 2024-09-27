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


    public String predecessor(String saying) {
        Node node = findNode(saying, root);
        if (node == null) {
            return "Saying not found"; // or another appropriate message
        }

        if (node.left != null) {
            Node temp = node.left;
            while (temp.right != null) {
                temp = temp.right;
            }
            return String.join(" ", temp.hawaiian); // Return the Hawaiian saying as a string
        }

        Node temp = node.parent;
        while (temp != null && node == temp.left) {
            node = temp;
            temp = temp.parent;
        }
        return (temp == null) ? "No predecessor" : String.join(" ", temp.hawaiian); // Return a message if no predecessor
    }   

    public String successor(String saying) {
        Node node = findNode(saying, root);
        if (node == null) {
            return "Saying not found"; // or another appropriate message
        }
        if (node.right != null) {
            Node temp = node.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            return String.join(" ", temp.hawaiian);
        }
        Node temp = node.parent;
        while (temp != null && node == temp.right) {
            node = temp;
            temp = temp.parent;
        }
        return (temp == null) ? "No successor" : String.join(" ", temp.hawaiian);
    }

    private Node findNode(String saying, Node root) {
        if (root == null) {
            return null;
        }
        int comparison = saying.compareTo(root.hawaiian[0]);
        if (comparison < 0) {
            return findNode(saying, root.left);
        } else if (comparison > 0) {
            return findNode(saying, root.right);
        } else {
            return root;
        }
    }

    public void withWord(String word) {
        ArrayList<String> sayings = findWithWord(root, word);
        if (sayings.isEmpty()) {
            System.out.println("No English sayings contain this word!");
        } else {
            System.out.println("Here are the English sayings that contain \"" + word + "\": " + String.join(", ", sayings));
        }
        System.out.println("");
    }


    private ArrayList<String> findWithWord(Node node, String word) {
        ArrayList<String> results = new ArrayList<>();
        if (node == null) {
            return results;
        }
        if (node.english[0].contains(word)) {
            results.add(node.english[0]);
        }
        results.addAll(findWithWord(node.left, word));
        results.addAll(findWithWord(node.right, word));
        return results;
    }

    public void meHua(String word) {
        ArrayList<String> sayingsWithWord = new ArrayList<>();
        meHuaHelper(root, word, sayingsWithWord);
        if (sayingsWithWord.isEmpty()) {
            System.out.println("No Hawaiian sayings contain this word!");
        } else {
            System.out.println("Here are the Hawaiian sayings that contain " + word + ": ");
            for (String saying : sayingsWithWord) {
                System.out.println(saying);
            }
        }
        System.out.println("");
    }

    public void meHuaHelper(Node node, String word, ArrayList<String> sayingsWithWord) {
        if (node == null) {
            return;
        }
        if (node.hawaiian[0].contains(word)) {
            sayingsWithWord.add(node.hawaiian[0]);
        }
        meHuaHelper(node.left, word, sayingsWithWord);
        meHuaHelper(node.right, word, sayingsWithWord);
    }

    public void leftRotate(Node x) {
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
            System.out.println("No Hawaiian sayings contain this word!");
        }
        else {
            System.out.println("Here are the Hawaiian sayings that contain " + word + ": ");
            for (i = 0; i < sayingsWithWord.size(); i++) {
                System.out.println(sayingsWithWord.get(i));
            }
        }
        System.out.println("");
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
    
        // Insert sayings into the tree
        tree.insert("E kaupē aku nō i ka hoe a kō mai.", "XXX", "Put forward the paddle and draw it back", "Go on with the task that is started and finish it.");
        tree.insert("Hoʻokahi nō lā o ka malihini.", "XXX", "A stranger only for a day.", "After the first day as a guest, one must help with the work.");
        tree.insert("Kūlia i ka nuʻu.", "XXX", "Strive to reach the highest.", "Strive to do your best.");
        tree.insert("Ma ka hana ka ʻike.", "XXX", "In working one learns.", "Knowledge can be acquired by doing.");
        tree.insert("Na ka ʻeleu miki.", "XXX", "(The prize) goes to the quick one", "Similar to the saying, “The early bird gets the worm.”");
        tree.insert("ʻAʻa i ka hula, waiho i ka hilahila i ka hale.", "XXX", "When one wants to dance the hula, bashfulness should be left at home.", "Don’t be shy. Participate!");
        tree.insert("ʻIke i ke au nui me ke au iki.", "XXX", "Knows the big currents and the little currents.", "Is very well versed");
        tree.insert("Ua hala ʻē ka Puʻulena.", "XXX", "The Puʻulena wind of Puna has passed.", "Too late! Your chance has passed. Be quick next time.");
    
        // Check the order of the tree
        tree.checkOrder(); // This should validate the structure of the tree

        tree.member("Kūlia i ka nuʻu.");
        tree.first();
        tree.last();
        System.out.println(tree.predecessor("Hoʻokahi nō lā o ka malihini."));
        System.out.println(tree.successor("Ma ka hana ka ʻike."));
        tree.withWord("the");
        tree.meHua("i");

        tree.insert("I maikaʻi ke kalo i ka ʻohā.", "XXX", "The goodness of the taro is judged by the young plant it produces", "Parents are often judged by the behavior of their children."); 
        tree.checkOrder();
    }
}
