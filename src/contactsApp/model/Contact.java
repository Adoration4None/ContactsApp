package contactsApp.model;

/**
 * Contact class
 * @author Samuel Echeverri
 * @author Juan Felipe Lopez
 *
 */
public class Contact {
	// Attributes
	String name, number, email;
	DoubleList contacts = new DoubleList();
	 
	// Empty constructor
	public Contact() {
		
	}
	
	// Constructor with arguments
	public Contact(String name, String number, String email) {
		this.name = name;
		this.number = number;
		this.email = email;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DoubleList getContacts() {
		return contacts;
	}

	public void setContacts(DoubleList contacts) {
		this.contacts = contacts;
	}

	/**
	 * Adds a person to the contacts list
	 * @param person
	 */
	public void addContact(Contact person) {
		contacts.insertFirst(person);
	}

	/**
	 * Edits the information of a person in the contacts list
	 * @param person
	 */
	public void editContact(Contact person) {
		
		ListNode found = contacts.getNode(person);
		found.getData().setName( person.getName() );
		found.getData().setNumber( person.getNumber() );
		found.getData().setEmail( person.getEmail() );
	}

	/**
	 * Deletes a person from the contacts list
	 * @param person
	 */
	public void deleteContact(Contact person) {
		contacts.delete(person);
	}
	
}
