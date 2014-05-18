/**
 * 
 */
package br.edu.unb.dao.impl;

import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import br.edu.unb.entities.Atividade;
import br.edu.unb.entities.RelationshipProvenanceType;
import br.edu.unb.util.DateUtil;


/**
 * @author Wallace
 *
 */
public class AtividadeDao extends BioInformaticaDaoImpl<Atividade> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtividadeDao() {
	}
	
	public Node createNode(Atividade atividade) {
		Node nodeActivity = createNode();
		nodeActivity.setProperty("nomePrograma", atividade.getNomePrograma());
		nodeActivity.setProperty("versaoPrograma", atividade.getVersaoPrograma());
		nodeActivity.setProperty("linhaComando", atividade.getLinhaComando());
		nodeActivity.setProperty("funcao", atividade.getFuncao());
		nodeActivity.setProperty("dataHoraInicio", DateUtil.date2String(atividade.getDataHoraInicio()));
		nodeActivity.setProperty("dataHoraFim", DateUtil.date2String(atividade.getDataHoraFim()));
		nodeActivity.setProperty("nomeAtividade", atividade.getNomeAtividade());
		nodeActivity.setProperty("nomeArquivo", atividade.getNomeArquivo());
		nodeActivity.setProperty("type", atividade.getType().getName());
		
		return nodeActivity;
	}

//	private Node createNodeCollection(CollectionProvenance collection) {
//		Node nodeCollection = createNode();
//		nodeCollection.setProperty("nome", collection.getName());
//		nodeCollection.setProperty("tamanho", collection.getSize());
//		if (collection.getDescription() != null) {
//			nodeCollection.setProperty("descricao", collection.getDescription());
//		}
//		nodeCollection.setProperty("localizacao", collection.getLocation());
//		if (collection.getNotes() != null) {
//			nodeCollection.setProperty("notacao", collection.getNotes());
//		}
//		nodeCollection.setProperty("type", collection.getType().getName());
//		
//		return nodeCollection;
//	}

	@Override
	protected List<Atividade> montaLista(ExecutionResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createQueryListar() {
		return "start n=node(*) where n.Type = 'Activity' return n";
	}
	

	@Override
	public Boolean excluir(Atividade object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Node getNodeSource(Atividade atividade) {
		return  getNode(atividade.getExperimentoOrigem().getId());	
	}

	@Override
	protected void createRelationship(Node nodeFrom, Node nodeTo) {
		Relationship myRelationship = nodeFrom.createRelationshipTo(nodeTo, RelationshipProvenanceType.HAS);
		myRelationship.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
	}

}
