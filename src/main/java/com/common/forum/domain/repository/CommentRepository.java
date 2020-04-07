package com.common.forum.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.forum.domain.entity.CommentEntity;
import com.common.forum.domain.entity.PostEntity;



public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

	List<CommentEntity> findAllByPostEntity(PostEntity postentity);

}
