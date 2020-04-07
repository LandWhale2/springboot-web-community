package com.common.forum.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.forum.domain.entity.CategoryEntity;





public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {


	Optional<CategoryEntity> findByName(String category);

}
