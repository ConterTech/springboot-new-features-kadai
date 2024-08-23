package com.example.samuraitravel.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.samuraitravel.entity.FavoriteEntity;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FavoriteController {
	private final HouseRepository houseRepository;
	private final FavoriteRepository favoriteRepository;
	private final FavoriteService favoriteService;
	
	@GetMapping("/favorites")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
        User user = userDetailsImpl.getUser();
        Integer userId = user.getId();
        
        List<FavoriteEntity> favorites = favoriteRepository.findByUser(user);
        List<Integer> houseIdList = new ArrayList<>();
        for (FavoriteEntity favorite : favorites) {
            Integer houseId = favorite.getId();
            houseIdList.add(houseId);
        }
        
        Page<House> housePage = houseRepository.findByIdIn(houseIdList, pageable);
//        housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable); 
        model.addAttribute("housePage", housePage);         
        
        return "favorites/index";
    }
	
	@PostMapping("/houses/{id}/addFavorite")
	public String addFavorite(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		Integer userId = user.getId();
		favoriteService.addFavorite(userId, id);
		
		return "redirect:/houses/{id}";
	}
	
	@PostMapping("/houses/{id}/deleteFavorite")
	public String deleteFavorite(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		
		favoriteRepository.deleteByUserAndId(user, id);
		
		return "redirect:/houses/{id}";
	}
}
