package com.example.flight;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface FlightRepository extends PagingAndSortingRepository<Flight, Long> {

	
}
