package com.common.forum.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;




@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="postlike")
public class PostlikeEntity {
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name="board_id")
    private PostEntity postEntity;
	
	@ManyToOne
    @JoinColumn(name="member_id")
	private MemberEntity memberEntity;
	
	
	public void setPostAndMemberEntity() {
		this.postEntity.getPostlike().add(this);
//		this.memberEntity.getPostlikes().add(this);
	}
	
	public void removePostAndMemberEntity() {
		this.postEntity.getPostlike().remove(this);
//		this.memberEntity.getPostlikes().remove(this);
	}
	
	
	
	
	
	@Builder
	public PostlikeEntity(Long id, PostEntity postEntity ,MemberEntity memberEntity) {
		this.id = id;
		this.postEntity = postEntity;
		this.memberEntity = memberEntity;
	}

}
