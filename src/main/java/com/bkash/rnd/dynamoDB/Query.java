package com.bkash.rnd.dynamoDB;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2019-08-27
 */
// snippet-start:[dynamodb.java2.query.complete]
// snippet-start:[dynamodb.java2.query.import]
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalOperator;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.regions.Region;

// snippet-end:[dynamodb.java2.query.import]
/**
 * Query a DynamoDB table.
 *
 * Takes the name of the table to update, the read capacity and the write
 * capacity to use.
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class Query
{
    public static void main(String[] args)
    {
        final String USAGE = "\n" +
                "Usage:\n" +
                "    Query <table> <partitionkey> <partitionkeyvalue>\n\n" +
                "Where:\n" +
                "    table - the table to put the item in.\n" +
                "    partitionkey  - partition key name of the table.\n" +
                "    partitionkeyvalue - value of the partition key that should match.\n\n" +
                "Example:\n" +
                "    Query GreetingsTable Language eng \n";

        if (args.length < 3) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String table_name = args[0];
        String partition_key_name = args[1];
        String partition_key_val = args[2];
        String partition_alias = "#a";

        System.out.format("Querying %s", table_name);
        System.out.println("");


        // snippet-start:[dynamodb.java2.query.main]
        DynamoDbClient ddb = DynamoDBHelper.getDynamoDBClient();

        //set up an alias for the partition key name in case it's a reserved word
        HashMap<String,String> attrNameAlias =
                new HashMap<String,String>();
        attrNameAlias.put(partition_alias, partition_key_name);

        //set up mapping of the partition name with the value
        HashMap<String, AttributeValue> attrValues =
                new HashMap<String,AttributeValue>();
        attrValues.put(":"+partition_key_name, AttributeValue.builder().s(partition_key_val).build());

        QueryRequest queryReq = QueryRequest.builder()
                .tableName(table_name)
                .keyConditionExpression(partition_alias + " = :" + partition_key_name)
                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();

        try {
            QueryResponse response = ddb.query(queryReq);
            System.out.println("response = " + response);
            System.out.println(response.count());
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // snippet-end:[dynamodb.java2.query.main]
        System.out.println("Done!");
    }
}

// snippet-end:[dynamodb.java2.query.complete]