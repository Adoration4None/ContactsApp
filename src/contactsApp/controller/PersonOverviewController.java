package contactsApp.controller;

import java.util.Optional;

import contactsApp.App;
import contactsApp.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for the PersonOverview GUI
 *
 * @author Samuel Echeverri & Juan Felipe Lopez
 *  With code from the AddressApp project by Marco Jakob
 */
public class PersonOverviewController {
	// GUI elements
	@FXML private TableView<Contact> personTable;
	@FXML private TableView<Contact> personContactsTable;
	
	@FXML private TableColumn<Contact, String> nameColumn;

	@FXML private TableColumn<Contact, String> contactEmailColumn;
	@FXML private TableColumn<Contact, String> contactNameColumn;
	@FXML private TableColumn<Contact, String> contactNumberColumn;
	
	@FXML private Label numberLabel;
    @FXML private Label emailLabel;
    @FXML private Label nameLabel;
    @FXML private TextField searchField;

	// Reference to the main application
	private App app;

	// Lists to be shown in the tables
	private ObservableList<Contact> personData = FXCollections.observableArrayList();
	private ObservableList<Contact> personContactsData = FXCollections.observableArrayList();

	/**
	 * The constructor is called before the initialize() method.
	 */
	public PersonOverviewController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// Initialize the person table with the column.
		nameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
		
		contactNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
		contactNumberColumn.setCellValueFactory( new PropertyValueFactory<>("number") );
		contactEmailColumn.setCellValueFactory( new PropertyValueFactory<>("email") );

		// Clear person details.
		showPersonDetails(null);

		// Listen for selection changes and show the person details when changed.
		personTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param app
	 */
	public void setMainApp(App app) {
		this.app = app;
		personData.addAll( app.getTree().getContacts() );

		// Add observable list data to the table
		personTable.setItems(personData);
	}

	/**
	 * Fills all text fields to show details about the person.
	 * If the specified person is null, all text fields are cleared.
	 *
	 * @param person
	 */
	private void showPersonDetails(Contact person) {
		if (person != null) {
			// Fill the labels with info from the person object.
			nameLabel.setText(person.getName());
			numberLabel.setText(person.getNumber());
			emailLabel.setText(person.getEmail());
			
			personContactsData.clear();
			personContactsTable.setItems(personContactsData);
			personContactsData.addAll( person.getContacts().getContent() );
			personContactsTable.setItems(personContactsData);
		}
		else {
			// Person is null, remove all the text.
			nameLabel.setText("");
			numberLabel.setText("");
			emailLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePerson() {
		Contact selectedPerson = personTable.getSelectionModel().getSelectedItem();;

		if (selectedPerson != null) {
			if( showConfirmationMessage("Are you sure you want to delete this contact?") ) {
				app.deletePerson(selectedPerson);
				refreshData();
			}
		}
		else { // Nothing selected.
			showMessage("No Selection", "No Person Selected", "Please select a person in the table.", AlertType.ERROR);
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewPerson() {
		Contact tempPerson = new Contact();
		boolean okClicked = app.showPersonEditDialog(tempPerson, 1, null);

		if (okClicked) {
			refreshData();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	* details for the selected person.
	*/
	@FXML
	private void handleEditPerson() {
		Contact selectedPerson = personTable.getSelectionModel().getSelectedItem();

		if (selectedPerson != null) {
			boolean okClicked = app.showPersonEditDialog(selectedPerson, 2, null);

			if (okClicked) {
				refreshData();
			}
		} else {
			// Nothing selected.
			showMessage("No Selection", "No Person Selected", "Please select a person in the table.", AlertType.ERROR);
		}
	}

	/**
	 * Called when user clicks the Search button. Displays the searched
	 * contact in the table view
	 */
	@FXML
    private void handleSearch(ActionEvent event) {
		String search = searchField.getText();
		Contact foundPerson = new Contact();
		Contact[] foundPeople = new Contact[2];
		ObservableList<Contact> result = FXCollections.observableArrayList();
		ObservableList<Contact> innerResult = FXCollections.observableArrayList();

		personTable.setItems(personData);

		if( search != null && !search.equals("") ) {
			foundPerson = app.searchPerson(search);

			if(foundPerson == null) {
				
				foundPeople = app.deepSearch(search);
				
				if(foundPeople.length == 0) {
					showMessage("No Results", "No Results Found", "The search couldn't find a result. Please enter a valid name", AlertType.WARNING);
				}
				else {
					result.add(foundPeople[0]);
					personTable.setItems(result);
					showPersonDetails(foundPeople[0]);
					
					innerResult.add(foundPeople[1]);
					personContactsTable.setItems(innerResult);
				}
			}
			else {
				result.add( foundPerson );
				personTable.setItems(result);
			}
		}
    }

	/**
	 * Called when user clicks the Delete button in 'Contact Details' section 
	 */
	@FXML
    void handleDeletePersonContact(ActionEvent event) {
		Contact owner = personTable.getSelectionModel().getSelectedItem();
		Contact selectedContact = personContactsTable.getSelectionModel().getSelectedItem();

		if (selectedContact != null && owner != null) {
			if( showConfirmationMessage("Are you sure you want to delete this contact?") ) {
				app.deletePersonContact(selectedContact, owner);
				refreshData();
			}
		}
		else { // Nothing selected.
			showMessage("No Selection", "No Person Selected", "Please select a person's contact in the table.", AlertType.ERROR);
		}
    }
	
	/**
	 * Called when user clicks the Edit button in 'Contact Details' section
	 */
	@FXML
	void handleEditPersonContact(ActionEvent event) {
		Contact owner = personTable.getSelectionModel().getSelectedItem();
		Contact selectedContact = personContactsTable.getSelectionModel().getSelectedItem();
		
		if (selectedContact != null && owner != null) {
			boolean okClicked = app.showPersonEditDialog(selectedContact, 2, owner);

			if (okClicked) {
				refreshData();
			}
		} else {
			// Nothing selected.
			showMessage("No Selection", "No Person Selected", "Please select a person's contact in the table.", AlertType.ERROR);
		}
	}
	
	/**
	 * Called when user clicks the New button in 'Contact Details' section
	 * @param event
	 */
	@FXML
	void handleNewPersonContact(ActionEvent event) {
		Contact selectedContact = personTable.getSelectionModel().getSelectedItem();
		
		if(selectedContact != null) {
			Contact tempPerson = new Contact();
			boolean okClicked = app.showPersonEditDialog(tempPerson, 1, selectedContact);
			
			if (okClicked) {
				refreshData();
			}
		}
		else {
			showMessage("No Selection", "No Person Selected", "Please select a person in the table.", AlertType.ERROR);
		}
	}

	/**
	 * Replaces the existing observable list data with the tree data
	 */
	private void refreshData() {
		Contact selectedPerson = personTable.getSelectionModel().getSelectedItem();
		
		personData.clear();
		personData.addAll( app.getTree().getContacts() );
		
		personContactsData.clear();
		
		if(selectedPerson != null) {
			personContactsData.addAll( selectedPerson.getContacts().getContent() );
		}
	}

	/**
	 * Shows an emergent message
	 * @param title
	 * @param header
	 * @param content
	 * @param alertType
	 */
	private void showMessage(String title, String header, String content, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);

		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/**
	 * Shows a confirmation message with the entered message
	 * @param message
	 * @return true if OK clicked, false otherwise
	 */
	private boolean showConfirmationMessage(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(message);
		Optional<ButtonType> action = alert.showAndWait();

		if(action.get() == ButtonType.OK) {
			return true;
		}
		else {
			return false;
		}
	}
}

