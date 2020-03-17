package org.zerock;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Member;
import org.zerock.domain.Profile;
import org.zerock.presistence.MemberRepository;
import org.zerock.presistence.ProfileRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit	//테스트 결과 commit
public class ProfileTests {
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	ProfileRepository profileRepo;
	
	// 더미 회원 데이터의 추가
	@Test
	public void testInsertMembers() {
		
		IntStream.range(1, 101).forEach(i -> {
			Member member = new Member();
			member.setUid("user" + i);
			member.setUpw("pw" + i);
			member.setUname("사용자" + i);
			
			memberRepo.save(member);
		});
	}	//end method
	
	// 특정 회원의 프로필 데이터 처리	
	@Test
	public void testInsertProfile() {
		
		Member member = new Member();
		member.setUid("user1");
		
		for (int i = 1; i < 5; i++) {
			
			Profile profile1 = new Profile();
			profile1.setFname("face" + i + ".jpg");
			
			if(i == 1) {
				profile1.setCurrent(true);
			}
			
			profile1.setMember(member);
			
			profileRepo.save(profile1);
		}
	}
	
	// uid가 'user1'인 회원의 정보와 더불어 회원의 프로필 사진 숫자
	@Test
	public void testFetchJoin1() {
		
		List<Object[]> result = memberRepo.getMemberWithProfileCount("user1");
		
		result.forEach(arr ->
		System.out.println(Arrays.toString(arr)));
	}
	
	// 회원 정보와 현재 사용 중인 프로필에 대한 정보
	@Test
	public void testFetchJoin2() {
		
		List<Object[]> result = memberRepo.getMemberWithProfile("user1");
		
		result.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
}

