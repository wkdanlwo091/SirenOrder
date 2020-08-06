package com.example.sirenorder.mapper;

import java.util.ArrayList;
import com.example.sirenorder.vo.UserVO;

public interface UserMapper {
	public UserVO select(String users_id);
	public ArrayList<UserVO> selectall();
	public void update(UserVO users);
	public void updateStore_name(UserVO users);
	public void delete(String users_id);
	public void insert(UserVO users);
	public void updateToken(UserVO m) ;
	public void updateRole(UserVO m) ;
	public String selectToken(String orders_id);

}
