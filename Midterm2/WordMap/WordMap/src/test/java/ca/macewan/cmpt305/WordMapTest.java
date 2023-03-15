package ca.macewan.cmpt305;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordMapTest {
    private WordMap smallMap;
    private WordMap largeMap;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        smallMap = new WordMap("test.txt");
        largeMap = new WordMap("dict.txt");
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(8, smallMap.size());
        assertEquals(19911, largeMap.size());
    }

    @org.junit.jupiter.api.Test
    void isNeighbour() {
        assertTrue(smallMap.isNeighbour("dog", "dot"));
        assertFalse(smallMap.isNeighbour("dot", "dot"));
        assertFalse(smallMap.isNeighbour("dog", "rat"));
        assertFalse(smallMap.isNeighbour("log", "dog")); // log is not in test.txt

        assertTrue(largeMap.isNeighbour("log", "dog")); // log is in dict.txt
    }

    @org.junit.jupiter.api.Test
    void distance() {
        // Using text.txt
        assertEquals(0, smallMap.distance("mat", "mat"));
        assertEquals(1, smallMap.distance("dog", "dot")); // dog, dot
        assertEquals(2, smallMap.distance("rut", "cat")); // rut, rat, cat
        assertEquals(3, smallMap.distance("cot", "rut")); // cot, cat, rat, rut
        assertEquals(4, smallMap.distance("dog", "rat")); // dog, dot, cot, cat, rat
        assertEquals(-1, smallMap.distance("dog", "man")); // man is not in test.txt

        // Using dict.txt
        assertEquals(1, largeMap.distance("log", "dog"));
        // possible path: jam, jag, jog, dog
        assertEquals(3, largeMap.distance("jam", "dog"));
        // possible path: glass, class, clash, slash, slosh, sloth, sooth, south, sough, rough, rouge, rouse, house
        assertEquals(12, largeMap.distance("glass", "house"));
        // possible path: gone, gore, wore, wire, wile, wild
        assertEquals(5, largeMap.distance("gone", "wild"));
        assertEquals(-1, largeMap.distance("course", "across"));
    }

    @org.junit.jupiter.api.Test
    void path() {
        // Note: if there are multiple paths between two words, any correct path will be accepted
        assertEquals(new ArrayList<String>(), smallMap.path("man", "dog")); // man is not in test.txt
        assertEquals(List.of("mat"), smallMap.path("mat", "mat"));
        assertEquals(Arrays.asList("dog", "dot"), smallMap.path("dog", "dot"));
        assertEquals(Arrays.asList("rut", "rat", "cat"), smallMap.path("rut", "cat"));
        assertEquals(Arrays.asList("cot", "cat", "rat", "rut"), smallMap.path("cot", "rut"));
        assertEquals(Arrays.asList("dog", "dot", "cot", "cat", "rat"), smallMap.path("dog", "rat"));
    }
}