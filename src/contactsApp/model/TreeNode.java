package contactsApp.model;

public class TreeNode {
	// Attributes
	private Contact data;
	private TreeNode left;
	private TreeNode right;
	
	// Constructor
	public TreeNode(Contact data) {
		this.data = data;
	}
	
	// Empty constructor
	public TreeNode() {
		
	}

	// Getters and setters
	public Contact getData() {
		return data;
	}

	public void setData(Contact data) {
		this.data = data;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}
	
}
