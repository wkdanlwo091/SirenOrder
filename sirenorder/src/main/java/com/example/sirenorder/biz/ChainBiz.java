package com.example.sirenorder.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.ChainVO;

@Service("chainbiz")
public class ChainBiz implements Biz<String, ChainVO>{
	@Resource(name = "chaindao")
	Dao<String, ChainVO> dao;
	@Override
	public ChainVO get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<ChainVO> get() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public  ChainVO getByChain_name(String chain_name) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectByChain_name(chain_name);
	}
}
