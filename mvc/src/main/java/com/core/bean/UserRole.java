package com.core.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_user_role", catalog = "mvc")
public class UserRole extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Role role;
	private User user;
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID", nullable = false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name="USER_ID", nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
