import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author Ethan Jones
 */

public class GlossaryTest {
    /**
     * Testing outputHeader.
     *
     * Just a print method, only testing if it is printing as expected. There
     * are no other cases that I could account for in this.
     */
    @Test
    public void testCreateIndexHeader() {
        SimpleWriter out = new SimpleWriter1L("data/outputHeaderTest");
        SimpleReader in = new SimpleReader1L("data/outputHeaderTest");
        Glossary.createIndexHeader(out);
        assertEquals("<html>", in.nextLine());
        assertEquals("<head>", in.nextLine());
        assertEquals("<title>Glossary</title>", in.nextLine());
        assertEquals("</head>", in.nextLine());
        assertEquals("<body>", in.nextLine());
        assertEquals("<h2>Glossary</h2>", in.nextLine());
        assertEquals("<hr>", in.nextLine());
        assertEquals("<h3>Index:</h3>", in.nextLine());
        assertEquals("<ul>", in.nextLine());
        out.close();
        in.close();
    }

    /**
     * Testing outputFooter.
     *
     * Just a print method, only testing if it is printing as expected. There
     * are no other cases that I could account for in this.
     */
    @Test
    public void testCreateIndexFooter() {
        SimpleWriter out = new SimpleWriter1L("data/outputFooterTest");
        SimpleReader in = new SimpleReader1L("data/outputFooterTest");
        Glossary.createIndexFooter(out);
        assertEquals("</ul>", in.nextLine());
        assertEquals("</body>", in.nextLine());
        assertEquals("</html>", in.nextLine());
        out.close();
        in.close();
    }

    /**
     * Testing outputWordFile using Application.
     *
     * Testing outputWordFile using only one word.
     */
    @Test
    public void testOutputWordFileApplication() {
        SimpleWriter out = new SimpleWriter1L("data/outputWordFileTest");
        SimpleReader in = new SimpleReader1L("data/outputWordFileTest");
        Glossary.createHTMLWords(out, "application",
                "the act of putting to a special use or purpose.");
        assertEquals("<html>", in.nextLine());
        assertEquals("<head>", in.nextLine());
        assertEquals("<title>application</title>", in.nextLine());
        assertEquals("</head>", in.nextLine());
        assertEquals("<body>", in.nextLine());
        assertEquals(
                "<h2><b><i><font color=\"red\">application</font></i></b></h2>",
                in.nextLine());
        assertEquals("<blockquote>the act of putting to a special use or "
                + "purpose.</blockquote>", in.nextLine());
        assertEquals("<hr />", in.nextLine());
        assertEquals("<p>Return to <a href=\"index.html\">index</a>.</p>",
                in.nextLine());
        assertEquals("</body>", in.nextLine());
        assertEquals("</html>", in.nextLine());
        assertEquals("", in.nextLine());
        out.close();
        in.close();
    }

    /**
     * Testing outputWordFile using no words.
     *
     * Since this usually is expecting words, I decided to use no words The
     * reaction should be that it prints code with a blank instead of a word
     */
    @Test
    public void testOutputWordFileNoWords() {
        SimpleWriter out = new SimpleWriter1L("data/outputWordFileTest");
        SimpleReader in = new SimpleReader1L("data/outputWordFileTest");
        Glossary.createHTMLWords(out, "", "");
        assertEquals("<html>", in.nextLine());
        assertEquals("<head>", in.nextLine());
        assertEquals("<title></title>", in.nextLine());
        assertEquals("</head>", in.nextLine());
        assertEquals("<body>", in.nextLine());
        assertEquals("<h2><b><i><font color=\"red\"></font></i></b></h2>",
                in.nextLine());
        assertEquals("<blockquote></blockquote>", in.nextLine());
        assertEquals("<hr />", in.nextLine());
        assertEquals("<p>Return to <a href=\"index.html\">index</a>.</p>",
                in.nextLine());
        assertEquals("</body>", in.nextLine());
        assertEquals("</html>", in.nextLine());
        assertEquals("", in.nextLine());
        out.close();
        in.close();
    }

