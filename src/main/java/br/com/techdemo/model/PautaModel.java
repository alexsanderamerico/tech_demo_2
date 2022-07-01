package br.com.techdemo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PAUTA")
@EntityListeners(AuditingEntityListener.class)
public class PautaModel extends BaseModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "descricao", nullable = true, unique = false)
  private String descricao;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "dataCriacao")
  private Date dataCriacao;

  @OneToMany(mappedBy = "pauta")
  private List<SessaoModel> sessao;

}
