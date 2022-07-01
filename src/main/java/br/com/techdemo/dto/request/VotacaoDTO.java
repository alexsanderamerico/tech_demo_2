package br.com.techdemo.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotacaoDTO {

	@NotNull
	private String cpf;
	@NotNull
	private String voto;
}
