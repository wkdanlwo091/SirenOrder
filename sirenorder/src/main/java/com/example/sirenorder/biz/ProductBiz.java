package com.example.sirenorder.biz;

import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.vo.ProductVO;

@Service("productbiz")
public class ProductBiz implements Biz<String, ProductVO>{
	
	@Resource(name = "productdao")
	Dao<String, ProductVO> dao;
	
	@Override
	public ArrayList<ProductVO> getProduct(String name, int num) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectProduct(name, num);
	}
	@Override
	public String getProduct_id(String product_name) throws Exception {
		return dao.selectProduct_id(product_name);
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
