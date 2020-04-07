package com.common.forum.dto;

import com.common.forum.domain.entity.MemberEntity;
import com.common.forum.domain.entity.PostEntity;
import com.common.forum.domain.entity.PostlikeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
public class PostlikeDto {

	
	private Long id;
	private PostEntity postEntity;
	private MemberEntity memberEntity;
	
	
	
	public PostlikeEntity toEntity() {
		return PostlikeEntity.builder()
				.id(id)
				.postEntity(postEntity)
				.memberEntity(memberEntity)
				.build();
	}
	
	
	
	@Builder
	public PostlikeDto(Long id, PostEntity postEntity, MemberEntity memberEntity) {
		this.id = id;
		this.postEntity = postEntity;
		this.memberEntity = memberEntity;
	}
}
