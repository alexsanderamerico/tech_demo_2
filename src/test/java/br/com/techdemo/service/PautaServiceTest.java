package br.com.techdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.List;
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

import br.com.techdemo.dto.request.PautaDTO;
import br.com.techdemo.model.PautaModel;
import br.com.techdemo.repository.PautaRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

	private UUID ID = null;
	private PautaDTO pautaDTO = null;
	private PautaModel pautaModel = null;

	@Autowired
    PautaService pautaService;
    
	@MockBean
    PautaRepository pautaRepo;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ID = UUID.fromString("f7785731-073a-4138-a4fc-e1d8f85e40de");
        
        pautaDTO = new PautaDTO();
    	pautaDTO.setId(ID);
    	pautaDTO.setDescricao("Pauta");
    	
    	pautaModel = new PautaModel();
    	pautaModel.setId(ID);
    	pautaModel.setDescricao("Pauta");
    }
    
    @Test
    public void teste_inserir_sucesso() {
    	Mockito.when(pautaRepo.save(Mockito.any())).thenReturn(pautaModel);
    	ResponseEntity<PautaDTO> responsePauta = this.pautaService.inserir(pautaDTO);
    	assertEquals(HttpStatus.CREATED, responsePauta.getStatusCode());
    }
    
    @Test
    public void teste_obter_todas_pautas() {
    	ResponseEntity<List<PautaDTO>> responsePauta = this.pautaService.obterTodasPautas();
    	assertEquals(HttpStatus.OK, responsePauta.getStatusCode());
    }
    
    @Test
    public void teste_obter_pauta_uuid() {
    	doReturn(Optional.of(pautaModel)).when(pautaRepo).findById(ID);
    	PautaModel pauta = this.pautaService.obterPautaPorId(ID);
    	assertEquals(ID, pauta.getId());
    }
   

}
