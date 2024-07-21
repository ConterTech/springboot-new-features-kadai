package com.example.samuraitravel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.ReviewEntity;
import com.example.samuraitravel.entity.Reviewpk;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Reviewpk>{
	Page<ReviewEntity> findByIdOrderByCreatedAtDesc(Integer houseId, Pageable pageable);
}
