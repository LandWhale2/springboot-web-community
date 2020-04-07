package com.common.forum.service;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.common.forum.domain.entity.CategoryEntity;
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
	private CategoryRepository categoryRepository;
	
	
	//get dto, entity
	@Transactional
	public PostDto getPostDto(Long id) {
		Optional<PostEntity> postEntityWrapper = postRepository.findById(id);
		PostEntity postEntity = postEntityWrapper.get();
		PostDto postDTO = this.convertEntityToDtowithoutCategoryEntity(postEntity);
		return postDTO;
	}
	
	
	public CategoryEntity getCategoryEntity(Long id) {
		Optional<CategoryEntity> CategoryEntityWrapper = categoryRepository.findById(id);
		
		CategoryEntity categoryEntity = CategoryEntityWrapper.get();
		
		
		return categoryEntity;
		
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
		
		Long categoryid = postDto.getCategoryid();
		
		CategoryEntity categoryEntity = this.getCategoryEntity(categoryid);
		
		Long postid = postRepository.save(postDto.toEntity(categoryEntity)).getId();
		
		PostEntity postEntity = this.getPostEntity(postid);
		
		postEntity.setPostEntityAndCategoryEntity(categoryEntity);
		
		
//		categoryEntity.addPost(postentity);
		
        
    }
	
	@Transactional
	public Long updatePost(PostDto postDto) {
		PostEntity postEntity = this.getPostEntity(postDto.getId());
		PostDto postDTO = this.convertEntityToDto(postEntity);
		postDTO.setTitle(postDto.getTitle());
		postDTO.setContent(postDto.getContent());		
		return postRepository.save(postDto.toEntity(postDTO.getCategoryEntity())).getId();
	}
	
	
	
	@Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
	
	
	//convert
	
	
	//중복되는 작업을 개선하기위한 함수,
	@Transactional
	private PostDto convertEntityToDto(PostEntity postEntity) {
		return PostDto.builder()
				.id(postEntity.getId())
				.title(postEntity.getTitle())
				.content(postEntity.getContent())
				.writer(postEntity.getWriter())
				.createdDate(postEntity.getCreatedDate())
				.hit(postEntity.getHit())
				.categoryEntity(postEntity.getCategoryEntity())
				.comment(postEntity.getComment())
				.build();
	}
	
	@Transactional
	private PostDto convertEntityToDtowithoutCategoryEntity(PostEntity postEntity) {
		return PostDto.builder()
				.id(postEntity.getId())
				.title(postEntity.getTitle())
				.content(postEntity.getContent())
				.writer(postEntity.getWriter())
				.createdDate(postEntity.getCreatedDate())
				.hit(postEntity.getHit())
				.build();
	}
	
	
	@Transactional
	public List<PostDto> searchPosts(String keyword){
		List<PostEntity> postEntities = postRepository.findByTitleContaining(keyword);
		List<PostDto> postDtoList = new ArrayList<>();
		
		if (postEntities.isEmpty()) return postDtoList;
		
		for (PostEntity postEntity: postEntities) {
			postDtoList.add(this.convertEntityToDto(postEntity));
		}
		
		return postDtoList;
	}
	
	//페이징
	
	private static final int BLOCK_PAGE_NUM_COUNT = 10;// 블럭에 존재하는 페잊지수
	private static final int PAGE_POST_COUNT = 4;// 한페이지에 존재하는 게시글 수
	
	
	
	//기존 사용 전체 게시물 페이징
	
	@Transactional
	public List<PostDto> getPostList(Integer pageNum) {
		//첫번째 인자 limit 를 의미함 , 현재 페이지 번호 -1 를 계산한값, 실제페이지 번호랑 Sql조회시 limit랑달라서
		//두번째 인자 Offset를 의미, 몇개를 가져올것인가
		//세번째 인자 정렬방식을 결정함. 
		Page<PostEntity> page = postRepository.findAll(PageRequest.of(pageNum -1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));
		
		List<PostEntity> postEntities = page.getContent();
		List<PostDto> postDtoList = new ArrayList<>();
		
		for (PostEntity postEntity : postEntities) {
			PostDto postDto = this.convertEntityToDto(postEntity);
			postDto.setCommentCount();
			postDtoList.add(postDto);
		}
		
		return postDtoList;
	}
	
	
	
	
	@Transactional
	public Long getPostCount(Long categoryId) {
		if (categoryId == 0)
			return postRepository.count();
		
		CategoryEntity categoryentity = this.getCategoryEntity(categoryId);
		return (long) postRepository.findAllByCategoryEntity(categoryentity).size();
	}
	
	
	
	public Integer[] getPageList(Integer curPageNum, Long categoryId) {
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
		
		// 총 게시글 수
		Double postsTotalCount = Double.valueOf(this.getPostCount(categoryId));
		
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
		
		return pageList;
		
	}
	
	
	//카테고리 기준 페이징
	
	
	
	@Transactional
	public List<PostDto> getPostListByCategoryid(Long CategoryId, Integer pageNum) {
		PageRequest pageRequest = PageRequest.of(pageNum-1, PAGE_POST_COUNT,Sort.by(Sort.Direction.ASC, "createdDate"));
		CategoryEntity categoryEntity = this.getCategoryEntity(CategoryId);
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
		int posthit = postDto.getHit();
		
		
		postDto.setHit(posthit+1);
		
		postRepository.save(postDto.toEntity(postDto.getCategoryEntity()));
		
	}
	

}
