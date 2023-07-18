package com.fivvy.disclaimerapi.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.fivvy.disclaimerapi.model.Disclaimer;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DisclaimerDynamoDbRepository implements DisclaimerRepository {
    private final DynamoDBMapper dynamoDBMapper;

    public DisclaimerDynamoDbRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public void save(Disclaimer disclaimer) {
        dynamoDBMapper.save(disclaimer);
    }

    @Override
    public Optional<Disclaimer> findById(String disclaimerId) {
        return Optional.of(dynamoDBMapper.load(Disclaimer.class, disclaimerId));
    }

    @Override
    public void delete(String disclaimerId) {
        Disclaimer disclaimer = dynamoDBMapper.load(Disclaimer.class, disclaimerId);
        dynamoDBMapper.delete(disclaimer);
    }

    @Override
    public void update(String disclaimerId, Disclaimer disclaimer) {
        dynamoDBMapper.save(disclaimer,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("disclaimerId",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(disclaimerId)
                                )));
    }

    @Override
    public List<Disclaimer> findAllByText(String text) {
        Map<String, AttributeValue> eav = Map.of(":val1", new AttributeValue().withS(text));
        DynamoDBScanExpression expression = new DynamoDBScanExpression()
                .withFilterExpression("contains(#attrName, :val1)")
                .withExpressionAttributeValues(eav)
                .withExpressionAttributeNames(Collections.singletonMap("#attrName", "text"));

        return dynamoDBMapper.scan(Disclaimer.class, expression);
    }

    @Override
    public List<Disclaimer> findAll() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(Disclaimer.class, scanExpression);
    }
}
