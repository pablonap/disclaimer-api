package com.fivvy.disclaimerapi.repository;

import com.fivvy.disclaimerapi.model.Disclaimer;

import java.util.List;
import java.util.Optional;

public interface DisclaimerRepository {
    void save(Disclaimer disclaimer);
    Optional<Disclaimer> findById(String disclaimerId);
    void delete(String disclaimerId);
    void update(String disclaimerId, Disclaimer disclaimer);
    List<Disclaimer> findAllByText(String text);
    List<Disclaimer> findAll();
}
