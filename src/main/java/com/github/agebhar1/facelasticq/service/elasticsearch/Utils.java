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
