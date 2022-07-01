package br.com.techdemo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.techdemo.enums.SessaoStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SESSAO")
public class SessaoModel extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private SessaoStatusEnum status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataCriacao")
    private Date dataCriacao;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataEncerramento")
    private Date dataEncerramento;
    
    @ManyToOne
    @JoinColumn(name="pauta_id", nullable=false)
    private PautaModel pauta;
    
    
    @OneToMany(mappedBy="sessao")
    private List<VotacaoModel> votacao;

}
