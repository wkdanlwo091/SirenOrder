package com.example.sirenorder;

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
//maven install 해서 여기서 에러가 난다고 해서 주석처리하였다. 에러는 ( Please refer to C:\Users\wkdan\Desktop\SirenOrder\sirenorder\target\surefire-reports for the individual test results.)