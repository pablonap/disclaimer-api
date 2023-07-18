package com.fivvy.disclaimerapi.repository;

import com.fivvy.disclaimerapi.model.Disclaimer;

public interface DisclaimerRepository {
    void save(Disclaimer disclaimer);
    Disclaimer getById(String disclaimerId);
    void delete(String disclaimerId);
    void update(String disclaimerId, Disclaimer disclaimer);
}
