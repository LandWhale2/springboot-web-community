package com.common.forum.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.forum.domain.entity.MemberEntity;
import com.common.forum.domain.entity.PostEntity;
import com.common.forum.domain.entity.PostlikeEntity;





public interface PostlikeRepository extends JpaRepository<PostlikeEntity, Long>{

	Optional<PostlikeEntity> findByPostEntityAndMemberEntity(PostEntity postEntity, MemberEntity memberEntity);

	void deleteByPostEntityAndMemberEntity(PostEntity postEntity, MemberEntity memberEntity);

	List<PostlikeEntity> findAllByPostEntity(PostEntity postEntity);

	Long countByPostEntity(PostEntity postEntity);

	

}
