/**
 * 
 */
package br.edu.unb.dao.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import br.edu.unb.dao.BioInformaticaDaoIf;
import br.edu.unb.entities.EntityProvenance;
import br.edu.unb.entities.EntityType;
import br.edu.unb.entities.RelationshipProvenanceType;
import br.edu.unb.util.DateUtil;


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
			createGraph(nodeFrom);
			tx.success();
			return object;
		}
//		Node nodeFrom = getNodeSource(object);
//		try(Transaction tx = managerConnection.beginTx())  {
//			Node nodeTo = createNode(object);
//			if (nodeFrom != null) {
//				createRelationship(nodeFrom, nodeTo);
//			}
//			System.out.println(String.format("%d --> %d", nodeFrom != null ? nodeFrom.getId() : 0, nodeTo.getId()));
//			tx.success();
//			return object.transform(nodeTo);
//		}
	}
	
	/* Método temporário */
//	private void createGraph(Node nodeUser) {
//		Node nodeProject = createNode();
//		nodeProject.setProperty("nome", "PROJECT_METAGENOMA");
//		nodeProject.setProperty("descricao", "PROJECT_METAGENOMA");
//		nodeProject.setProperty("coordenador", "MARISTELA T. HOLANDA");
//		nodeProject.setProperty("dataHoraInicial", DateUtil.date2String(new Date()));
//		nodeProject.setProperty("dataHoraFinal", DateUtil.date2String(new Date()));
//		nodeProject.setProperty("observacao", "PROJECT METAGENOMA");
//		nodeProject.setProperty("type", EntityType.PROJECT.getName());
//		nodeProject.setProperty("usuario", "AG_RODRIGO");
//		Relationship userHasProject = nodeUser.createRelationshipTo(nodeProject, RelationshipProvenanceType.HAS);
//		userHasProject.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());
//		
//		Node nodeAccount = createNode();
//		nodeAccount.setProperty("nome", "ACCOUNT_METAGENOMA");
//		nodeAccount.setProperty("descricao", "ACCOUNT METAGENOMA");
//		nodeAccount.setProperty("localExecucao", "UNB");
//		nodeAccount.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		nodeAccount.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		nodeAccount.setProperty("anotacoes", "ACCOUNT METAGENOMA");
//		nodeAccount.setProperty("versao", "1");
//		nodeAccount.setProperty("type", EntityType.ACCOUNT.getName());
//		Relationship projectHasAccount = nodeProject.createRelationshipTo(nodeAccount, RelationshipProvenanceType.HAS);
//		projectHasAccount.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());
//		
//		Node activity01 = createNode();
//		activity01.setProperty("nomePrograma", "perl");
//		activity01.setProperty("versaoPrograma", "5.4.1");
//		activity01.setProperty("linhaComando", "perl");
//		activity01.setProperty("funcao", "Filtrar");
//		activity01.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity01.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity01.setProperty("nomeAtividade", "A001_Formata");
//		activity01.setProperty("nomeArquivo", "C001_Metagenoma_virus.fna");
//		activity01.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity01 = nodeAccount.createRelationshipTo(activity01, RelationshipProvenanceType.HAS);
//		accountHasActivity01.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity01WasAssociatedWith = activity01.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity01WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		
//		Node c001 = createNode();
//		c001.setProperty("nome", "C001_Metagenoma_virus.fna");
//		c001.setProperty("tamanho", 13);
//		c001.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C001_Metagenoma_virus.fna");
//		c001.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c001Used = activity01.createRelationshipTo(c001, RelationshipProvenanceType.USED);
//		c001Used.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c002 = createNode();
//		c002.setProperty("nome", "C002_saidablasta.fasta");
//		c002.setProperty("tamanho", 13);
//		c002.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C002_saidablasta.fasta");
//		c002.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c002WasGenerated = c002.createRelationshipTo(activity01, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c002WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c002WasDerived = c002.createRelationshipTo(c001, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c002WasDerived.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity02 = createNode();
//		activity02.setProperty("nomePrograma", "blastx");
//		activity02.setProperty("versaoPrograma", "5.4.1");
//		activity02.setProperty("linhaComando", "perl");
//		activity02.setProperty("funcao", "Filtrar");
//		activity02.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity02.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity02.setProperty("nomeAtividade", "A002_Filtra");
//		activity02.setProperty("nomeArquivo", "C002_saidablasta.fasta");
//		activity02.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity02 = nodeAccount.createRelationshipTo(activity02, RelationshipProvenanceType.HAS);
//		accountHasActivity02.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity02WasAssociatedWith = activity02.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity02WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity02UsedC002 = activity02.createRelationshipTo(c002, RelationshipProvenanceType.USED);
//		activity02UsedC002.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c003 = createNode();
//		c003.setProperty("nome", "C003_blastfiltrado.fasta");
//		c003.setProperty("tamanho", 13);
//		c003.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C003_blastfiltrado.fasta");
//		c003.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c003WasGenerated = c003.createRelationshipTo(activity02, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c003WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c003WasDerived = c003.createRelationshipTo(c002, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c003WasDerived.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity03 = createNode();
//		activity03.setProperty("nomePrograma", "perl");
//		activity03.setProperty("versaoPrograma", "5.4.1");
//		activity03.setProperty("linhaComando", "perl");
//		activity03.setProperty("funcao", "Filtrar");
//		activity03.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity03.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity03.setProperty("nomeAtividade", "A003_Hit");
//		activity03.setProperty("nomeArquivo", "C003_blastfiltrado.fasta, C001_Metagenoma_virus.fna");
//		activity03.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity03 = nodeAccount.createRelationshipTo(activity03, RelationshipProvenanceType.HAS);
//		accountHasActivity03.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity03WasAssociatedWith = activity03.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity03WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity03UsedC001 = activity03.createRelationshipTo(c001, RelationshipProvenanceType.USED);
//		activity03UsedC001.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		Relationship activity03UsedC003 = activity03.createRelationshipTo(c003, RelationshipProvenanceType.USED);
//		activity03UsedC003.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c004 = createNode();
//		c004.setProperty("nome", "C004_blastfinal.fasta");
//		c004.setProperty("tamanho", 26);
//		c004.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C004_blastfinal.fasta");
//		c004.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c004WasGenerated = c004.createRelationshipTo(activity03, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c004WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c004WasDerivedC001 = c004.createRelationshipTo(c001, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c004WasDerivedC001.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		Relationship c004WasDerivedC003 = c004.createRelationshipTo(c003, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c004WasDerivedC003.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node c005 = createNode();
//		c005.setProperty("nome", "C005_out0.fasta");
//		c005.setProperty("tamanho", 26);
//		c005.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C005_out0.fasta");
//		c005.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c005WasGenerated = c005.createRelationshipTo(activity03, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c005WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c005WasDerivedC001 = c005.createRelationshipTo(c001, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c005WasDerivedC001.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		Relationship c005WasDerivedC003 = c005.createRelationshipTo(c003, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c005WasDerivedC003.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity04 = createNode();
//		activity04.setProperty("nomePrograma", "perl");
//		activity04.setProperty("versaoPrograma", "5.4.1");
//		activity04.setProperty("linhaComando", "perl");
//		activity04.setProperty("funcao", "Filtrar");
//		activity04.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity04.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity04.setProperty("nomeAtividade", "A004_Agrupa");
//		activity04.setProperty("nomeArquivo", "C004_blastfinal.fasta, C003_blastfiltrado.fasta");
//		activity04.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity04 = nodeAccount.createRelationshipTo(activity04, RelationshipProvenanceType.HAS);
//		accountHasActivity04.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity04WasAssociatedWith = activity04.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity04WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity04UsedC003 = activity04.createRelationshipTo(c003, RelationshipProvenanceType.USED);
//		activity04UsedC003.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		Relationship activity04UsedC004 = activity04.createRelationshipTo(c004, RelationshipProvenanceType.USED);
//		activity04UsedC004.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c006 = createNode();
//		c006.setProperty("nome", "C006_readsisoladas.fasta");
//		c006.setProperty("tamanho", 26);
//		c006.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C006_readsisoladas.fasta");
//		c006.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c006WasGenerated = c006.createRelationshipTo(activity04, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c006WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c006WasDerivedC003 = c006.createRelationshipTo(c003, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c006WasDerivedC003.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		Relationship c006WasDerivedC004 = c006.createRelationshipTo(c004, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c006WasDerivedC004.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node c007 = createNode();
//		c007.setProperty("nome", "C007_Grupo3_in.sanger.fasta");
//		c007.setProperty("tamanho", 26);
//		c007.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C007_Grupo3_in.sanger.fasta");
//		c007.setProperty("type", EntityType.COLLECTION.getName());
//		
//		Node c008 = createNode();
//		c008.setProperty("nome", "C008_Grupo6_in.sanger.fasta");
//		c008.setProperty("tamanho", 26);
//		c008.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C008_Grupo6_in.sanger.fasta");
//		c008.setProperty("type", EntityType.COLLECTION.getName());
//		
//		Node activity05 = createNode();
//		activity05.setProperty("nomePrograma", "perl");
//		activity05.setProperty("versaoPrograma", "5.4.1");
//		activity05.setProperty("linhaComando", "perl");
//		activity05.setProperty("funcao", "Filtrar");
//		activity05.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity05.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity05.setProperty("nomeAtividade", "A005_Mira");
//		activity05.setProperty("nomeArquivo", "C006_readsisoladas.fasta, C007_Grupo3_in.sanger.fasta, C008_Grupo6_in.sanger.fasta");
//		activity05.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity05 = nodeAccount.createRelationshipTo(activity05, RelationshipProvenanceType.HAS);
//		accountHasActivity05.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity05WasAssociatedWith = activity05.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity05WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity05UsedC006 = activity05.createRelationshipTo(c006, RelationshipProvenanceType.USED);
//		activity05UsedC006.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		Relationship activity05UsedC007 = activity05.createRelationshipTo(c007, RelationshipProvenanceType.USED);
//		activity05UsedC007.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		Relationship activity05UsedC008 = activity05.createRelationshipTo(c008, RelationshipProvenanceType.USED);
//		activity05UsedC008.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c009 = createNode();
//		c009.setProperty("nome", "C009_out_unpadded.fasta");
//		c009.setProperty("tamanho", 26);
//		c009.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C009_out_unpadded.fasta");
//		c009.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c009WasGenerated = c009.createRelationshipTo(activity05, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c009WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c009WasDerivedC006 = c009.createRelationshipTo(c006, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c009WasDerivedC006.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		Relationship c009WasDerivedC007 = c009.createRelationshipTo(c007, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c009WasDerivedC007.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		Relationship c009WasDerivedC008 = c009.createRelationshipTo(c008, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c009WasDerivedC008.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity06 = createNode();
//		activity06.setProperty("nomePrograma", "perl");
//		activity06.setProperty("versaoPrograma", "5.4.1");
//		activity06.setProperty("linhaComando", "perl");
//		activity06.setProperty("funcao", "Filtrar");
//		activity06.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity06.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity06.setProperty("nomeAtividade", "A006_Uniao");
//		activity06.setProperty("nomeArquivo", "C009_out_unpadded.fasta");
//		activity06.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity06 = nodeAccount.createRelationshipTo(activity06, RelationshipProvenanceType.HAS);
//		accountHasActivity06.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity06WasAssociatedWith = activity06.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity06WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity06UsedC009 = activity06.createRelationshipTo(c009, RelationshipProvenanceType.USED);
//		activity06UsedC009.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c010 = createNode();
//		c010.setProperty("nome", "C010_group6_out.fasta");
//		c010.setProperty("tamanho", 26);
//		c010.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C010_group6_out.fasta");
//		c010.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c010WasGenerated = c010.createRelationshipTo(activity06, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c010WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c010WasDerivedC009 = c010.createRelationshipTo(c009, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c010WasDerivedC009.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node c011 = createNode();
//		c011.setProperty("nome", "C011_group3_out.fasta");
//		c011.setProperty("tamanho", 26);
//		c011.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C011_group3_out.fasta");
//		c011.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c011WasGenerated = c011.createRelationshipTo(activity06, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c011WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c011WasDerivedC009 = c011.createRelationshipTo(c009, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c011WasDerivedC009.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity07 = createNode();
//		activity07.setProperty("nomePrograma", "perl");
//		activity07.setProperty("versaoPrograma", "5.4.1");
//		activity07.setProperty("linhaComando", "perl");
//		activity07.setProperty("funcao", "Filtrar");
//		activity07.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity07.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity07.setProperty("nomeAtividade", "A007_Recupera_Proteinas");
//		activity07.setProperty("nomeArquivo", "C010_group6_out.fasta, C011_group3_out.fasta");
//		activity07.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity07 = nodeAccount.createRelationshipTo(activity07, RelationshipProvenanceType.HAS);
//		accountHasActivity07.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity07WasAssociatedWith = activity07.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity07WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity07UsedC010 = activity07.createRelationshipTo(c010, RelationshipProvenanceType.USED);
//		activity07UsedC010.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		Relationship activity07UsedC011 = activity07.createRelationshipTo(c011, RelationshipProvenanceType.USED);
//		activity07UsedC011.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c012 = createNode();
//		c012.setProperty("nome", "C012_proteinas_grupo6.fasta");
//		c012.setProperty("tamanho", 26);
//		c012.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C012_proteinas_grupo6.fasta");
//		c012.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c012WasGenerated = c012.createRelationshipTo(activity07, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c012WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c012WasDerivedC010 = c012.createRelationshipTo(c010, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c012WasDerivedC010.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		Relationship c012WasDerivedC011 = c012.createRelationshipTo(c011, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c012WasDerivedC011.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity08 = createNode();
//		activity08.setProperty("nomePrograma", "perl");
//		activity08.setProperty("versaoPrograma", "5.4.1");
//		activity08.setProperty("linhaComando", "perl");
//		activity08.setProperty("funcao", "Filtrar");
//		activity08.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity08.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity08.setProperty("nomeAtividade", "A008_Transforma_Contigs_Proteinas");
//		activity08.setProperty("nomeArquivo", "C012_proteinas_grupo6.fasta");
//		activity08.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity08 = nodeAccount.createRelationshipTo(activity08, RelationshipProvenanceType.HAS);
//		accountHasActivity08.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity08WasAssociatedWith = activity08.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity08WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity08UsedC009 = activity08.createRelationshipTo(c009, RelationshipProvenanceType.USED);
//		activity08UsedC009.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		Relationship activity08UsedC012 = activity08.createRelationshipTo(c012, RelationshipProvenanceType.USED);
//		activity08UsedC012.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c013 = createNode();
//		c013.setProperty("nome", "C013_proteinas_6.fasta");
//		c013.setProperty("tamanho", 26);
//		c013.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C013_proteinas_6.fasta");
//		c013.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c013WasGenerated = c013.createRelationshipTo(activity08, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c013WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c013WasDerivedC009 = c013.createRelationshipTo(c009, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c013WasDerivedC009.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		Relationship c013WasDerivedC012 = c013.createRelationshipTo(c012, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c013WasDerivedC012.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity09 = createNode();
//		activity09.setProperty("nomePrograma", "perl");
//		activity09.setProperty("versaoPrograma", "5.4.1");
//		activity09.setProperty("linhaComando", "perl");
//		activity09.setProperty("funcao", "Filtrar");
//		activity09.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity09.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity09.setProperty("nomeAtividade", "A009_Muscle");
//		activity09.setProperty("nomeArquivo", "C013_proteinas_6.fasta");
//		activity09.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity09 = nodeAccount.createRelationshipTo(activity09, RelationshipProvenanceType.HAS);
//		accountHasActivity09.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity09WasAssociatedWith = activity09.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity09WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity09UsedC013 = activity09.createRelationshipTo(c013, RelationshipProvenanceType.USED);
//		activity09UsedC013.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c014 = createNode();
//		c014.setProperty("nome", "C014_muscle6.afa");
//		c014.setProperty("tamanho", 26);
//		c014.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C014_muscle6.afa");
//		c014.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c014WasGenerated = c014.createRelationshipTo(activity09, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c014WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c014WasDerivedC013 = c014.createRelationshipTo(c013, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c014WasDerivedC013.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity10 = createNode();
//		activity10.setProperty("nomePrograma", "perl");
//		activity10.setProperty("versaoPrograma", "5.4.1");
//		activity10.setProperty("linhaComando", "perl");
//		activity10.setProperty("funcao", "Filtrar");
//		activity10.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity10.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity10.setProperty("nomeAtividade", "A010_Reducao_Nome");
//		activity10.setProperty("nomeArquivo", "C014_muscle6.afa");
//		activity10.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity10 = nodeAccount.createRelationshipTo(activity10, RelationshipProvenanceType.HAS);
//		accountHasActivity10.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity10WasAssociatedWith = activity10.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity10WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity10UsedC014 = activity10.createRelationshipTo(c014, RelationshipProvenanceType.USED);
//		activity10UsedC014.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c015 = createNode();
//		c015.setProperty("nome", "C015_muscle6_reduzido.afa");
//		c015.setProperty("tamanho", 26);
//		c015.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C015_muscle6_reduzido.afa");
//		c015.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c015WasGenerated = c015.createRelationshipTo(activity10, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c015WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c015WasDerivedC014 = c015.createRelationshipTo(c014, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c015WasDerivedC014.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity11 = createNode();
//		activity11.setProperty("nomePrograma", "perl");
//		activity11.setProperty("versaoPrograma", "5.4.1");
//		activity11.setProperty("linhaComando", "perl");
//		activity11.setProperty("funcao", "Filtrar");
//		activity11.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity11.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity11.setProperty("nomeAtividade", "A011_Fasta_2_Phylip");
//		activity11.setProperty("nomeArquivo", "C015_muscle6_reduzido.afa");
//		activity11.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity11 = nodeAccount.createRelationshipTo(activity11, RelationshipProvenanceType.HAS);
//		accountHasActivity11.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity11WasAssociatedWith = activity11.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity11WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity11UsedC015 = activity11.createRelationshipTo(c014, RelationshipProvenanceType.USED);
//		activity11UsedC015.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c016 = createNode();
//		c016.setProperty("nome", "C016_grupo6.phy");
//		c016.setProperty("tamanho", 26);
//		c016.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C016_grupo6.phy");
//		c016.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c016WasGenerated = c016.createRelationshipTo(activity11, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c016WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c016WasDerivedC015 = c016.createRelationshipTo(c015, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c016WasDerivedC015.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity12 = createNode();
//		activity12.setProperty("nomePrograma", "perl");
//		activity12.setProperty("versaoPrograma", "5.4.1");
//		activity12.setProperty("linhaComando", "perl");
//		activity12.setProperty("funcao", "Filtrar");
//		activity12.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity12.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity12.setProperty("nomeAtividade", "A012_Raxml");
//		activity12.setProperty("nomeArquivo", "C015_muscle6_reduzido.afa");
//		activity12.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity12 = nodeAccount.createRelationshipTo(activity12, RelationshipProvenanceType.HAS);
//		accountHasActivity12.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity12WasAssociatedWith = activity12.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity12WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity12UsedC016 = activity12.createRelationshipTo(c016, RelationshipProvenanceType.USED);
//		activity12UsedC016.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c017 = createNode();
//		c017.setProperty("nome", "C017_Raxml_besTree.grupo6");
//		c017.setProperty("tamanho", 26);
//		c017.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C017_Raxml_besTree.grupo6");
//		c017.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c017WasGenerated = c017.createRelationshipTo(activity12, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c017WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c017WasDerivedC016 = c017.createRelationshipTo(c016, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c017WasDerivedC016.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		
//		Node activity13 = createNode();
//		activity13.setProperty("nomePrograma", "perl");
//		activity13.setProperty("versaoPrograma", "5.4.1");
//		activity13.setProperty("linhaComando", "perl");
//		activity13.setProperty("funcao", "Filtrar");
//		activity13.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity13.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity13.setProperty("nomeAtividade", "A013_Geracao_Arvore");
//		activity13.setProperty("nomeArquivo", "C017_Raxml_besTree.grupo6");
//		activity13.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity13 = nodeAccount.createRelationshipTo(activity13, RelationshipProvenanceType.HAS);
//		accountHasActivity13.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity13WasAssociatedWith = activity13.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity13WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity13UsedC017 = activity13.createRelationshipTo(c017, RelationshipProvenanceType.USED);
//		activity13UsedC017.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		
//		Node c018 = createNode();
//		c018.setProperty("nome", "grupo06.pdf");
//		c018.setProperty("tamanho", 26);
//		c018.setProperty("localizacao", "Users/Documents/ProvenanceFiles/grupo06.pdf");
//		c018.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c018WasGenerated = c018.createRelationshipTo(activity13, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c018WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c018WasDerivedC017 = c018.createRelationshipTo(c017, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c018WasDerivedC017.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//	}

	private void createGraph(Node nodeUser) {
		Node nodeProject = createNode();
		nodeProject.setProperty("nome", "PROJECT_BACILLUS");
		nodeProject.setProperty("descricao", "PROJECT_BACILLUS");
		nodeProject.setProperty("coordenador", "MARISTELA T. HOLANDA");
		nodeProject.setProperty("dataHoraInicial", DateUtil.date2String(new Date()));
		nodeProject.setProperty("dataHoraFinal", DateUtil.date2String(new Date()));
		nodeProject.setProperty("observacao", "PROJECT BACILLUS");
		nodeProject.setProperty("type", EntityType.PROJECT.getName());
		nodeProject.setProperty("usuario", "AG_RODRIGO");
		Relationship userHasProject = nodeUser.createRelationshipTo(nodeProject, RelationshipProvenanceType.HAS);
		userHasProject.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());
		
		Node nodeAccount = createNode();
		nodeAccount.setProperty("nome", "ACCOUNT_BACILLUS");
		nodeAccount.setProperty("descricao", "ACCOUNT BACILLUS");
		nodeAccount.setProperty("localExecucao", "UNB");
		nodeAccount.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
		nodeAccount.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
		nodeAccount.setProperty("anotacoes", "ACCOUNT BACILLUS");
		nodeAccount.setProperty("versao", "1");
		nodeAccount.setProperty("type", EntityType.ACCOUNT.getName());
		Relationship projectHasAccount = nodeProject.createRelationshipTo(nodeAccount, RelationshipProvenanceType.HAS);
		projectHasAccount.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());
		
