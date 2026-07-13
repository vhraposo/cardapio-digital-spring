package com.lanchonete.cardapio.service;

import com.lanchonete.cardapio.dto.AuthResponse;
import com.lanchonete.cardapio.dto.LoginRequest;
import com.lanchonete.cardapio.dto.RegisterRequest;
import com.lanchonete.cardapio.entity.Role;
import com.lanchonete.cardapio.entity.User;
import com.lanchonete.cardapio.exception.BusinessException;
import com.lanchonete.cardapio.repository.UserRepository;
import com.lanchonete.cardapio.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException(HttpStatus.CONFLICT, "Ja existe uma conta cadastrada com este email");
        }

        User user = User.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .telefone(request.telefone())
                .role(Role.USER) // cadastro publico sempre cria usuario comum, nunca admin
                .ativo(true)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getNome(), user.getEmail(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.senha())
            );
        } catch (BadCredentialsException ex) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Email ou senha invalidos");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(HttpStatus.UNAUTHORIZED, "Email ou senha invalidos"));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getNome(), user.getEmail(), user.getRole().name());
    }
}
