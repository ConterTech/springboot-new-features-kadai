package com.example.samuraitravel.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.FavoriteEntity;
import com.example.samuraitravel.entity.Favoritepk;
import com.example.samuraitravel.entity.User;

import jakarta.transaction.Transactional;

@Transactional
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Favoritepk> {
	public FavoriteEntity getByIdAndUserId(Integer id, Integer userId);
	Optional <FavoriteEntity> findByUserAndId(User user, Integer id);
	List <FavoriteEntity> findByUser(User user);
	Optional<FavoriteEntity> deleteByUserAndId(User user, Integer id);
}
