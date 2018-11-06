public class Node {

  /** Node-class representing the nodes in a Trie data structure. */
  private Node[] children;

  private Node parent;
  private char ch;
  private Integer points;
  private static final int ALPHABET_LENGTH = 26;

  private int toLexIndex(char c) {
    return Character.toLowerCase(c) - 'a';
  }

  /** Constructor that initializes an array of child-Nodes. */
  public Node() {
    children = new Node[ALPHABET_LENGTH];
  }

  /**
   * Constructor that initializes an array of child-Nodes. Gives the Node a parent Node. Gives the
   * Node a identifying char.
   *
   * @param ch char identifying the Node among its sibling-Nodes
   * @param parent Node being set as the parent-Node
   */
  public Node(char ch, Node parent) {
    children = new Node[ALPHABET_LENGTH];
    this.parent = parent;
    this.ch = ch;

    if (parent != null) {
      parent.setChild(this.ch, this);
    }
  }

  private void setChild(char ch, Node child) {
    children[toLexIndex(ch)] = child;
  }

  /**
   * Returns the child-Node corresponding to a ch-value.
   *
   * @param ch the char that indicates the Nodes ch-value and the Nodes position among all the
   *     children-Nodes
   * @return the Node corresponding to the ch-value
   */
  public Node getChild(char ch) {

    return children[toLexIndex(ch)];
  }

  /**
   * Finds the Node at the end of a given key.
   *
   * @param key the String representing the key that identifies the Node
   * @return the Node at the end of the key null if no such Node could be found
   */
  public Node find(String key) {

    if (key.length() == 0) {
      return this;
    }

    char ch = key.charAt(0);
    String newkey = key.substring(1);

    if (children[toLexIndex(ch)] == null) {
      return null;
    }
    return children[toLexIndex(ch)].find(newkey);
  }

  private int numberOfChildren() {

    int count = 0;

    for (int i = 0; i < children.length; i++) {
      if (children[i] != null) {
        count++;
      }
    }

    return count;
  }

  /**
   * Deletes unnecessary Nodes above the Node the method is called from.
   *
   * @param deleteIfEndNode a boolean that indicates, if a Node that is at the end of a key, should
   *     be deleted
   *
   *     <p>typically true when first calling the method, since a complete entry is to be cleaned up
   *
   *     <p>false during recursive calls, since entries corresponding to a substring of the original
   *     key are not to be deleted
   */
  private void cleanupHelp(boolean deleteIfEndNode) {

    boolean delete = (deleteIfEndNode || this.points == null) && !hasChildren();

    if (delete) {
      if (parent != null) {
        this.parent.children[toLexIndex(this.ch)] = null;
      }
      if (parent != null) {
        parent.cleanupHelp(false);
      }
      this.points = null;
      parent = null;
    }
  }

  private void cleanup() {
    cleanupHelp(true);
  }

  /**
   * Deletes the Node the Method got called from. Deletes the Nodes above and below that are now
   * unnecessary
   */
  public void deleteMe() {

    /*  if the Node has children, it is not an end Node
     *  therefore the Nodes below it correspond to a different key
     *  and should not be deleted
     *
     *  To "delete" the Node its point-value gets set to null
     */
    if (hasChildren()) {
      this.points = null;
      return;
    }

    // Deletes all Nodes above the original Node that have become obsolete
    this.cleanup();
    this.parent = null;
    this.points = null;
  }

  /**
   * Checks, if a Node has children-Nodes or not.
   *
   * @return a boolean, that indicates if the Node has children-Notes or not
   */
  private boolean hasChildren() {

    boolean hasChildren = false;

    if (children != null) {
      for (Node n : children) {
        if (n != null) {
          hasChildren = true;
        }
      }
    }

    return hasChildren;
  }

  private String generateChildStrings(String str) {

    StringBuilder builder = new StringBuilder(str);

    if (children != null) {
      for (Node n : children) {
        if (n != null) {
          builder.append(n.toString());
        }
      }
    }

    return builder.toString();
  }

  private String addSquareBrackets(String str) {
    if (points != null) {
      str = str + "[" + points + "]";
    }

    return str;
  }

  private String addOpenBracket(String str) {
    boolean hasChildren = hasChildren();

    if (hasChildren) {
      str = str + "(";
    }

    return str;
  }

  private String addClosedBracket(String str) {
    boolean hasChildren = hasChildren();

    if (hasChildren) {
      str = str + ")";
    }

    return str;
  }

  /**
   * Returns a String representing the Node and all its children-Nodes. The strings format
   * represents a Trie data structure.
   *
   * @return a String representing the Node and all its children-Nodes
   */
  public String toString() {

    String out = String.valueOf(this.ch);
    out = addSquareBrackets(out);
    out = addOpenBracket(out);
    out = generateChildStrings(out);
    out = addClosedBracket(out);

    return out;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  public Integer getPoints() {
    return points;
  }
}