package br.com.techdemo.service;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import br.com.techdemo.dto.request.VotacaoDTO;

public interface VotacaoService {
	ResponseEntity<?> votar(VotacaoDTO votacaoDTO, UUID sessaoID);
	ResponseEntity<?> contabilizar(UUID sessaoID);
}
