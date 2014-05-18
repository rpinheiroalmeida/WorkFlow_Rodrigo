/**
 * 
 */
package br.edu.unb.dao.impl;

import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;

import br.edu.unb.entities.CollectionProvenance;


/**
 * @author Wallace
 *
 */
public class CollectionProvenanceDao extends BioInformaticaDaoImpl<CollectionProvenance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollectionProvenanceDao() {
	}
	
	public Node createNode(CollectionProvenance collection) {
		Node nodeCollection = createNode();
		nodeCollection.setProperty("nome", collection.getName());
		nodeCollection.setProperty("tamanho", collection.getSize());
		if (collection.getDescription() != null) {
			nodeCollection.setProperty("descricao", collection.getDescription());
		}
		nodeCollection.setProperty("localizacao", collection.getLocation());
		if (collection.getNotes() != null) {
			nodeCollection.setProperty("notacao", collection.getNotes());
		}
		nodeCollection.setProperty("type", collection.getType().getName());
		
		return nodeCollection;
	}


	@Override
	protected List<CollectionProvenance> montaLista(ExecutionResult result) {
		return null;
	}

	@Override
	protected String createQueryListar() {
		return "start n=node(*) where n.Type = 'Collection' return n";
	}
	

	@Override
	public Boolean excluir(CollectionProvenance object) {
		return null;
	}

	@Override
	protected Node getNodeSource(CollectionProvenance atividade) {
		return  null;	
	}

	@Override
	protected void createRelationship(Node nodeFrom, Node nodeTo) {
	}

}
