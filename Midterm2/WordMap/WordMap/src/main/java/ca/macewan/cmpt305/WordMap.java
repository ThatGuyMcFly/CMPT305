package ca.macewan.cmpt305;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The edit distance between two strings is the minimum number of operations that are needed to transform one string
 * into the other. For this assignment, an operation is a substitution of a single character,
 * such as from “brisk” to “brick”. For example, the edit distance between the words “dog” and “cat” is 3,
 * following the chain of “cog”, “cot”, and “cat” to transform “dog” into “cat” (note: there may be multiple paths
 * between two words, but their edit distance should be the same). When you compute the edit distance between two words,
 * each immediate word must be an actual valid word (i.e., listed in the dictionary file given to the constructor).
 * Acknowledgement: this question was adapted from a Java textbook with modifications.
 */
public class WordMap {
    // a map of word to its neighbours
    private final Map<String, List<String>> neighbours;

    private List<String> getStringList(String filename) {
        List<String> stringList = new ArrayList<>();

        File file = new File(filename);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e){
            System.out.println("No file " + filename + " found");
            return stringList;
        }

        while(scanner.hasNextLine()) {
            stringList.add(scanner.nextLine());
        }

        return stringList;
    }

    /**
     * Construct a WordMap object and populate the map of a word to its neighbours (40 points).
     * From the given file, compute a map from every word to its immediate neighbours.
     * Use the isNeighbour method to determine if two words have an edit distance of one.
     * Once this map is built, you can walk it to find paths from one word to another.
     *
     * @param filename the dictionary filename
     */
    public WordMap(String filename) {
        neighbours = new HashMap<>();

        List<String> stringList = getStringList(filename);

        for (String string1: stringList) {
            List<String> neighbourStrings = new ArrayList<>();

            for (String string2: stringList) {
                if(isNeighbour(string1, string2)) {
                    neighbourStrings.add(string2);
                }
            }

            neighbours.put(string1, neighbourStrings);
        }
    }

    /**
     * Return the number of key-value pairs in the neighbours map (5 points).
     *
     * @return the number of key-value pairs
     */
    public int size() {
        return neighbours.size(); // replace with your implementation
    }

    /**
     * Determine if two words are neighbours (15 points).
     * Two words are neighbours if they have an edit distance of one.
     * Both words must exist in the dictionary file given in the constructor.
     * For example, dog and dot are neighbours.
     *
     * @param word1 the first word
     * @param word2 the second word
     *
     * @return true if word1 and word2 are neighbours; false, otherwise.
     */
    public boolean isNeighbour(String word1, String word2) {
        if (word1.length() != word2.length() || word1.equals(word2)) {
            return false;
        }

        int notMatchCount = 0;

        for (int i = 0; i < word1.length(); i++) {
            if(word1.charAt(i) != word2.charAt(i)){
                notMatchCount++;
            }

            if (notMatchCount > 1) {
                return false;
            }
        }
        return true; // replace with your implementation
    }

    /**
     * Calculate the distance between start and end (25 points).
     * You can calculate the edit distance between two words by trying to find a path from the start word to
     * the end word. A good way to process paths to walk the neighbour map is to use a queue of words to visit,
     * starting with the beginning of word, such as “dog”. Your algorithm should repeatedly remove the front word
     * of the queue and add all its neighbours to the end of the queue, until the ending word (e.g., “cat”) is found
     * or until the queue becomes empty, which indicates that no path exists between the two words.
     * Do not add the same word twice to the queue (otherwise, the queue may never be empty, or the edit distance
     * calculation produces an incorrect result).
     *
     * @param start the start word
     * @param end the end word
     * @return  the edit distance between start and end (or -1 if there is no path from start to end
     *          or any of the given words don't exist in the neighbours map).
     */
    public int distance(String start, String end) {
        if(start.length() != end.length() || neighbours.get(start) == null || neighbours.get(end) == null) {
            return -1;
        }

        if(start.equals(end)) {
            return 0;
        }

        Queue<String> checkingQueue = new ArrayDeque<>(neighbours.get(start));
        List<String> checkedList = new ArrayList<>();

        checkedList.add(start);

        int dist = 1;

        // Put the distance in the queue to track when the current level being checked has ended
        checkingQueue.add(Integer.toString(dist));

        while (!checkingQueue.isEmpty()) {

            String currentString = checkingQueue.remove();

            if(currentString.equals(end)) {
                // Return the distance when the end string has been found
                return dist;
            } else if (currentString.equals(Integer.toString(dist)) && !checkingQueue.isEmpty()) {
                // increment the distance when all words in the current level have been checked
                dist++;
                // Insert the new level into the queue to track current level
                checkingQueue.add(Integer.toString(dist));
            } else if (!checkingQueue.isEmpty()) {
                // put current string into the checked list to avoid checking the same word twice
                checkedList.add(currentString);
                for (String neighbourString: neighbours.get(currentString)){
                    // check if neighbour string is has already been checked
                    if(!checkedList.contains(neighbourString)) {
                        // only add strings to the checking queue if it hasn't yet been checked
                        checkingQueue.add(neighbourString);
                    }
                }
            }
        }

        return -1;
    }

    /**
     * Return the path, if exists, between start and end (15 points).
     * You can find a possible path by keeping track of the transformation from start to end words by
     * using a map that keeps track of a word’s neighbours to the word (e.g., mapping start’s neighbours to start
     * until finding the end/target word). When the end word is reached, the map can be traced back to find
     * a possible path from start to end words. During the process of finding a path, don't update an existing key,
     * only add a new key/word to the possible paths.
     *
     * @param start the start word
     * @param end the end word
     * @return  the path between start and end (or empty list if there is no path from start to end
     *          or any of the given words don't exist in the neighbours map).
     */
    public List<String> path(String start, String end) {
        return new ArrayList<>(); // replace with your implementation
    }

    public static void main(String[] args) {
        System.out.println("Midterm Exam");
        System.out.println("Please complete the implementation of each method and review the examples of test cases\\" +
                " carefully");
        System.out.println("Additional test cases may be used to test your solution");

        WordMap wordMap = new WordMap("test.txt");

        System.out.println(wordMap.distance("mat", "mat"));
        System.out.println(wordMap.distance("dog", "dot"));
        System.out.println(wordMap.distance("rut", "cat"));
        System.out.println(wordMap.distance("cot", "rut"));
        System.out.println(wordMap.distance("dog", "rat"));
        System.out.println(wordMap.distance("dog", "man"));
        System.out.println(wordMap.distance("dog", "bob"));

        System.out.println("Done");
    }
}