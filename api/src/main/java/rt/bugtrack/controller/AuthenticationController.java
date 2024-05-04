package rt.bugtrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rt.bugtrack.auth.service.AuthenticationService;
import rt.bugtrack.auth.dto.AuthenticationResponse;
import rt.bugtrack.auth.dto.SignInRequest;
import rt.bugtrack.auth.dto.SignUpRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(
        @RequestBody SignUpRequest request
    ) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(
        @RequestBody SignInRequest request
    ) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}

