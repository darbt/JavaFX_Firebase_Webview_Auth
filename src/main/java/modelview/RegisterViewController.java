/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package modelview;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import viewmodel.AccessDataViewModel;

/**
 * FXML Controller class
 *
 * @author trintydarbouze
 */
public class RegisterViewController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button logInButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void regRecord(ActionEvent event) {
         registerUser();
    }

    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
          App.setRoot("LogInView.fxml");
    }
    
    public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        } catch (Exception e) {
        }
    }
    
    public boolean registerUser() {
         UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(nameField.getText())
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber(phoneNumberField.getText())
                .setDisplayName(nameField.getText())
                .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = App.fauth.createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            return true;

        } catch (FirebaseAuthException ex) {
           // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public void returnLoginIn(){
        try {
            App.setRoot("LogInView.fxml");
        } catch (IOException ex) {
            Logger.getLogger(RegisterViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
