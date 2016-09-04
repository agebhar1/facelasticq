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
package com.github.agebhar1.facelasticq.service.elasticsearch.index;

import static java.lang.String.format;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FacebookIndex implements IndexConfiguration {

	private final static Logger logger = LoggerFactory.getLogger(FacebookIndex.class);
	
	private final static String Index = "facebook";
	private final static String IndexVersion = "v1.0";
	
	public FacebookIndex() {
		logger.info("Create instance of class '{}'.", getClass().getCanonicalName());
	}

	@Override
	public void apply(final Client client) {
		
		final IndicesAdminClient indices = client.admin().indices();
		
		final IndicesExistsResponse response = indices.exists(new IndicesExistsRequest(Index)).actionGet();
		
		logger.info("Elasticsearch index '{}' exists: {}.", Index, response.isExists());
		
		if (!response.isExists()) {
			
			final String qualifiedIndexName = format("%s_%s", Index, IndexVersion);
			
			logger.info("Create Elasticsearch index '{}'.", qualifiedIndexName);
			final String mapping = format("elasticsearch/indices/%s.json", qualifiedIndexName);
			
			final CreateIndexRequestBuilder index = indices.prepareCreate(qualifiedIndexName);
			try(final InputStream json = getClass().getClassLoader().getResourceAsStream(mapping)) {
				
				if (json == null) {
					throw new IllegalStateException(format("Could not load index mapping '%s'.", mapping));
				}
				
				final String source = IOUtils.toString(json);
				logger.info("Index '{}' source: {}", qualifiedIndexName, source);
				
				final CreateIndexResponse res = index.setSource(source).get();
				logger.info("Acknowledged on Elasticsearch index creation: {}", res.isAcknowledged());
				
			} catch (final Exception e) {
				logger.error("Got excpetion of class '{}' with message '{}'.", e.getClass().getCanonicalName(), e.getMessage());
				throw new RuntimeException(e);
			}
			
		}
		
	}

}
