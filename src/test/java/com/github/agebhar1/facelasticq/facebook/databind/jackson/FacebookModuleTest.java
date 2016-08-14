/*
 * Copyright (c) 2016 Andreas Gebhardt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.agebhar1.facelasticq.facebook.databind.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agebhar1.facelasticq.facebook.api.Post;

@RunWith(Parameterized.class)
public class FacebookModuleTest {
	
	@Parameter(value = 0)
	public String resource;
	
	@Parameters(name = "{index}: de-/serialization round-trip for {0}")
	public static Collection<Object[]> files() {
		return Arrays.asList(new Object[][] {
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.00.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.01.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.02.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.03.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.04.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.05.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.06.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.07.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.08.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.09.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.10.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.11.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.12.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.13.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.14.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.15.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.16.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.17.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.18.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.19.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.20.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.21.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.22.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.23.json" },
			{ "graph.facebook.com/v2.7/elastic.co/feed/response.data.24.json" }
		});
	}
	
	@Test
	public void deserialization_serialization_round_trip() throws JsonParseException, JsonMappingException, IOException {

		final ObjectMapper mapper = new ObjectMapper();

		mapper.registerModule(new FacebookModule());
		
		try(final InputStream is = getClass().getClassLoader().getResourceAsStream(resource)) {
			
			final String expected = IOUtils.toString(is, "UTF-8");
			
			final Post post = mapper.readValue(expected, Post.class);
			
			final String actual = mapper.writeValueAsString(post);
			
			JSONAssert.assertEquals(expected, actual, true);
			
		}

	}

}
