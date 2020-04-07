package com.common.forum.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Type;


//Entity 클래스는테이블과 관련이있는것을 알수있음, 비지니스로직은 엔티티를 기준으로 돌아가기때문에
//엔티티를 Request, Response 용도로 사용하는것은 적절하지못함, 
// 그래서 데이터 전달목적을 갖는 dto 클래스를 정의하여 사용함.




//@NoArgsConstructor <-파라미터가 없는 기본생성자를 추가하는 annotation(JPA 사용을 위해 기본생성자 필수)
//@Getter <- 모든 필드에 getter를 자동 생성해주는 annotation
//@Setter annotation은 setter 를 자동생성해주지만 무분별한 사용은 안정성을 보장받기 어려움
//@Setter + @Getter 모두 해결해주는 @Data annotation도 존재
//@Entity 객체를 테이블과 매핑할 entity라고 JPA 에게 알려주는 역활을함 @Entity가 붙은클래스는 모두
//JPA가 관리하며 , 이를 엔티티 클래스 라고함.
//##주의사항## 1. 기본생성자는 꼭 존재해야함, 2.final class, inner class, enum, interface사용불가
//@Table <- Entity클래스와 매되는 테이블의 정보를 명시하는 annotation	
//name 속성으로테이블명을 작성할수있으며, 생략시 엔티티이름이 테이블명으로 자동으로 매핑된다. 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "post")
public class PostEntity extends TimeEntity{
	
	//@Id -테이블의 기본키임을 명시하는 annotation
	//@Id annotation이 있는 필드를 식별자 필드라고 함.
	//@GeneratedValue -<기본키로 대체키를 사용할떄, 키본키 값 생성 전략을 명시함.
	//strategy= GenerationType.IDENTITY <-- 기본키 생성을데이터베이스에 위임함.
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    
    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryEntity categoryEntity;
    
    @OneToMany(mappedBy="postEntity", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comment = new ArrayList<>();
    
    
    @OneToMany(mappedBy="postEntity", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostlikeEntity> postlike = new ArrayList<>();
    
    
    
    @Column(columnDefinition = "integer default 0")
    private int hit;
    
    
    
    
    
    
    
    
	
    public void setPostEntityAndCategoryEntity(CategoryEntity categoryentity) {
    	
    	if(this.categoryEntity != null)
    		this.categoryEntity.getPost().remove(this);
    	
    	this.categoryEntity = categoryentity;
    	
    	categoryentity.getPost().add(this);
    	
    	
    }
    
    
    
	//Builder 패턴 클래시를 생성해주는 annotation, @Setter 사용대신 빌더패턴을 사용해야  안정성을 보장가능
	
    @Builder
    public PostEntity(Long id, String title, String writer, String content, CategoryEntity categoryEntity,  List<CommentEntity> comment,int hit) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.categoryEntity = categoryEntity;
        this.comment = comment;
//        this.setPostEntityAndCategoryEntity(categoryEntity);
        this.hit = hit;
        
    }

}
