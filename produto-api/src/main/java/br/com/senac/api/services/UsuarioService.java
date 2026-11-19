package br.com.senac.api.services;

import br.com.senac.api.dtos.TokenResponseDto;
import br.com.senac.api.dtos.UsuarioRequestDto;
import br.com.senac.api.entidades.Usuarios;
import br.com.senac.api.jwt.JwtService;
import br.com.senac.api.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public Usuarios criar(UsuarioRequestDto usuario) {
        Usuarios usuarioPersit =
                this.usuariosRequestDtoParaUsuarios(usuario);

        return usuarioRepositorio.save(usuarioPersit);

    }

    public TokenResponseDto login(UsuarioRequestDto usuario) {
        Optional<Usuarios> usuariosRetorno =
                usuarioRepositorio.findByEmail(usuario.getEmail());
        if(usuariosRetorno.isPresent()) {
            Usuarios usuarioDb = usuariosRetorno.get();

            if(passwordEncoder.matches(usuario.getSenha(), usuarioDb.getSenha())) {
                String token = jwtService.gerarToken(usuarioDb);

                return new TokenResponseDto(token);
            }

            throw new RuntimeException("Senha invalda!");
        }

        throw new RuntimeException("Usuario não encotrado!");
    }

    private Usuarios usuariosRequestDtoParaUsuarios
            (UsuarioRequestDto entrada){
        Usuarios saida = new Usuarios();
        saida.setNome(entrada.getNome());
        saida.setEmail(entrada.getEmail());
        saida.setSenha(passwordEncoder.encode(entrada.getSenha()));

        return saida;
    }
}
