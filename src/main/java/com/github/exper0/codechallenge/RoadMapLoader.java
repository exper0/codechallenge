package com.github.exper0.codechallenge;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import javax.xml.ws.Holder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

public class RoadMapLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoadMapLoader.class);

    private static final String SEPARATOR = ",";
    private Function<SetMultimap<String, String>, RoadMap> roadMapFactory;

    public RoadMapLoader(Function<SetMultimap<String, String>, RoadMap> roadMapFactory) {
        this.roadMapFactory = roadMapFactory;
    }

    public RoadMap load(String filename) throws IOException, URISyntaxException {
        Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
        Holder<Integer> line = new Holder<>(1);
        SetMultimap<String, String> mm = LinkedHashMultimap.create();
        lines.forEach(l->{
            String [] pair = l.split(SEPARATOR);
            if (pair.length != 2) {
                LOGGER.error("line {} error: expected 2 comma separate values, got {}. Skipping", line.value, pair.length);
                return;
            }
            if (!StringUtils.hasText(pair[0]) || !StringUtils.hasText(pair[1])) {
                LOGGER.error("line {} error: values cannot be empty", line.value);
                return;
            }
            mm.put(pair[0].trim().toLowerCase(), pair[1].trim().toLowerCase());
            ++line.value;
        });
        return roadMapFactory.apply(mm);
    }
}
