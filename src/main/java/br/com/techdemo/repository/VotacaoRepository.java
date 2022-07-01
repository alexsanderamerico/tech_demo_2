package br.com.techdemo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.techdemo.model.VotacaoModel;

@Repository
public interface VotacaoRepository extends CrudRepository<VotacaoModel, Long> {
	Optional<VotacaoModel> findById(UUID uuid);
	List<VotacaoModel> findByCpf(String cpf);
}
