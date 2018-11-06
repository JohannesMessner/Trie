
/** Trie data Structure. Can map Strings (e.g. names) to Integers (e.g. points). */
public class Trie {

  private Node root;

  /** Constructor that creates a root-Node for the Trie. */
  public Trie() {
    root = new Node('+', null);
  }

  /**
   * Generates the needed Nodes when adding an entry.
   *
   * @param key the String representing the key to be added
   */
  private void generateNodes(String key) {
    char ch;
    String newKey = key;
    Node temp = root;

    for (int i = 0; i < key.length(); i++) {
      ch = newKey.charAt(0);
      newKey = newKey.substring(1);

      if (temp.getChild(ch) != null) {
        temp = temp.getChild(ch);
      } else {
        new Node(ch, temp);
        temp = temp.getChild(ch);
      }
    }
  }

  /**
   * Maps a point-value to a key and adds them to the data structure. Returns false if the key is
   * already present in the Trie. Returns true if key and points were added.
   *
   * @param key the String representing the key to be added
   * @param points the Integer of points-value corresponding to the key
   * @return boolean that indicates, if key and points were actually added
   */
  public boolean add(String key, Integer points) {

    Node target = root.find(key);
    if (target != null && target.getPoints() != null) {
      return false;
    }

    generateNodes(key);
    root.find(key).setPoints(points);

    return true;
  }

  /**
   * Deletes an entry (key an corresponding points) from the Trie. Returns false if the key could
   * not be found. Returns true if the entry corresponding to the key was deleted.
   *
   * @param key the String representing the key to be deleted
   * @return boolean that indicates, if entry was actually deleted
   */
  public boolean delete(String key) {

    Node target = root.find(key);

    if (target == null) {
      return false;
    }
    if (target.getPoints() == null) {
      return false;
    }

    target.deleteMe();
    return true;
  }

  /**
   * Changes the point-value of an existing entry. Returns false if the key could not be found.
   * Returns true if the points-value corresponding to the key was changed.
   *
   * @param key the String representing the key whose corresponding points-value is to be changed
   * @param points the Integer the entries points-value will be changed to
   * @return boolean that indicates, if points-value was actually changed
   */
  public boolean change(String key, Integer points) {

    Node target = root.find(key);
    if (target == null) {
      return false;
    }
    if (target.getPoints() != null) {
      target.setPoints(points);
      return true;
    }
    return false;
  }

  /**
   * Returns the points-value corresponding to a key in the data structure.
   *
   * @param key the String representing the key whose corresponding points-value is being returned
   * @return the Integer with the corresponding points-value
   */
  public Integer points(String key) {

    // Node n = root.find(key);
    if (root.find(key) == null) {
      return null;
    }
    return root.find(key).getPoints();
  }

  /**
   * Returns a String representing the Trie data structure ans its entries.
   *
   * @return a String representing the Trie data structure ans its entries
   */
  public String toString() {
    return root.toString();
  }
}