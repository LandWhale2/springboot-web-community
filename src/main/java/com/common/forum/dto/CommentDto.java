package com.common.forum.dto;

import java.time.LocalDateTime;

import com.common.forum.domain.entity.CommentEntity;
import com.common.forum.domain.entity.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {
	
	
	private Long id;
	private String writer;
	private String content;
	private PostEntity postEntity;
	private LocalDateTime createdDate;
	
	
	
	public CommentEntity toEntity() {
		CommentEntity build = CommentEntity.builder()
				.id(id)
				.writer(writer)
				.content(content)
				.postEntity(postEntity)
				.build();
		
		return build;
	}
	
	
	
	
	
	
	@Builder
	public CommentDto(Long id, String writer, String content, LocalDateTime createdDate, PostEntity postEntity) {
		this.id = id;
		this.writer = writer;
		this.content = content;
		this.createdDate = createdDate;
		this.postEntity = postEntity;
	}
	
}
