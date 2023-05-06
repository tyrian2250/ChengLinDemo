package com.chengLinDemo.chengLinDemo;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

@Entity
@Table( name = "\"NoteBoard\"" )
public class NoteBoard {
    
    @Id
    @GeneratedValue
    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "timestamp_without_timezone")
    @CreationTimestamp
    private Timestamp timestamp_without_timezone;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public Timestamp getTimestamp_without_timezone() {
        return timestamp_without_timezone;
    }

    public void setTimestamp_without_timezone(Timestamp timestamp_without_timezone) {
        this.timestamp_without_timezone = timestamp_without_timezone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
