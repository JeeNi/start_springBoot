package org.zerock;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Board;
import org.zerock.persistence.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class Boot3ApplicationTests {

	@Autowired
	private BoardRepository repo;
	
	@Test
	public void testInsert200() {
		for (int i = 1; i <= 200; i++) {
			
			Board board = new Board();
			board.setTitle("제목.." + i);
			board.setContent("내용..." + i + "채우기 ");
			board.setWriter("user0" + (i % 10));
			repo.save(board);
		}
	}
	
//	@Test
//	public void testByTitle() {
//		
//		// before Java 8
//		// List<Board> list = repo.findBoardByTitle("제목..177");
//		
//		// for(int i = 0, len = list.size(); i < len; i++){
//		// System.out.println(list.get(i));
//		// }
//		
//		// Java 8
//		repo.findBoardByTitle("제목...177")
//		.forEach(board -> System.out.println(board));
//	}
	
	@Test
	public void testByWriter() {
		
		Collection<Board> results = repo.findByWriter("user00");
		
		results.forEach(
			board -> System.out.println(board)
		);
	}
	
	@Test
	public void testByWriterContaining() {
		
		Collection<Board> results = repo.findByWriterContaining("05");
		
		// for 루프 대신 forEach
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByTitleAndBno() {
		
		Collection<Board> results = 
				repo.findByTitleContainingAndBnoGreaterThan("5", 50L);
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderBy() {
		
		Collection<Board> results = 
				repo.findByBnoGreaterThanOrderByBnoDesc(90L);
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderByPaging() {
		
		// spring boot 2.0.0
		Pageable paging = (Pageable) PageRequest.of(0, 10);
		
		Collection<Board> results = 
				repo.findByBnoGreaterThanOrderByBnoDesc(0L, paging);
		results.forEach(board -> System.out.println(board));
	}
	
//	@Test
//	public void testBnoPagingSort() {
//		
//		Pageable paging = (Pageable) PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
//		
//		Collection<Board> results = repo.findByBnoGreaterThan(0L, paging);
//		results.forEach(board -> System.out.println(board));
//	}
	
	@Test
	public void testBnoPagingSort() {
		
		//spring boot 2.0.0
		Pageable paging = (Pageable) PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
		
		Page<Board> result = repo.findByBnoGreaterThan(0L, paging);
		
		System.out.println("PAGE SIZE: " + result.getSize());
		System.out.println("TOTAL PAGES: " + result.getTotalPages());
		System.out.println("TOTAL COUNT: " + result.getTotalElements());
		System.out.println("NEXT: " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		list.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByTitle() {
		
		repo.findByTitle("17")
		.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByPaging() {
		
		Pageable pageable = new PageRequest(0, 10);
		
		repo.findByPage(pageable).forEach(board -> System.out.println(board));
	}
}
