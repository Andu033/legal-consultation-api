package com.code4ro.legalconsultation.controller;

import com.code4ro.legalconsultation.model.persistence.Assignment;
import com.code4ro.legalconsultation.service.impl.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

    private AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> getAll() {
        return new ResponseEntity<>(assignmentService.getAll(), HttpStatus.OK);
    }

    public ResponseEntity<Assignment> save(@RequestBody final Assignment assignment) {

        return new ResponseEntity<>(assignmentService.save(assignment), HttpStatus.OK);
    }

    public ResponseEntity<Void> approve(@PathVariable final UUID assignmentId){
        Assignment assignment = assignmentService.approve(assignmentId);
        if(assignment == null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> revoke(@PathVariable final UUID assignmentId){
        Assignment assignment = assignmentService.revoke(assignmentId);
        if(assignment == null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
