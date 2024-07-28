package com.example.samuraitravel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Embeddable
@Data
public class Reviewpk {
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Id
	@JoinColumn(name = "user_id")
	@OneToOne
	private User user;
}
