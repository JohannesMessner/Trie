/** Shell-class handling I/O for a Trie data structure. */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public final class Shell {

  private static final String[] COMMAND_REG_EX = new String[8];
  private static final int NEW = 0;
  private static final int ADD = 1;
  private static final int CHANGE = 2;
  private static final int DELETE = 3;
  private static final int POINTS = 4;
  private static final int TRIE = 5;
  private static final int HELP = 6;
  private static final int QUIT = 7;
  private static final int NO_COMMAND = -1;
  private static final int INVALID_COMMAND = -2;
  private static final String NAMES_NUMBERS_ERROR = "Error! Names cannot contain numbers.";
  private static final String ONLY_LOWERCASE_ERROR = "Error! Only use lowercase letters.";
  private static final String NO_NAME_ERROR = "Error! No Name.";
  private static final String NO_POINTS_ERROR = "Error! No points.";
  private static final String NO_COMMAND_ERROR = "Error! No command.";
  private static final String INVALID_COMMAND_ERROR = "Error! Invalid command.";
  private static final String HELP_MESSAGE =
      "\nYou can use the following commands to interact with this application:\n\n"
          + "'add <name> <points>':\t\t Lets you add a new entry, "
          + "consisting of a name and its corresponding point-value\n"
          + "'delete <name>':\t\t\t Lets you delete an entry from the data structure\n"
          + "'change <name> <points>':\t Lets you change an entry's point-value to a new value\n"
          + "'points <name>':\t\t\t Lets you request the point value corresponding to a name\n"
          + "'trie':\t\t\t\t\t\t Gives you a visual representation of "
          + "the data structure and all its entries\n"
          + "'new':\t\t\t\t\t\t Deletes the data structure "
          + "and creates a new, empty one to work with\n"
          + "'quit':\t\t\t\t\t\t Quits the application\n"
          + "'help':\t\t\t\t\t\t Prints this message\n\n"
          + "You cane also call all of these commands by using "
          + "a prefix of the corresponding command.\n";

  private Shell() {}

  /**
   * Initializes the Array containing the regex'es to match the commands. The regex'es will match to
   * the commands as well as their prefixes.
   */
  private static void initCommandRegEx() {
    COMMAND_REG_EX[NEW] = "n(e(w)?)?";
    COMMAND_REG_EX[ADD] = "a(d(d)?)?";
    COMMAND_REG_EX[CHANGE] = "c(h(a(n(g(e)?)?)?)?)?";
    COMMAND_REG_EX[DELETE] = "d(e(l(e(t(e)?)?)?)?)?";
    COMMAND_REG_EX[POINTS] = "p(o(i(n(t(s)?)?)?)?)?";
    COMMAND_REG_EX[TRIE] = "t(r(i(e)?)?)?";
    COMMAND_REG_EX[HELP] = "h(e(l(p)?)?)?";
    COMMAND_REG_EX[QUIT] = "q(u(i(t)?)?)?";
  }

  private static int getCommandId(String command) {
    if (command.equals("")) {
      return NO_COMMAND;
    }
    for (int i = 0; i < COMMAND_REG_EX.length; i++) {
      if (command.matches(COMMAND_REG_EX[i])) {
        return i;
      }
    }
    return INVALID_COMMAND;
  }

  private static boolean containsNumbers(String str) {
    if (str.matches(".*\\d+.*")) {
      return true;
    }
    return false;
  }

  private static void handleAdd(Scanner sc, Trie t) {
    String name = null;
    Integer pointsAdd = null;

    if (sc.hasNext()) {
      name = sc.next();
      if (containsNumbers(name)) {
        System.out.println(NAMES_NUMBERS_ERROR);
        return;
      }
    } else {
      System.out.println(NO_NAME_ERROR);
      return;
    }

    if (sc.hasNextInt()) {
      pointsAdd = sc.nextInt();
    } else {
      System.out.println(NO_POINTS_ERROR);
      return;
    }

    if (!t.add(name, pointsAdd)) {
      System.out.println("Error! '" + name + "' is already allocated.");
    }
  }

  private static void handleChange(Scanner sc, Trie t) {
    String name = null;
    Integer pointsChange = null;

    if (sc.hasNext()) {
      name = sc.next();
      if (containsNumbers(name)) {
        System.out.println(NAMES_NUMBERS_ERROR);
        return;
      }
    } else {
      System.out.println(NO_NAME_ERROR);
      return;
    }

    if (sc.hasNextInt()) {
      pointsChange = sc.nextInt();
    } else {
      System.out.println(NO_POINTS_ERROR);
      return;
    }
    if (!t.change(name, pointsChange)) {
      System.out.println("Error! '" + name + "' could not be found.");
    }
  }

  private static void handleDelete(Scanner sc, Trie t) {
    String name = null;

    if (sc.hasNext()) {
      name = sc.next();
      if (containsNumbers(name)) {
        System.out.println(NAMES_NUMBERS_ERROR);
        return;
      }
    } else {
      System.out.println(NO_NAME_ERROR);
      return;
    }

    if (!t.delete(name)) {
      System.out.println("Error! '" + name + "' could not be found.");
    }
  }

  private static void handelPoints(Scanner sc, Trie t) {
    String name = null;

    if (sc.hasNext()) {
      name = sc.next();
      if (containsNumbers(name)) {
        System.out.println(NAMES_NUMBERS_ERROR);
        return;
      }
    } else {
      System.out.println(NO_NAME_ERROR);
      return;
    }

    Integer points = t.points(name);

    if (points != null) {
      System.out.println(points);
    } else {
      System.out.println("Error! '" + name + "' could not be found or does not have any points.");
    }
  }

  private static Trie handleCommand(String inputLine, Trie t) {
    initCommandRegEx();
    Scanner sc = new Scanner(inputLine);
    sc.useDelimiter("\\s+");

    String command = "";
    if (sc.hasNext()) {
      command = sc.next();
    }
    int commandId = getCommandId(command);

    switch (commandId) {
      case NO_COMMAND:
        System.out.println(NO_COMMAND_ERROR);
        break;

      case NEW:
        t = new Trie();
        break;

      case ADD:
        handleAdd(sc, t);
        break;

      case CHANGE:
        handleChange(sc, t);
        break;

      case DELETE:
        handleDelete(sc, t);
        break;

      case POINTS:
        handelPoints(sc, t);
        break;

      case TRIE:
        System.out.println(t);
        break;

      case HELP:
        System.out.println(HELP_MESSAGE);
        break;

      case QUIT:
        return null;

      default:
        System.out.println(INVALID_COMMAND_ERROR);
        break;
    }

    return t;
  }

  private static boolean isLetterOrNumber(String str) {
    char[] strCharArray = str.toCharArray();

    boolean out = true;
    for (char c : strCharArray) {
      if (!Character.isLetter(c) && !Character.isDigit(c) && c != ' ') {
        out = false;
      }
    }

    return out;
  }

  private static boolean isLowerCase(String str) {
    return (str.equals(str.toLowerCase()));
  }

  /** Handles the Interactions between user-I/O and the Trie data structure. */
  public static void main(String[] args) throws IOException {

    Trie t = new Trie();
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

    boolean quit = false;

    while (!quit) {
      System.out.print("trie> ");

      String inputLine = stdin.readLine();
      if (inputLine == null) {
        System.out.println("Error!");

      } else if (!isLetterOrNumber(inputLine) || !isLowerCase(inputLine)) {
        System.out.println(ONLY_LOWERCASE_ERROR);
      } else {
        t = handleCommand(inputLine, t);
        if (t == null) {
          quit = true;
        }
      }
    }
  }
}