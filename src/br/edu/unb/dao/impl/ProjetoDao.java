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
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.helpers.collection.IteratorUtil;

import br.edu.unb.entities.Projeto;
import br.edu.unb.entities.RelationshipProvenanceType;
import br.edu.unb.entities.Usuario;
import br.edu.unb.util.DateUtil;

public class ProjetoDao extends BioInformaticaDaoImpl<Projeto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProjetoDao() {
	}
	
	public Node createNode(Projeto projeto) {
		Node nodeProject = createNode();
		nodeProject.setProperty("nome", projeto.getNome());
		nodeProject.setProperty("descricao", projeto.getDescricao());
		nodeProject.setProperty("coordenador", projeto.getCoordenador());
		nodeProject.setProperty("dataHoraInicial", DateUtil.date2String(projeto.getDataHoraInicio()));
		nodeProject.setProperty("dataHoraFinal", DateUtil.date2String(projeto.getDataHoraFim()));
		nodeProject.setProperty("observacao", projeto.getObservacao());
		nodeProject.setProperty("type", projeto.getType().getName());
//		nodeProject.setProperty("usuario", projeto.getUsuario().getNome());

		String[] nomesInstituicoesParticipantes = new String[projeto.getNomesInstituicoesParticipantes().size()];
		nodeProject.setProperty("instituicoesParticipantes", projeto.getNomesInstituicoesParticipantes().
				toArray(nomesInstituicoesParticipantes));
		
		String[] nomesInstituicoesFinanciadoras = new String[projeto.getNomesInstituicoesFinanciadoras().size()];
		nodeProject.setProperty("instituicoesFinanciadoras", projeto.getNomesInstituicoesFinanciadoras().
				toArray(nomesInstituicoesFinanciadoras));
		
		return nodeProject;
	}

	@Override
	protected List<Projeto> montaLista(ExecutionResult result) {
		Iterator<Node> n_column = result.columnAs( "n" );
		Projeto projeto = null;
		List<Projeto> projetos = new ArrayList<>();
		for ( Node node : IteratorUtil.asIterable( n_column ) )
		{
			projeto = new Projeto();
			projeto.setNome((String) node.getProperty("nome"));
			projeto.setDescricao((String) node.getProperty("descricao"));
			projeto.setCoordenador((String) node.getProperty("coordenador"));
			projeto.setDataHoraInicio(DateUtil.string2Date((String) node.getProperty("dataHoraInicial")));
			projeto.setDataHoraFim(DateUtil.string2Date((String) node.getProperty("dataHoraFinal")));
			projeto.setObservacao((String) node.getProperty("observacao"));
			projeto.setUsuario(new Usuario());
//			projeto.getUsuario().setNome((String) node.getProperty("usuario"));
			projeto.setId(node.getId());
			projetos.add(projeto);
//
//			String[] nomesInstituicoesParticipantes = new String[projeto.getNomesInstituicoesParticipantes().size()];
//			nodeProject.setProperty("instituicoesParticipantes", projeto.getNomesInstituicoesParticipantes().
//					toArray(nomesInstituicoesParticipantes));
//			
//			String[] nomesInstituicoesFinanciadoras = new String[projeto.getNomesInstituicoesFinanciadoras().size()];
//			nodeProject.setProperty("instituicoesFinanciadoras", projeto.getNomesInstituicoesFinanciadoras().

		}
		return projetos;
	}

	@Override
	protected String createQueryListar() {
		return "start n=node(*) where n.type = 'Project' return n";
	}

	@Override
	public Boolean excluir(Projeto object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String createQueryRecuperar(Long id) {
		return "start n=node(*) where n.Type = 'Project' AND n.id = " + id + " return n";
	}

	@Override
	protected Node getNodeSource(Projeto projeto) {
		try(Transaction tx = getManagerConnection().beginTx())  {
			Index<Node> usersIndex = getManagerConnection().getGraphDb().index().forNodes( "users" );
			return usersIndex.get( "login", projeto.getUsuario().getLogin()).getSingle();
		}
	}

	@Override
	protected void createRelationship(Node nodeFrom, Node nodeTo) {
		Relationship myRelationship = nodeFrom.createRelationshipTo(nodeTo, RelationshipProvenanceType.HAS);
		myRelationship.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
	}

}
