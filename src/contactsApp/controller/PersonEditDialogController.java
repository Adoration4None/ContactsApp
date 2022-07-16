package contactsApp.controller;

import contactsApp.App;
import contactsApp.model.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the PersonEditDialog GUI
 * 
 * @author Samuel Echeverri & Juan Felipe Lopez
 *  With code from the AddressApp project by Marco Jakob
 */
public class PersonEditDialogController {

	@FXML private TextField numberField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;

	private Stage dialogStage;
	private Contact person;
	private Contact owner;
	private boolean okClicked = false;
	private App app;

	// Mode 1 is New contact; 2 is Edit contact
	private int mode;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Contact getOwner() {
		return owner;
	}

	public void setOwner(Contact owner) {
		this.owner = owner;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 *
	 * @param person
	 */
	public void setPerson(Contact person) {
		this.person = person;

		nameField.setText(person.getName());
		numberField.setText(person.getNumber());
		emailField.setText(person.getEmail());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {

		if ( isInputValid() ) {
			person.setName( nameField.getText() );
			person.setNumber(numberField.getText());
			person.setEmail(emailField.getText());
			okClicked = true;

			if(mode == 1) {
				// New contact
				if(owner != null) {
					app.addPersonContact(person, owner);
				}
				else {
					app.addPerson(person);
				}
				
			}
			else if(mode == 2) {
				// Edit contact
				if(owner != null) {
					app.editPersonContact(person, owner);
				}
				else {
					app.editPerson(person);
				}
			}

			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "No valid name!\n";
		}

		if (numberField.getText() == null || numberField.getText().length() == 0) {
			errorMessage += "No valid phone number!\n"; }

		if (emailField.getText() == null || emailField.getText().length() == 0) {
			errorMessage += "No valid e-mail!\n"; }

		if (errorMessage.length() == 0) {
			return true;
		}
		else {
			// Show the error message.
			showMessage("Invalid Fields", "Please correct invalid fields", errorMessage, AlertType.ERROR);
			return false;
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

}
