// package com.bugtrack.project;

// import com.bugtrack.user.User;
// import jakarta.persistence.*;
// import java.time.LocalDateTime;
// import java.util.UUID;

// @Entity
// @Table(name = "projects")
// public class Project {

//     @Id
//     @GeneratedValue(strategy = GenerationType.UUID)
//     private UUID id;

//     @Column(nullable = false, length = 100)
//     private String name;

//     @Column(name = "invite_code", unique = true, length = 10)
//     private String inviteCode;

//     @ManyToOne
//     @JoinColumn(name = "created_by", nullable = false)
//     private User createdBy;

//     @Column(name = "created_at")
//     private LocalDateTime createdAt;

//     @PrePersist
//     protected void onCreate() {
//         this.createdAt = LocalDateTime.now();
//     }

//     public UUID getId() { return id; }
//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }
//     public String getInviteCode() { return inviteCode; }
//     public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
//     public User getCreatedBy() { return createdBy; }
//     public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
//     public LocalDateTime getCreatedAt() { return createdAt; }
// }





package com.bugtrack.project;

import com.bugtrack.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "invite_code", nullable = false, unique = true, length = 10)
    private String inviteCode;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.inviteCode == null) {
            this.inviteCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        }
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getInviteCode() { return inviteCode; }
    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}