    /**
     * Testing my sort method using 15 words.
     *
     * This is a routine case, just a lot of different words
     */
    @Test
    public void testSorting() {
        Queue<String> wordList = new Queue1L<>();
        Queue<String> wordListExpected = new Queue1L<>();
        wordList.enqueue("frank");
        wordList.enqueue("cook");
        wordList.enqueue("important");
        wordList.enqueue("adult");
        wordList.enqueue("distributor");
        wordList.enqueue("taste");
        wordList.enqueue("conversation");
        wordList.enqueue("peace");
        wordList.enqueue("knit");
        wordList.enqueue("arrest");
        wordList.enqueue("denial");
        wordList.enqueue("tease");
        wordList.enqueue("determine");
        wordList.enqueue("assertive");
        wordList.enqueue("restoration");
        wordListExpected.enqueue("adult");
        wordListExpected.enqueue("arrest");
        wordListExpected.enqueue("assertive");
        wordListExpected.enqueue("conversation");
        wordListExpected.enqueue("cook");
        wordListExpected.enqueue("denial");
        wordListExpected.enqueue("determine");
        wordListExpected.enqueue("distributor");
        wordListExpected.enqueue("frank");
        wordListExpected.enqueue("important");
        wordListExpected.enqueue("knit");
        wordListExpected.enqueue("peace");
        wordListExpected.enqueue("restoration");
        wordListExpected.enqueue("taste");
        wordListExpected.enqueue("tease");
        Comparator<String> queueSort = new Glossary.StringOrder();
        wordList.sort(queueSort);
        assertEquals(wordList, wordListExpected);
    }

    /**
     * Testing createMap method with the word "Application.
     *
     * This is an edge case because this is only testing one word, as testing no
     * words would not result in anything useful
     */
    @Test
    public void testCreateMapApplication() {
        SimpleWriter out = new SimpleWriter1L("data/ApplicationMap");
        out.println("application");
        out.println("the act of putting to a special use or purpose");
        out.println();
        SimpleReader in = new SimpleReader1L("data/ApplicationMap");
        Queue<String> wordList = new Queue1L<>();
        Queue<String> wordListExpected = new Queue1L<>();
        wordListExpected.enqueue("application");
        Map<String, String> map = Glossary.createMap(in, wordList);
        Map<String, String> mapExpected = new Map1L<>();
        mapExpected.add("application",
                "the act of putting to a special use or purpose");
        out.close();
        in.close();
        assertEquals(map, mapExpected);
        assertEquals(wordList, wordListExpected);
    }

    /**
     * Testing createMap using Application and Chauvinist.
     *
     * This is a normal case with only two words
     */
    @Test
    public void testCreateMapApplicationChauvinist() {
        SimpleWriter out = new SimpleWriter1L("data/Application_Chauvinist");
        out.println("chauvinist");
        out.println("a person who is aggressively and blindly patriotic, "
                + "especially one devoted to military glory");
        out.println();
        out.println("application");
        out.println("the act of putting to a special use or purpose");
        out.println();
        SimpleReader in = new SimpleReader1L("data/Application_Chauvinist");
        Queue<String> wordList = new Queue1L<>();
        Queue<String> wordListExpected = new Queue1L<>();
        wordListExpected.enqueue("application");
        wordListExpected.enqueue("chauvinist");
        Map<String, String> map = Glossary.createMap(in, wordList);
        Map<String, String> mapExpected = new Map1L<>();
        mapExpected.add("application",
                "the act of putting to a special use or purpose");
        mapExpected.add("chauvinist",
                "a person who is aggressively and blindly patriotic, "
                        + "especially one devoted to military glory");
        out.close();
        in.close();
        assertEquals(map, mapExpected);
        assertEquals(wordList, wordListExpected);
    }

