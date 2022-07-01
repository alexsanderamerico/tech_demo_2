package br.com.techdemo.dto.response;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotacaoResponseDTO {
	private UUID id;
	private String cpf;
	private String voto;
}
