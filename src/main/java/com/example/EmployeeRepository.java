package com.example;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by dubsta on 21.02.2017.
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {


}
