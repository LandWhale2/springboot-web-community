package com.common.forum.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.forum.domain.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	Optional<MemberEntity> findByEmail(String userEmail);

	Optional<MemberEntity> findByAuthkey(String authkey);

	Optional<MemberEntity> findByNickname(String username);

}
