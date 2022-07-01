package br.com.techdemo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "VOTACAO")
public class VotacaoModel extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "cpf")
  private String cpf;

  @Column(name = "voto")
  private String voto;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "sessao_id", nullable = false)
  private SessaoModel sessao;
}
