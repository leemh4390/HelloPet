package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.CsVO;
import kr.co.hellopet.vo.MemberCouponVO;
import kr.co.hellopet.vo.MessageVO;

/* 
 *  날짜 : 2023/03/09
 *  이름 : 김채영
 *  설명 : HelloPet CS 페이지 기능구현
 */

@Mapper
@Repository
public interface CsDAO {

	/* notice */
	public int insertNotice(CsVO vo);
	public List<CsVO> selectNotices(@Param("start") int start, int pageSize);
	public int selectCountTotalNotice();
	public CsVO getPrev(String rdate);
	public CsVO getNext(String rdate);
	public CsVO selectNotice(int no);
	public int insertCoupon(MemberCouponVO vo);
	public int countCoupon(String cpNo, String uid);
	public int updateDownload(int cpNo);
	public int updateCouponOwner(String uid);
	
    /* qna */
	public int insertQna(CsVO vo);
	public List<CsVO> selectQnas(@Param("start") int start, int pageSize);
	public int selectCountTotalQna();
	
	/* qna - reply*/
	public int insertReply(CsVO vo);
	public List<CsVO> selectReply(int no);
	public int deleteReply(int no);
	public int updateReply(int no);
	public int updateDelReply(int no);
	
	/* faq */
	public int insertFaq(CsVO vo);
	public List<CsVO> selectFaqs();
	
	/* 공통 */
	public CsVO selectArticle(int no);
	public int updateArticleHit(int no);
	public int updateArticle(CsVO vo);
    public int deleteArticle(int no);

    /* message */
    public int insertMsg(MessageVO vo);
    public int selectMsg(String uid);
}
