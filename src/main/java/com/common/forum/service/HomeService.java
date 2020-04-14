package com.common.forum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.forum.domain.entity.CategoryEntity;
import com.common.forum.domain.entity.PostEntity;
import com.common.forum.domain.repository.PostRepository;
import com.common.forum.dto.PostDto;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class HomeService {
	
	private PostRepository postRepository;
	private CategoryService categoryService;
	private PostService postService;
	
	@Transactional
	public List<PostDto> getHomeBoard(String category)	{
		CategoryEntity categoryEntity = categoryService.getCategoryEntity(category);
		List<PostEntity> postEntities = postRepository.findTop10ByCategoryEntity(categoryEntity);
		List<PostDto> postDtoList = new ArrayList<>();
		
		
		for (PostEntity postEntity : postEntities) {
			PostDto postDto = postService.convertEntityToDto(postEntity);
			postDtoList.add(postDto);
		}
		
		
		return postDtoList;
	}
}

