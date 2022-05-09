package com.generation.graphome.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.graphome.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository <Postagem, Long>{
	
	public List <Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo")String titulo);
	public List<Postagem> findByDataIn(List<LocalDateTime> data);

	
	

}
