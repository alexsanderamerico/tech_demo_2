package br.com.techdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techdemo.dto.request.SessaoDTO;
import br.com.techdemo.service.SessaoService;

@RestController
@RequestMapping(value = "/sessao", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class SessaoController {

	@Autowired
	SessaoService sessaoService;

	@PostMapping
	@CrossOrigin
	public ResponseEntity<?> inserir(@RequestBody SessaoDTO sessao) {
		return sessaoService.inserir(sessao);
	}


}
