package contactsApp;

import java.io.IOException;

import contactsApp.controller.PersonEditDialogController;
import contactsApp.controller.PersonOverviewController;
import contactsApp.model.Contact;
import contactsApp.model.Tree;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main application
 *
 * @author Samuel Echeverri & Juan Felipe Lopez
 * With code from the AddressApp project by Marco Jakob
 */
public class App extends Application {
	// Binary tree where contacts are saved
	private Tree tree = new Tree();

	private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * Constructor
     */ public App() {
    	 // Add some sample data
    	 Contact c1 = new Contact("Hans Muster", "3665289", "hans@mail.com");
    	 Contact c2 = new Contact("Ernesto Perez", "900884", "ernestop@mail.com");
    	 Contact c3 = new Contact("Ruth Angels", "155643", "ruth@mail.com");
    	 Contact c4 = new Contact("Laurie Strode", "788653", "laurie.str@mail.com");
    	 
    	 Contact cc1 = new Contact("Paula", "7998756", "paula@gmail.com");
    	 Contact cc2 = new Contact("Andres", "345552", "andres@mail.es");
    	 
    	 c1.addContact(cc1);
    	 c1.addContact(cc2);
    	 
    	 tree.insert(c1);
    	 tree.insert(c2);
    	 tree.insert(c3);
    	 tree.insert(c4);
    }

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    	this.primaryStage.setTitle("AddressApp");

    	// Set the application icon.
    	this.primaryStage.getIcons().add( new Image("file:resources/images/iconfinder_6498721_smartphone_mobile_user interface_book_ui_icon_512px.png") );

    	initRootLayout();
    	showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
    	try {
    		// Load person overview.
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(App.class.getResource("view/PersonOverview.fxml"));
    		AnchorPane personOverview = (AnchorPane) loader.load();

    		// Set person overview into the center of root layout.
    		rootLayout.setCenter(personOverview);

    		// Give the controller access to the main app.
    		PersonOverviewController controller = loader.getController();
    		controller.setMainApp(this);

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @param mode 1: New person, 2: edit person
     * @param owner the contact owner
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Contact person, int mode, Contact owner) {
    	try {
    		// Load the fxml file and create a new stage for the popup dialog.
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(App.class.getResource("view/PersonEditDialog.fxml"));
    		AnchorPane page = (AnchorPane) loader.load();

    		// Create the dialog Stage.
    		Stage dialogStage = new Stage();
    		dialogStage.setTitle("Edit Person");
    		dialogStage.getIcons().add( new Image("file:resources/images/edit.png") );
    		dialogStage.initModality(Modality.WINDOW_MODAL);
    		dialogStage.initOwner(primaryStage);
    		Scene scene = new Scene(page);
    		dialogStage.setScene(scene);

    		// Set the person into the controller.
    		PersonEditDialogController controller = loader.getController();
    		controller.setApp(this);
    		controller.setDialogStage(dialogStage);
    		controller.setPerson(person);
    		controller.setMode(mode);
    		
    		if(owner != null) controller.setOwner(owner);
    		
    		// Show the dialog and wait until the user closes it
    		dialogStage.showAndWait();

    		return controller.isOkClicked();

    	} catch (IOException e) {
    		e.printStackTrace();
    		return false;
    	}
    }

	/**
	 * Returns the main stage.
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Returns the binary tree
	 * @return tree
	 */
    public Tree getTree() {
		return tree;
	}

	public static void main(String[] args) {
        launch(args);
    }

	/**
	 * Adds a person to the tree
	 * @param person
	 */
	public void addPerson(Contact person) {
		tree.insert(person);
	}

	/**
	 * Edits the existing information from a specified person in the tree
	 * @param person
	 */
	public void editPerson(Contact person) {
		tree.edit(person);
	}

	/**
	 * Deletes a person from the tree
	 * @param person
	 */
	public void deletePerson(Contact person) {
		tree.delete(person);
	}
	
	/**
	 * Looks for a person in the tree searching by name
	 * @param search person's name
	 * @return found person from the tree
	 */
	public Contact searchPerson(String search) {
		return tree.getContact(search);
	}
	
	/**
	 * Adds a person to the owner's contacts list 
	 * (owner is in the tree, person will be added to its list)
	 * @param person
	 * @param owner
	 */
	public void addPersonContact(Contact person, Contact owner) {
		owner.addContact(person);
	}

	/**
	 * Edits the existing information from a specified person in its owner's list
	 * (owner is in the tree, person is an existing contact in  its list)
	 * @param person
	 * @param owner
	 */
	public void editPersonContact(Contact person, Contact owner) {
		owner.editContact(person);
	}

	/**
	 * Deletes a person from its owner's list
	 * (owner is in the tree, person is an existing contact in its list)
	 * @param person
	 * @param owner
	 */
	public void deletePersonContact(Contact person, Contact owner) {
		owner.deleteContact(person);
	}

	/**
	 * Looks for a contact in each tree person's list
	 * @param search contact's name
	 * @return a Contact array: Contact[0] = owner; Contact[1] = found contact 
	 */
	public Contact[] deepSearch(String search) {
		return tree.deepSearch(search);
	}

}
