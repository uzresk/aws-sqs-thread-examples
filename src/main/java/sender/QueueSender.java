package sender;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import common.AwsClientProvider;

/**
 * SQSにメッセージを詰め込むサンプルです。
 *
 * @author uzresk
 *
 */
public class QueueSender {

	private static final String QUEUE = "MyQueue";

	public static void main(String[] args) {

		AmazonSQS sqs = AwsClientProvider.createSQSClient();

		CreateQueueRequest request = new CreateQueueRequest(QUEUE);
		String queueUrl = sqs.createQueue(request).getQueueUrl();

		for (int i = 0; i < 100; i++) {
			sqs.sendMessage(new SendMessageRequest(queueUrl, Integer
					.toString(i)));
		}
		sqs.shutdown();
	}
}
