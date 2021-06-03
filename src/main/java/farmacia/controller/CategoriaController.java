package farmacia.controller;

import java.security.Provider.Service;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import farmacia.categoria.service.CategoriaService;
import farmacia.model.Categoria;
import farmacia.repository.CategoriaRepository;

@RestController
@CrossOrigin("*") // Permite acesso aos recursos a partir de origens diferentes
@RequestMapping("/categorias")
public class CategoriaController {

	
	@Autowired
	private CategoriaRepository repositoryC;
	
    @Autowired private CategoriaService serviceC;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> pegarTodasCategorias(){
		List<Categoria> listaDeCategorias = repositoryC.findAll();	
		
		if(listaDeCategorias.isEmpty()) {
			return ResponseEntity.status(204).build();
			
		} else {
			
			return ResponseEntity.status(200).body(listaDeCategorias);
		}
	}
	
	
	@GetMapping("/id{id_categoria}")
	public ResponseEntity<Optional<Categoria>> buscarCategoriaPorId(@PathVariable(value = "id_categoria") Long idCategoria){
		Optional<Categoria> idDaCategoria = repositoryC.findById(idCategoria);
		if(idDaCategoria.isEmpty()) {
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(idDaCategoria);
		}
	}
	
	
	@GetMapping("/buscar")
	public ResponseEntity<Object> buscarCategoriaPorNome(@RequestParam String nomeCategoria){
		List<Object> listaDeCategorias = repositoryC.findAllByNomeCategoriaContaining(nomeCategoria);
		
		if(listaDeCategorias.isEmpty()) {
			
			return ResponseEntity.status(400).body("Não há categorias com este nome!");
		}else {
			
			return ResponseEntity.status(200).body(listaDeCategorias);
		}
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Object> salvarNotaCategoria(@RequestBody Categoria categoria){
		return serviceC.cadastrarNovaCategoria(categoria)
				.map(verificandoCategoria -> ResponseEntity.status(200).body(verificandoCategoria))
				.orElse(ResponseEntity.status(400).body("Categoria já cadastrada"));
	}
	
	@PutMapping("/atualizar/{id_categoria}")
	public ResponseEntity<Object> remodelarCategoria(@PathVariable(value = "id_categoria") Long idCategoria,
		@Valid @RequestBody Categoria atualizacaoCategoria) {
		
			return serviceC.atualizarCategoria(idCategoria, atualizacaoCategoria)
					.map(categoriaAtualizada -> ResponseEntity.status(201).body(categoriaAtualizada))
					.orElse(ResponseEntity.status(400).body("Categoria Inexistente"
					+ "ou ja está cadastrado"  ));
		}
		
		@DeleteMapping("/deletar")
		public ResponseEntity<String> deletarCategoria(@RequestParam Long idCategoria){
			return serviceC.deletarIdCategoria(idCategoria)
					.map(categoriaDeletada -> ResponseEntity.status(400).body("Categoria não localizada!"
							 + "Por favor, tente outro id"))
					.orElse(ResponseEntity.status(200).body("Categoria deletada"));
		}
	}

