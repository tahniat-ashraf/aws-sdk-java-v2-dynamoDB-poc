package com.bkash.rnd.dynamoDB;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2019-08-27
 */
public class PutItem
{
    public static void main(String[] args)
    {
        final String USAGE = "\n" +
                "Usage:\n" +
                "    PutItem <table> <name> [field=value ...]\n\n" +
                "Where:\n" +
                "    table    - the table to put the item in.\n" +
                "    name     - a name to add to the table. If the name already\n" +
                "               exists, its entry will be updated.\n" +
                "Additional fields can be added by appending them to the end of the\n" +
                "input.\n\n" +
                "Example:\n" +
                "    PutItem Cellists Pau Language=ca Born=1876\n";

        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String table_name = args[0];
        String name = args[1];
        ArrayList<String[]> extra_fields = new ArrayList<String[]>();

        // any additional args (fields to add to database)?
        for (int x = 2; x < args.length; x++) {
            String[] fields = args[x].split("=", 2);
            if (fields.length == 2) {
                extra_fields.add(fields);
            } else {
                System.out.format("Invalid argument: %s\n", args[x]);
                System.out.println(USAGE);
                System.exit(1);
            }
        }

        System.out.format("Adding \"%s\" to \"%s\"", name, table_name);
        if (extra_fields.size() > 0) {
            System.out.println("Additional fields:");
            for (String[] field : extra_fields) {
                System.out.format("  %s: %s\n", field[0], field[1]);
            }
        }

        // snippet-start:[dynamodb.java2.put_item.main]
        HashMap<String, AttributeValue> item_values =
                new HashMap<String,AttributeValue>();

        item_values.put("Name", AttributeValue.builder().s(name).build());

        for (String[] field : extra_fields) {
            item_values.put(field[0], AttributeValue.builder().s(field[1]).build());
        }

        DynamoDbClient ddb = DynamoDBHelper.getDynamoDBClient();
        PutItemRequest request = PutItemRequest.builder()
                .tableName(table_name)
                .item(item_values)
                .build();

        try {
            ddb.putItem(request);
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", table_name);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // snippet-end:[dynamodb.java2.put_item.main]
        System.out.println("Done!");
    }
}
