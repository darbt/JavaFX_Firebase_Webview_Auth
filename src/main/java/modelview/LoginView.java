/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author trintydarbouze
 */

public class LoginView {
    
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("AccessFBView.fxml");
    }

    @FXML
    private void LoginUser() throws IOException {

        LogIn();

    }
    
    public void LogIn()
    {
        try
        {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(emailField.getText().trim());
            System.out.println("Fetched user data: "+ userRecord.getEmail());
            
        }catch (FirebaseAuthException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


}


