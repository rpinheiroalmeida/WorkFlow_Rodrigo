package br.edu.unb.dao.impl;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class ManagerConnection {

	private static final String DB_PATH = "/usr/local/Cellar/neo4j/2.0.1/libexec/data/graph.db";
	private static GraphDatabaseService graphDb;
	private static ManagerConnection instance = new ManagerConnection();

	private ManagerConnection() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
	}

	protected static ManagerConnection getInstance() {
		return instance;
	}

	protected GraphDatabaseService getGraphDb() {
		return graphDb;
	}
	
	protected Node createNode() {
		return graphDb.createNode();
	}
	
	public Transaction beginTx() {
		return graphDb.beginTx();
	}

	public static void startGraphDbService() {
		if (graphDb != null) {
			boolean isUsable = graphDb.isAvailable(0);
			if (!isUsable) {
				graphDb.shutdown();
			}
		}
	}
	
	public static void shutdown() {
		if (graphDb != null) {
			graphDb.shutdown();
		}
	}
}