    /**
     * Testing createMap using 15 words.
     *
     * This is a challenge case because it is giving 15 words in an order that
     * is not sorted properly and seeing if all 15 words are sorted
     * alphabetically and have the proper keys.
     *
     */
    @Test
    public void testCreateMap15Words() {
        SimpleWriter out = new SimpleWriter1L("data/15Words");
        out.println("frank");
        out.println("direct and unreserved in speech");
        out.println();
        out.println("cook");
        out.println("to prepare (food) by the use of heat, as by boiling, "
                + "baking, or roasting.");
        out.println();
        out.println("important");
        out.println("of much or great significance or consequence");
        out.println();
        out.println("adult");
        out.println("a person who is fully grown or developed or of age.");
        out.println();
        out.println("distributor");
        out.println("a person or thing that distributes");
        out.println();
        out.println("taste");
        out.println("to try or test the flavor or quality of (something) by"
                + " taking some into the mouth");
        out.println();
        out.println("conversation");
        out.println("informal interchange of thoughts, information, etc., by "
                + "spoken words; oral communication between persons");
        out.println();
        out.println("peace");
        out.println("the nonwarring condition of a nation, group of "
                + "nations, or the world.");
        out.println();
        out.println("knit");
        out.println("to join closely and firmly, as members or parts");
        out.println();
        out.println("arrest");
        out.println("to seize (a person) by legal authority or warrant");
        out.println();
        out.println("denial");
        out.println("an assertion that something said or believed is false");
        out.println();
        out.println("tease");
        out.println("to irritate or provoke with persistent petty distractions,"
                + " trifling jests, or other annoyances, "
                + "often in a playful way");
        out.println();
        out.println("determine");
        out.println("to conclude or ascertain, as after "
                + "reasoning, observation, etc.");
        out.println();
        out.println("assertive");
        out.println("confidently aggressive or self-assured");
        out.println();
        out.println("restoration");
        out.println("the act of restoring");
        out.println();
        SimpleReader in = new SimpleReader1L("data/15Words");
        Queue<String> wordList = new Queue1L<>();
        Queue<String> wordListExpected = new Queue1L<>();
        wordListExpected.enqueue("frank");
        wordListExpected.enqueue("cook");
        wordListExpected.enqueue("important");
        wordListExpected.enqueue("adult");
        wordListExpected.enqueue("distributor");
        wordListExpected.enqueue("taste");
        wordListExpected.enqueue("conversation");
        wordListExpected.enqueue("peace");
        wordListExpected.enqueue("knit");
        wordListExpected.enqueue("arrest");
        wordListExpected.enqueue("denial");
        wordListExpected.enqueue("tease");
        wordListExpected.enqueue("determine");
        wordListExpected.enqueue("assertive");
        wordListExpected.enqueue("restoration");
        Comparator<String> queueSort = new Glossary.StringOrder();
        wordListExpected.sort(queueSort);
        Map<String, String> map = Glossary.createMap(in, wordList);
        Map<String, String> mapExpected = new Map1L<>();
        mapExpected.add("frank", "direct and unreserved in speech");
        mapExpected.add("cook",
                "to prepare (food) by the use of heat, as by boiling, "
                        + "baking, or roasting.");
        mapExpected.add("important",
                "of much or great significance or consequence");
        mapExpected.add("adult",
                "a person who is fully grown or developed or of age.");
        mapExpected.add("distributor", "a person or thing that distributes");
        mapExpected.add("taste",
                "to try or test the flavor or quality of (something) by"
                        + " taking some into the mouth");
        mapExpected.add("conversation",
                "informal interchange of thoughts, information, etc., by "
                        + "spoken words; oral communication between persons");
        mapExpected.add("peace",
                "the nonwarring condition of a nation, group of "
                        + "nations, or the world.");
        mapExpected.add("knit",
                "to join closely and firmly, as members or parts");
        mapExpected.add("arrest",
                "to seize (a person) by legal authority or warrant");
        mapExpected.add("denial",
                "an assertion that something said or believed is false");
        mapExpected.add("tease",
                "to irritate or provoke with persistent petty distractions,"
                        + " trifling jests, or other annoyances, "
                        + "often in a playful way");
        mapExpected.add("determine", "to conclude or ascertain, as after "
                + "reasoning, observation, etc.");
        mapExpected.add("assertive", "confidently aggressive or self-assured");
        mapExpected.add("restoration", "the act of restoring");
        out.close();
        in.close();
        assertEquals(map, mapExpected);
        assertEquals(wordList, wordListExpected);
    }

    /**
     * Testing processWord using chauvinist.
     *
     * Testing if processing a word works properly
     */
    @Test
    public void testProcessWordChauvinist() {
        SimpleWriter out = new SimpleWriter1L("data/processWordTest");
        SimpleReader in = new SimpleReader1L("data/processWordTest");
        Glossary.processWord(out, "chauvinist",
                "a person who is aggressively and blindly patriotic, "
                        + "especially one devoted to military glory",
                "data");
        assertEquals("<li><a href=\"chauvinist.html\">chauvinist</a></li>",
                in.nextLine());
        out.close();
        in.close();
    }

