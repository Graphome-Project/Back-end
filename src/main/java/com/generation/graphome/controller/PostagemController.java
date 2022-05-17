package com.generation.graphome.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.graphome.model.Postagem;
import com.generation.graphome.repository.PostagemRepository;
import com.generation.graphome.repository.TemaRepository;
import com.generation.graphome.service.PostagemService;

@RestController
@RequestMapping ("/postagem")
@CrossOrigin (origins = "*", allowedHeaders = "*" )
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	@Autowired
	private PostagemService postagemService;
	
	@GetMapping
	public ResponseEntity <List<Postagem>> getAll (){
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity <Postagem> getById (@PathVariable Long id){
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping ("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo (@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@GetMapping("/data_inicial/{inicio}/data_final/{fim}")
	public ResponseEntity<List<Postagem>> getByDataIntervalo(@PathVariable String inicio, @PathVariable String fim){
		
		LocalDate data_start = LocalDate.parse(inicio, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate data_end = LocalDate.parse(fim, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		return ResponseEntity.ok(postagemRepository.findByDataBetween(data_start,data_end));
	}
	@PostMapping
	public ResponseEntity <Postagem> postPostagem (@Valid @RequestBody Postagem postagem){
		
		if (temaRepository.existsById(postagem.getTema().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
			
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	
	}
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem (@Valid @RequestBody Postagem postagem){
		if (postagemRepository.existsById(postagem.getId())) {
			
			if (temaRepository.existsById(postagem.getTema().getId()))
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping ("/{id}")
	@ResponseStatus (HttpStatus.NO_CONTENT)
	public void deletePostagem (@Valid @PathVariable Long id){
		Optional<Postagem> postagem = postagemRepository.findById(id); 
		
		if (postagem.isEmpty()) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
		
		postagemRepository.deleteById(id);
		
		
	}	
	
	@PutMapping("/curtir/{id}")
	public ResponseEntity<Postagem> curtirProdutoId (@PathVariable Long id){
		
		return postagemService.curtir(id)
			.map(resposta-> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.badRequest().build());
	
	}
	
	
	
	
	
	
	

}
