package common;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class AwsClientProvider {

	public static AmazonSQS createSQSClient() {
		AWSCredentialsProvider provider = new ClasspathPropertiesFileCredentialsProvider();

		ClientConfiguration cc = new ClientConfiguration();
//		cc.setProxyHost("PROXY_HOST");
//		cc.setProxyPort(PROXY_PORT);

		AmazonSQS sqs = new AmazonSQSClient(provider, cc);
		sqs.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		return sqs;
	}
}
