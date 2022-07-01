package br.com.techdemo.dto.response;

import java.util.Date;
import java.util.UUID;

import br.com.techdemo.enums.SessaoStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessaoResponseDTO {
	private UUID id;
	private Date encerramento;
	private SessaoStatusEnum status;
}
