enum Color {
    RED,
    BLACK
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

public class App {
    public static void main(String[] args) throws Exception {
        String hs = "A'ea'e mōhala i luna o ke kukui.";
        String hm = "Something";
        String es = "Whiteness unfolds on the kukui trees.";
        String em = "Used in reference to a person who grays, comparing him to a blooming kukui tree laden with white flowers.";
        Node saying = new Node(hs, hm, es, em, Color.BLACK);
        System.out.println(saying.hawaiian[0]);
    }
}
