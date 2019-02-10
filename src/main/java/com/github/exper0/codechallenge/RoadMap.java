package com.github.exper0.codechallenge;

import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RoadMap {
    private DynamicConnectivity connectivity;
    private Map<String, Integer> name2index;

    public RoadMap(SetMultimap<String, String> data, Function<Integer, DynamicConnectivity> supplier) {
        this.name2index = new HashMap<>(data.size() << 1);
        initIndexes(data);
        this.connectivity = supplier.apply(name2index.size());
        initRoutes(data);
    }

    private void initRoutes(Multimap<String, String> data) {
        for (Map.Entry<String, String> entry: data.entries()) {
            connectivity.connect(
                    name2index.get(entry.getKey()),
                    name2index.get(entry.getValue())
            );
        }
    }

    private void initIndexes(Multimap<String, String> data) {
        int i=0;
        for (Map.Entry<String, String> entry: data.entries()) {
            if (name2index.putIfAbsent(entry.getKey(), i) == null) {
                ++i;
            }
            if (name2index.putIfAbsent(entry.getValue(), i) == null) {
                ++i;
            }
        }
    }

    public boolean isConnected(String a, String b)  throws CityNotFoundException{
        return connectivity.isConnected(resolve(a), resolve(b));
    }

    private int resolve(String city) throws CityNotFoundException {
        Integer i = name2index.get(city);
        if (i == null) {
            throw new CityNotFoundException(city);
        }
        return i;
    }
}
