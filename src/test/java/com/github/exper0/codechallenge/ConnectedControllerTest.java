/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.exper0.codechallenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConnectedControllerTest {
    private static final String URL = "/connected";
    private static final String ORIGIN = "origin";
    private static final String DESTINATION = "destination";
    private static final String YES = "yes";
    private static final String NO = "no";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldConnectBostonAndNewark() throws Exception {

        this.mockMvc.perform(get(URL).param(ORIGIN, "Boston").param(DESTINATION, "Newark"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(YES));
    }

    @Test
    public void shouldConnectBostonAndPhiladelphia() throws Exception {
        this.mockMvc.perform(get(URL).param(ORIGIN, "Boston").param(DESTINATION, "Philadelphia"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(YES));
    }

    @Test
    public void shouldConnectPhiladelphiaAndAlbany() throws Exception {
        this.mockMvc.perform(get(URL).param(ORIGIN, "Philadelphia").param(DESTINATION, "Albany"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(NO));
    }

    @Test
    public void shouldSayNoOnUnknownCity() throws Exception {
        this.mockMvc.perform(get(URL).param(ORIGIN, "1Boston").param(DESTINATION, "Newark"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(NO));
    }

    @Test
    public void shouldSayNoOnInvalidOrigin() throws Exception {
        this.mockMvc.perform(get(URL).param(ORIGIN, " ").param(DESTINATION, "Newark"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(NO));
    }

    @Test
    public void shouldSayNoOnInvalidDestination() throws Exception {
        this.mockMvc.perform(get(URL).param(ORIGIN, "Boston").param(DESTINATION, ""))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(NO));
    }
}
