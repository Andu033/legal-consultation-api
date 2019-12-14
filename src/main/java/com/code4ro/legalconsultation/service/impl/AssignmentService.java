package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.model.persistence.AssigmentStatus;
import com.code4ro.legalconsultation.model.persistence.Assignment;
import com.code4ro.legalconsultation.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAll() {
        return assignmentRepository.findAll();
    }

    public Assignment save(final Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment approve(final UUID assigmentId) {
        Optional<Assignment> optional = assignmentRepository.findById(assigmentId);
        if(optional.isPresent()){
            Assignment assignment = optional.get();
            assignment.setStatus(AssigmentStatus.COMPLETED);
            assignment = assignmentRepository.save(assignment);
            return assignment;
        }
        return null;
    }

    public Assignment revoke(final UUID assigmentId) {
        Optional<Assignment> optional = assignmentRepository.findById(assigmentId);
        if(optional.isPresent()){
            Assignment assignment = optional.get();
            assignment.setStatus(AssigmentStatus.REVOKED);
            assignment = assignmentRepository.save(assignment);
            return assignment;
        }
        return null;
    }
}
