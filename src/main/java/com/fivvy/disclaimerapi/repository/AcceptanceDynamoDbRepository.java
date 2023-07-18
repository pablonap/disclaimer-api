package com.fivvy.disclaimerapi.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fivvy.disclaimerapi.model.Acceptance;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AcceptanceDynamoDbRepository implements AcceptanceRepository {
    private final DynamoDBMapper dynamoDBMapper;
    public AcceptanceDynamoDbRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }
    @Override
    public void save(Acceptance acceptance) {
        dynamoDBMapper.save(acceptance);
    }

    @Override
    public List<Acceptance> findAllByUserId(String userId) {
        Map<String, AttributeValue> eav = Map.of(":val1", new AttributeValue().withS(userId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :val1")
                .withExpressionAttributeValues(eav);

        return dynamoDBMapper.scan(Acceptance.class, scanExpression);
    }

    @Override
    public List<Acceptance> findAll() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(Acceptance.class, scanExpression);
    }
}
