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

import static com.github.agebhar1.facelasticq.service.elasticsearch.Utils.waitForYellowStatus;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.transport.Netty4Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmbeddedNode implements Closeable {
	
	static {
		System.setProperty("es.log4j.shutdownEnabled", "false");
	}
	
	private final static Logger logger = LoggerFactory.getLogger(EmbeddedNode.class);

	private final Node node;
	
	static class PluginNode extends Node {
		public PluginNode(final Settings settings) {
			super(InternalSettingsPreparer.prepareEnvironment(settings, null),
					Collections.singletonList(Netty4Plugin.class));
		}
	}
	
	@Autowired
	public EmbeddedNode(final ElasticsearchConfiguration config) throws NodeValidationException {
		
		logger.info("Create instance of class '{}' with {}.", getClass().getCanonicalName(), config);
		
		if (config == null) {
			throw new IllegalArgumentException("Elasticsearch embedded node configuration MUST not be null.");
		}
		
		node = new PluginNode(Settings.builder()
				.put("path.home", config.getPath().getHome())
				.put("http.enabled", config.getHttp().isEnabled())
				.put("http.port", config.getHttp().getPort())
				.put("transport.type", "local")
				.put("discovery.type", "zen")
				.put("node.name", config.getNode().getName())
				.put("node.master", true)
				.put("node.data", true)
				.put("node.ingest", true)
				.put("cluster.name", "elasticsearch")
				.build()
			);
		node.start();
		
		waitForYellowStatus(node, new TimeValue(5000));
		
	}

	@Override
	public void close() throws IOException {
		logger.info("Try to shutdown embedded Elasticsearch node '{}'.", node.settings().get("node.name"));
		node.close();
	}

}
