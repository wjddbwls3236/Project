package com.naver.dao;

import java.util.List;

import com.naver.vo.OderSeqVO;
import com.naver.vo.RecipyVO;

public interface RecipyDAO {

	List<RecipyVO> getRList(RecipyVO r);

	void insertRecipy(RecipyVO r);

	void insertOderSeq(OderSeqVO o);

	void updateHit(int recipy_no);

	RecipyVO getRecipyCont(int recipy_no);

	OderSeqVO getOderCont(int recipy_no);

	void insertMap(OderSeqVO o);

	List<RecipyVO> myList(RecipyVO r);

	void delRecipy(int recipy_no);

	

}
