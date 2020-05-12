package com.example.sirenorder.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sirenorder.frame.Dao;
import com.example.sirenorder.mapper.UserMapper;
import com.example.sirenorder.vo.UserVO;

@Repository("userdao")
public class UserDao implements Dao<String, UserVO> {
	
	@Autowired
	UserMapper usermapper;

	@Override
	public UserVO select(String userID) {
		return usermapper.select(userID);
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
}