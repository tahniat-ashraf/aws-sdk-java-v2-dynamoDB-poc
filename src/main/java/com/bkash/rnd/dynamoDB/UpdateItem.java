package com.bkash.rnd.dynamoDB;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2019-08-27
 */
public class UpdateItem
{
    public static void main(String[] args)
    {
        final String USAGE = "\n" +
                "Usage:\n" +
                "    UpdateItem <table> <name> <greeting>\n\n" +
                "Where:\n" +
                "    table    - the table to put the item in.\n" +
                "    name     - a name to update in the table. The name must exist,\n" +
                "               or an error will result.\n" +
                "Additional fields can be specified by appending them to the end of the\n" +
                "input.\n\n" +
                "Examples:\n" +
                "    UpdateItem SiteColors text default=000000 bold=b22222\n" +
                "    UpdateItem SiteColors background default=eeeeee code=d3d3d3\n\n";

        if (args.length < 3) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String table_name = args[0];
        String name = args[1];
        ArrayList<String[]> extra_fields = new ArrayList<String[]>();

        // any additional args (fields to add or update)?
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

        // snippet-start:[dynamodb.java2.update_item.main]
        System.out.format("Updating \"%s\" in %s\n", name, table_name);
        if (extra_fields.size() > 0) {
            System.out.println("Additional fields:");
            for (String[] field : extra_fields) {
                System.out.format("  %s: %s\n", field[0], field[1]);
            }
        }

        HashMap<String, AttributeValue> item_key =
                new HashMap<String,AttributeValue>();

        item_key.put("Name", AttributeValue.builder().s(name).build());

        HashMap<String, AttributeValueUpdate> updated_values =
                new HashMap<String,AttributeValueUpdate>();

        for (String[] field : extra_fields) {
            updated_values.put(field[0], AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(field[1]).build())
                    .action(AttributeAction.PUT)
                    .build());
        }

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(table_name)
                .key(item_key)
                .attributeUpdates(updated_values)
                .build();

        DynamoDbClient ddb = DynamoDBHelper.getDynamoDBClient();

        try {
            ddb.updateItem(request);
        } catch (ResourceNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // snippet-end:[dynamodb.java2.update_item.main]
        System.out.println("Done!");
    }
}

// snippet-end:[dynamodb.java2.update_item.complete]