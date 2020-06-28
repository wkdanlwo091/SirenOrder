package com.example.sirenorder.mapper;

import com.example.sirenorder.vo.ChainVO;

public interface ChainMapper {
	public ChainVO selectByChainName(String chain_name);

}
