package com.fivvy.disclaimerapi.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.fivvy.disclaimerapi.model.Disclaimer;
import org.springframework.stereotype.Repository;

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
    public Disclaimer getById(String disclaimerId) {
        return dynamoDBMapper.load(Disclaimer.class, disclaimerId);
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
}
