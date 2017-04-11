package com.guonima.wxapp.domain;

import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 4697983666949763147L;

	private Long userId;/*主键*/
	
    private String userName;/*用户名*/
    private String loginName;/*登陆名*/
	private String password;/*密码*/
	
	private String gender;/*性别*/
	
	private Date birthday;/*生日*/
	
	private String email;/*电子邮箱*/
	

	public String getLoginName(){
		 return this.loginName;
	}

	public void setLoginName(String loginName){
		 this.loginName = loginName;
	}
	
	public String getUserName(){
		 return this.userName;
	}

	public void setUserName(String userName){
		 this.userName = userName;
	}

	public Long getUserId(){
		 return this.userId;
	}

	public void setUserId(Long userId){
		 this.userId = userId;
	}

	public String getPassword(){
		 return this.password;
	}

	public void setPassword(String password){
		 this.password = password;
	}

	public String getEmail(){
		 return this.email;
	}

	public void setEmail(String email){
		 this.email = email;
	}

	public Date getBirthday(){
		 return this.birthday;
	}

	public void setBirthday(Date birthday){
		 this.birthday = birthday;
	}
	
    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
    public String toString() {
        return "User (" +
                "id=" + userId +
                ", username='" + userName + '\'' 
                +"gender=" + gender +", email='"
                +email + '\''+
                ')';
    }

}
