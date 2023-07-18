package com.fivvy.disclaimerapi.repository;

import com.fivvy.disclaimerapi.model.Acceptance;

import java.util.List;

public interface AcceptanceRepository {
    void save(Acceptance acceptance);
    List<Acceptance> findAllByUserId(String userId);
    List<Acceptance> findAll();

}
