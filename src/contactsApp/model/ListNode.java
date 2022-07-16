package contactsApp.model;

/**
 * Node from doubly linked list
 * @author Samuel Echeverri
 * @author Juan Felipe Lopez
 *
 */
public class ListNode {
	// Attributes
	private Contact data;
	private ListNode prev;
	private ListNode next;
	
	// Constructor
	public ListNode(Contact data) {
		this.data = data;
	}

	// Getters and setters
	public ListNode getPrev() {
		return prev;
	}

	public ListNode getNext() {
		return next;
	}

	public void setPrev(ListNode prev) {
		this.prev = prev;
	}

	public void setNext(ListNode next) {
		this.next = next;
	}

	public Contact getData() {
		return data;
	}

	public void setData(Contact data) {
		this.data = data;
	}
	
}
