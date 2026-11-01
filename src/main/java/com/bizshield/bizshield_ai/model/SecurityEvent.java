
package com.bizshield.bizshield_ai.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_events")
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventTime;

    public SecurityEvent() {
    }

    public SecurityEvent(String eventType, String description) {
        this.eventType = eventType;
        this.description = description;
        this.eventTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }
}