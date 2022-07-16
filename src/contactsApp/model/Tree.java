package contactsApp.model;

import java.util.ArrayList;

/**
 * Binary tree where contacts are saved
 * @author Samuel Echeverri
 * @author Juan Felipe Lopez
 *
 */
public class Tree {
	// Attribute
	private TreeNode root;
	
	// Contacts list that will be given to UI
	ArrayList<Contact> contactsList = new ArrayList<>();
	
	// Contacts array that will be returned to the UI as a search result
	Contact[] searchResults = new Contact[2]; 
	
	// Constructor
	public Tree() {
		root = null;
	}
	
	/**
	 * Verifies if tree is empty
	 * @return True if it's empty, false otherwise
	 */
	public boolean isEmpty() { return root == null; }
	
	/**
	 * Verifies if an entered value is in the tree 
	 * @param value 
	 * @return True si el valor esta en el arbol, false en caso contrario
	 */
	public boolean contains(Contact value) {
				
		if( isEmpty() ) {
			System.err.println("Empty tree");
			return false;
		}
		
		return getNode(value, root) != null;
	}
	
	/**
	 * Finds a value in the tree
	 * @param value
	 * @param start (Node from which the search is going to start)
	 * @return True if the entered vaue is in the tree, false otherwise
	 */
	private TreeNode getNode(Contact value, TreeNode start) {
		
		if(start == null) {
			return null;
		}
		
		if( value.equals( start.getData() ) ) {
			return start;
		}
		else if( compare( value.getName(), start.getData().getName() ) == -1 ) {
			return getNode( value, start.getLeft() );
		}
		else {
			return getNode( value, start.getRight() );
		}
	}
	
	/**
	 * Gets a contact in the tree searched by name
	 * @param name
	 * @return found Contact
	 */
	public Contact getContact(String name) {
		return getContactRecursive(name, root);
	}
	
	/**
	 * Recursive method used to find a Contact in the tree
	 * @param name
	 * @param start (Node from which the search is going to start)
	 * @return found Contact
	 */
	private Contact getContactRecursive(String name, TreeNode start) {
		
		if(start == null) {
			return null;
		}
		
		if( name.equalsIgnoreCase( start.getData().getName() ) ) {
			return start.getData();
		}
		else if( compare( name, start.getData().getName() ) == -1 ) {
			return getContactRecursive( name, start.getLeft() );
		}
		else {
			return getContactRecursive( name, start.getRight() );
		}
	}

	/**
	 * Gets a contact searching it in each contact's list from the tree  
	 * @param name
	 * @return found Contact
	 */
	public Contact[] deepSearch(String name) {
		deepSearchRecursive(name, root);
		return searchResults;
	}
	
	/**
	 * Recursive method used to find a contact searching it in each contact's list from the tree
	 * @param name
	 * @param start (Node from which the search is going to start)
	 * @return found Contact
	 */
	private void deepSearchRecursive(String name, TreeNode start) {
		
		if( start != null ) {
			if( start.getData().getContacts().getContact(name) != null ) {
				searchResults[0] = start.getData();
				searchResults[1] = start.getData().getContacts().getContact(name);
			}
			else {
				deepSearchRecursive( name, start.getLeft() );
				deepSearchRecursive( name, start.getRight() );
			}
		}
		
	}

	/**
	 * Compares the alphabetical value of two entered words 
	 * @param word
	 * @param word2
	 * @return -1 if alphabetical value of word is less than word2; 
	 * 1 if alphabetical value of word is greater than word2; 
	 * 0 if their value is the same
	 */
	private int compare(String word, String word2) {
		word = word.toUpperCase();
		word2 = word2.toUpperCase();
		
		for(int i = 0; i < word.length(); i++) {
			
			if(word.charAt(i) < word2.charAt(i) ) {
				return -1;
			}
			else if( word.charAt(i) > word2.charAt(i) ) {
				return 1;
			}
		}
		
		return 0;
	}

	/**
	 * Gets the parent node of an entered node
	 * @param node
	 * @param start (Node from which the search is going to start)
	 * @return Parent node
	 */
	private TreeNode getParent(TreeNode node, TreeNode start) {
		TreeNode left = start.getLeft();
		TreeNode right = start.getRight();
		
		if(node == root) {
			return null;
		}
			
		if(left != null && left.getData().equals( node.getData() ) || right != null && right.getData().equals( node.getData() )  ) {
			return start;
		}
		else if( compare( node.getData().getName(), start.getData().getName() ) == -1 ) {
			return getParent(node, left);
		}
		else if( compare( node.getData().getName(), start.getData().getName() ) == 1 ) {
			return getParent(node, right);
		}
		
		return null;
	}
	
	// --------------------------------------------------------- ITERATE ---------------------------------------------------------------------
	
	/**
	 * Saves all the tree contacts in a contacts list
	 * (Allows communication with UI)
	 * @param start
	 */
	private void fill_List(TreeNode start) {
		
		if(start != null ) {
			if(start.getLeft() != null) {
				fill_List( start.getLeft() );
			}
			
			contactsList.add( start.getData() );
			
			if(start.getRight() != null) {
				fill_List( start.getRight() );
			}
		}
		
	}

	// --------------------------------------------------------- INSERT ----------------------------------------------------------------------
	
	/**
	 * Inserts a value in the tree
	 * @param value
	 */
	public void insert(Contact value) {
		TreeNode newNode = new TreeNode(value);
		
		if( isEmpty() ) {
			root = newNode;
		}
		else if( contains(value) ){
			System.err.println("The value you try to enter exists already in the tree (" + value + ")");
		}
		else {
			insertRecursive(newNode, root);
		}	
	}
	
