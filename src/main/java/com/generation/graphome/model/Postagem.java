package com.generation.graphome.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_postagens")
public class Postagem {

//atributos
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
	@NotBlank(message = "Obrigatório")
	@Size (min = 5, max =100, message = "o Título deve ter no mínimo 5 e no máximo 100 caracteres")
    private String titulo;
    
	@NotNull
	@Size (min = 5, max =1000, message = "o texto deve ter no mínimo 10 e no máximo 1000 caracteres")
    private String texto;
    
    private String midia;
    
    @JsonFormat(pattern="yyyy-MM-dd")
    @UpdateTimestamp
	private LocalDate data;
    
    private int curtir;
   
    
//relacionamentos
    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;
    
    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Usuario usuario;

 
//getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getMidia() {
		return midia;
	}

	public void setMidia(String midia) {
		this.midia = midia;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getCurtir() {
		return curtir;
	}

	public void setCurtir(int curtir) {
		this.curtir = curtir;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
    
    
    

}
