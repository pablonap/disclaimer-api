package com.fivvy.disclaimerapi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Comparator;
import java.util.Objects;

@DynamoDBTable(tableName = "acceptance")
public class Acceptance {
    @DynamoDBHashKey
    private String disclaimerId;

    @DynamoDBAttribute
    private String userId;

    @DynamoDBAttribute(attributeName = "create_at")
    private String createAt;

    public String getDisclaimerId() {
        return disclaimerId;
    }

    public void setDisclaimerId(String disclaimerId) {
        this.disclaimerId = disclaimerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Disclaimer)) {
            return false;
        }

        return 0 == Comparator.comparing(Acceptance::getDisclaimerId)
                .thenComparing(Acceptance::getUserId)
                .thenComparing(Acceptance::getCreateAt)
                .compare(this, (Acceptance) obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getDisclaimerId(),
                getUserId(),
                getCreateAt());
    }

    @Override
    public String toString() {
        return "Acceptance{" +
                "disclaimerId='" + disclaimerId + '\'' +
                ", userId='" + userId + '\'' +
                ", createAt='" + createAt + '\'' +
                '}';
    }
}