//		Node activity01 = createNode();
//		activity01.setProperty("nomePrograma", "perl");
//		activity01.setProperty("versaoPrograma", "5.4.1");
//		activity01.setProperty("linhaComando", "perl");
//		activity01.setProperty("funcao", "Filtrar");
//		activity01.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity01.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity01.setProperty("nomeAtividade", "A001_Family13_Filtro");
//		activity01.setProperty("nomeArquivo", "C001_Family13");
//		activity01.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity01 = nodeAccount.createRelationshipTo(activity01, RelationshipProvenanceType.HAS);
//		accountHasActivity01.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity01WasAssociatedWith = activity01.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity01WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
		
//		Node c001 = createNode();
//		c001.setProperty("nome", "C001_Family13");
//		c001.setProperty("tamanho", 13);
//		c001.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C001_Family13");
//		c001.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c001Used = activity01.createRelationshipTo(c001, RelationshipProvenanceType.USED);
//		c001Used.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
		
		Node activity02 = createNode();
		activity02.setProperty("nomePrograma", "perl");
		activity02.setProperty("versaoPrograma", "5.4.1");
		activity02.setProperty("linhaComando", "perl");
		activity02.setProperty("funcao", "Filtrar");
		activity02.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
		activity02.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
		activity02.setProperty("nomeAtividade", "A002_Family57_Filtro");
		activity02.setProperty("nomeArquivo", "C002_Family57");
		activity02.setProperty("type", EntityType.ACTIVITY.getName());
		Relationship accountHasActivity02 = nodeAccount.createRelationshipTo(activity02, RelationshipProvenanceType.HAS);
		accountHasActivity02.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
		Relationship activity02WasAssociatedWith = activity02.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
		activity02WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
		
		Node c002 = createNode();
		c002.setProperty("nome", "C002_Family57");
		c002.setProperty("tamanho", 13);
		c002.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C002_Family57");
		c002.setProperty("type", EntityType.COLLECTION.getName());
		Relationship c002Used = activity02.createRelationshipTo(c002, RelationshipProvenanceType.USED);
		c002Used.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
		
