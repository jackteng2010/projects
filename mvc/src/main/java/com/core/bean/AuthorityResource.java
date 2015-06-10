package com.core.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_authority_resource", catalog = "mvc")
public class AuthorityResource extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Resource resource;
	private Authority authority;
	
	@ManyToOne
	@JoinColumn(name="RESOURCE_ID", nullable = false)
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
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
