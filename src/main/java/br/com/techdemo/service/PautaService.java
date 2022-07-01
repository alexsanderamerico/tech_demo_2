package br.com.techdemo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import br.com.techdemo.dto.request.PautaDTO;
import br.com.techdemo.model.PautaModel;

public interface PautaService {
	ResponseEntity<PautaDTO> inserir(PautaDTO pautaDTO);
	ResponseEntity<List<PautaDTO>> obterTodasPautas();
	PautaModel obterPautaPorId(UUID id);
}
