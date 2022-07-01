package br.com.techdemo.service;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.techdemo.dto.request.SessaoDTO;
import br.com.techdemo.dto.response.SessaoResponseDTO;
import br.com.techdemo.enums.SessaoStatusEnum;
import br.com.techdemo.model.PautaModel;
import br.com.techdemo.model.SessaoModel;
import br.com.techdemo.repository.SessaoRepository;

@Service
public class SessaoServiceImpl extends BaseServiceImpl implements SessaoService{

	private static final Integer SESSENTA = 60;
	private static final Integer MIL = 1000;
	private static final Integer DURACAO_SESSAO_DEFAULT = 1;

	@Autowired 
	SessaoRepository sessaoRepository;

	@Autowired 
	PautaService pautaService;

	public ResponseEntity<?> inserir(SessaoDTO sessaoDTO) {
	
		Integer duracao = sessaoDTO.getDuracao() == null ? DURACAO_SESSAO_DEFAULT : sessaoDTO.getDuracao();
		
		SessaoModel sessaoAtiva = sessaoRepository.findByStatus(SessaoStatusEnum.A).orElseGet(() -> {return null;});
		if (Objects.nonNull(sessaoAtiva)) 
			return new ResponseEntity<>("Existe uma sessão ativa", HttpStatus.CONFLICT);

		// Buscar pauta existente
		PautaModel pautaExistente = pautaService.obterPautaPorId(sessaoDTO.getPautaId());
		if (Objects.isNull(pautaExistente)) 
			return new ResponseEntity<>("Pauta informada não existe", HttpStatus.NOT_FOUND);
		
		// Calcular horario de encerramento
		Long horarioAtual = System.currentTimeMillis();
		Date dataAtual = new Date(horarioAtual);
		Date dataEncerramento = new Date(horarioAtual + (duracao * SESSENTA * MIL));
		
		// Montar objeto para persistencia
		SessaoModel sessaoEntity = modelMapper.map(sessaoDTO, SessaoModel.class);
		sessaoEntity.setPauta(pautaExistente);
	    sessaoEntity.setDataCriacao(dataAtual);
	    sessaoEntity.setDataEncerramento(dataEncerramento);
	    sessaoEntity.setStatus(SessaoStatusEnum.A);
	
	    SessaoModel sessao = sessaoRepository.save(sessaoEntity);
		return new ResponseEntity<>(toSessaoResponseDTO(sessao), HttpStatus.CREATED);
	}
	
	public SessaoModel buscarSessaoPorId(UUID sessaoId) {
		return sessaoRepository.findById(sessaoId).orElseGet(() -> {return null;});
	}
  
	private SessaoResponseDTO toSessaoResponseDTO(SessaoModel sessao) {
		return modelMapper.map(sessao, SessaoResponseDTO.class);
	}
    
}
