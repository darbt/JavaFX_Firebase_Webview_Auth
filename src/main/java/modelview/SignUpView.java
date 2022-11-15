/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import viewmodel.AccessDataViewModel;
/**
 *
 * @author trintydarbouze
 */
public class SignUpView {
    
     @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField passwordField;

    void initialize() {

        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        nameField.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
    }
    
     @FXML
    private void regRecord(ActionEvent event) {
        registerUser();
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
                .setEmail(nameField.getText().trim()+"@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber(phoneNumberField.getText().trim())
                .setDisplayName(nameField.getText().trim())
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
}
