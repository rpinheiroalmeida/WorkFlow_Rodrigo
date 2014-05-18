package br.edu.unb.entities;

import java.io.Serializable;

import org.neo4j.graphdb.Node;


public interface EntityProvenance extends Serializable {

	Long getId();
	
	EntityType getType();
	
	EntityProvenance transform(Node node);
	
}
