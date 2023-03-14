package kr.co.hellopet.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.hellopet.vo.SearchVO;


@Mapper
@Repository
public interface SearchDAO {
	
	//searchHs 검색 후 목록 출력하기
	public List<SearchVO> selectSearchHs(String search);
	public int selectSearchHsTotal(String search);

}
