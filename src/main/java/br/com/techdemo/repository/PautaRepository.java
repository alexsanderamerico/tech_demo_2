package br.com.techdemo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.techdemo.model.PautaModel;

@Repository
public interface PautaRepository extends CrudRepository<PautaModel, Long> {
	Optional<PautaModel> findById(UUID id);
}
