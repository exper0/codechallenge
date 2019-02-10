package com.github.exper0.codechallenge.config;

import com.github.exper0.codechallenge.RoadMap;
import com.github.exper0.codechallenge.RoadMapLoader;
import com.github.exper0.codechallenge.UFBySizePathCompression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RoadMapLoader roadMapLoader() {
        return new RoadMapLoader(mm->new RoadMap(mm, UFBySizePathCompression::new));
    }
}
