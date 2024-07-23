package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.ReviewEntity;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void createReview(ReviewRegisterForm reviewRegisterForm, User user, Integer id) {
		ReviewEntity review = new ReviewEntity();
		
		review.setId(id);
		review.setUserId(user.getId());
		review.setReviewStar(reviewRegisterForm.getReviewStar());
		review.setReviewText(reviewRegisterForm.getReviewText());
		
		reviewRepository.save(review);
	}
}
