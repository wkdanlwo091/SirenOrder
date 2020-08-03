package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.ChainMapper;
import com.example.sirenorder.vo.ChainVO;

@Repository("chaindao")
public class ChainDao  implements Dao<String, ChainVO>{
	@Autowired
	ChainMapper chainMapper;
	@Override
	public ChainVO select(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insert(ChainVO chainVO) {
		chainMapper.insert(chainVO);
	}
	@Override
	public ArrayList<ChainVO> selectall() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ChainVO selectByChain_name(String chain_name) {
		// TODO Auto-generated method stub
		return chainMapper.selectByChainName(chain_name);
	}
}
