package com.github.exper0.codechallenge;

public class UFBySizePathCompression implements DynamicConnectivity {
    private int [] data;
    private int [] size;

    public UFBySizePathCompression(int n) {
        this.data = new int[n];
        this.size = new int[n];
        for (int i = 0; i < n; ++i) {
            this.data[i] = i;
            this.size[i] = 1;
        }
    }

    private int root(int i) {
        while (i != data[i]) {
            data[i] = data[data[i]];
            i = data[i];
        }
        return i;
    }

    public void connect(int a, int b) {
        int i = root(a);
        int j = root(b);
        if (i == j) {
            return;
        }
        if (size[i]< size[j]) {
            data[i] = j;
            size[j] += size[i];
        } else {
            data[j] = i;
            size[i] += size[j];
        }
    }

    public boolean isConnected(int a, int b) {
        return root(a) == root(b);
    }
}
