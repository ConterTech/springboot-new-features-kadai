package com.example.samuraitravel.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.ReviewEntity;
import com.example.samuraitravel.entity.Reviewpk;
import com.example.samuraitravel.entity.User;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Reviewpk>{
	public Page<ReviewEntity> findByIdOrderByCreatedAtDesc(Integer houseId, Pageable pageable);

	Optional <ReviewEntity> findByUserAndId(User user, Integer id);
	@Transactional
	Optional <ReviewEntity> deleteByUserAndId(User user, Integer id);

}
