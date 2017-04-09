package com.example.domain.respository;

import com.example.domain.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by dubsta on 21.02.2017.
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {


}
