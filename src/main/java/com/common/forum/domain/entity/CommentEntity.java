package com.common.forum.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="comment")
public class CommentEntity extends TimeEntity{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 10, nullable = false)
    private String writer;
	
	@Column(nullable = false)
	@Type(type = "text")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="post_id")
	private PostEntity postEntity;
	
	
	
	@Builder
	public CommentEntity(Long id, String writer, String content, PostEntity postEntity) {
		this.id = id;
		this.writer = writer;
		this.content = content;
		this.postEntity = postEntity;
	}
	
}
