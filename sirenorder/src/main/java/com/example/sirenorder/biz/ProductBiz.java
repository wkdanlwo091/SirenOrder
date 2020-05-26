package com.example.sirenorder.biz;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.ProductVO;
import com.example.sirenorder.vo.StoreVO;

@Service("productbiz")
public class ProductBiz implements Biz<String, ProductVO>{

	
	@Resource(name = "productdao")
	Dao<String, ProductVO> dao;
	
	@Override
	public ProductVO getProduct(ProductVO m) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectProduct(m);
	}
	@Override
	public void deleteProduct(ProductVO m) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteProduct(m);
	}
	@Override
	public void register(ProductVO m) throws Exception {
		// TODO Auto-generated method stub
		dao.insert(m);
	}
	
	
	@Override
	public ProductVO get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<ProductVO> get() {
		// TODO Auto-generated method stub
		return null;
	}
}
