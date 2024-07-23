package com.example.samuraitravel.form;

import com.example.samuraitravel.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRegisterForm {
	@NotNull
	private Integer id;

	@NotNull
	private User userId;
	
	@NotNull(message = "レビュー数を選択してください")
	private Integer reviewStar;

	@NotBlank(message = "コメントを入力してください。")
	private String reviewText;
}
