package com.common.forum.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.common.forum.domain.entity.CategoryEntity;
import com.common.forum.domain.entity.CommentEntity;
import com.common.forum.domain.entity.MemberEntity;
import com.common.forum.domain.entity.PostEntity;



@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostDto {
	private Long id;
	private String writer;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private CategoryEntity categoryEntity;
	private List<CommentEntity> comment;
	private String category;
	private int hit;
	private int commentcount;
	private MemberEntity memberEntity;
	
	
	
	
	
	public void setCommentCount() {
		this.commentcount = this.comment.size();
	}
	
	
	
	
	//dto에 필요한 부분을 빌더패턴을 통해 entity로 만듭니다.
	//dto는 Controller <> Service <> Repository 간에필요한 데이터를캡슐화한 데이터전달객체
	
	public PostEntity toEntity(){
        PostEntity build = PostEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .categoryEntity(categoryEntity)
                .comment(comment)
                .hit(hit)
                .memberEntity(memberEntity)
                .build();
        return build;
    }
	
	//빌더 패턴의 장점, 멤버변수별로 메서드가 있으므로 인스턴스생성이 명확해진다.
	//필수적인 멤버변수와 선택적인 멤버변수를 효과적으로 구분할수있다.
	//getter, setter 는 필수변수와 선택적인변수는 로직으로 구분하여 인스턴스를 생성해야한다.
	//이렇게되면 런타임시에 오류를 많이 보게되고 , 빌더패턴은 이를줄여준다.
	
	@Builder
    public PostDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate, int hit, CategoryEntity categoryEntity, List<CommentEntity> comment, MemberEntity memberEntity) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.hit = hit;
        this.categoryEntity = categoryEntity;
        this.comment = comment;
        this.memberEntity = memberEntity;
    }
	

}
