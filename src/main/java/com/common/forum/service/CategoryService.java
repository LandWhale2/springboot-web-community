package com.common.forum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.common.forum.domain.entity.CategoryEntity;
import com.common.forum.domain.repository.CategoryRepository;
import com.common.forum.dto.CategoryDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	
	@Transactional
	public List<CategoryDto> getCategoryList() {
		List<CategoryEntity> categoryEntities = categoryRepository.findAll();
		List<CategoryDto> categoryDtoList = new ArrayList<>();
		
		for ( CategoryEntity categoryEntity : categoryEntities) {
			CategoryDto categoryDTO = CategoryEntityToDto(categoryEntity);
			categoryDtoList.add(categoryDTO);
		}
		
		return categoryDtoList;
		
	}

	
	@Transactional
	public Long saveCategory(CategoryDto categoryDto) {
		return categoryRepository.save(categoryDto.toEntity()).getId();
	}
	
	
	
	private CategoryDto CategoryEntityToDto(CategoryEntity categoryEntity) {
		return CategoryDto.builder()
				.id(categoryEntity.getId())
				.name(categoryEntity.getName())
				.post(categoryEntity.getPost())
				.build();
	}
	
	
	
	
	@Transactional
	public CategoryDto getcategoryDto(String category) {
		Optional<CategoryEntity> CategoryEntityWrapper = categoryRepository.findByName(category);
		
		CategoryEntity categoryEntity = CategoryEntityWrapper.get();
		
		CategoryDto categoryDto = CategoryEntityToDto(categoryEntity);
		
		return categoryDto;
		
	}
	
	
	public CategoryEntity getCategoryEntity(String category) {
		
		Optional<CategoryEntity> CategoryEntityWrapper = categoryRepository.findByName(category);
		
		CategoryEntity categoryEntity = CategoryEntityWrapper.get();
		
		
		return categoryEntity;
		
	}
	
}
