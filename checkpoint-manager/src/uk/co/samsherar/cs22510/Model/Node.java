package uk.co.samsherar.cs22510.Model;

public class Node {
	private int checkpointID;
	private boolean visited;
	private Node nextNode = null;
	
	public Node(int checkpointID) {
		this.checkpointID = checkpointID;
		this.visited = false;
	}
	
	public void setVisited(boolean val) {
		this.visited = val;
	}
	
	public boolean hasVisited() {
		return this.visited;
	}
	
	public int getCheckpoint() {
		return this.checkpointID;
	}
	
	public void setNextNode(Node n) {
		this.nextNode = n;
	}
	
	public Node getNextNode() {
		return this.nextNode;
	}
}
