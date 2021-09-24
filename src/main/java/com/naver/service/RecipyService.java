package com.naver.service;

import java.util.List;

import com.naver.vo.OderSeqVO;
import com.naver.vo.RecipyVO;

public interface RecipyService {

	List<RecipyVO> getRList(RecipyVO r);

	void insertRecipy(RecipyVO r);

	void insertOderSeq(OderSeqVO o);

	RecipyVO getRecipyCont(int recipy_no);

	RecipyVO getRecipyCont2(int recipy_no);

	OderSeqVO getOderCont(int recipy_no);

	void insertMap(OderSeqVO o);

	List<RecipyVO> myList(RecipyVO r);

	

}
