package kr.co.hellopet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.DiseaseDAO;
import kr.co.hellopet.vo.DiseaseResultMapVO;
import kr.co.hellopet.vo.DiseaseVO;

/*
 * 날짜 : 2023/03/09
 * 이름 : 임민지
 * 내용 : Disease Service 기능구현 
 */
@Service
public class DiseaseService {

	@Autowired
	private DiseaseDAO dao;
	
	public List<DiseaseResultMapVO> selectDiseases(String group) {
		return dao.selectDiseases(group);
	}
	
	public DiseaseVO selectDisease(int cate1, int cate2) {
		return dao.selectDisease(cate1, cate2);
	}
	public int selectMsg(String uid) {
		return dao.selectMsg(uid);
	}
	
}
