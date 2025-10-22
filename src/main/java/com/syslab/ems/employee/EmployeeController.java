package com.syslab.ems.employee;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public Employee create(@Valid @RequestBody Employee e) {
        return service.create(e);
    }

    @GetMapping
    public Page<Employee> list(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size) {
        return service.list(page, size);
    }

    @GetMapping("/{id}")
    public Employee get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @Valid @RequestBody Employee patch) {
        return service.update(id, patch);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/search")
    public Page<Employee> search(@RequestParam String q,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size) {
        return service.search(q, page, size);
    }
}
