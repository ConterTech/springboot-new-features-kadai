package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRegisterForm {
	
	@NotNull(message = "レビュー数を選択してください")
	private Integer reviewStar;
	
	@NotBlank(message = "コメントを入力してください。")
	private String reviewText;
}
