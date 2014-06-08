package br.edu.unb.entities;

import java.util.Date;

import org.neo4j.graphdb.Node;

import br.edu.unb.util.DateUtil;

public class Atividade implements EntityProvenance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nomeAtividade;
	private String nomePrograma;
	private String versaoPrograma;
	private String linhaComando;
	private String funcao;
	private Date dataHoraInicio;
	private Date dataHoraFim;
	private String nomeArquivo;
	private Experimento experimentoOrigem;
	private CollectionProvenance collection;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long idAtividade) {
		this.id = idAtividade;
	}
	public String getNomeAtividade() {
		return nomeAtividade;
	}
	public void setNomeAtividade(String nomeAtividade) {
		this.nomeAtividade = nomeAtividade;
	}
	
	/**
	 * @return the nomePrograma
	 */
	public String getNomePrograma() {
		return nomePrograma;
	}
	/**
	 * @param nomePrograma the nomePrograma to set
	 */
	public void setNomePrograma(String nomePrograma) {
		this.nomePrograma = nomePrograma;
	}
	/**
	 * @return the versaoPrograma
	 */
	public String getVersaoPrograma() {
		return versaoPrograma;
	}
	/**
	 * @param versaoPrograma the versaoPrograma to set
	 */
	public void setVersaoPrograma(String versaoPrograma) {
		this.versaoPrograma = versaoPrograma;
	}
	/**
	 * @return the linhaComando
	 */
	public String getLinhaComando() {
		return linhaComando;
	}
	/**
	 * @param linhaComando the linhaComando to set
	 */
	public void setLinhaComando(String linhaComando) {
		this.linhaComando = linhaComando;
	}
	/**
	 * @return the funcao
	 */
	public String getFuncao() {
		return funcao;
	}
	/**
	 * @param funcao the funcao to set
	 */
	public void setFuncao(String funcao) {
		this.funcao = funcao;
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
	 * @return the nomeArquivo
	 */
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	/**
	 * @param nomeArquivo the nomeArquivo to set
	 */
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public Experimento getExperimentoOrigem() {
		return experimentoOrigem;
	}
	public void setExperimentoOrigem(Experimento experimentoOrigem) {
		this.experimentoOrigem = experimentoOrigem;
	}
	public CollectionProvenance getCollection() {
		return collection;
	}
	public void setCollection(CollectionProvenance collection) {
		this.collection = collection;
	}
	public EntityType getType() {
		return EntityType.ACTIVITY;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof Atividade)) {
			return false;
		}
		Atividade other = (Atividade) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return "Atividade [projeto=" + projeto + ", nomePrograma="
//				+ nomePrograma + ", versaoPrograma=" + versaoPrograma
//				+ ", funcao=" + funcao + "]";
//	}
	
	public EntityProvenance transform(Node node) {
		return transforma(node);
		
	}
	
	public static Atividade transforma(Node node) {
		Atividade atividade = new Atividade();
		atividade.setNomePrograma((String) node.getProperty("nomePrograma"));
		atividade.setVersaoPrograma((String) node.getProperty("versaoPrograma"));
		atividade.setLinhaComando((String) node.getProperty("linhaComando"));
		atividade.setFuncao((String) node.getProperty("funcao"));
		atividade.setLinhaComando((String) node.getProperty("linhaComando"));
		atividade.setDataHoraFim(DateUtil.string2Date((String) node.getProperty("dataHoraFim")));
		atividade.setDataHoraInicio(DateUtil.string2Date((String) node.getProperty("dataHoraInicio")));
		atividade.setId(node.getId());
		atividade.setNomeAtividade((String) node.getProperty("nomeAtividade"));
//		atividade.setNomeArquivo((String) node.getProperty("nomeArquivo"));
		
		return atividade;
	}
	
	public static String buildJson(Node node) {
		return String.format("{id:%d, name: '%s', type:'%s' }", 
			node.getId(), node.getProperty("nomeAtividade"), EntityType.ACTIVITY.getName());
		
	}
}
