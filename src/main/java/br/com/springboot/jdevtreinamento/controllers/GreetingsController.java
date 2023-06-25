package br.com.springboot.jdevtreinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.jdevtreinamento.model.Usuario;
import br.com.springboot.jdevtreinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired /* Injeção de dependência */
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Curso Spring Boot API: " + name + "!";
	}

	@RequestMapping(value = "olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMunda(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario);

		return "Olá Mundo " + nome;
	}

	@GetMapping(value = "listatodos") /* Método de chamada da api */
	@ResponseBody /* Retorna os dados para o corpo da resposta */
	public ResponseEntity<List<Usuario>> listUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	@PostMapping(value = "salvar") /* mapeia a url */
	@ResponseBody /* Descrição da resposta */
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "deleteUsuario/{id}")
	public ResponseEntity<String> deleteUsuario(@RequestParam Long id) {
		usuarioRepository.deleteById(id);
		return new ResponseEntity<String>("Usuário delete co sucesso", HttpStatus.OK);
	}

	@GetMapping(value = "buscarUsuario/{id}")
	@ResponseBody
	public ResponseEntity<Usuario> buscarUsuario(@RequestParam(name = "iduser") Long iduser) {
		Usuario usuario = usuarioRepository.findById(iduser).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
	}

	@PutMapping(value = "atualizar") /* mapeia a url */
	@ResponseBody /* Descrição da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado", HttpStatus.OK);
		}

		usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@GetMapping(value = "buscarUsuarioPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarUsuarioPorNome(@RequestParam(name = "name") String name) {
		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.CREATED);
	}
}
