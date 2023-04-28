package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.ListsVO;
import kr.co.hellopet.vo.MedicalVO;
/* 
 *  날짜 : 2023/03/14
 *  이름 : 김채영
 *  설명 : HelloPet Lists 페이지 기능구현
 */
@Mapper
@Repository
public interface ListsDAO {
	
	public List<ListsVO> selectHit(@Param("start") int start);
	public List<ListsVO> selectNew(@Param("start") int start);
	public List<ListsVO> selectReserve(@Param("start") int start);
	public int selectCountTotal();
	public int selectMsg(String uid);
	
}
