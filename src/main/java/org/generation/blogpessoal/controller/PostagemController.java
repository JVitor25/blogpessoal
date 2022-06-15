package org.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.blogpessoal.model.Postagem;
import org.generation.blogpessoal.repository.PostagemRepository;
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

//indica que a classe é uma classe controladora da api(Onde fica os endpoints)
@RestController

//um endpoint é um caminho que vai direcionar para executar alguma função na aplicação.
@RequestMapping("/postagens")//cria um endpoint

/*habilita a minha API para receber requisições de qualquer porta.
o Spring abre na 8080, e o framework do front end pode abrir em outra 
(o React abre na 3000)*/
@CrossOrigin("*")// ou se quiser pode deixar assim:@CrossOrigin(origins="*")

public class PostagemController {
	
	/*Autowired funciona como injeção de dependência --> transferindo a responsabilidade
	 de manipular o banco de dados para o repository*/
	@Autowired 
	private PostagemRepository repository;	//isso é o apelido da instância
	
	@GetMapping//indica o verbo http que pode ser utilizado no endpoint. Neste caso --> (Get)
	public ResponseEntity<List<Postagem>> getAll(){ //GetAll é o nome da função. Ou seja, posso colocar qualquer um.
		return ResponseEntity.ok(repository.findAll());
	}
	
	//Fazendo um novo get por id. Vamos usar uma subrota
	@GetMapping("/{id}")// "/{id}" é uma variável da minha subrota.
	public ResponseEntity <Postagem> getById(@PathVariable Long id){
		return repository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity <List<Postagem>> getByTitulo (@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping //criar
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	/*
	Forma de estruturar dados do JSON: 
	{
	    "titulo":"",
	    "texto":""
	}
	*/
	@PutMapping //atualizar
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
}
 