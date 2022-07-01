package br.com.techdemo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotacaoResultadoResponseDTO {
	private Long totalDeVotos;
	private Long votosSim;
	private Long votosNao;
}
