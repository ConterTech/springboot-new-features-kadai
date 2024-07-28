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
	public String index(@PathVariable(name = "id") Integer id,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Page<ReviewEntity> reviews = reviewRepository.findByIdOrderByCreatedAtDesc(id, pageable);
		House house = houseRepository.getReferenceById(id);

		model.addAttribute("house", house);
		model.addAttribute("reviews", reviews);

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
			@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			BindingResult bindingResult,Model model) {

		User user = userDetailsImpl.getUser();
		Integer userId = user.getId();
		System.out.println(id);
		System.out.println(user);
			
		if (bindingResult.hasErrors()) {
			return "review/post";
		}

		reviewService.createReview(reviewRegisterForm, userId, id);

		return "redirect:/houses";
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
}
