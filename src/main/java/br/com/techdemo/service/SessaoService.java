package br.com.techdemo.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import br.com.techdemo.dto.request.SessaoDTO;
import br.com.techdemo.model.SessaoModel;

public interface SessaoService {
	ResponseEntity<?> inserir(SessaoDTO sessaoDTO);
	SessaoModel buscarSessaoPorId(UUID sessaoId);
}
