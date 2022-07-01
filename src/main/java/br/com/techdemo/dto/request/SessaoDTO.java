package br.com.techdemo.dto.request;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessaoDTO {
	private UUID pautaId;
	private Integer duracao;
}
