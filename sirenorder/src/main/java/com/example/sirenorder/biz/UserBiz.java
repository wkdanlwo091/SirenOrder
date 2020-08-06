package com.example.sirenorder.biz;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.frame.Biz;
import com.example.sirenorder.vo.UserVO;

@Service("userbiz")
public class UserBiz implements Biz<String, UserVO> {
	
	@Resource(name = "userdao")
	Dao<String, UserVO> dao;
	
	@Override
	public UserVO get(String userID) {
		return dao.select(userID);
	}
	
	@Override 
	public void updateRole(UserVO m) {
		dao.updateRole(m);
	}	

	@Override
	public ArrayList<UserVO> get() {
		return dao.selectall();
	}
	
	@Override
	public String getToken(String orders_id) {
		return dao.selectToken(orders_id);
	}
	@Override
	public void register(UserVO m) throws Exception {
		dao.insert(m);
	}
	@Override
	public void update(UserVO m) throws Exception {
		dao.update(m);
	}
	@Override
	public void updateStore_name(UserVO m) throws Exception {
		dao.updateStore_name(m);
	}
	@Override
	public void updateToken(UserVO m) throws Exception {
		dao.updateToken(m);
	}
	@Override
	public void delete(String m) throws Exception {
		dao.delete(m);
	}
}