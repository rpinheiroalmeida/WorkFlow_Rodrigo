package br.edu.unb.dao.impl;

import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;

import br.edu.unb.entities.EntityProvenance;

public class RelationshipDao extends BioInformaticaDaoImpl<EntityProvenance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	@Override
	protected Node createNode(EntityProvenance object) {
		return null;
	}

	@Override
	protected Node getNodeSource(EntityProvenance object) {
		return null;
	}

	@Override
	protected void createRelationship(Node nodeFrom, Node nodeTo) {
	}

	@Override
	protected List<EntityProvenance> montaLista(ExecutionResult result) {
		return null;
	}

	@Override
	protected String createQueryListar() {
		return null;
	}

}
