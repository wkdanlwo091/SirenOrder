package com.example.sirenorder;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.Store_productVO;

@SpringBootTest
class SirenorderApplicationTests {

	@Test
	void contextLoads() {
		Biz<String, Store_productVO> store_productbiz;
	}

}
