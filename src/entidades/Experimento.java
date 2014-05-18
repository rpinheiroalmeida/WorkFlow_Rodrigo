package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Experimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idExperimento;

	private String nome;
	private String descricao;
	private String localExecucao;
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private String anotacoes;
	private String versao;
	private Date dataVersao;
	private Set<Atividade> atividades;
	private Projeto projeto;
	
	public Integer getIdExperimento() {
		return idExperimento;
	}
	public void setIdExperimento(Integer idExperimento) {
		this.idExperimento = idExperimento;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getLocalExecucao() {
		return localExecucao;
	}
	public void setLocalExecucao(String localExecucao) {
		this.localExecucao = localExecucao;
	}
	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}
	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	public Date getDataHoraFim() {
		return dataHoraFim;
	}
	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}
	public String getAnotacoes() {
		return anotacoes;
	}
	public void setAnotacoes(String anotacoes) {
		this.anotacoes = anotacoes;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	/**
	 * @return the projeto
	 */
	public Projeto getProjeto() {
		return projeto;
	}
	/**
	 * @param projeto the projeto to set
	 */
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	public Date getDataVersao() {
		return dataVersao;
	}
	public void setDataVersao(Date dataVersao) {
		this.dataVersao = dataVersao;
	}
	/**
	 * @return the atividades
	 */
	public Set<Atividade> getAtividades() {
		return atividades;
	}
	/**
	 * @param atividades the atividades to set
	 */
	public void setAtividades(Set<Atividade> atividades) {
		this.atividades = atividades;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idExperimento == null) ? 0 : idExperimento.hashCode());
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
		if (!(obj instanceof Experimento)) {
			return false;
		}
		Experimento other = (Experimento) obj;
		if (idExperimento == null) {
			if (other.idExperimento != null) {
				return false;
			}
		} else if (!idExperimento.equals(other.idExperimento)) {
			return false;
		}
		return true;
	}
	


}
