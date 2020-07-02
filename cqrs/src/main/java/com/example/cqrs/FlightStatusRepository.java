package com.example.cqrs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightStatusRepository extends CrudRepository<FlightStatus, Long> {


}
