package com.example.sirenorder.mapper;

import com.example.sirenorder.vo.ChainVO;

public interface ChainMapper {
	public ChainVO selectByChainName(String chain_name);
	public void insert(ChainVO chainVO);
	public void updatePoint_rate(ChainVO chainVO);
}