//		Node c003 = createNode();
//		c003.setProperty("nome", "C003_Family13_Filtrado");
//		c003.setProperty("tamanho", 13);
//		c003.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C003_Family13_Filtrado");
//		c003.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c003WasGenerated = c003.createRelationshipTo(activity01, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c003WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c003WasDerived = c003.createRelationshipTo(c001, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c003WasDerived.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
		
		Node c004 = createNode();
		c004.setProperty("nome", "C004_Family57_Filtrado");
		c004.setProperty("tamanho", 13);
		c004.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C004_Family57_Filtrado");
		c004.setProperty("type", EntityType.COLLECTION.getName());
		Relationship c004WasGenerated = c004.createRelationshipTo(activity02, RelationshipProvenanceType.WAS_GENERATED_BY);
		c004WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
		Relationship c004WasDerived = c004.createRelationshipTo(c002, RelationshipProvenanceType.WAS_DERIVED_FROM);
		c004WasDerived.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
		
		Node c005 = createNode();
		c005.setProperty("nome", "C005_ORFS");
		c005.setProperty("tamanho", 13);
		c005.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C005_ORFS");
		c005.setProperty("type", EntityType.COLLECTION.getName());
		
//		Node activity03 = createNode();
//		activity03.setProperty("nomePrograma", "perl");
//		activity03.setProperty("versaoPrograma", "5.4.1");
//		activity03.setProperty("linhaComando", "perl");
//		activity03.setProperty("funcao", "Filtrar");
//		activity03.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity03.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity03.setProperty("nomeAtividade", "A003_Family13_Merge");
//		activity03.setProperty("nomeArquivo", "C003_Family13_Filtrado, C005_ORFS");
//		activity03.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity03 = nodeAccount.createRelationshipTo(activity03, RelationshipProvenanceType.HAS);
//		accountHasActivity03.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity03WasAssociatedWith = activity03.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity03WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity03UsedC005 = activity03.createRelationshipTo(c005, RelationshipProvenanceType.USED);
//		activity03UsedC005.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
//		Relationship activity03UsedC003 = activity03.createRelationshipTo(c003, RelationshipProvenanceType.USED);
//		activity03UsedC003.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
		
		Node activity04 = createNode();
		activity04.setProperty("nomePrograma", "perl");
		activity04.setProperty("versaoPrograma", "5.4.1");
		activity04.setProperty("linhaComando", "perl");
		activity04.setProperty("funcao", "Filtrar");
		activity04.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
		activity04.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
		activity04.setProperty("nomeAtividade", "A004_Family57_Merge");
		activity04.setProperty("nomeArquivo", "C004_Family57_Filtrado, C005_ORFS");
		activity04.setProperty("type", EntityType.ACTIVITY.getName());
		Relationship accountHasActivity04 = nodeAccount.createRelationshipTo(activity04, RelationshipProvenanceType.HAS);
		accountHasActivity04.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
		Relationship activity04WasAssociatedWith = activity04.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
		activity04WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
		Relationship activity04UsedC005 = activity04.createRelationshipTo(c005, RelationshipProvenanceType.USED);
		activity04UsedC005.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
		Relationship activity04UsedC004 = activity04.createRelationshipTo(c004, RelationshipProvenanceType.USED);
		activity04UsedC004.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());

