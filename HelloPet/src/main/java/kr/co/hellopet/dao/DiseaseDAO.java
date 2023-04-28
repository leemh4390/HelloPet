package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.DiseaseResultMapVO;
import kr.co.hellopet.vo.DiseaseVO;

/*
 * 날짜 : 2023/03/09
 * 이름 : 임민지
 * 내용 : HelloPet DAO 페이지 기능구현 
 */
@Mapper
@Repository
public interface DiseaseDAO {
	public List<DiseaseResultMapVO> selectDiseases(@Param("group") String group);
	public DiseaseVO selectDisease(@Param("cate1") int cate1, @Param("cate2") int cate2);
	
	public int selectMsg(String uid);

}
