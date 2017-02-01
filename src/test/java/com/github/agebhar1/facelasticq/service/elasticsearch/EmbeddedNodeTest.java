/*
 * Copyright (c) 2016-2017 Andreas Gebhardt
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
package com.github.agebhar1.facelasticq.service.elasticsearch;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.http.HttpStatus.OK;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.elasticsearch.node.NodeValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.SocketUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class EmbeddedNodeTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	@Test
	public void elasticsearch_is_running() throws IOException, NodeValidationException {
		
		final File home = folder.newFolder();
		
		final int port = SocketUtils.findAvailableTcpPort();
		final ElasticsearchConfiguration config = new ElasticsearchConfiguration();
		final ElasticsearchConfiguration.Http http = new ElasticsearchConfiguration.Http();
		final ElasticsearchConfiguration.Path path = new ElasticsearchConfiguration.Path();
		final ElasticsearchConfiguration.Node node = new ElasticsearchConfiguration.Node();	
		
		config.setHttp(http);
		config.setPath(path);
		config.setNode(node);
		
		http.setEnabled(true);
		http.setPort(port);
		path.setHome(home.getAbsolutePath());
		
		final String name = UUID.randomUUID().toString();
		node.setName(name);
		
		final EmbeddedNode embeddedNode = new EmbeddedNode(config);
		
		final RestTemplate template = new RestTemplate();
		
		final ResponseEntity<ObjectNode> response = template.getForEntity(format("http://localhost:%d", port), ObjectNode.class);
		
		final String expected = format(
				  "{ "
				+ "  'name': '%s',"
				+ "  'cluster_name':'elasticsearch',"
				+ "  'version': {"
				+ "    'number': '5.4.1',"
				+ "    'build_hash': '2cfe0df',"
				+ "    'build_date': '2017-05-29T16:05:51.443Z',"
				+ "    'build_snapshot': false,"
				+ "    'lucene_version': '6.5.1'"
				+ "  },"
				+ "  'tagline': 'You Know, for Search'"
				+ "}", name)
				.replaceAll("'", "\"");
		
		final String actual = response.getBody().toString();
		
		assertThat(response.getStatusCode(), is(equalTo(OK)));
		assertEquals(expected, actual, false);
		
		embeddedNode.close();
		
	}

}
