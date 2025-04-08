package pro.cvartan.test.bookkeeper.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pro.cvartan.test.bookkeeper.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var user = userRepository.getUserByLogin(username);

            return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassHash())
                .roles(user.getPrivilegeNames().toArray(new String[0]))
                .build();
        }
        catch(RuntimeException e) {
            throw new UsernameNotFoundException("user not found",e);
        }

    }
}
