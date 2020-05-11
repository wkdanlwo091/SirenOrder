package com.example.sirenorder.mapper;

import java.util.ArrayList;
import com.example.sirenorder.vo.UserVO;

public interface UserMapper {
	public UserVO select(String users_id);
	public ArrayList<UserVO> selectall();
	public void update(UserVO users);
	public void delete(String users_id);
	public void insert(UserVO users);
}
