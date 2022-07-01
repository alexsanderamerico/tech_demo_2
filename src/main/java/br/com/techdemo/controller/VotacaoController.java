package br.com.techdemo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techdemo.dto.request.VotacaoDTO;
import br.com.techdemo.service.VotacaoService;

@RestController
@RequestMapping(value = "/votacao", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class VotacaoController {

	@Autowired
	VotacaoService votarService;
	
	@PostMapping("/votar/{sessaoId}")
	@CrossOrigin
	public ResponseEntity<?> votar(@PathVariable UUID sessaoId, @RequestBody VotacaoDTO votacaoDTO) {
		return votarService.votar(votacaoDTO, sessaoId);
	}
	
	@GetMapping("/resultado/{sessaoId}")
	@CrossOrigin
	public ResponseEntity<?> resultado(@PathVariable UUID sessaoId) {
		return votarService.contabilizar(sessaoId);
	}


}
