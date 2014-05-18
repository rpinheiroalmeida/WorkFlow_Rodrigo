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
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.helpers.collection.IteratorUtil;

import br.edu.unb.entities.Atividade;
import br.edu.unb.entities.Experimento;
import br.edu.unb.entities.Projeto;
import br.edu.unb.entities.RelationshipProvenanceType;
import br.edu.unb.entities.Usuario;
import br.edu.unb.util.DateUtil;



/**
 * 
 */
public class UsuarioDao extends BioInformaticaDaoImpl<Usuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UsuarioDao() {
	}

	public Node createNodeUsuario(Usuario usuario) {
		Node lockNode;
		try ( Transaction tx = getManagerConnection().getGraphDb().beginTx() )
		{
		    lockNode = getManagerConnection().getGraphDb().createNode();
		    tx.success();
		}
		
		try(Transaction tx = getManagerConnection().beginTx())  { 
			Index<Node> usersIndex = getManagerConnection().getGraphDb().index().forNodes( "users" );
			Node userNode = usersIndex.get( "login", usuario.getLogin()).getSingle();
			if ( userNode != null )
			{
				return userNode;
			}
			
			tx.acquireWriteLock( lockNode );
			userNode = usersIndex.get( "login", usuario.getLogin() ).getSingle();
			if ( userNode == null )
			{
				userNode = getManagerConnection().getGraphDb().createNode( DynamicLabel.label( "User" ) );
				usersIndex.add( userNode, "login", usuario.getLogin() );
				userNode.setProperty( "login", usuario.getLogin() );
				userNode.setProperty( "nome", usuario.getNome() );
				userNode.setProperty( "senha", usuario.getSenha() );
				userNode.setProperty("type", usuario.getType().getName());
			}
			tx.success();
			return userNode;
		}
	}

	@Override
	protected List<Usuario> montaLista(ExecutionResult result) {
		Iterator<Node> n_column = result.columnAs( "n" );
		Usuario usuario = null;
		List<Usuario> usuarios = new ArrayList<>();
		for ( Node node : IteratorUtil.asIterable( n_column ) )
		{
			usuario = new Usuario();
			usuario.setNome((String) node.getProperty("nome"));
			usuario.setLogin((String) node.getProperty("login"));
			
			usuarios.add(usuario);
		}
		return usuarios;
	}

	@Override
	protected String createQueryListar() {
		return null;
	}

	@Override
	public Boolean excluir(Usuario object) {
		return null;
	}

	@Override
	protected String createQueryRecuperar(Long id) {
		return null;
	}

	@Override
	protected Node getNodeSource(Usuario object) {
		return null;
	}

	@Override
	protected void createRelationship(Node nodeFrom, Node nodeTo) {
	}

	public List<Projeto> buscarProjetos(Usuario usuario) {
		List<Projeto> projetos = new ArrayList<>();
		try(Transaction tx = getManagerConnection().beginTx())  {
			Node nodeUser = getManagerConnection().getGraphDb().getNodeById(usuario.getId());
			Iterable<Relationship> allRelationshipsUsers = nodeUser.getRelationships(RelationshipProvenanceType.HAS);
			Set<Experimento> experimentos;
			Set<Atividade> atividades;
			for(Relationship relHasProject : allRelationshipsUsers) {
				Node nodeProject = relHasProject.getOtherNode(nodeUser);
				Projeto projeto = Projeto.transforma(nodeProject);
				
				experimentos = new HashSet<>();
				Iterable<Relationship> allRelHasAccount = nodeProject.getRelationships(Direction.OUTGOING);
				for (Relationship relHasAccount : allRelHasAccount) {
					Node nodeExperiment = relHasAccount.getOtherNode(nodeProject);
					Experimento experimento = Experimento.transforma(nodeExperiment);
					
					atividades = new HashSet<>();
					Iterable<Relationship> allRelHasActivity = nodeExperiment.getRelationships(Direction.OUTGOING);
					for (Relationship relHasActivity : allRelHasActivity) {
						Node nodeActivity = relHasActivity.getOtherNode(nodeExperiment);
						Atividade atividade = Atividade.transforma(nodeActivity);
						System.out.println("Id Atividade = " + atividade.getId());
						atividade.setExperimentoOrigem(experimento);
						atividades.add(atividade);
					}
					experimento.setAtividades(atividades);
					experimentos.add(experimento);
				}
				projeto.setExperimentos(experimentos);
				projetos.add(projeto);
			}
		}
		
		return projetos;
	}
	
	
	
	private List<Projeto> montaListaProjetos(ExecutionResult result) {
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
			projeto.getUsuario().setNome((String) node.getProperty("usuario"));
			projeto.setIdProjeto(node.getId());
			projetos.add(projeto);

		}
		return projetos;
	}

	@Override
	protected Node createNode(Usuario object) {
		return null;
	}

	public Usuario recuperarUsuario(Usuario usuario) {
		try(Transaction tx = getManagerConnection().beginTx())  { 
			Index<Node> usersIndex = getManagerConnection().getGraphDb().index().forNodes( "users" );
			Node userNode = usersIndex.get( "login", usuario.getLogin()).getSingle();
			if ( userNode != null )
			{
				Usuario pUsuario = new Usuario();
				pUsuario.setNome((String) userNode.getProperty("nome"));
				pUsuario.setLogin((String) userNode.getProperty("login"));
				pUsuario.setId(userNode.getId());
				
				return pUsuario;
			}
		}
		return null;
//		ExecutionEngine engine = new ExecutionEngine(ManagerConnection.getInstance().getGraphDb());
//		ExecutionResult result;
//		try(Transaction tx = getManagerConnection().beginTx())  {
//			result = engine.execute("start n=node(*) where n.Type = 'AGENT' AND n.login = '" + usuario.getLogin() +
//					"' return n");
//			List<Usuario> listUsuario = montaLista(result); 
//			return listUsuario.isEmpty() ? null : listUsuario.get(0);
//		}
	}




}
