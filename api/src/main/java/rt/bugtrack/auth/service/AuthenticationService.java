package rt.bugtrack.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rt.bugtrack.auth.entity.User;
import rt.bugtrack.auth.dto.AuthenticationResponse;
import rt.bugtrack.auth.dto.SignInRequest;
import rt.bugtrack.auth.dto.SignUpRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .build();
        userService.createUser(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .username(user.getUsername())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword()
            )
        );
        User user = userService.getUserByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .username(user.getUsername())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .token(jwtToken)
            .build();
    }
}
