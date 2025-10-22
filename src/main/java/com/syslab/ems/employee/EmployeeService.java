package com.syslab.ems.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee create(Employee e) {
        return repo.save(e);
    }

    public Page<Employee> list(int page, int size) {
        return repo.findAll(PageRequest.of(page, size));
    }

    public Employee get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found: " + id));
    }

    public Employee update(Long id, Employee patch) {
        Employee e = get(id);
        if (patch.getFirstName() != null) e.setFirstName(patch.getFirstName());
        if (patch.getLastName() != null) e.setLastName(patch.getLastName());
        if (patch.getEmail() != null) e.setEmail(patch.getEmail());
        if (patch.getDepartment() != null) e.setDepartment(patch.getDepartment());
        if (patch.getSalary() >= 0) e.setSalary(patch.getSalary());
        return repo.save(e);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Page<Employee> search(String q, int page, int size) {
        return repo.search(q == null ? "" : q, PageRequest.of(page, size));
    }
}
