package com.bkash.rnd.dynamoDB;

import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2019-08-27
 */
public class DynamoDBHelper {

    public static DynamoDbClient getDynamoDBClient() {
        final String endpoint = "http://localhost:8000";

        DynamoDbClientBuilder builder = DynamoDbClient.builder();
        builder.httpClient(ApacheHttpClient.builder().build());
        builder.endpointOverride(URI.create(endpoint));
        builder.region(Region.AP_SOUTHEAST_1);

        return builder.build();
    }
}
