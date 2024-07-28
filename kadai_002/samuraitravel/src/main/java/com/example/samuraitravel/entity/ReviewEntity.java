package com.example.samuraitravel.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "review")
@Data
@IdClass(value=Reviewpk.class)
public class ReviewEntity {
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Id
	@JoinColumn(name = "user_id")
	@OneToOne
	private User user;
	
	@Column(name = "review_star")
	private int reviewStar;
	
	@Column(name = "review_text")
	private String reviewText;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDate createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private LocalDate updatedAt;
	
	@Column(name = "delete_flag", insertable = false)
	private boolean deleteFlag;
}
