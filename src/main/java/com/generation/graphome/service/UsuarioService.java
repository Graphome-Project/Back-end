package com.generation.graphome.service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.graphome.model.Usuario;
import com.generation.graphome.model.UsuarioLogin;
import com.generation.graphome.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuariorepository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario){
		if(usuariorepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
		
		if(calculoIdade(usuario.getDataNascimento())<18)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário é menor de idade",null);
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		
		return Optional.of(usuariorepository.save(usuario));
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){
		
		if(usuariorepository.findById(usuario.getId()).isPresent()) {
			
			Optional<Usuario> buscaUsuario = usuariorepository.findByUsuario(usuario.getUsuario());
			
			if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
					throw new ResponseStatusException(
							HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			if(calculoIdade(usuario.getDataNascimento())<18)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário é menor de idade",null);
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.ofNullable(usuariorepository.save(usuario));
		}
		return Optional.empty();
	}
public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
		
		Optional<Usuario> buscaUsuario = usuariorepository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(buscaUsuario.isPresent()) {
			
			if( compararSenhas(usuarioLogin.get().getSenha(), buscaUsuario.get().getSenha()) ) {
				
				usuarioLogin.get().setId(buscaUsuario.get().getId());
				usuarioLogin.get().setNome(buscaUsuario.get().getNome());
				usuarioLogin.get().setBio(buscaUsuario.get().getBio());
				usuarioLogin.get().setFoto(buscaUsuario.get().getBio());
				usuarioLogin.get().setDataNascimento(buscaUsuario.get().getDataNascimento());
				usuarioLogin.get().setTipo(buscaUsuario.get().getTipo());
				usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(buscaUsuario.get().getSenha());
				
				return usuarioLogin;
			}
			
		}
		
		return Optional.empty();
	
	}
//metodos complementares
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);
	}
	
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaBanco);
	}
	
	private String gerarBasicToken(String usuario, String senha) {
		
		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII"))); 
		
		return "Basic " + new String(tokenBase64);
	
	}
	
	private	int calculoIdade(LocalDate nascimento) {
		
		return Period.between(nascimento,LocalDate.now()).getYears();
	}
}