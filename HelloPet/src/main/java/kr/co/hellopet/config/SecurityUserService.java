package kr.co.hellopet.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.MemberDAO;
import kr.co.hellopet.entity.MedicalEntity;
import kr.co.hellopet.entity.MemberEntity;
import kr.co.hellopet.repo.MedicalRepo;
import kr.co.hellopet.repo.MemberRepo;

@Service
public class SecurityUserService  implements UserDetailsService{
	
	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private MedicalRepo medicalRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Member 테이블에서 사용자 정보 조회
		Optional<MemberEntity> memberOpt = memberRepo.findById(username);
		if (!memberOpt.isPresent()) {
			// Member 테이블에 사용자 정보가 없는 경우 Medical 테이블에서 조회
			Optional<MedicalEntity> medicalOpt = medicalRepo.findById(username);
			if (!medicalOpt.isPresent()) {
				throw new UsernameNotFoundException(username);
			} else {
				MedicalEntity medical = medicalOpt.get();
				UserDetails userDts = MyUserDetails.builder().medical(medical).build();
				return userDts;
			}
		} else {
			MemberEntity member = memberOpt.get();
			UserDetails userDts = MyUserDetails.builder().member(member).build();
			return userDts;
		}
	}
}
