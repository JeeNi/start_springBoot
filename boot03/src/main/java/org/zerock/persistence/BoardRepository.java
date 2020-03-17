package org.zerock.persistence;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

	// findBy를 이용한 특정 컬럼 처리
	public List<Board> findBoardByTitle(String title);
	
//	public Collection<Board> findByWriter(String writer);
	
	// 작성자에 대한 like % 키워드 % 
	public Collection<Board> findByWriterContaining(String writer);
	
	// AND, OR 조건의 처리
	public Collection<Board> findByTitleContainingOrContentContaining(
			String title, String content);
	
	// 부등호 처리 title LIKE % ? % AND BNO > ?
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(
			String keywoard, Long num);
	
	// order by 처리 bno > ? ORDER BY bno DESC
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bon);
	
	// 페이징 처리와 정력 bno > ? ORDER BY bno DESC limit ?, ?
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);
	
//	public List<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	
	// Page<T> 타입
	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	
	// Query를 이용하기
	// 제목에 대한 검색 처리
	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByTitle(String title);
	
	// 내용에 대한 검색 처리
	@Query("SELECT b FROM Board b WHERE b.content LIKE %:content% AND b.bno > 0"
			+ "ORDER BY b.bno DESC")
	public List<Board> findByContent(@Param("Content") String content);
	
	// 작성자에 대한 검색 처리 엔티티 타입, PK 타입을 자동으로 참고
	@Query("SELECT b FROM #{#entityName} b WHERE b.writer LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	List<Board> findByWriter(String writer);
	
	// 필요한 칼럼만 추출하는 경우
	@Query("SELECT b.bno, b.title, b.writer, b.redate"
			+ "FROM Board b WHRER b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Object[]> findByTitle2(String title);
	
	// nativeQuery 사용
	@Query(value="select bno, title, writer form tbl_boards where title like"
			+ "CONCAT('%', ?1, '%') and bno > 0 order by bno desc", nativeQuery=true)
	List<Object[]> findByTitle3(String title);
	
	// @Query와 Paging 처리/정렬
	@Query("SELECT b FROM Board b WHERE b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByPage(Pageable pageable);
	
}
