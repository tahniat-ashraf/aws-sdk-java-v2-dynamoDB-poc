package com.bkash.rnd.dynamoDB;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2019-08-27
 */
public class GetItem
{
    public static void main(String[] args)
    {
        final String USAGE = "\n" +
                "Usage:\n" +
                "    GetItem <table> <name> [projection_expression]\n\n" +
                "Where:\n" +
                "    table - the table to get an item from.\n" +
                "    name  - the item to get.\n\n" +
                "You can add an optional projection expression (a quote-delimited,\n" +
                "comma-separated list of attributes to retrieve) to limit the\n" +
                "fields returned from the table.\n\n" +
                "Example:\n" +
                "    GetItem HelloTable World\n" +
                "    GetItem SiteColors text \"default, bold\"\n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String table_name = args[0];
        String name = args[1];
        String projection_expression = null;

        // if a projection expression was included, set it.
        if (args.length == 3) {
            projection_expression = args[2];
        }

        System.out.format("Retrieving item \"%s\" from \"%s\"\n",
                name, table_name);

        // snippet-start:[dynamodb.java2.get_item.main]
        HashMap<String,AttributeValue> key_to_get =
                new HashMap<String,AttributeValue>();

        key_to_get.put("Name", AttributeValue.builder()
                .s(name).build());

        GetItemRequest request = null;
        if (projection_expression != null) {
            request = GetItemRequest.builder()
                    .key(key_to_get)
                    .tableName(table_name)
                    .projectionExpression(projection_expression)
                    .build();
        } else {
            request = GetItemRequest.builder()
                    .key(key_to_get)
                    .tableName(table_name)
                    .build();
        }

        DynamoDbClient ddb = DynamoDBHelper.getDynamoDBClient();

        try {
            Map<String, AttributeValue> returned_item =
                    ddb.getItem(request).item();
            if (returned_item != null) {
                Set<String> keys = returned_item.keySet();
                for (String key : keys) {
                    System.out.format("%s: %s\n",
                            key, returned_item.get(key).toString());
                }
            } else {
                System.out.format("No item found with the key %s!\n", name);
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // snippet-end:[dynamodb.java2.get_item.main]
    }
}

// snippet-end:[dynamodb.java2.get_item.complete]
