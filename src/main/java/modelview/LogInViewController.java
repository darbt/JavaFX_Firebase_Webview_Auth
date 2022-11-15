/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package modelview;

import com.google.firebase.auth.FirebaseAuth;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author trintydarbouze
 */
public class LogInViewController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private Button regButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void LoginUser(ActionEvent event) {
         LogIn();
    }

    @FXML
    private void switchToRegister(ActionEvent event) throws IOException {
    App.setRoot("RegisterView.fxml");
    }
   
    public void LogIn()
    {
        try
        {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(emailField.getText().trim());
            System.out.println("Fetched user data: "+ userRecord.getEmail());
            
        }catch (FirebaseAuthException ex) {
            Logger.getLogger(LogInViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
