package com.example.samuraitravel.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.ReviewEntity;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.repository.UserRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;

@Controller
@RequestMapping("/houses")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	private final HouseRepository houseRepository;
	private final UserRepository userRepository;
	private final ReviewService reviewService;

	public ReviewController(ReviewRepository reviewRepository, HouseRepository houseRepository,UserRepository userRepository,
			ReviewService reviewService) {
		this.reviewRepository = reviewRepository;
		this.houseRepository = houseRepository;
		this.reviewService = reviewService;
		this.userRepository = userRepository;
	}

	@GetMapping("/{id}/review")
	public String index(@PathVariable(name = "id") Integer id,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Page<ReviewEntity> reviews = reviewRepository.findByIdOrderByCreatedAtDesc(id, pageable);
		House house = houseRepository.getReferenceById(id);

		model.addAttribute("house", house);
		model.addAttribute("reviews", reviews);
		if(userDetailsImpl != null) {
			model.addAttribute("userId", userDetailsImpl.getUser().getId());
		}

		return "review/index";
	}

	@GetMapping("/{id}/reviewRegister")
	public String reviewRegister(@PathVariable(name = "id") Integer id, Model model) {
		House house = houseRepository.getReferenceById(id);

		model.addAttribute("house", house);
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());

		return "review/post";
	}

	@PostMapping("/{id}/createReview")
	public String createReview(@PathVariable(name = "id") Integer id,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, 
			BindingResult bindingResult,Model model) {

		if (bindingResult.hasErrors()) {
			House house = houseRepository.getReferenceById(id);
	        model.addAttribute("house", house);
	        model.addAttribute("reviewRegisterForm", reviewRegisterForm);
			return "review/post";
		}
		
		User user = userDetailsImpl.getUser();
		Integer userId = user.getId();
		
		reviewService.createReview(reviewRegisterForm, userId, id);

		return "redirect:/houses/{id}";
	}
	
	@GetMapping("/{id}/editReview")
	public String editReview(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		House house = houseRepository.getReferenceById(id);
		
		User user = userDetailsImpl.getUser();
		Integer userId = user.getId();
		Optional<ReviewEntity> optionalReview = reviewRepository.findByUserAndId(user, id);
		
		ReviewEntity review = optionalReview.get();
        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getReviewStar(), review.getReviewText());
        model.addAttribute("house", house);
        model.addAttribute("reviewEditForm", reviewEditForm);
        return "review/edit";
	}
	
	@PostMapping("/{id}/updateReview")
	public String updateReview(@PathVariable(name = "id") Integer id,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @ModelAttribute @Validated ReviewEditForm reviewEditForm, 
			BindingResult bindingResult,Model model) {
			
		if (bindingResult.hasErrors()) {
			House house = houseRepository.getReferenceById(id);
	        model.addAttribute("house", house);
	        model.addAttribute("reviewEditForm", reviewEditForm);
			return "review/edit";
		}
		User user = userDetailsImpl.getUser();
		Integer userId = user.getId();
		reviewService.updateReview(reviewEditForm, userId, id);

		return "redirect:/houses/{id}";
	}
}
