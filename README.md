# aws-sqs-thread-examples

aws-sqs-thread-examples is an aws sdk java examples for [Aws Simple Queue Service](http://aws.amazon.com/sqs/)

## Provides

* QueueSender.java send a simple message to the queue
* SingleThreadQueueReceiver.java Take out the message from the queue in a single thread .
* MultiThreadQueueReceiver.java Take out the message from the queue in a multi thread .

## Requires

1. Java8
2. AWS Account

## Usage

### Get the code
`$ git clone https://github.com/uzresk/aws-sqs-thead-examples.git`

### Create Queue

1.Create a queue named "MyQueue".  
2.To get the access key secret access key for accessing the AWS  
3.grant permissions to the queue using the IAM

### Create AwsCredentials.properties

Please set to get the access key secret access key AwsCredentials.properties.

    accessKey=XXXXXXXXXXXXXXXXXXX
    secretKey=XXXXXXXXXXXXXXXXXXX

### (Option)ProxySetting

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
	
### Run

1. QueueSender
2. SingleThreadQueueReceiver or MultiThreadQueueReceiver