	/**
	 * Recursive method used to find the position in which a new value must be inserted in te tree, and to insert it in that position
	 * @param newNode
	 * @param start
	 */
	private void insertRecursive(TreeNode newNode, TreeNode start) {
				
		if( compare( newNode.getData().getName(), start.getData().getName() ) == -1 ) {
			
			if(start.getLeft() != null) {
				insertRecursive( newNode, start.getLeft() );
			}
			else {
				start.setLeft(newNode);
			}
		}
		
		if( compare( newNode.getData().getName(), start.getData().getName() ) == 1 ) {
			
			if(start.getRight() != null) {
				insertRecursive( newNode, start.getRight() );
			}
			else {
				start.setRight(newNode);
			}
		}
	}
	
	// ------------------------------------------------------- DELETE -----------------------------------------------------------------------
	
	/**
	 * Deletes a value from the tree
	 * @param value
	 */
	public void delete(Contact value) {
		TreeNode toDelete = getNode(value, root);
		
		if( isEmpty() ) {
			System.err.println("Empty tree. Nothing to delete");
		}
		else {
			if( toDelete != null) {
				deleteRecursive(toDelete, root);
			}
			else {
				System.err.println("Value not found");
			}
		}
	}
 
	/**
	 * Recursive method used to delete a value and to rearrange the tree
	 * @param toDelete
	 * @param start
	 */
	private void deleteRecursive(TreeNode toDelete, TreeNode start) {
		TreeNode parent;
		
		if( toDelete.getData().equals( start.getData() )  ) {
			
			// Verifies the node to delete has no children
			if(toDelete.getLeft() == null && toDelete.getRight() == null) {
				
				parent = getParent(toDelete, root);
				
				// If parent node is null, it means the node to delete is the root
				if(parent == null) {
					root = null;
				}
				else {
					if( compare( toDelete.getData().getName(), parent.getData().getName() ) == -1 ) {
						parent.setLeft(null);
					}
					else if( compare( toDelete.getData().getName(), parent.getData().getName() ) == 1 ) {
						parent.setRight(null);
					}
				}
			}
			// Verifies the node to delete has only one child (right child)
			else if(toDelete.getLeft() == null) {
				parent = getParent(toDelete, root);
				
				// If parent node is null, it means the node to delete is the root
				if(parent == null) {
					root = toDelete.getRight();
				}
				else {
					if( compare( toDelete.getData().getName(), parent.getData().getName() ) == -1 ) {
						parent.setLeft( toDelete.getRight() );
					}
					else if( compare( toDelete.getData().getName(), parent.getData().getName() ) == 1 ) {
						parent.setRight( toDelete.getRight() );
					}
				}
			}
			// Verifies the node to delete has only one child (left child)
			else if(toDelete.getRight() == null) {
				parent = getParent(toDelete, root);
				
				// If parent node is null, it means the node to delete is the root
				if(parent == null) {
					root = toDelete.getLeft();
				}
				else {
					if( compare( toDelete.getData().getName(), parent.getData().getName() ) == -1 ) {
						parent.setLeft( toDelete.getLeft() );
					}
					else if( compare( toDelete.getData().getName(), parent.getData().getName() ) == 1 ) {
						parent.setRight( toDelete.getLeft() );
					}
				}
			}
			else {
				// Finds the minor value node in the right subtree to replace the node to delete 
				TreeNode replacement = getMinorNode( toDelete.getRight() );
				
				// Removes the replacement node from its previous position
				delete( replacement.getData() );
				
				TreeNode left = toDelete.getLeft();
				TreeNode right = toDelete.getRight();
				
				// Assigns to the replacement node the children of the node to delete
				replacement.setLeft(left);
				replacement.setRight(right);
				
				parent = getParent(toDelete, root);
				
				// If parent node is null, it means the node to delete is the root
				if(parent == null) {
					root = replacement;
				}
				else {
					if( compare( toDelete.getData().getName(), parent.getData().getName() ) == -1 ) {
						parent.setLeft(replacement);
					}
					else if( compare( toDelete.getData().getName(), parent.getData().getName() ) == 1 ) {
						parent.setRight(replacement);
					}
				}
			}
		}
		else if( compare( toDelete.getData().getName(), start.getData().getName() ) == -1 ) {
			deleteRecursive( toDelete, start.getLeft() );
		}
		else {
			deleteRecursive( toDelete, start.getRight() );
		}
		
	}

	/**
	 * Gets the node with least value 
	 * @param start (Node from which the search is going to start)
	 * @return Node with least value
	 */
	private TreeNode getMinorNode(TreeNode start) {
		TreeNode left = start.getLeft();
		TreeNode right = start.getRight();
		
		if( left != null && compare( left.getData().getName(), start.getData().getName() ) == -1 ) {
			return getMinorNode(left);
		}
		else if( right != null && compare( right.getData().getName(), start.getData().getName() ) == -1 ) {
			return getMinorNode(right);
		}
		else {
			return start;
		}
	
	}

	/**
	 * Edits the information of a contact in the tree
	 * @param name
	 * @param newNumber
	 * @param newEmail
	 */
	public void edit(Contact person) {
		
		TreeNode found = getNode(person, root);
		found.getData().setName( person.getName() );
		found.getData().setNumber( person.getNumber() );
		found.getData().setEmail( person.getEmail() );
	}

	/**
	 * Gets a list containing all contacts in the tree
	 * (Allows communication with UI)
	 * @return contacts list
	 */
	public ArrayList<Contact> getContacts() {
		contactsList.clear();
		fill_List(root);
		
		return contactsList;
	}

}
