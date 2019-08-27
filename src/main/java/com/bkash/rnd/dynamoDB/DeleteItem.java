package com.bkash.rnd.dynamoDB;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2019-08-27
 */
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.util.HashMap;

// snippet-end:[dynamodb.java2.delete_item.import]
/**
 * Delete an item from a DynamoDB table.
 *
 * Takes the table name and item (primary key: "Name") to delete.
 *
 * **Warning** The named item will actually be deleted!
 *
 * This code expects that you have AWS credentials set up per:
 * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/setup-credentials.html
 */
public class DeleteItem
{
    public static void main(String[] args)
    {
        final String USAGE = "\n" +
                "Usage:\n" +
                "    DeleteItem <table> <name>\n\n" +
                "Where:\n" +
                "    table - the table to delete the item from.\n" +
                "    name  - the item to delete from the table,\n" +
                "            using the primary key \"Name\"\n\n" +
                "Example:\n" +
                "    DeleteItem HelloTable World\n\n" +
                "**Warning** This program will actually delete the item\n" +
                "            that you specify!\n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String table_name = args[0];
        String name = args[1];

        System.out.format("Deleting item \"%s\" from %s\n", name, table_name);

        // snippet-start:[dynamodb.java2.delete_item.main]
        HashMap<String,AttributeValue> key_to_get =
                new HashMap<String,AttributeValue>();

        key_to_get.put("Name", AttributeValue.builder()
                .s(name)
                .build());

        DeleteItemRequest deleteReq = DeleteItemRequest.builder()
                .tableName(table_name)
                .key(key_to_get)
                .build();

        DynamoDbClient ddb = DynamoDBHelper.getDynamoDBClient();

        try {
            ddb.deleteItem(deleteReq);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // snippet-end:[dynamodb.java2.delete_item.main]
        System.out.println("Done!");
    }
}

// snippet-end:[dynamodb.java2.delete_item.complete]

