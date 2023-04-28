package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.MessageVO;

@Mapper
@Repository
public interface MessageDAO {
	
	public List<MessageVO> selectMsgs(int start, String uid);
	public MessageVO selectMsg(String msgNo);
	public int updateMsg(String msgNo);
	public int selectCountTotal(String uid);
	public int selectMsgTotal(String uid);

}
