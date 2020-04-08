package com.common.forum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.common.forum.domain.entity.CommentEntity;
import com.common.forum.domain.entity.PostEntity;
import com.common.forum.domain.repository.CommentRepository;
import com.common.forum.dto.CommentDto;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class CommentService {
	
	CommentRepository commentRepository;
	PostService postService;
	
	
	@Transactional
	public List<CommentDto> getCommentList(Long bno) {
		
		PostEntity postentity = postService.getPostEntity(bno);
		
		List<CommentEntity> commentEntities = commentRepository.findAllByPostEntity(postentity);
		
		List<CommentDto> commentDtoList = new ArrayList<>();
		
		
		for (CommentEntity commentEntity : commentEntities) {
			
			CommentDto commentDto = this.convertEntitytoDto(commentEntity);
			
			commentDtoList.add(commentDto);
		}
		
		
		return commentDtoList;
	}
	
	
	@Transactional
	private CommentDto convertEntitytoDto(CommentEntity commentEntity) {
		return CommentDto.builder()
				.id(commentEntity.getId())
				.writer(commentEntity.getWriter())
				.content(commentEntity.getContent())
				.createdDate(commentEntity.getCreatedDate())
				.build();
	}
	
	@Transactional
	private CommentDto convertEntitytoDtowithPostEntity(CommentEntity commentEntity) {
		return CommentDto.builder()
				.id(commentEntity.getId())
				.writer(commentEntity.getWriter())
				.content(commentEntity.getContent())
				.postEntity(commentEntity.getPostEntity())
				.createdDate(commentEntity.getCreatedDate())
				.build();
	}

	
	@Transactional
	public CommentEntity getCommentEntity(Long cno) {
		Optional<CommentEntity> commentEntityWrapper = commentRepository.findById(cno);
		CommentEntity commentEntity = commentEntityWrapper.get();
		
		return commentEntity;
	}
	
	
	
	@Transactional
	public Long saveComment(Long bno, String writer, String content) {
		
		PostEntity postEntity = postService.getPostEntity(bno);
		
		CommentDto commentDto =new CommentDto();
		commentDto.setWriter(writer);
		commentDto.setContent(content);
		commentDto.setPostEntity(postEntity);
		
		
		return commentRepository.save(commentDto.toEntity()).getId();
	}
	
	
	@Transactional
	public Long updateComment(Long cno, String content) {
		CommentEntity commentEntity = this.getCommentEntity(cno);
		CommentDto commentDto = this.convertEntitytoDtowithPostEntity(commentEntity);
		commentDto.setContent(content);
		
		return commentRepository.save(commentDto.toEntity()).getId();
	}
	
	
	@Transactional
	public void deleteComment(Long cno) {
		commentRepository.deleteById(cno);
	}
	
	

}
