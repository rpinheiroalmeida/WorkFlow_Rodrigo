/**
 * 
 */
package br.edu.unb.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IteratorUtil;

import br.edu.unb.entities.Atividade;
import br.edu.unb.entities.CollectionProvenance;
import br.edu.unb.entities.Experimento;
import br.edu.unb.entities.RelationshipProvenanceType;
import br.edu.unb.entities.Usuario;
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
		return  getNode(experimento.getProjeto().getId());
	}

	@Override
	protected void createRelationship(Node nodeFrom, Node nodeTo) {
		Relationship myRelationship = nodeFrom.createRelationshipTo(nodeTo, RelationshipProvenanceType.HAS);
		myRelationship.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
	}
	
	public String buildJsonAccount(Long idExperimento) {
	//   "links": [  {"source": "1","target": "2" }, { "source": "1", "target": "3" }]
		StringBuilder jsonNodes = new StringBuilder("\"nodes\" : [");
		StringBuilder jsonLinks = new StringBuilder("\"edges\" : [");
		Set<Long> idsUsados = new HashSet<>();
		try(Transaction tx = getManagerConnection().beginTx())  {
			Node root = getManagerConnection().getGraphDb().getNodeById(idExperimento);
			Iterable<Relationship> allRelationships = root.getRelationships(Direction.OUTGOING);
			if (allRelationships != null) {
				for (Relationship relationship : allRelationships) {
					Node nodeActivity = relationship.getOtherNode(root);
					if (!idsUsados.contains(nodeActivity.getId())) {
						jsonNodes.append(Atividade.buildJson(nodeActivity)).append(",");
						idsUsados.add(nodeActivity.getId());
					}
					Iterable<Relationship> allRelationshipUsed = nodeActivity.getRelationships(RelationshipProvenanceType.USED);
					if (allRelationshipUsed != null) {
						for (Relationship relationshipUsed : allRelationshipUsed) {
							Node collectionUsed = relationshipUsed.getEndNode();
							if (!idsUsados.contains(collectionUsed.getId())) {
								jsonNodes.append(CollectionProvenance.buildJson(collectionUsed)).append(",");
								idsUsados.add(nodeActivity.getId());
							}
							if (!idsUsados.contains(relationshipUsed.getId())) {
								jsonLinks.append("{source : ").append(nodeActivity.getId()).append(", target : ")
									.append(collectionUsed.getId()).append(" ,type: '").append(relationshipUsed.getProperty("relationship-type"))
									.append("'}").append(",");
							}
						}
					}
					Iterable<Relationship> relsWasAssociatedWith = nodeActivity.getRelationships(RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
					if (relsWasAssociatedWith != null)  {
						for (Relationship relWasAssociatedWith : relsWasAssociatedWith) {
							Node nodeAgent = relWasAssociatedWith.getOtherNode(nodeActivity);
							if (!idsUsados.contains(nodeAgent.getId())) {
								jsonNodes.append(Usuario.buildJson(nodeAgent)).append(",");
								idsUsados.add(nodeAgent.getId());
							}
							if (!idsUsados.contains(relWasAssociatedWith.getId())) {
								jsonLinks.append("{source : ").append(nodeActivity.getId()).append(", target : ")
									.append(nodeAgent.getId()).append(" ,type: '").append(relWasAssociatedWith.getProperty("relationship-type"))
									.append("'}").append(",");
							}
						}
					}
				}
			}
		}
		jsonNodes.deleteCharAt(jsonNodes.length()-1).append("]");
		jsonLinks.deleteCharAt(jsonLinks.length()-1).append("]");
		return "{" +  jsonNodes.toString() + " , " + jsonLinks.toString() + "}";
		
//		List<Projeto> projetos = new ArrayList<>();
//		try(Transaction tx = getManagerConnection().beginTx())  {
//			Node nodeUser = getManagerConnection().getGraphDb().getNodeById(usuario.getId());
//			Iterable<Relationship> allRelationshipsUsers = nodeUser.getRelationships(RelationshipProvenanceType.HAS);
//			Set<Experimento> experimentos;
//			Set<Atividade> atividades;
//			for(Relationship relHasProject : allRelationshipsUsers) {
//				Node nodeProject = relHasProject.getOtherNode(nodeUser);
//				Projeto projeto = Projeto.transforma(nodeProject);
//				
//				experimentos = new HashSet<>();
//				Iterable<Relationship> allRelHasAccount = nodeProject.getRelationships(Direction.OUTGOING);
//				for (Relationship relHasAccount : allRelHasAccount) {
//					Node nodeExperiment = relHasAccount.getOtherNode(nodeProject);
//					Experimento experimento = Experimento.transforma(nodeExperiment);
//					
//					atividades = new HashSet<>();
//					Iterable<Relationship> allRelHasActivity = nodeExperiment.getRelationships(Direction.OUTGOING);
//					for (Relationship relHasActivity : allRelHasActivity) {
//						Node nodeActivity = relHasActivity.getOtherNode(nodeExperiment);
//						Atividade atividade = Atividade.transforma(nodeActivity);
//						System.out.println("Id Atividade = " + atividade.getId());
//						atividade.setExperimentoOrigem(experimento);
//						atividades.add(atividade);
//					}
//					experimento.setAtividades(atividades);
//					experimentos.add(experimento);
//				}
//				projeto.setExperimentos(experimentos);
//				projetos.add(projeto);
//			}
//		}
//		
//		return projetos;
	}

}
