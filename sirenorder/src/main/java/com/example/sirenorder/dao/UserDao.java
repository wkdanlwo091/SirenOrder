package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.UserMapper;
import com.example.sirenorder.vo.UserVO;

@Repository("userdao")
public class UserDao implements Dao<String, UserVO> {
	
	@Autowired
	UserMapper usermapper;
	
	@Override
	public String selectToken(String orders_id) {
		return usermapper.selectToken(orders_id);
	}
	@Override
	public void updateRole(UserVO m) {
		usermapper.updateRole(m);
	}

	@Override
	public UserVO select(String users_id) {
		return usermapper.select(users_id);
	}
	@Override
	public void updateToken(UserVO m) throws Exception {
		usermapper.updateToken(m);
	}

	@Override
	public ArrayList<UserVO> selectall() {
		return usermapper.selectall();
	}

	@Override
	public void insert(UserVO m) throws Exception {
		usermapper.insert(m);
	}
	@Override
	public void delete(String m) throws Exception {
		usermapper.delete(m);
	}
	
	@Override
	public void update(UserVO m) throws Exception {
		usermapper.update(m);
	}
	@Override
	public void updateStore_name(UserVO m) throws Exception {
		usermapper.updateStore_name(m);
	}
}