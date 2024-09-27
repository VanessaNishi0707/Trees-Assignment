import java.util.ArrayList;

enum Color {
    RED, BLACK
}

public class RBTree {
    public Node root;

    public RBTree() {
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

    public void checkOrder() {
        if (root == null) {
            System.out.println("This tree is empty.");
            return;
        }
        checkOrder2(root);
    }

    public void checkOrder2(Node node) {
        if (node == null) {
            return;
        }
        checkOrder2(node.left);
        System.out.println(node.hawaiian[0] + " " + (node.color == Color.RED ? "RED" : "BLACK"));
        checkOrder2(node.right);
    }

    public void member(String saying) {
        if (root == null) {
            System.out.println("This tree is empty.");
            return;
        }
        if (member2(root, saying) == null) {
            System.out.println("The saying: " + saying + " is NOT a member!");
        } else {
            System.out.println("The saying: " + saying + " is a member!");
        }
    }

    public Node member2(Node node, String saying) {
        if (node == null) {
            return null;
        }
        if (node.hawaiian[0].equals(saying)) {
            return node;
        }
        return (node.hawaiian[0].compareTo(saying) < 0) ?
            member2(node.right, saying) : member2(node.left, saying);
    }

    public void first() {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        System.out.println("Here's the first saying: ");
        System.out.println("Hawaiian saying: " + current.hawaiian[0]);
        System.out.println("Hawaiian meaning: " + current.hawaiian[1]);
        System.out.println("English saying: " + current.english[0]);
        System.out.println("English meaning: " + current.english[1] + "\n");
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
            return "Saying not found";
        }

        if (node.left != null) {
            Node temp = node.left;
            while (temp.right != null) {
                temp = temp.right;
            }
            return String.join(" ", temp.hawaiian);
        }

        Node temp = node.parent;
        while (temp != null && node == temp.left) {
            node = temp;
            temp = temp.parent;
        }
        return (temp == null) ? "No predecessor" : String.join(" ", temp.hawaiian);
    }

    public String successor(String saying) {
        Node node = findNode(saying, root);
        if (node == null) {
            return "Saying not found";
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
            System.out.println("No Hawaiian sayings contain this word!");
        } else {
            System.out.println("Here are the Hawaiian sayings that contain \"" + word + "\": " + String.join(", ", sayings));
        }
    }

    private ArrayList<String> findWithWord(Node node, String word) {
        ArrayList<String> results = new ArrayList<>();
        if (node == null) {
            return results;
        }
        if (node.hawaiian[0].contains(word)) {
            results.add(node.hawaiian[0]);
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

    public void insert(String hs, String hm, String es, String em) {
        Node z = new Node(hs, hm, es, em, Color.RED);
        insert2(z);
        insertFix(z);
    }

    public void insert2(Node z) {
        Node x = root;
        Node y = null;
        while (x != null) {
            y = x;
            if (z.hawaiian[0].compareTo(x.hawaiian[0]) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == null) {
            root = z;
        } else if (z.hawaiian[0].compareTo(y.hawaiian[0]) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = null;
        z.right = null;
    }

    public void insertFix(Node z) {
        while (z.parent != null && z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y != null && y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;
                if (y != null && y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
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

    public void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
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
    

      
        tree.checkOrder();
        tree.member("Aloha");
        tree.first();
        tree.last();
        System.out.println(tree.predecessor("Mahalo"));
        System.out.println(tree.successor("Aloha"));
        tree.withWord("Aloha");
        tree.meHua("Aloha");
    }
}
