package com.example.samuraitravel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class Favoritepk {
	@Id
	@Column(name = "id")
	private Integer id	;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
