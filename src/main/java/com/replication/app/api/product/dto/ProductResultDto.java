package com.replication.app.api.product.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResultDto {

	private Long id;
	
	private String title;
	
	private String content;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime modifiedDate;
	
}
