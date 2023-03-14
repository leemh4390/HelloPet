package kr.co.hellopet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hellopet.entity.MemberEntity;

public interface MemberRepo extends JpaRepository<MemberEntity, String>{
	
	public int countByuid(String uid);

}

