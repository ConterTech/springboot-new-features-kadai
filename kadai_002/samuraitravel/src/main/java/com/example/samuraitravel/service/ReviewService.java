package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.ReviewEntity;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void createReview(ReviewRegisterForm reviewForm) {
		ReviewEntity review = new ReviewEntity();
		
		review.setReviewStar(reviewForm.getReviewStar());
		review.setReviewText(reviewForm.getReviewText());
		
		reviewRepository.save(review);
	}
	
	//public  find(String houseId, Pageable pageable)
}
