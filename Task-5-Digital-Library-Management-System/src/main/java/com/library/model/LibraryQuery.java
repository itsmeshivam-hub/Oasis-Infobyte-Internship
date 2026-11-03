package com.library.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LibraryQuery {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private AppUser user;
    @Column(nullable=false, length=2000) private String message;
    private LocalDateTime submittedAt = LocalDateTime.now();
    private boolean resolved = false;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public AppUser getUser(){return user;} public void setUser(AppUser user){this.user=user;}
    public String getMessage(){return message;} public void setMessage(String message){this.message=message;}
    public LocalDateTime getSubmittedAt(){return submittedAt;} public void setSubmittedAt(LocalDateTime submittedAt){this.submittedAt=submittedAt;}
    public boolean isResolved(){return resolved;} public void setResolved(boolean resolved){this.resolved=resolved;}
}
