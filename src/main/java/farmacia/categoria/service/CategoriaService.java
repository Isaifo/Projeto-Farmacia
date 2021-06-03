package farmacia.categoria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import farmacia.model.Categoria;
import farmacia.repository.CategoriaRepository;
import farmacia.repository.ProdutoRepository;

public class CategoriaService {

	@Autowired
	private CategoriaRepository repositoryC;
	private @Autowired ProdutoRepository repositoryP;
	
	public Optional<Object> cadastrarNovaCategoria(Categoria novaCategoria){ // Verifica informações do banco
		  Optional<Object> verificaCategoria = repositoryC.findByNomeCategoria(novaCategoria.getNomeCategoria()); // retorna o nome do findbynome
		
		if(verificaCategoria.isPresent()) {
			
			return Optional.empty();
		} else {
			return Optional.ofNullable(repositoryC.save(novaCategoria));
		}
	}
	
	// atualização de categoria,verificar se a categoria existe e se tem nomes iguais
	
	 public Optional <Object> atualizarCategoria(Long idCategoria, Categoria atualizacaoCategoria){
		  Optional<Categoria> verificaIdCategoria = repositoryC.findById(idCategoria);
		  Optional<Object> verificaNomeCategoria = repositoryC.findByNomeCategoria(atualizacaoCategoria.getNomeCategoria());
		  
		  if(verificaIdCategoria.isPresent() && verificaNomeCategoria.isEmpty()) {
			  verificaIdCategoria.get().setNomeCategoria(atualizacaoCategoria.getNomeCategoria());
		  	  verificaIdCategoria.get().setTipoCategoria(atualizacaoCategoria.getTipoCategoria());
		  		return Optional.ofNullable(repositoryC.save(verificaIdCategoria.get())); 		
		  		
	 }else {
		 return Optional.empty();
	 }
	 } 
	 public Optional<Object> deletarIdCategoria(Long idCategoria){
		 Optional<Categoria> verificaIdCategoria = repositoryC.findById(idCategoria);
		 
		 if(verificaIdCategoria.isEmpty()) {
			 return Optional.ofNullable(verificaIdCategoria);
		 } else {
			 
			 repositoryC.deleteById(idCategoria);
			 return Optional.empty();
	 }
	 }
	
}
