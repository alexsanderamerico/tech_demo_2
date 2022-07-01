package br.com.techdemo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.techdemo.dto.request.PautaDTO;
import br.com.techdemo.model.PautaModel;
import br.com.techdemo.repository.PautaRepository;

@Service
public class PautaServiceImpl extends BaseServiceImpl implements PautaService {

	@Autowired
	PautaRepository pautaRepo;

	public ResponseEntity<PautaDTO> inserir(PautaDTO pautaDTO) {
		
		PautaModel pautaEntity = modelMapper.map(pautaDTO, PautaModel.class);
		PautaModel pauta = pautaRepo.save(pautaEntity);
		
		return new ResponseEntity<>(toPautaDTO(pauta), HttpStatus.CREATED);
	}

	public ResponseEntity<List<PautaDTO>> obterTodasPautas() {
		return new ResponseEntity<>(IterableUtils.toList(pautaRepo.findAll()).parallelStream().map(this::toPautaDTO).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	public PautaModel obterPautaPorId(UUID id) {
		Optional<PautaModel> findById = pautaRepo.findById(id);
		
		
		return findById.get();
	}
	
	private PautaDTO toPautaDTO(PautaModel pauta) {
		return modelMapper.map(pauta, PautaDTO.class);
	}

}
