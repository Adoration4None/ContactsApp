package contactsApp.model;

import java.util.ArrayList;

/**
 * Doubly linked list
 * @author Samuel Echeverri
 * @author Juan Felipe Lopez
 *
 */
public class DoubleList {
	// Attributes
	private int size;
	private ListNode first;
	private ListNode last;
	
	// Contacts list that will be given to UI
	ArrayList<Contact> contactsList = new ArrayList<>();

	// Constructor
	public DoubleList() {
		size = 0;
		first = null;
		last = null;
	}

	// Getters and setters
	public int getSize() {
		return size;
	}

	public ListNode getFirst() {
		return first;
	}

	public void setFirst(ListNode first) {
		this.first = first;
	}

	public ListNode getLast() {
		return last;
	}

	public void setLast(ListNode last) {
		this.last = last;
	}

	/**
	 * Verifies if empty
	 * @return true if it's empty, false otherwise
	 */
	public boolean isEmpty() { return (first == null && last == null); }
	
	/**
	 * Verifies if the list contains an entered value
	 * @param value
	 * @return true if value is in the list, false otherwise
	 */
	public boolean contains(Contact value) {

		if( isEmpty() ) {
			return false;
		}
		else {
			ListNode aux = first;

			while(aux != null) {

				if( aux.getData().equals(value) ) {
					return true;
				}
				else {
					aux = aux.getNext();
				}
			}
		}

		return false;
	}

	/**
	 * Inserts a value as the first in the list
	 * @param value
	 */
	public void insertFirst(Contact value) {

		ListNode newNode = new ListNode(value);

		if( isEmpty() ) {
			first = newNode;
			last = newNode;
		}
		else {
			newNode.setNext(first);
			first.setPrev(newNode);
			first = newNode;
		}

		size++;
	}

	/**
	 * Inserts a value as the last in the list
	 * @param value
	 */
	public void insertLast(Contact value) {

		ListNode newNode = new ListNode(value);

		if( isEmpty() ) {
			first = newNode;
			last = newNode;
		}
		else {
			newNode.setPrev(last);
			last.setNext(newNode);
			last = newNode;
		}
	}

	/**
	 * Deletes a value in the list
	 * @param value
	 */
	public void delete(Contact value) {

		if( isEmpty() ) {
			System.err.println("Empty list");
		}
		else if( contains(value) ) {

			ListNode aux = first;

			while(aux != null) {
				// Delete first node
				if( value == first.getData() ) {
	            	first = first.getNext();
	            	break;
	            }
				// Delete some node in the middle
				else if(aux.getNext() != null && aux.getData() == value) {
					aux.getPrev().setNext( aux.getNext() );
					aux.getNext().setPrev( aux.getPrev() );
					break;
				}
				// Delete last node
				else if(aux.getNext() == null && aux.getData() == value) {
					aux.getPrev().setNext(null);
					last = aux.getPrev();
					break;
				}

				aux = aux.getNext();
			}

			size--;
		}
		else {
			System.err.println("Value not found to delete");
		}
	}

	/**
	 * Shows all the list data
	 */
	public void show() {

		if( isEmpty() ) {
			System.err.println("Empty list");
		}
		else {
			ListNode aux = first;

			while(aux != null) {
				System.out.print( "<-[" + aux.getData() + "]->" );
				aux = aux.getNext();
			}
		}
	}

	/**
	 * Gets a node in the list searched by its value
	 * @param value
	 * @return found Node
	 */
	public ListNode getNode(Contact value) {

		if( isEmpty() ) {
			return null;
		}
		else {
			ListNode aux = first;

			while(aux != null) {

				if( aux.getData().equals(value) ) {
					return aux;
				}
				else {
					aux = aux.getNext();
				}
			}
		}

		return null;
	}

	/**
	 * Gets a list containing all contacts in the list
	 * (Allows communication with UI)
	 * @return contacts list
	 */
	public ArrayList<Contact> getContent() {
		contactsList.clear();
		fill_List();
		return contactsList;
	}

	private void fill_List() {
		
		ListNode aux = first;
		
		while(aux != null) {
			contactsList.add( aux.getData() );
			aux = aux.getNext();
		}
	}

	/**
	 * Gets a Contact in the list searched by name
	 * @param name
	 * @return found Contact
	 */
	public Contact getContact(String name) {
		ListNode aux = first;
		
		while(aux != null) {
			
			if( aux.getData().getName().equals(name) ) {
				return aux.getData();
			}
			else {
				aux = aux.getNext();
			}
		}
		
		return null;
	}

}
