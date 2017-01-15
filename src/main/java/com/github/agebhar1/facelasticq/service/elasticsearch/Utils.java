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

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Utils {
	
	public final static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	/*
	 *  http://stackoverflow.com/questions/27603570/elasticsearch-noshardavailableactionexception-after-startup
	 */
	public static void waitForYellowStatus(final Node node, final TimeValue timeout) {
		logger.info("Wait for Elasticsearch 'Yellow' node status.");
		node.client().admin().cluster().prepareHealth().setWaitForYellowStatus().get(timeout);		
	}

}
