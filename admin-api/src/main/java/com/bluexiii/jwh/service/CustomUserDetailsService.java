package com.bluexiii.jwh.service;

import com.bluexiii.jwh.domain.Role;
import com.bluexiii.jwh.domain.User;
import com.bluexiii.jwh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户[%s]不存在", username));
        }
        return new CustomUserDetails(user);
    }

    public final static class CustomGrantedAuthority implements GrantedAuthority {
        Role role;

        public CustomGrantedAuthority(Role role) {
            this.role = role;
        }

        @Override
        public String getAuthority() {
            return role.getRoleName();
        }
    }

    private final static class CustomUserDetails implements UserDetails {
        private User user;

        private CustomUserDetails(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {

            final Set<GrantedAuthority> _grntdAuths = new HashSet<GrantedAuthority>();

            Set<Role> _roles = null;

            if (user != null) {
                _roles = user.getRoles();
            }

            if (_roles != null) {
                for (Role _role : _roles) {
                    _grntdAuths.add(new CustomGrantedAuthority(_role));
                }
            }

            return _grntdAuths;
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return user.isStatus();
        }
    }
}