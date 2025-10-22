package com.syslab.ems.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
        select e from Employee e
        where lower(e.firstName) like lower(concat('%', ?1, '%'))
           or lower(e.lastName)  like lower(concat('%', ?1, '%'))
           or lower(e.email)     like lower(concat('%', ?1, '%'))
           or lower(e.department) like lower(concat('%', ?1, '%'))
    """)
    Page<Employee> search(String q, Pageable pageable);
}
