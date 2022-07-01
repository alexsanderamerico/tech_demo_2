package br.com.techdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techdemo.dto.request.PautaDTO;
import br.com.techdemo.service.PautaService;

@RestController
@RequestMapping(value = "/pauta", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PautaController {

	@Autowired
	PautaService pautaService;

	@PostMapping
	@CrossOrigin
	public ResponseEntity<PautaDTO> inserir(@RequestBody PautaDTO pauta) {
		return pautaService.inserir(pauta);
	}
	
	@GetMapping("/todas")
	@CrossOrigin
	public ResponseEntity<List<PautaDTO>> todas() {
		return pautaService.obterTodasPautas();
	}

}
