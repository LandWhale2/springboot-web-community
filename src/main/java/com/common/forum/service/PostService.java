package com.common.forum.service;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.common.forum.domain.entity.CategoryEntity;
import com.common.forum.domain.entity.MemberEntity;
import com.common.forum.domain.entity.PostEntity;
import com.common.forum.domain.repository.CategoryRepository;
import com.common.forum.domain.repository.PostRepository;
import com.common.forum.dto.PostDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Bean 주입방식과 관련있음, 생성자로부터 Bean 객체를 받는방식을 해결해주는 Annotation,덕분 BoardServe 객체를
//주입받을때 @Autowirred 와같은 특별한 어노테이션을 부여하지않는다.
//Service 서비스 계층임을 명시해주는 Annotation

@AllArgsConstructor
@Service
public class PostService {
	private PostRepository postRepository;
	private CategoryService categoryService;
	private MemberService memberService;
	
	
	//get dto, entity
	@Transactional
	public PostDto getPostDto(Long id) {
		Optional<PostEntity> postEntityWrapper = postRepository.findById(id);
		PostEntity postEntity = postEntityWrapper.get();
		
		PostDto postDTO = this.convertEntityToDto(postEntity);
		postDTO.setCommentCount();
		return postDTO;
	}
	
	
	
	
	public PostEntity getPostEntity(Long id) {
		
		Optional<PostEntity> PostEntityWrapper = postRepository.findById(id);
		
		
		PostEntity PostEntity = PostEntityWrapper.get();
		
		
		return PostEntity;
		
	}
	
	
	
	//save, update, delete
	
	//선언적 트랜젝션으로 불리며 , 트랜젝션을 적용하는 Annotation
	@Transactional
    public void savePost(PostDto postDto) {
		MemberEntity memberEntity = memberService.getMemberEntity();
		CategoryEntity categoryEntity = categoryService.getCategoryEntity(postDto.getCategory());
		
		
		postDto.setWriter(memberEntity.getNickname());
		postDto.setCategoryEntity(categoryEntity);
		
		PostEntity postEntity = postRepository.save(postDto.toEntity());
		
		
		
		
		postEntity.setPostEntityAndCategoryEntity(categoryEntity);
		postEntity.setMemberAndPost(memberEntity);
        
    }
	
	@Transactional
	public void updatePost(PostDto postDto) {
		if (this.postauth(postDto.getId())) {
			PostEntity postEntity = this.getPostEntity(postDto.getId());
			PostDto postDTO = this.convertEntityToDto(postEntity);
			postDTO.setTitle(postDto.getTitle());
			postDTO.setContent(postDto.getContent());
			postDTO.setCategoryEntity(postDTO.getCategoryEntity());
			postDTO.setMemberEntity(postDTO.getMemberEntity());
			
			
			postRepository.save(postDTO.toEntity());
		}
		
		
	}
	
	
	
	@Transactional
    public void deletePost(Long id) {
		if (this.postauth(id)) {
			postRepository.deleteById(id);
		}
		
    }
	
	
	//convert
	
	
	//중복되는 작업을 개선하기위한 함수,
	@Transactional
	public PostDto convertEntityToDto(PostEntity postEntity) {
		return PostDto.builder()
				.id(postEntity.getId())
				.title(postEntity.getTitle())
				.content(postEntity.getContent())
				.writer(postEntity.getWriter())
				.createdDate(postEntity.getCreatedDate())
				.hit(postEntity.getHit())
				.categoryEntity(postEntity.getCategoryEntity())
				.comment(postEntity.getComment())
				.memberEntity(postEntity.getMemberEntity())
				.build();
	}
	
	
	
	
	
	@Transactional
	public List<PostDto> searchPosts(String keyword){
		List<PostEntity> postEntities = postRepository.findByTitleContaining(keyword);
		List<PostDto> postDtoList = new ArrayList<>();
		
		if (postEntities.isEmpty()) return postDtoList;
		
		for (PostEntity postEntity: postEntities) {
			PostDto postDto = this.convertEntityToDto(postEntity);
			postDto.setCommentCount();
			postDto.setCategory(postDto.getCategoryEntity().getName());
			postDtoList.add(postDto);
		}
		
		return postDtoList;
	}
	
	//페이징
	
	private static final int BLOCK_PAGE_NUM_COUNT = 10;// 블럭에 존재하는 페잊지수
	private static final int PAGE_POST_COUNT = 10;// 한페이지에 존재하는 게시글 수
	
	
	@Transactional
	public Long getPostCount(String category) {
		CategoryEntity categoryentity = categoryService.getCategoryEntity(category);
		return (long) postRepository.findAllByCategoryEntity(categoryentity).size();
	}
	
	
	
	public Integer[] getPageList(Integer curPageNum, String category) {
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
		
		// 총 게시글 수
		Double postsTotalCount = Double.valueOf(this.getPostCount(category));
		
		// 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
		Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));
		
		//현재 페이지를 기준으로 블럭의 마지막 페이지 번호를 계산
		Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;
		
		//페이지 시작번호 조정
		curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;
		
		//페이지 번호 할당
		for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }
		
		if (pageList[0] == null) {
			pageList = null;
		}
		
		return pageList;
		
	}
	
	
	//카테고리 기준 페이징
	
	
	
	@Transactional
	public List<PostDto> getPostListByCategoryid(String category, Integer pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, PAGE_POST_COUNT,Sort.by(Sort.Direction.ASC, "createdDate"));
		CategoryEntity categoryEntity = categoryService.getCategoryEntity(category);
		Page<PostEntity> page = postRepository.findAllByCategoryEntity(categoryEntity, pageRequest);
		
		List<PostEntity> postEntities = page.getContent();
		List<PostDto> postDtoList = new ArrayList<>();
		
		
		
		if (postEntities.isEmpty()) return postDtoList;
		
		for (PostEntity postEntity: postEntities) {
			PostDto postDto = this.convertEntityToDto(postEntity);
			postDto.setCommentCount();
			postDtoList.add(postDto);
		}
		
		
		return postDtoList;
	}
	
	
	@Transactional
	public void addhit(Long id) {
		PostEntity postEntity = this.getPostEntity(id);
		PostDto postDto = this.convertEntityToDto(postEntity);
		int posthit = postDto.getHit() + 1;
		
		
		postDto.setHit(posthit);
		postDto.setCategoryEntity(postDto.getCategoryEntity());
		postDto.setMemberEntity(postDto.getMemberEntity());
		postRepository.save(postDto.toEntity());
		
	}
	
	
	private boolean postauth(Long id) {
		String postEmail = this.getPostEntity(id).getMemberEntity().getEmail();
		String userEmail = memberService.getMemberEntity().getEmail();
		
		if(postEmail == userEmail) {
			return true;
		}else {
			return false;
		}
	}
	
	
	@Transactional
	public List<PostDto> getReCommandPost() {
		int len = 1;
		List<PostEntity> postEntities = postRepository.findAll();
		List<PostDto> postDtoList = new ArrayList<>();
		for (PostEntity postEntity : postEntities) {
			if (postEntity.getPostlike().size() >= len) {
				PostDto postDto = this.convertEntityToDto(postEntity);
				postDto.setCommentCount();
				postDtoList.add(postDto);
			}
		}
		
		return postDtoList;
	}

}
