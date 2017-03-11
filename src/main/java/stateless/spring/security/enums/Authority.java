package stateless.spring.security.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority{

	ROLE_USER,
	ROLE_ADMIN;

	@Override
	public String getAuthority() {
		return this.toString();
	}

	public static String roleHierarchyStringRepresentation(){
		return Authority.ROLE_ADMIN +" > "+Authority.ROLE_USER;

	}
}
