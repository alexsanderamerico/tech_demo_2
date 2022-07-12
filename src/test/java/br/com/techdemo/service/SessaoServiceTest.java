package br.com.techdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;

import java.util.Date;
import java.util.Optional;
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
import br.com.techdemo.enums.SessaoStatusEnum;
import br.com.techdemo.model.PautaModel;
import br.com.techdemo.model.SessaoModel;
import br.com.techdemo.repository.SessaoRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

	private UUID ID = null;
	private SessaoDTO sessaoDTO = null;
	private SessaoModel sessaoModel = null;
	private PautaModel pautaModel = null;
	
	@Autowired
    SessaoService sessaoService;
    
	@MockBean
    SessaoRepository sessaoRepository;
	
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

    }
    
    @Test
    public void teste_inserir_sessao_existente() {
    	sessaoDTO.setDuracao(null);
    	doReturn(Optional.of(sessaoModel)).when(sessaoRepository).findFirstByDataEncerramentoAfter(Mockito.any());
    	ResponseEntity<?> responseSessao = this.sessaoService.inserir(sessaoDTO);
    	assertEquals(HttpStatus.CONFLICT, responseSessao.getStatusCode());
    }
    
    @Test
    public void teste_inserir_pauta_inexistente() {
    	doReturn(Optional.ofNullable(null)).when(sessaoRepository).findByStatus(Mockito.any());
    	ResponseEntity<?> responseSessao = this.sessaoService.inserir(sessaoDTO);
    	assertEquals(HttpStatus.NOT_FOUND, responseSessao.getStatusCode());
    }
    
    @Test
    public void teste_inserir_pauta_existente() {
    	doReturn(Optional.ofNullable(null)).when(sessaoRepository).findByStatus(Mockito.any());
    	Mockito.when(pautaService.obterPautaPorId(Mockito.any())).thenReturn(pautaModel);
    	Mockito.when(sessaoRepository.save(Mockito.any())).thenReturn(sessaoModel);
    	ResponseEntity<?> responseSessao = this.sessaoService.inserir(sessaoDTO);
    	assertEquals(HttpStatus.CREATED, responseSessao.getStatusCode());
    }
    
    @Test
    public void teste_busca_id() {
    	doReturn(Optional.of(sessaoModel)).when(sessaoRepository).findById(ID);
    	SessaoModel sessao = this.sessaoService.buscarSessaoPorId(ID);
    	assertEquals(ID, sessao.getId());
    }
    
    @Test
    public void teste_busca_id_inexistente() {
    	doReturn(Optional.of(sessaoModel)).when(sessaoRepository).findById(ID);
    	SessaoModel sessao = this.sessaoService.buscarSessaoPorId(Mockito.any());
    	assertNull(sessao);
    }

}
