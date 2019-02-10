package com.github.exper0.codechallenge;

import com.google.common.collect.LinkedHashMultimap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RoadMapTest {
    private DynamicConnectivity unionFind;

    @Before
    public void setUp() {
        unionFind = Mockito.mock(DynamicConnectivity.class);
    }

    @Test
    public void shouldPassProperIndexesToUF() throws CityNotFoundException {
        LinkedHashMultimap<String, String> mm = LinkedHashMultimap.create();
        mm.put("A", "B");
        mm.put("A", "C");
        mm.put("D", "E");
        RoadMap map = new RoadMap(mm, (x)->unionFind);
        Mockito.verify(unionFind).connect(0, 1);
        Mockito.verify(unionFind).connect(0, 2);
        Mockito.verify(unionFind).connect(3, 4);
        Mockito.when(unionFind.isConnected(3, 4)).thenReturn(true);
        Assert.assertTrue(map.isConnected("D", "E"));
        Mockito.verify(unionFind).isConnected(3,4);
    }

    @Test(expected = CityNotFoundException.class)
    public void shouldThrowExceptionOnInvalidCity() throws CityNotFoundException {
        RoadMap map = new RoadMap(LinkedHashMultimap.create(), (x)->unionFind);
        map.isConnected("A", "B");
    }
}
