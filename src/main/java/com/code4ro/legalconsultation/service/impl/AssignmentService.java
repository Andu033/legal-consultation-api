package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.common.exceptions.LegalValidationException;
import com.code4ro.legalconsultation.config.security.CurrentUserService;
import com.code4ro.legalconsultation.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.model.persistence.AssigmentStatus;
import com.code4ro.legalconsultation.model.persistence.Assignment;
import com.code4ro.legalconsultation.model.persistence.UserRole;
import com.code4ro.legalconsultation.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignmentService {

    private final CurrentUserService currentUserService;

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(CurrentUserService currentUserService,
            AssignmentRepository assignmentRepository) {
        this.currentUserService = currentUserService;
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAll() {
        return assignmentRepository.findAll();
    }

    public Assignment save(final Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment approve(final UUID assigmentId) {
        final ApplicationUser currentUser = currentUserService.getCurrentUser();
        if(currentUser == null || currentUser.getUser().getRole() != UserRole.ADMIN)
            throw new LegalValidationException("comment.Unauthorized.user", HttpStatus.BAD_REQUEST);

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