//		Node c006 = createNode();
//		c006.setProperty("nome", "C006_Family13_ORFS");
//		c006.setProperty("tamanho", 26);
//		c006.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C006_Family13_ORFS");
//		c006.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c006WasGenerated = c006.createRelationshipTo(activity03, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c006WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c006WasDerivedC005 = c006.createRelationshipTo(c005, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c006WasDerivedC005.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
//		Relationship c006WasDerivedC003 = c006.createRelationshipTo(c003, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c006WasDerivedC003.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
		
		Node c007 = createNode();
		c007.setProperty("nome", "C007_Family57_ORFS");
		c007.setProperty("tamanho", 26);
		c007.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C007_Family57_ORFS");
		c007.setProperty("type", EntityType.COLLECTION.getName());
		Relationship c007WasGenerated = c007.createRelationshipTo(activity04, RelationshipProvenanceType.WAS_GENERATED_BY);
		c007WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
		Relationship c007WasDerivedC005 = c007.createRelationshipTo(c005, RelationshipProvenanceType.WAS_DERIVED_FROM);
		c007WasDerivedC005.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
		Relationship c007WasDerivedC004 = c007.createRelationshipTo(c004, RelationshipProvenanceType.WAS_DERIVED_FROM);
		c007WasDerivedC004.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
		
