package com.code4ro.legalconsultation.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="assigments")
@Getter
@Setter
public class Assignment extends BaseEntity {

    private UUID userId;

    private UUID documentNodeId;

    private AssigmentStatus status = AssigmentStatus.ACTIVE;

}
