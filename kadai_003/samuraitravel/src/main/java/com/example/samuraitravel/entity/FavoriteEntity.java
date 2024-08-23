
package com.example.samuraitravel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="favorites")
@Data
@IdClass(value=Favoritepk.class)
public class FavoriteEntity {
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
