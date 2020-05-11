package com.example.sirenorder.biz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
	public ArrayList<UserVO> get() {
		return dao.selectall();
	}
	@Override
	public void register(UserVO m) throws Exception {
		// TODO Auto-generated method stub
		
	}
}