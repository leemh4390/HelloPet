package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.CommunityVO;
import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.ReserveVO;

@Mapper
@Repository
public interface MyDAO {
	
	public MemberVO selectUser(@Param("uid") String uid);
	
	public List<CommunityVO> selectMyArticle(String uid, int start);
	
	public List<CsVO> selectMyQna(String uid, int start);
	
	public List<ReserveVO> selectMyReserve(String uid, int start);
	
	public List<ReserveVO> selectMyReserves(String uid);
	
	public int selectCountMyArticle(String uid);
	
	public int selectCountMyQna(String uid);
	
	public int selectCountMyReserve(String uid);
	
	public void updateInfoModify(@Param("name") String name, @Param("email") String email, @Param("nick") String nick, @Param("hp") String hp, @Param("uid") String uid);
	
	public int deleteWithdrawMember(@Param("uid") String uid);
	
	public int deleteMyReserve(@Param("no") int no );
}
