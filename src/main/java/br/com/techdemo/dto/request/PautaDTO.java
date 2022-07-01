package br.com.techdemo.dto.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PautaDTO {

  private UUID id;
  @NotNull private String descricao;

}
