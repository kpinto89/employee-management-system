package com.demo.employee.service;

import com.demo.employee.dto.EmployeeDTO;
import com.demo.employee.entity.Employee;
import com.demo.employee.exception.ResourceNotFoundException;
import com.demo.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeDTO create(EmployeeDTO dto) {
        Employee employee = mapToEntity(dto);
        return mapToDTO(repository.save(employee));
    }

    public List<EmployeeDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToDTO(employee);
    }

    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());

        return mapToDTO(repository.save(employee));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private Employee mapToEntity(EmployeeDTO dto) {
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .department(dto.getDepartment())
                .salary(dto.getSalary())
                .build();
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .build();
    }
}

