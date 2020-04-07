package com.common.forum.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


//@MappedSuperclass <--테이블로 매핑하지않고 자식클래스에게 매핑정보를 상속하기위한 annotation
//@EntityListeners  <- JPA 에게 해당 Entity는 Auditing기능을 사용한다는것을 알리는 annotation

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {
	//abstract 는 추상클래스로 자식클래스에 의해 완성된다. 전체적인 구성을가지지못한채 설계만 가지고있는 클래스
	
	
	//@CreatedDate <- 최초생성시 생성일 주입, 생성일을 업데이트 할 필요가 없으므로 false속성 추가 
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;
	
	
	//Entity가 수정될때 수정일자를 주입하는 annotation
	@LastModifiedDate
	private LocalDateTime modifiedDate;

}
