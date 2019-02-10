package com.github.exper0.codechallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UFBySizePathCompressionTest {
    private static final int SIZE = 4;
    private UFBySizePathCompression unionFind;


    @Before
    public void setUp() throws Exception {
        unionFind = new UFBySizePathCompression(SIZE);
    }

    @Test
    public void shouldConnectToItselfOnlyWhenCreated() {
        verifyInitialState();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowExceptionOnIsConnectedWithWrongArg() {
        unionFind.isConnected(0, SIZE);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowExceptionOnConnectedWithWrongArg() {
        unionFind.connect(-1, 0);
    }

    @Test
    public void shouldConnectDirectlyConnectedVertexes() {
        unionFind.connect(0,1);
        Assert.assertTrue(unionFind.isConnected(0,1));
        Assert.assertFalse(unionFind.isConnected(0,2));
        Assert.assertFalse(unionFind.isConnected(0,3));
        Assert.assertFalse(unionFind.isConnected(1,2));
        Assert.assertFalse(unionFind.isConnected(1,3));
        Assert.assertFalse(unionFind.isConnected(2,3));
    }


    @Test
    public void shouldConnectIndirectlyConnectedVertexes() {
        unionFind.connect(0,1);
        unionFind.connect(1,2);
        Assert.assertTrue(unionFind.isConnected(0,2));
        Assert.assertTrue(unionFind.isConnected(0,1));
        Assert.assertTrue(unionFind.isConnected(1,2));
        Assert.assertFalse(unionFind.isConnected(0,3));
        Assert.assertFalse(unionFind.isConnected(1,3));
        Assert.assertFalse(unionFind.isConnected(2,3));
    }

    @Test
    public void shouldNotChangeStateOnConnectToItself() {
        unionFind.connect(1,1);
        verifyInitialState();
    }

    private void verifyInitialState() {
        for (int i=0; i<SIZE; ++i) {
            for (int j = 0; j<SIZE; ++j) {
                if (i == j) {
                    Assert.assertTrue(unionFind.isConnected(i,j));
                } else {
                    Assert.assertFalse(unionFind.isConnected(i,j));
                }
            }
        }
    }
}
