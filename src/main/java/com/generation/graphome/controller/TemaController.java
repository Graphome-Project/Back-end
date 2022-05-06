package com.generation.graphome.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.generation.graphome.model.Tema;
import com.generation.graphome.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*",allowedHeaders="*")
public class TemaController {

	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity <List<Tema>> getAll(){
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Tema> getById(@PathVariable Long id){
		return temaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity <List<Tema>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(temaRepository.findByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping 
	 public ResponseEntity <Tema> postTema(@Valid @RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
	}
	
	@PutMapping
	 public ResponseEntity <Tema> putTema(@Valid @RequestBody Tema tema){
		return temaRepository.findById(tema.getId())
				.map(resposta -> ResponseEntity.ok().body(temaRepository.save(tema)))
				.orElse(ResponseEntity.notFound().build());
			}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePostagem(@Valid @PathVariable Long id){
		return temaRepository.findById(id)
				.map(resposta -> {
					temaRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		})
				.orElse(ResponseEntity.notFound().build());
	}
}
