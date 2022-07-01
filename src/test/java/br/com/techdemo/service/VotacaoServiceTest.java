package br.com.techdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.techdemo.dto.request.SessaoDTO;
import br.com.techdemo.dto.request.VotacaoDTO;
import br.com.techdemo.enums.SessaoStatusEnum;
import br.com.techdemo.model.PautaModel;
import br.com.techdemo.model.SessaoModel;
import br.com.techdemo.model.VotacaoModel;
import br.com.techdemo.repository.SessaoRepository;
import br.com.techdemo.repository.VotacaoRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VotacaoServiceTest {

	private UUID ID = null;
	private VotacaoDTO votacaoDTO = null;
	private VotacaoModel votacaoModel = null;
	private SessaoDTO sessaoDTO = null;
	private SessaoModel sessaoModel = null;
	private PautaModel pautaModel = null;
	private Long horarioAtual = null;
	private Date dataEncerramentoMenor = null;
	private Date dataEncerramentoMaior = null;
	
	@Autowired
	VotacaoService votacaoService;
	
	@MockBean
    SessaoService sessaoService;
    
	@MockBean
    SessaoRepository sessaoRepository;
	
	@MockBean
    VotacaoRepository votacaoRepository;
	
	@MockBean
    PautaService pautaService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ID = UUID.fromString("f7785731-073a-4138-a4fc-e1d8f85e40de");
    	
    	pautaModel = new PautaModel();
    	pautaModel.setId(ID);
    	pautaModel.setDescricao("Pauta");
    	
    	sessaoDTO = new SessaoDTO();
    	sessaoDTO.setDuracao(5);
    	
    	sessaoModel = new SessaoModel();
    	sessaoModel.setId(ID);
    	sessaoModel.setDataCriacao(new Date());
    	sessaoModel.setDataEncerramento(new Date());
    	sessaoModel.setPauta(pautaModel);
    	sessaoModel.setStatus(SessaoStatusEnum.A);
    	
    	votacaoDTO = new VotacaoDTO();
    	votacaoDTO.setCpf("60831135042");
    	votacaoDTO.setVoto("Sim");
    	
    	votacaoModel = new VotacaoModel();
    	votacaoModel.setId(ID);
    	votacaoModel.setCpf("60831135042");
    	votacaoModel.setVoto("Sim");
    	votacaoModel.setSessao(sessaoModel);
    	
		horarioAtual = System.currentTimeMillis();
		dataEncerramentoMenor = new Date(horarioAtual - (5 * 60 * 1000));
		dataEncerramentoMaior = new Date(horarioAtual + (5 * 60 * 1000));

    }
    
    @Test
    public void teste_inserir_voto_sessao_inexistente() {
    	ResponseEntity<?> responseVotacao = this.votacaoService.votar(votacaoDTO, ID);
    	assertEquals(HttpStatus.NOT_FOUND, responseVotacao.getStatusCode());
    }
    
    @Test
    public void teste_inserir_voto_sessao_finalizada() {
    	sessaoModel.setStatus(SessaoStatusEnum.F);
    	Mockito.when(sessaoService.buscarSessaoPorId(ID)).thenReturn(sessaoModel);
    	ResponseEntity<?> responseVotacao = this.votacaoService.votar(votacaoDTO, ID);
    	assertEquals(HttpStatus.FORBIDDEN, responseVotacao.getStatusCode());
    }
    
    @Test
    public void teste_inserir_voto_sessao_expirada() {
    	sessaoModel.setDataEncerramento(dataEncerramentoMenor);
    	Mockito.when(sessaoService.buscarSessaoPorId(ID)).thenReturn(sessaoModel);
    	ResponseEntity<?> responseVotacao = this.votacaoService.votar(votacaoDTO, ID);
    	assertEquals(HttpStatus.FORBIDDEN, responseVotacao.getStatusCode());
    }
   
    @Test
    public void teste_inserir_voto_existente() {
    	List<VotacaoModel> votacoes = new ArrayList<>();
    	votacoes.add(votacaoModel);
    	sessaoModel.setDataEncerramento(dataEncerramentoMaior);
    	Mockito.when(sessaoService.buscarSessaoPorId(ID)).thenReturn(sessaoModel);
    	doReturn(votacoes).when(votacaoRepository).findByCpf(Mockito.anyString());
    	ResponseEntity<?> responseVotacao = this.votacaoService.votar(votacaoDTO, ID);
    	assertEquals(HttpStatus.FORBIDDEN, responseVotacao.getStatusCode());
    }
    
    @Test
    public void teste_inserir_voto_inexistente() {
    	List<VotacaoModel> votacoes = new ArrayList<>();
    	sessaoModel.setDataEncerramento(dataEncerramentoMaior);
    	Mockito.when(sessaoService.buscarSessaoPorId(ID)).thenReturn(sessaoModel);
    	doReturn(votacoes).when(votacaoRepository).findByCpf(Mockito.anyString());
    	Mockito.when(votacaoRepository.save(Mockito.any())).thenReturn(votacaoModel);
    	ResponseEntity<?> responseVotacao = this.votacaoService.votar(votacaoDTO, ID);
    	assertEquals(HttpStatus.CREATED, responseVotacao.getStatusCode());
    }
    
    //sessaoService.buscarSessaoPorId(sessaoID)
    @Test
    public void teste_contabilizar_sessao_inexistente() {
    	Mockito.when(sessaoService.buscarSessaoPorId(ID)).thenReturn(null);
    	ResponseEntity<?> responseVotacao = this.votacaoService.contabilizar(ID);
    	assertEquals(HttpStatus.NOT_FOUND, responseVotacao.getStatusCode());
    }
    
    @Test
    public void teste_contabilizar_sessao_ativa() {
    	Mockito.when(sessaoService.buscarSessaoPorId(ID)).thenReturn(sessaoModel);
    	ResponseEntity<?> responseVotacao = this.votacaoService.contabilizar(ID);
    	assertEquals(HttpStatus.FORBIDDEN, responseVotacao.getStatusCode());
    }
    
    @Test
    public void teste_contabilizar_sucesso() {
    	List<VotacaoModel> votacoes = new ArrayList<>();
    	votacoes.add(votacaoModel);
    	sessaoModel.setVotacao(votacoes);
    	sessaoModel.setStatus(SessaoStatusEnum.F);
    	Mockito.when(sessaoService.buscarSessaoPorId(ID)).thenReturn(sessaoModel);
    	ResponseEntity<?> responseVotacao = this.votacaoService.contabilizar(ID);
    	assertEquals(HttpStatus.OK, responseVotacao.getStatusCode());
    }
    
