package br.com.techdemo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.techdemo.enums.SessaoStatusEnum;
import br.com.techdemo.model.SessaoModel;

@Repository
public interface SessaoRepository extends CrudRepository<SessaoModel, Long> {
  Optional<SessaoModel> findByStatus(SessaoStatusEnum status);

  Optional<SessaoModel> findById(UUID uuid);
}
