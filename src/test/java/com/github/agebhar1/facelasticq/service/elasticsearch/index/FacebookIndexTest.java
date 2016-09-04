package com.github.agebhar1.facelasticq.service.elasticsearch.index;

import static java.lang.String.format;

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
import com.github.agebhar1.facelasticq.service.elasticsearch.ElasticsearchConfiguration;
import com.github.agebhar1.facelasticq.service.elasticsearch.EmbeddedNode;

public class FacebookIndexTest {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	@Test
	public void facebook_index_created() throws IOException, NodeValidationException {
		
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
		embeddedNode.ensure(new FacebookIndex());
		
		final RestTemplate template = new RestTemplate();
		
		final ResponseEntity<ObjectNode> response = template.getForEntity(format("http://localhost:%d/facebook", port), ObjectNode.class);
		
		System.out.println(response);
		
//		final String expected = format(
//				  "{ "
//				+ "  'name': '%s',"
//				+ "  'cluster_name':'elasticsearch',"
//				+ "  'version': {"
//				+ "    'number': '2.3.4',"
//				+ "    'build_hash': 'e455fd0c13dceca8dbbdbb1665d068ae55dabe3f',"
//				+ "    'build_timestamp': '2016-06-30T11:24:31Z',"
//				+ "    'build_snapshot': false,"
//				+ "    'lucene_version': '5.5.0'"
//				+ "  },"
//				+ "  'tagline': 'You Know, for Search'"
//				+ "}", name)
//				.replaceAll("'", "\"");
//		
//		final String actual = response.getBody().toString();
//		
//		assertThat(response.getStatusCode(), is(equalTo(OK)));
//		assertEquals(expected, actual, true);
		
		embeddedNode.close();
		
	}

}