//    @Test
//    public void teste_inserir_pauta_inexistente() {
//    	doReturn(Optional.ofNullable(null)).when(sessaoRepository).findByStatus(Mockito.any());
//    	ResponseEntity<?> responseSessao = this.sessaoService.inserir(sessaoDTO);
//    	assertEquals(HttpStatus.NOT_FOUND, responseSessao.getStatusCode());
//    }
//    
//    @Test
//    public void teste_inserir_pauta_existente() {
//    	doReturn(Optional.ofNullable(null)).when(sessaoRepository).findByStatus(Mockito.any());
//    	Mockito.when(pautaService.obterPautaPorId(Mockito.any())).thenReturn(pautaModel);
//    	Mockito.when(sessaoRepository.save(Mockito.any())).thenReturn(sessaoModel);
//    	ResponseEntity<?> responseSessao = this.sessaoService.inserir(sessaoDTO);
//    	assertEquals(HttpStatus.CREATED, responseSessao.getStatusCode());
//    }
//    
//    @Test
//    public void teste_busca_id() {
//    	doReturn(Optional.of(sessaoModel)).when(sessaoRepository).findById(ID);
//    	SessaoModel sessao = this.sessaoService.buscarSessaoPorId(ID);
//    	assertEquals(ID, sessao.getId());
//    }
//    
//    @Test
//    public void teste_busca_id_inexistente() {
//    	doReturn(Optional.of(sessaoModel)).when(sessaoRepository).findById(ID);
//    	SessaoModel sessao = this.sessaoService.buscarSessaoPorId(Mockito.any());
//    	assertNull(sessao);
//    }

}
