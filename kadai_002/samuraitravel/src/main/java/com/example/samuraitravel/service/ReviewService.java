package com.example.samuraitravel.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.ReviewEntity;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.repository.UserRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	
	public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void createReview(ReviewRegisterForm reviewRegisterForm, Integer userId, Integer id ) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりませんでした。"));
		ReviewEntity review = new ReviewEntity();
		
		review.setId(id);
		review.setUser(user);
		review.setReviewStar(reviewRegisterForm.getReviewStar());
		review.setReviewText(reviewRegisterForm.getReviewText());
		
		reviewRepository.save(review);
	}
}
