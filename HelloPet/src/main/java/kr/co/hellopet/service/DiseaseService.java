package kr.co.hellopet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.hellopet.dao.DiseaseDAO;
import kr.co.hellopet.vo.DiseaseResultMapVO;

/*
 * 날짜 : 2023/03/09
 * 이름 : 임민지
 * 내용 : Disease Service 기능구현 
 */
@Service
public class DiseaseService {

	@Autowired
	private DiseaseDAO dao;
	
	public List<DiseaseResultMapVO> selectDisease(String group) {
		return dao.selectDisease(group);
	}
	
}
