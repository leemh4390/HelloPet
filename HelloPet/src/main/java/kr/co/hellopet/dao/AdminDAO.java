package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.hellopet.vo.AdminReserveVO;
import kr.co.hellopet.vo.MedicalVO;
import kr.co.hellopet.vo.MemberVO;
import kr.co.hellopet.vo.MessageVO;

/* 
 *  날짜 : 2023/03/14
 *  이름 : 김채영
 *  설명 : HelloPet Admin 페이지 기능구현
 */
@Mapper
@Repository
public interface AdminDAO {
	
	/* 현재 로그인한 관리자 가져오기*/
	public MedicalVO selectAdmin(String uid);
	public MemberVO selectUser(String uid);
	
	/* 관리자 정보 수정 */
	public int updateAdmin(MedicalVO vo);
	public int updateUser(MemberVO mem);
	public int deleteWithdrawOwner(@Param("uid") String uid);
	public int deleteWithdrawAdmin(@Param("uid") String uid);
	public int findPwChange(String uid, String pass);
	public int findPwChangeUser(String uid, String pass);
	
	
	/* 예약내역 */
	public List<AdminReserveVO> selectReserves(@Param("start") int start, String medNo, int pageSize);
	public int selectCountTotal(String medNo);
	public AdminReserveVO selectReserve(@RequestParam("revNo") Integer revNo);
	
	/* 예약 상태 변경 */
	public boolean updateConfirm(int revNo);
	public boolean updateReject(int revNo);
	public int deleteCoupon(int cpNo, String uid);
	public int updateCouponOwner(String uid);
	
	/* 메시지 */
	public int insertMsg(MessageVO vo);
	public int updateReserve(String medNo);
	public int selectMsg(String uid);
}
