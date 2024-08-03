package com.example.samuraitravel.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	@NotNull(message = "レビュー数を選択してください")
	private int reviewStar;

	@NotBlank(message = "コメントを入力してください。")
	private String reviewText;
}