//		Node activity05 = createNode();
//		activity05.setProperty("nomePrograma", "perl");
//		activity05.setProperty("versaoPrograma", "5.4.1");
//		activity05.setProperty("linhaComando", "perl");
//		activity05.setProperty("funcao", "Filtrar");
//		activity05.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity05.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity05.setProperty("nomeAtividade", "A005_Family13_Alignment");
//		activity05.setProperty("nomeArquivo", "C006_Family13_ORFS");
//		activity05.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity05 = nodeAccount.createRelationshipTo(activity05, RelationshipProvenanceType.HAS);
//		accountHasActivity05.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity05WasAssociatedWith = activity05.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity05WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity05UsedC006 = activity05.createRelationshipTo(c006, RelationshipProvenanceType.USED);
//		activity05UsedC006.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
		
		Node activity06 = createNode();
		activity06.setProperty("nomePrograma", "perl");
		activity06.setProperty("versaoPrograma", "5.4.1");
		activity06.setProperty("linhaComando", "perl");
		activity06.setProperty("funcao", "Filtrar");
		activity06.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
		activity06.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
		activity06.setProperty("nomeAtividade", "A006_Family57_Alignment");
		activity06.setProperty("nomeArquivo", "C007_Family57_ORFS");
		activity06.setProperty("type", EntityType.ACTIVITY.getName());
		Relationship accountHasActivity06 = nodeAccount.createRelationshipTo(activity06, RelationshipProvenanceType.HAS);
		accountHasActivity06.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
		Relationship activity06WasAssociatedWith = activity06.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
		activity06WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
		Relationship activity06UsedC007 = activity06.createRelationshipTo(c007, RelationshipProvenanceType.USED);
		activity06UsedC007.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
		
