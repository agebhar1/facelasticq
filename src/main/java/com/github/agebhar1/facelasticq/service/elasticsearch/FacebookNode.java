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
package com.github.agebhar1.facelasticq.service.elasticsearch;

import static org.elasticsearch.common.settings.Settings.settingsBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.Closeable;
import java.io.IOException;

import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacebookNode implements Closeable {
	
	private final static Logger logger = LoggerFactory.getLogger(FacebookNode.class);

	private final Node node;
	
	/*
	 *  http://stackoverflow.com/questions/27603570/elasticsearch-noshardavailableactionexception-after-startup
	 */
	private static void waitForYellowStatus(final Node node) {
		logger.info("Wait for Elasticsearch Yellow node status.");
		node.client().admin().cluster().prepareHealth().setWaitForYellowStatus().execute().actionGet(5000);		
	}
	
	@Autowired
	public FacebookNode(final ElasticsearchConfiguration config) {
		
		logger.info("Create instance of class '{}' with {}.", getClass().getCanonicalName(), config);
		
		if (config == null) {
			throw new IllegalArgumentException("Elasticsearch embedded node configuration MUST not be null.");
		}
				
		node = nodeBuilder().settings(
				settingsBuilder()
					.put("path.home", config.getPath().getHome())
					.put("http.enabled", config.getHttp().isEnabled())
					.put("http.port", config.getHttp().getPort())
					.put("node.name", config.getNode().getName())
				)
				.local(true)
				.data(true)
				.node();
		
		waitForYellowStatus(node);
		
	}

	@Override
	public void close() throws IOException {
		logger.info("Try to shutdown embedded Elasticsearch node '{}'.", node.settings().get("name"));
		node.close();
	}

}
