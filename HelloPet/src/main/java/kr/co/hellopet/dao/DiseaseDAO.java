package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.DiseaseResultMapVO;

/*
 * 날짜 : 2023/03/09
 * 이름 : 임민지
 * 내용 : HelloPet DAO 페이지 기능구현 
 */
@Mapper
@Repository
public interface DiseaseDAO {
	public List<DiseaseResultMapVO> selectDisease(String group);

}
