package com.common.forum.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.common.forum.domain.entity.MemberEntity;
import com.common.forum.domain.entity.PostEntity;
import com.common.forum.domain.entity.PostlikeEntity;
import com.common.forum.domain.repository.PostlikeRepository;
import com.common.forum.dto.PostlikeDto;

import lombok.AllArgsConstructor;



@AllArgsConstructor
@Service
public class PostlikeService {
	
	private PostlikeRepository postlikeRepository;
	private MemberService memberService;
	private PostService postService;
	
	@Transactional
	public PostlikeDto PostlikeDtoSet(Long bno) {
		MemberEntity memberEntity = memberService.getMemberEntity();
		PostEntity postEntity = postService.getPostEntity(bno);
		PostlikeDto postlikeDto = new PostlikeDto();
		postlikeDto.setMemberEntity(memberEntity);
		postlikeDto.setPostEntity(postEntity);
		
		return postlikeDto;
	}
	
	
	@Transactional
	public void likeSaveOrDelete(PostlikeDto postlikeDto) {
		Optional<PostlikeEntity> postlikeEntityWrapper = 
				postlikeRepository.findByPostEntityAndMemberEntity(postlikeDto.getPostEntity(), postlikeDto.getMemberEntity());
		
		if (postlikeEntityWrapper.isPresent()) {
			System.out.println("좋아요 취소");
			this.deletelike(postlikeDto);
		}else {
			System.out.println("좋아요");
			this.savelike(postlikeDto);
		}
		
		
	}
	
	
	
	@Transactional
	public Long savelike(PostlikeDto postlikeDto) {
		PostlikeEntity postlikeEntity = postlikeDto.toEntity();
		
		
		return postlikeRepository.save(postlikeEntity).getId();
	}
	
	
	@Transactional
	public void deletelike(PostlikeDto postlikeDto) {
		
		postlikeRepository.deleteByPostEntityAndMemberEntity(postlikeDto.getPostEntity(), postlikeDto.getMemberEntity());
	}
	
	
	
	@Transactional
	public Long getPostlikecount(Long bno) {
		PostEntity postEntity = postService.getPostEntity(bno);
		return postlikeRepository.countByPostEntity(postEntity);
	}
	


	
	
	

}
