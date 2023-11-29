import java.io.Serializable;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Generates a glossary of a group of HTML files.
 *
 * @author Ethan Jones
 */
public final class Glossary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
        // no code needed here
    }

    /**
     * Comparator used to order the words queue.
     */
    public static final class StringOrder
            implements Comparator<String>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    }

    /**
     * Creates the header for the index file.
     *
     * @param fileOut
     *            printing to a specified file
     */
    public static void createIndexHeader(SimpleWriter fileOut) {
        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println("<title>Glossary</title>");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h2>Glossary</h2>");
        fileOut.println("<hr>");
        fileOut.println("<h3>Index:</h3>");
        fileOut.println("<ul>");
    }

    /**
     * Creates the footer for the index file.
     *
     * @param fileOut
     *            printing to a specified file
     */
    public static void createIndexFooter(SimpleWriter fileOut) {
        fileOut.println("</ul>");
        fileOut.println("</body>");
        fileOut.println("</html>");
    }

    /**
     * Creates the header for the index file.
     *
     * @param fileOut
     *            printing to a file
     * @param word
     *            the word that is used as the title and is made bold and made
     *            red in the file
     * @param def
     *            the definition of the word that is made bold and made red in
     *            the file
     */
    public static void createHTMLWords(SimpleWriter fileOut, String word,
            String def) {
        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println("<title>" + word + "</title>");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h2><b><i><font color=\"red\">" + word
                + "</font></i></b></h2>");
        fileOut.println("<blockquote>" + def + "</blockquote>");
        fileOut.println("<hr />");
        fileOut.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
        fileOut.println("</body>");
        fileOut.println("</html>");
        fileOut.println();
    }

    /**
     * Creates a map that pairs the words to their definitions.
     *
     * @param in
     *            grabs input from the specified file
     * @param wordList
     *            a given queue that has the words
     * @return returns a sorted word definition list
     */
    public static Map<String, String> createMap(SimpleReader in,
            Queue<String> wordList) {
        Map<String, String> wordDefList = new Map1L<>();
        String word = "";
        String def = "";
        while (!in.atEOS()) {
            String text = in.nextLine();
            if (text.contains(" ") && !text.isBlank()) {
                def = text;
            } else if (!text.contains(" ") && !text.isBlank()) {
                word = text;
                wordList.enqueue(text);
            } else {
                wordDefList.add(word, def);
                word = "";
                def = "";
            }
        }
        Comparator<String> order = new StringOrder();
        wordList.sort(order);
        return wordDefList;
    }

    /**
     * Processes the words by creating files specific to each word.
     *
     * @param out
     *            the file that is named after the word
     * @param word
     *            the word used to name the file
     * @param def
     *            the definition of the word that the file is named after
     * @param folder
     *            the folder for the path of the file that is created
     */
    public static void processWord(SimpleWriter out, String word, String def,
            String folder) {
        String fileName = word + ".html";
        SimpleWriter fileOut = new SimpleWriter1L(folder + "/" + fileName);
        out.println("<li><a href=\"" + fileName + "\">" + word + "</a></li>");
        createHTMLWords(fileOut, word, def);
    }

    /**
     * Processes files by iterating through each word, capitalizing each word,
     * then processing each word.
     *
     * @param file
     *            The name of the file
     * @param folder
     *            the name of the folder for the path of the file
     * @param out
     *            the file that this is being printed to
     */
    public static void processFile(String file, String folder,
            SimpleWriter out) {
        SimpleReader inputFile = new SimpleReader1L(file);
        Queue<String> wordList = new Queue1L<>();
        Map<String, String> wordDefList = createMap(inputFile, wordList);
        for (String word : wordList) {
            String def = wordDefList.value(word);
            word = word.substring(0, 1).toUpperCase() + word.substring(1);
            processWord(out, word, def, folder);
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        SimpleWriter fileOut = new SimpleWriter1L("data/index.html");
        out.print("Enter a file name: ");
        String file = in.nextLine();
        out.print("Enter a folder name: ");
        String folder = in.nextLine();
        createIndexHeader(fileOut);
        processFile(file, folder, fileOut);
        createIndexFooter(fileOut);
        out.close();
        in.close();
        fileOut.close();
    }
}
