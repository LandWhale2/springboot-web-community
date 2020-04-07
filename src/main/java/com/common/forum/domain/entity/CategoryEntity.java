package com.common.forum.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "category")
public class CategoryEntity {
	
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	
	@Column(length=10, nullable=false)
	private String name;
	
	
	@OneToMany(mappedBy="categoryEntity", fetch = FetchType.LAZY)
	private List<PostEntity> post = new ArrayList<>();
	
	
//	public void addPost(PostEntity postentity) {
//		if (this.post.isEmpty()) {
//			this.post.add(postentity);
//		}
//	}
	
	
	
	
	
	
	@Builder
	public CategoryEntity(Long id, String name, List<PostEntity> post) {
		this.id= id;
		this.name = name;
		this.post = post;
	}
	
	
}
