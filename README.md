# aws-sdk-java-v2-dynamoDB-poc

## Based On:

1. [AWS Developer guide for Java SDK version 2](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/examples-dynamodb.html)
2. [AWS DOC SDK Examples - DynamoDB](https://github.com/awsdocs/aws-doc-sdk-examples/tree/master/javav2/example_code/dynamodb/src/main/java/com/example/dynamodb)

## Pre-Requisite:

1. [Docker installed](https://www.docker.com/)
2. [Locally running DynamoDB](https://github.com/instructure/dynamo-local-admin-docker)

## Usage:

If you are running Intellij or any other IDEs, pass these as Program arguments from Run/ Debug Configurations.
1. CreateTable : HelloWorld (create new table HelloWorld with a simple primary key: "Name")
2. PutItem : HelloWorld Tahniat age=31 (creates new entry in the HelloWorld table with pk name=Tahniat & age=31)
3. GetItem : HelloWorld Tahniat (retrieves the row from HelloWorld table having pk name=Tahniat)
4. DeleteItem : HelloWorld Tahniat (deletes a row from HelloWorld table having pk name=Tahniat)
5. Query : HelloWorld Name Tahniat (queries on a partition key (pk/gsi). Here it queries on table HelloWorld over the pk name where name is Tahniat)
