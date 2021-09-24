package com.naver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.dao.OderSeqDAO;

@Service
public class OderSeqServiceImpl implements OderSeqService {

	@Autowired
	private OderSeqDAO oderSeqDao;
}
