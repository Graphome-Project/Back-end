# Back-end
Back-end do projeto
- [x] Criar a Camada Model da Tabela de Temas
- [x] Criar a Camada Repository e Camada Controller de Temas
- [x] Criar a Camada Model, Respository, Controller da Tabela de Postagens
- [x] Criar a Camada Model de Usuario
- [ ] Editar o Pom.xml
- [ ] Criar a Camada de Security
- [ ] Criar a Camada de Service de Usuario
- [ ] Criar a Camada de Repository para a interface de Usuario
- [ ] Criar duas Classes Controller para Usuario


<br>
<h3 align="center">
DER: Modelo de Entidade-Relacionamento nessa Task:
  
```mermaid
classDiagram
class Tema {
  - id : Long
  - nome : String
  - descricao : String 
  - postagem : List ~Postagem~
  + getAll()
  + getById(Long id)
  + getByDescricao(String descricao)
  + postTema(Tema tema)
  + putTema(Tema tema)
  + deleteTema(Long id)
}
class Postagem {
  - id : Long
  - titulo : String
  - texto: String
  - midia: String
  - data: LocalDate
  - tema : Tema
  - curtir : int
  - usuario : Usuario
  + getAll()
  + getById(Long id)
  + getByTitulo(String titulo)
  + getByDataIntervalo (String inicio, String fim)
  + postPostagem(Postagem postagem)
  + putPostagem(Postagem postagem)
  + deleteTema(Long id)
}
class Usuario {
  - id : Long
  - nome : String
  - usuario : String
  - senha : String
  - foto : String
  - bio : String
  - postagem : List ~Postagem~
  + getAll()
  + getById(Long id)
  + autenticarUsuario(UsuarioLogin usuarioLogin)
  + cadastrarUsuario(Usuario usuario)
  + atualizarUsuario(Usuario usuario)
}
class UsuarioLogin{
  - id : Long
  - nome : String
  - usuario : String
  - senha : String
  - foto : String
  - bio : String
  - token : String
}
Tema --> Postagem
Usuario --> Postagem
```  
  
</h3>
<br>
<h4 align="center"> 
	🚧  API 🚀 Em construção...  🚧
</h4>
