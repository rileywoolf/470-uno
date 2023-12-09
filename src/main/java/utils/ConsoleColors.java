package utils;

/**
 * The {@code ConsoleColors} class provides ANSI escape codes for coloring text in the console.
 * It includes static constants for resetting text color and applying bold formatting to various colors.
 * <p>
 * The class allows customization of text color and style when printing to the console.
 * </p>
 * <p>
 * Usage example:
 * <pre>
 * {@code
 * System.out.println(ConsoleColors.RED_BOLD + "This is bold red text" + ConsoleColors.RESET);
 * }
 * </pre>
 * </p>
 * <p>
 * Note: ANSI escape codes might not be supported in all console environments.
 * </p>
 *
 * @author Riley Woolf
 * @version 1.0
 */
public class ConsoleColors {
    /**
     * ANSI escape code to reset text color and formatting.
     */
    public static final String RESET = "\033[0m";  // Text Reset

    /**
     * ANSI escape code for bold black text.
     */
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK

    /**
     * ANSI escape code for bold red text.
     */
    public static final String RED_BOLD = "\033[1;31m";    // RED

    /**
     * ANSI escape code for bold green text.
     */
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN

    /**
     * ANSI escape code for bold yellow text.
     */
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW

    /**
     * ANSI escape code for bold blue text.
     */
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE

    /**
     * ANSI escape code for bold white text.
     */
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
}
