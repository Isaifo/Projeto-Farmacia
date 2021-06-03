package farmacia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import farmacia.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Object> {

	
	public List<Object> findAllByNomeCategoriaContaining(String nomeCategoria); // criando um método sobrescrevendo o nomeCategoria, parecido com o like

	public Optional<Object> findByNomeCategoria(String nomeCategoria); //Procura exatamente aquele nome
	
}
