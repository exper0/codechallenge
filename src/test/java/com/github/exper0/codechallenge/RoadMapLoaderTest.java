package com.github.exper0.codechallenge;

import com.google.common.collect.LinkedHashMultimap;
import org.junit.Assert;
import org.junit.Test;

public class RoadMapLoaderTest {
    private static final String FILENAME = "test.txt";

    @Test
    public void shouldLoadTheFileWithProperErrorHandling() throws Exception {
        RoadMapLoader loader = new RoadMapLoader((x)->{
            LinkedHashMultimap<String, String> expected = LinkedHashMultimap.create();
            expected.put("a", "b");
            expected.put("c", "d");
            Assert.assertEquals(expected, x);
            // it doesn't matter what to return here
            return null;
        });
        loader.load(FILENAME);
    }
}