//		Node c008 = createNode();
//		c008.setProperty("nome", "C008_Family13_Alignment");
//		c008.setProperty("tamanho", 26);
//		c008.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C008_Family13_Alignment");
//		c008.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c008WasGenerated = c008.createRelationshipTo(activity05, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c008WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c008WasDerivedC006 = c008.createRelationshipTo(c006, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c008WasDerivedC006.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
		
		Node c009 = createNode();
		c009.setProperty("nome", "C009_Family57_Alignment");
		c009.setProperty("tamanho", 26);
		c009.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C009_Family57_Alignment");
		c009.setProperty("type", EntityType.COLLECTION.getName());
		Relationship c009WasGenerated = c009.createRelationshipTo(activity06, RelationshipProvenanceType.WAS_GENERATED_BY);
		c009WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
		Relationship c009WasDerivedC007 = c009.createRelationshipTo(c007, RelationshipProvenanceType.WAS_DERIVED_FROM);
		c009WasDerivedC007.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
		
//		Node activity07 = createNode();
//		activity07.setProperty("nomePrograma", "perl");
//		activity07.setProperty("versaoPrograma", "5.4.1");
//		activity07.setProperty("linhaComando", "perl");
//		activity07.setProperty("funcao", "Filtrar");
//		activity07.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
//		activity07.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
//		activity07.setProperty("nomeAtividade", "A007_Family13_Graph");
//		activity07.setProperty("nomeArquivo", "C008_Family13_Alignment");
//		activity07.setProperty("type", EntityType.ACTIVITY.getName());
//		Relationship accountHasActivity07 = nodeAccount.createRelationshipTo(activity07, RelationshipProvenanceType.HAS);
//		accountHasActivity07.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
//		Relationship activity07WasAssociatedWith = activity07.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
//		activity07WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
//		Relationship activity07UsedC008 = activity07.createRelationshipTo(c008, RelationshipProvenanceType.USED);
//		activity07UsedC008.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
		
		Node activity08 = createNode();
		activity08.setProperty("nomePrograma", "perl");
		activity08.setProperty("versaoPrograma", "5.4.1");
		activity08.setProperty("linhaComando", "perl");
		activity08.setProperty("funcao", "Filtrar");
		activity08.setProperty("dataHoraInicio", DateUtil.date2String(new Date()));
		activity08.setProperty("dataHoraFim", DateUtil.date2String(new Date()));
		activity08.setProperty("nomeAtividade", "A008_Family57_Graph");
		activity08.setProperty("nomeArquivo", "C008_Family57_Alignment");
		activity08.setProperty("type", EntityType.ACTIVITY.getName());
		Relationship accountHasActivity08 = nodeAccount.createRelationshipTo(activity08, RelationshipProvenanceType.HAS);
		accountHasActivity08.setProperty("relationship-type", RelationshipProvenanceType.HAS.getName());;
		Relationship activity08WasAssociatedWith = activity08.createRelationshipTo(nodeUser, RelationshipProvenanceType.WAS_ASSOCIATED_WITH);
		activity08WasAssociatedWith.setProperty("relationship-type", RelationshipProvenanceType.WAS_ASSOCIATED_WITH.getName());
		Relationship activity08UsedC009 = activity08.createRelationshipTo(c009, RelationshipProvenanceType.USED);
		activity08UsedC009.setProperty("relationship-type", RelationshipProvenanceType.USED.getName());
		
