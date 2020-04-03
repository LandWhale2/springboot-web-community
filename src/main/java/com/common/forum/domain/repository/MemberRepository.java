package com.common.forum.domain.repository;

import java.util.Optional;

import com.common.forum.domain.entity.MemberEntity;

public interface MemberRepository {

	Optional<MemberEntity> findByEmail(String userEmail);

}
