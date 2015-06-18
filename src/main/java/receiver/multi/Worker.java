package receiver.multi;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import common.AwsClientProvider;

public class Worker implements Runnable {

	private static final String QUEUE = "MyQueue";

	private String command;

	public Worker(String s) {
		this.command = s;
	}

	@Override
	public void run() {

		System.out.println(Thread.currentThread().getName()
				+ " Start. Command = " + command);

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

		System.out.println(Thread.currentThread().getName() + " End.");
	}

	private void deleteMessage(AmazonSQS sqs, Message message) {

		DeleteMessageRequest deleteRequest = new DeleteMessageRequest();
		deleteRequest.setQueueUrl(sqs.createQueue(QUEUE).getQueueUrl());
		deleteRequest.setReceiptHandle(message.getReceiptHandle());
		sqs.deleteMessage(deleteRequest);
	}
}
