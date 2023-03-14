package kr.co.hellopet.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.hellopet.entity.MedicalEntity;
import kr.co.hellopet.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@Builder
public class MyUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private MemberEntity member;
	private MedicalEntity medical;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 계정이 갖는 권한 목록 리턴
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(member != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getLevel()));
		}else if(medical != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + medical.getLevel()));
		}
        return authorities;
	}
	
    @Override
    public String getPassword() {
        if (member != null) {
            return member.getPass();
        } else if (medical != null) {
            return medical.getPass();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if (member != null) {
            return member.getUid();
        } else if (medical != null) {
            return medical.getUid();
        }
        return null;
    }

	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료 여부(true: 만료안됨, false:만료)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 잠긴 여부(true: 잠김안됨, false:잠긴)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 계정 비밀번호 만료 여부(true: 만료안됨, false:만료)
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화 여부(true: 활성화, false:비활성화)
		return true;
	}
}