    /**
     * Testing processWord using application.
     *
     * Again, testing if processing a word works as intended with a different
     * word
     */
    @Test
    public void testProcessWordApplication() {
        SimpleWriter out = new SimpleWriter1L("data/processWordTest2");
        SimpleReader in = new SimpleReader1L("data/processWordTest2");
        Glossary.processWord(out, "application",
                "the act of putting to a special use or purpose.", "data");
        assertEquals("<li><a href=\"application.html\">application</a></li>",
                in.nextLine());
        out.close();
        in.close();
    }

    /**
     * Testing processFile using Application and Chauvinist.
     *
     * Routine case, just testing if this works properly
     */
    @Test
    public void testProcessFile() {
        SimpleWriter out = new SimpleWriter1L("data/processFileTest");
        SimpleReader in = new SimpleReader1L("data/processFileTest");

        Glossary.processFile("data/Application_Chauvinist", "data", out);
        assertEquals("<li><a href=\"Application.html\">Application</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Chauvinist.html\">Chauvinist</a></li>",
                in.nextLine());
        out.close();
        in.close();
    }

    /**
     * Testing processFile using 15 different words.
     *
     * Challenge case, seeing if all 15 wors are properly capetalized and
     * alphabetized
     */
    @Test
    public void testProcessFile15Words() {
        SimpleWriter out = new SimpleWriter1L("data/processFileTest");
        SimpleReader in = new SimpleReader1L("data/processFileTest");

        Glossary.processFile("data/15Words", "data", out);
        assertEquals("<li><a href=\"Adult.html\">Adult</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Arrest.html\">Arrest</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Assertive.html\">Assertive</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Conversation.html\">Conversation</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Cook.html\">Cook</a></li>", in.nextLine());
        assertEquals("<li><a href=\"Denial.html\">Denial</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Determine.html\">Determine</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Distributor.html\">Distributor</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Frank.html\">Frank</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Important.html\">Important</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Knit.html\">Knit</a></li>", in.nextLine());
        assertEquals("<li><a href=\"Peace.html\">Peace</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Restoration.html\">Restoration</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Taste.html\">Taste</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Tease.html\">Tease</a></li>",
                in.nextLine());
        out.close();
        in.close();
    }

    /**
     * Testing processFile, createIndexHeader, and CreateIndexFooter using 15
     * different words. This is putting together a full glossary.
     *
     * This should make it so that the files are all stored in a file called
     * fullGlossaryTest.html
     */
    @Test
    public void testFullGlossary() {
        SimpleWriter out = new SimpleWriter1L("data/fullGlossaryTest.html");
        SimpleReader in = new SimpleReader1L("data/fullGlossaryTest.html");
        Glossary.createIndexHeader(out);
        Glossary.processFile("data/15Words", "data", out);
        Glossary.createIndexFooter(out);
        assertEquals("<html>", in.nextLine());
        assertEquals("<head>", in.nextLine());
        assertEquals("<title>Glossary</title>", in.nextLine());
        assertEquals("</head>", in.nextLine());
        assertEquals("<body>", in.nextLine());
        assertEquals("<h2>Glossary</h2>", in.nextLine());
        assertEquals("<hr>", in.nextLine());
        assertEquals("<h3>Index:</h3>", in.nextLine());
        assertEquals("<ul>", in.nextLine());
        assertEquals("<li><a href=\"Adult.html\">Adult</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Arrest.html\">Arrest</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Assertive.html\">Assertive</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Conversation.html\">Conversation</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Cook.html\">Cook</a></li>", in.nextLine());
        assertEquals("<li><a href=\"Denial.html\">Denial</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Determine.html\">Determine</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Distributor.html\">Distributor</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Frank.html\">Frank</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Important.html\">Important</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Knit.html\">Knit</a></li>", in.nextLine());
        assertEquals("<li><a href=\"Peace.html\">Peace</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Restoration.html\">Restoration</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Taste.html\">Taste</a></li>",
                in.nextLine());
        assertEquals("<li><a href=\"Tease.html\">Tease</a></li>",
                in.nextLine());
        assertEquals("</ul>", in.nextLine());
        assertEquals("</body>", in.nextLine());
        assertEquals("</html>", in.nextLine());
        out.close();
        in.close();
    }

}
