package kr.co.hellopet.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hellopet.entity.MedicalEntity;

public interface MedicalRepo extends JpaRepository<MedicalEntity, String>{
	
	public int countByuid(String uid);

}
