package com.common.forum.dto;

import java.util.List;

import com.common.forum.domain.entity.CategoryEntity;
import com.common.forum.domain.entity.PostEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryDto {
	
	
	private Long id;
	
	
	private String name;
	
	
	private List<PostEntity> post;
	
	
	
	public CategoryEntity toEntity() {
		CategoryEntity build = CategoryEntity.builder()
				.id(id)
				.name(name)
				.post(post)
				.build();
		return build;
	}
	
	
	
	
	@Builder
	public CategoryDto(Long id, String name, List<PostEntity> post) {
		this.id = id;
		this.name = name;
		this.post = post;
	}
	
	
	
	
}
