/**
 * 
 */
package br.edu.unb.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.helpers.collection.IteratorUtil;

import br.edu.unb.entities.Experimento;
import br.edu.unb.entities.RelationshipProvenanceType;
import br.edu.unb.util.DateUtil;

public class ExperimentoDao extends BioInformaticaDaoImpl<Experimento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExperimentoDao() {
	}
	
	public Node createNode(Experimento experimento) {
//		Node nodeProject = getNode(experimento.getProjeto().getIdProjeto());
		Node nodeExperiment = createNode();
		
		nodeExperiment.setProperty("nome", experimento.getNome());
		nodeExperiment.setProperty("descricao", experimento.getDescricao());
		nodeExperiment.setProperty("localExecucao", experimento.getLocalExecucao());
		nodeExperiment.setProperty("dataHoraInicio", DateUtil.date2String(experimento.getDataHoraInicio()));
		nodeExperiment.setProperty("dataHoraFim", DateUtil.date2String(experimento.getDataHoraFim()));
		nodeExperiment.setProperty("anotacoes", experimento.getAnotacoes());
		nodeExperiment.setProperty("versao", experimento.getVersao());
		nodeExperiment.setProperty("type", experimento.getType().getName());
//		nodeExperiment.setProperty("anotacoes", experimento.getAnotacoes());
		
//		Relationship myRelationship = nodeProject.createRelationshipTo(nodeExperiment, RelationshipProvenanceType.HAS);
//		myRelationship.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
		
		return nodeExperiment;
	}
	
	@Override
	protected List<Experimento> montaLista(ExecutionResult result) {
		Iterator<Node> n_column = result.columnAs( "n" );
		List<Experimento> experimentos = new ArrayList<>();
		for ( Node node : IteratorUtil.asIterable( n_column ) )
		{
			experimentos.add(Experimento.transforma(node));

		}
		return experimentos;
	}

	@Override
	protected String createQueryListar() {
		return "start n=node(*) where n.Type = 'Account' return n";
	}

	@Override
	public Boolean excluir(Experimento object) {
		return null;
	}

	@Override
	protected Node getNodeSource(Experimento experimento) {
		return  getNode(experimento.getProjeto().getIdProjeto());
	}

	@Override
	protected void createRelationship(Node nodeFrom, Node nodeTo) {
		Relationship myRelationship = nodeFrom.createRelationshipTo(nodeTo, RelationshipProvenanceType.HAS);
		myRelationship.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
	}

}
