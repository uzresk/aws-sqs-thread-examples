package receiver.single;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import common.AwsClientProvider;

/**
 *
 * 単一スレッド内でQueue内のMessageを取得するサンプル
 *
 * @author uzresk
 *
 */
public class SingleThreadQueueReceiver {

	private static final String QUEUE = "MyQueue";

	public static void main(String[] args) {

		new SingleThreadQueueReceiver().run();

	}

	public void run() {

		long start = System.currentTimeMillis();

		AmazonSQS sqs = AwsClientProvider.createSQSClient();

		String queueUrl = sqs.createQueue(QUEUE).getQueueUrl();
		ReceiveMessageRequest request = new ReceiveMessageRequest(QUEUE)
				.withQueueUrl(queueUrl);
		request.setVisibilityTimeout(5);

		boolean queueExists = true;
		while (queueExists) {
			ReceiveMessageResult result = sqs.receiveMessage(request);
			if (result.getMessages().isEmpty()) {
				queueExists = false;
			} else {
				result.getMessages().stream().forEach(s -> {
					System.out.println("[" + s.getBody() + "]");
					deleteMessage(sqs, s);
				});
			}
		}

		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}

	private void deleteMessage(AmazonSQS sqs, Message message) {

		DeleteMessageRequest deleteRequest = new DeleteMessageRequest();
		deleteRequest.setQueueUrl(sqs.createQueue(QUEUE).getQueueUrl());
		deleteRequest.setReceiptHandle(message.getReceiptHandle());
		sqs.deleteMessage(deleteRequest);
	}
}
