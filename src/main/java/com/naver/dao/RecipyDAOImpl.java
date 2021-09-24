package com.naver.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.vo.OderSeqVO;
import com.naver.vo.RecipyVO;

@Repository
public class RecipyDAOImpl implements RecipyDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<RecipyVO> getRList(RecipyVO r) { //레시피 목록 가져오기
		return sqlSession.selectList("r_li",r);
	}

	@Override
	public void insertRecipy(RecipyVO r) { //레시피저장
		sqlSession.insert("r_in",r);	
	}

	@Override
	public void insertOderSeq(OderSeqVO o) { //레시피순서저장
		sqlSession.insert("o_in",o);
		
	}

	@Override
	public void updateHit(int recipy_no) { //조회수 증가
		this.sqlSession.update("recipy_hitUp",recipy_no);
		
	}

	@Override
	public RecipyVO getRecipyCont(int recipy_no) { //레시피 상세보기
		return this.sqlSession.selectOne("recipy_co",recipy_no);
	}

	@Override
	public OderSeqVO getOderCont(int recipy_no) { //레시피 상세보기
		
		return sqlSession.selectOne("o_cont",recipy_no);
	}

	@Override
	public void insertMap(OderSeqVO o) { //레시피 순서 맵으로 저장
		sqlSession.insert("map_in",o);		
	}

	@Override
	public List<RecipyVO> myList(RecipyVO r) { //마이레시피 리스트
		return sqlSession.selectList("my_li",r);
	}

	
	
}
