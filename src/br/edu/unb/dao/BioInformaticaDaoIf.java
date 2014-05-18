package br.edu.unb.dao;

import java.util.List;

import br.edu.unb.entities.EntityProvenance;
import br.edu.unb.entities.RelationshipProvenanceType;

/**
 *Interface padrao para registro em banco de dados.
 * 
 * @param <T>
 */
public interface BioInformaticaDaoIf<T extends EntityProvenance> {

	public EntityProvenance incluir(T object);
	public Boolean alterar(T object);
	public List<T> listar();
	public Boolean excluir(T object);
	public T recuperar(Long id);
	public void createRelationship(EntityProvenance entityFrom, EntityProvenance entityTo, 
			RelationshipProvenanceType relationship);
	
	
}
