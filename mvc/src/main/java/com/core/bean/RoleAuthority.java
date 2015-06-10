package com.core.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_role_authority", catalog = "mvc")
public class RoleAuthority extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Role role;
	private Authority authority;
	
	@ManyToOne
	@JoinColumn(name="ROLE_ID", nullable = false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@ManyToOne
	@JoinColumn(name="AUTHORITY_ID", nullable = false)
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

}
