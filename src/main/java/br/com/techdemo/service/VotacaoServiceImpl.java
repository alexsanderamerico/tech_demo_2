package br.com.techdemo.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.techdemo.dto.request.VotacaoDTO;
import br.com.techdemo.dto.response.VotacaoResponseDTO;
import br.com.techdemo.dto.response.VotacaoResultadoResponseDTO;
import br.com.techdemo.enums.SessaoStatusEnum;
import br.com.techdemo.model.PautaModel;
import br.com.techdemo.model.SessaoModel;
import br.com.techdemo.model.VotacaoModel;
import br.com.techdemo.repository.VotacaoRepository;

@Service
public class VotacaoServiceImpl extends BaseServiceImpl implements VotacaoService {

	@Autowired
	VotacaoRepository votacaoRepository;
	
	@Autowired
	SessaoService sessaoService;
		
	public ResponseEntity<?> votar(VotacaoDTO votacaoDTO, UUID sessaoID) {
		
		// Verificar se sessao informada existe
		SessaoModel sessaoAtual = sessaoService.buscarSessaoPorId(sessaoID);
		if(Objects.isNull(sessaoAtual))
			return new ResponseEntity<>("Sessão inexistente", HttpStatus.NOT_FOUND);
		// Verificar se sessao está ativa
		if(sessaoAtual.getStatus().equals(SessaoStatusEnum.F))
			return new ResponseEntity<>("Sessão finalizada", HttpStatus.FORBIDDEN);
		
		if(!validarEncerramento(sessaoAtual.getDataEncerramento().getTime()))
			return new ResponseEntity<>("Sessão finalizada", HttpStatus.FORBIDDEN);
		
		PautaModel pautaSessaoAutal = sessaoAtual.getPauta();
		
		//Buscar votacoes do associado por identificador
		List<VotacaoModel> votacoesExistentes = votacaoRepository.findByCpf(votacaoDTO.getCpf());
	  
		//Validar se usuario já realizou votação nesta pauta
		Boolean votacaoJaRealizadaParaEssaPauta = votacoesExistentes.parallelStream().anyMatch(v -> v.getSessao().getPauta().getId() == pautaSessaoAutal.getId());
	    if(votacaoJaRealizadaParaEssaPauta)
	    	return new ResponseEntity<>("Votação já realizada para essa pauta", HttpStatus.FORBIDDEN);

		VotacaoModel votacaoEntity = new VotacaoModel();
		votacaoEntity.setCpf(votacaoDTO.getCpf());
		votacaoEntity.setVoto(votacaoDTO.getVoto());
		votacaoEntity.setSessao(sessaoAtual);
		
		VotacaoModel votacao = votacaoRepository.save(votacaoEntity);
		return new ResponseEntity<>(toVotacaoResponseDTO(votacao), HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> contabilizar(UUID sessaoID) {
		
		// Verificar se sessao informada existe
		SessaoModel sessao = sessaoService.buscarSessaoPorId(sessaoID);
		if(Objects.isNull(sessao))
			return new ResponseEntity<>("Sessão inexistente", HttpStatus.NOT_FOUND);
		// Verificar se sessao está ativa
		if(sessao.getStatus().equals(SessaoStatusEnum.A))
			return new ResponseEntity<>("Sessão em andamento", HttpStatus.FORBIDDEN);
		// Contabilizar resultados da votacao
		Long totalDeVotos = Long.valueOf(sessao.getVotacao().size());
		Long votosSim = sessao.getVotacao().parallelStream().filter(v -> v.getVoto().toUpperCase().equalsIgnoreCase("SIM")).count();
		Long votosNao = totalDeVotos - votosSim;
		
		VotacaoResultadoResponseDTO votacaoResultadoDTO = new VotacaoResultadoResponseDTO();
		votacaoResultadoDTO.setTotalDeVotos(totalDeVotos);
		votacaoResultadoDTO.setVotosSim(votosSim);
		votacaoResultadoDTO.setVotosNao(votosNao);
		
		return new ResponseEntity<>(votacaoResultadoDTO, HttpStatus.OK);
	}

	private Boolean validarEncerramento( Long encerramento ) {
	    Long atual = System.currentTimeMillis();
	    return encerramento > atual;
	}
	
	private VotacaoResponseDTO toVotacaoResponseDTO(VotacaoModel votacao) {
		return modelMapper.map(votacao, VotacaoResponseDTO.class);
	}

}
