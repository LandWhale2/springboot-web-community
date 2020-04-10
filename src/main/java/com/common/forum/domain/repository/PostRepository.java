package com.common.forum.domain.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.common.forum.domain.entity.CategoryEntity;
import com.common.forum.domain.entity.PostEntity;

import java.util.List;


//Repository 는 인터페이스로 정의하고 JpaRepository 인터페이스를 상속받으면됨
//JpaRepository의 제네릭 타입에는 Entity클래스와 Pk의타입을명시해주면됨
//JpaRepository에는 일반적으로 많이 사용하는 데이터 조작을 다루는 함수가 정의되어있기때문에,CRUD작업이편해짐
public interface PostRepository extends JpaRepository<PostEntity, Long>{
	//JpaRepository에서 메서드명의 By이후는 SQL 의 Where조건절에 대응되는것인데, Containing 붙여주면
	//Like 검색이 된다. 
	List<PostEntity> findByTitleContaining(String keyword);

	Page<PostEntity> findAllByCategoryEntity(CategoryEntity categoryEntity, Pageable pageable);

	List<PostEntity> findAllByCategoryEntity(CategoryEntity categoryentity);

	List<PostEntity> findTop10ByCategoryEntity(CategoryEntity categoryEntity);
}
