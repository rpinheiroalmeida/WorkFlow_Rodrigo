/**
 * 
 */
package br.edu.unb.dao.impl;


import java.io.Serializable;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import br.edu.unb.dao.BioInformaticaDaoIf;
import br.edu.unb.entities.EntityProvenance;
import br.edu.unb.entities.RelationshipProvenanceType;


/**
 * Implementação dos métodos de salvamento de dados em banco de dados.
 * @param <T>
 *
 */
public abstract class BioInformaticaDaoImpl<T extends EntityProvenance> implements BioInformaticaDaoIf<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<T> listaDados; 
	private ManagerConnection managerConnection = ManagerConnection.getInstance();

	protected Node createNode() {
		return managerConnection.createNode();
	}
	
	protected ManagerConnection getManagerConnection() {
		return managerConnection;
	}
	
//	public void saveNode(T object) {
//		try(Transaction tx = managerConnection.beginTx())  {
//			Node node = createNode(object);
//			System.out.println(node.getId());
//			tx.success();
//		} 
//	}
	
	protected abstract Node createNode(T object);
	
	protected abstract Node getNodeSource(T object);
	
	protected abstract void createRelationship(Node nodeFrom, Node nodeTo);

	@Override
	public EntityProvenance incluir(T object) {
		Node nodeFrom = getNodeSource(object);
		try(Transaction tx = managerConnection.beginTx())  {
			Node nodeTo = createNode(object);
			if (nodeFrom != null) {
				createRelationship(nodeFrom, nodeTo);
			}
			System.out.println(String.format("%d --> %d", nodeFrom != null ? nodeFrom.getId() : 0, nodeTo.getId()));
			tx.success();
			return object.transform(nodeTo);
		}
	}
	
	public void createRelationship(EntityProvenance entityFrom, EntityProvenance entityTo, RelationshipProvenanceType relationship) {
		Node nodeFrom = getNode(entityFrom.getId());
		Node nodeTo = getNode(entityTo.getId());
		
		try(Transaction tx = managerConnection.beginTx())  {
			Relationship myRelationship = nodeFrom.createRelationshipTo(nodeTo, relationship);
			myRelationship.setProperty("relationship-type", relationship.getName());
			System.out.println(String.format("Relationship: %d ----(%d)---> %d", nodeFrom.getId(), myRelationship.getId(), 
				nodeTo.getId()) );
			tx.success();
		}
	}
	

	@Override
	public Boolean alterar(T object) {
		int indiceFound = -1;
		for(int indice = 0; indice < listaDados.size(); indice++){
			T objeto = listaDados.get(indice);
			if (objeto.equals(object)){
				indiceFound = indice;
				break;
			}
		}
		
		if (indiceFound > -1){
			listaDados.set(indiceFound, object);
			return true;
		} else {
			return false;
		}
	}	
	
//	private boolean exists(EntityProvenance object){
//		for(EntityProvenance objeto : listaDados){
//			if (objeto.equals(object)){
//				return true;
//			}
//		}
//		return false;
//	}

	@Override
	public List<T> listar() {
		ExecutionEngine engine = new ExecutionEngine(ManagerConnection.getInstance().getGraphDb());
		ExecutionResult result;
		try(Transaction tx = managerConnection.beginTx())  {
			result = engine.execute(createQueryListar());
			return montaLista(result);
		}
	}

	protected abstract List<T> montaLista(ExecutionResult result);

	protected abstract String createQueryListar();
	
	protected String createQueryRecuperar(Long id) {
		return "start n=node(*) where id(n) = " + id + " return n";
	}

	@Override
	public Boolean excluir(EntityProvenance object) {
		int indiceFound = -1;
		for(int indice = 0; indice < listaDados.size(); indice++){
			EntityProvenance objeto = listaDados.get(indice);
			if (objeto.equals(object)){
				indiceFound = indice;
				break;
			}
		}
		if (indiceFound > -1){
			listaDados.remove(indiceFound);
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public T recuperar(Long id) {
		ExecutionEngine engine = new ExecutionEngine(ManagerConnection.getInstance().getGraphDb());
		ExecutionResult result;
		try(Transaction tx = managerConnection.beginTx())  {
			result = engine.execute(createQueryRecuperar(id));
			return montaLista(result).get(0);
		}
	}
	
	protected Node getNode(Long id) {
		try(Transaction tx = managerConnection.beginTx())  {
			return getManagerConnection().getGraphDb().getNodeById(id);
		}
	}

}