//		Node c010 = createNode();
//		c010.setProperty("nome", "C010_Family13_Graph");
//		c010.setProperty("tamanho", 26);
//		c010.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C010_Family13_Graph");
//		c010.setProperty("type", EntityType.COLLECTION.getName());
//		Relationship c010WasGenerated = c010.createRelationshipTo(activity07, RelationshipProvenanceType.WAS_GENERATED_BY);
//		c010WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
//		Relationship c010WasDerivedC008 = c010.createRelationshipTo(c008, RelationshipProvenanceType.WAS_DERIVED_FROM);
//		c010WasDerivedC008.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
		
		Node c011 = createNode();
		c011.setProperty("nome", "C011_Family57_Graph");
		c011.setProperty("tamanho", 26);
		c011.setProperty("localizacao", "Users/Documents/ProvenanceFiles/C011_Family57_Graph");
		c011.setProperty("type", EntityType.COLLECTION.getName());
		Relationship c011WasGenerated = c011.createRelationshipTo(activity08, RelationshipProvenanceType.WAS_GENERATED_BY);
		c011WasGenerated.setProperty("relationship-type", RelationshipProvenanceType.WAS_GENERATED_BY.getName());
		Relationship c011WasDerivedC009 = c011.createRelationshipTo(c009, RelationshipProvenanceType.WAS_DERIVED_FROM);
		c011WasDerivedC009.setProperty("relationship-type", RelationshipProvenanceType.WAS_DERIVED_FROM.getName());
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
