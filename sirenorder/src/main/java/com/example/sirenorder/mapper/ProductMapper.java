package com.example.sirenorder.mapper;

import java.util.ArrayList;
import com.example.sirenorder.vo.ProductVO;

public interface ProductMapper {
	public ProductVO selectProduct(ProductVO product);
	public void deleteProduct(ProductVO product);
	public void insert(ProductVO product);

}
