package br.com.senac.api.dtos;

public class LoginRequestDto {
    private final String token;

    public LoginRequestDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
