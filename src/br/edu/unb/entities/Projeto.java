package br.edu.unb.entities;

import java.util.Date;
import java.util.Set;

import org.neo4j.graphdb.Node;

import br.edu.unb.util.DateUtil;

public class Projeto implements EntityProvenance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String nome;
	private String descricao;
	private String coordenador;
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private String observacao;
	private Set<Experimento> experimentos;
	private Set<String> nomesInstituicoesParticipantes;
	private Set<String> nomesInstituicoesFinanciadoras;
<<<<<<< HEAD:src/br/edu/unb/entities/Projeto.java
	private Usuario usuario;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
=======
	private Set<Experimento> experimentos;
	

	public Projeto(){
		this.idProjeto = 0;
	}
	
>>>>>>> FETCH_HEAD:src/entidades/Projeto.java
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the coordenador
	 */
	public String getCoordenador() {
		return coordenador;
	}
	/**
	 * @param coordenador the coordenador to set
	 */
	public void setCoordenador(String coordenador) {
		this.coordenador = coordenador;
	}
	/**
	 * @return the dataHoraInicio
	 */
	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}
	/**
	 * @param dataHoraInicio the dataHoraInicio to set
	 */
	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	/**
	 * @return the dataHoraFim
	 */
	public Date getDataHoraFim() {
		return dataHoraFim;
	}
	/**
	 * @param dataHoraFim the dataHoraFim to set
	 */
	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}
	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}
	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	/**
	 * @return the nomesInstituicoesParticipantes
	 */
	public Set<String> getNomesInstituicoesParticipantes() {
		return nomesInstituicoesParticipantes;
	}
	/**
	 * @param nomesInstituicoesParticipantes the nomesInstituicoesParticipantes to set
	 */
	public void setNomesInstituicoesParticipantes(
			Set<String> nomesInstituicoesParticipantes) {
		this.nomesInstituicoesParticipantes = nomesInstituicoesParticipantes;
	}
	/**
	 * @return the nomesInstituicoesFinanciadoras
	 */
	public Set<String> getNomesInstituicoesFinanciadoras() {
		return nomesInstituicoesFinanciadoras;
	}
	/**
	 * @param nomesInstituicoesFinanciadoras the nomesInstituicoesFinanciadoras to set
	 */
	public void setNomesInstituicoesFinanciadoras(
			Set<String> nomesInstituicoesFinanciadoras) {
		this.nomesInstituicoesFinanciadoras = nomesInstituicoesFinanciadoras;
	}
	/**
	 * @return the idProjeto
	 */
	public Long getIdProjeto() {
		return id;
	}
	/**
	 * @param idProjeto the idProjeto to set
	 */
	public void setIdProjeto(Long idProjeto) {
		this.id = idProjeto;
	}	
<<<<<<< HEAD:src/br/edu/unb/entities/Projeto.java
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Set<Experimento> getExperimentos() {
		return experimentos;
	}
=======
	/**
	 * @return the experimentos
	 */
	public Set<Experimento> getExperimentos() {
		return experimentos;
	}
	/**
	 * @param experimentos the experimentos to set
	 */
>>>>>>> FETCH_HEAD:src/entidades/Projeto.java
	public void setExperimentos(Set<Experimento> experimentos) {
		this.experimentos = experimentos;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Projeto [nome=" + nome + ", descricao=" + descricao
				+ ", coordenador=" + coordenador + ", dataHoraInicio="
				+ dataHoraInicio + ", dataHoraFim=" + dataHoraFim + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Projeto)) {
			return false;
		}
		Projeto other = (Projeto) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	@Override
	public EntityType getType() {
		return EntityType.PROJECT;
	}
	public static Projeto transforma(Node node) {
		Projeto projeto = new Projeto();
		projeto.setNome((String) node.getProperty("nome"));
		projeto.setDescricao((String) node.getProperty("descricao"));
		projeto.setCoordenador((String) node.getProperty("coordenador"));
		projeto.setDataHoraInicio(DateUtil.string2Date((String) node.getProperty("dataHoraInicial")));
		projeto.setDataHoraFim(DateUtil.string2Date((String) node.getProperty("dataHoraFinal")));
		projeto.setObservacao((String) node.getProperty("observacao"));
		projeto.setUsuario(new Usuario());
		projeto.setIdProjeto(node.getId());
//
//		String[] nomesInstituicoesParticipantes = new String[projeto.getNomesInstituicoesParticipantes().size()];
//		nodeProject.setProperty("instituicoesParticipantes", projeto.getNomesInstituicoesParticipantes().
//				toArray(nomesInstituicoesParticipantes));
//		
//		String[] nomesInstituicoesFinanciadoras = new String[projeto.getNomesInstituicoesFinanciadoras().size()];
//		nodeProject.setProperty("instituicoesFinanciadoras", projeto.getNomesInstituicoesFinanciadoras().

		return projeto;
	}
	
	@Override
	public EntityProvenance transform(Node node) {
		return Projeto.transforma(node);
	}

}
