package com.example.sirenorder.mapper;
import java.util.ArrayList;
import com.example.sirenorder.vo.ProductVO;
public interface ProductMapper {
	public ArrayList<ProductVO> selectProduct(String chain_name, int number);
	public void deleteProduct(ProductVO product);
	public void insert(ProductVO product);
	public String selectProduct_id(String product_name);
	public int selectListCnt(String chain_name);
	public ArrayList<ProductVO> selectProductList(String chain_name, int startList, int listSize);
}