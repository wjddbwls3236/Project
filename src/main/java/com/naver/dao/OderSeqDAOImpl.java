package com.naver.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OderSeqDAOImpl implements OderSeqDAO {

	@Autowired
	private SqlSession sqlSession;
}
