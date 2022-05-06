package com.generation.graphome.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_temas")
public class Tema {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@NotBlank ( message = "O título do tema é obrigatório")
@Size( min = 5, max = 100 ,message = " O texto deve ter no mínimo 5 e no máximo 100" )
private String nome;


@NotBlank ( message = "A descrição é obrigatória!")
@Size( min = 5, max = 500 ,message = " O texto deve ter no mínimo 5 e no máximo 500" )
private String descricao;


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getNome() {
	return nome;
}


public void setNome(String nome) {
	this.nome = nome;
}


public String getDescricao() {
	return descricao;
}


public void setDescricao(String descricao) {
	this.descricao = descricao;
}



}
