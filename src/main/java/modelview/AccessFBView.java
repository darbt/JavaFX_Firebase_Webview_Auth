package modelview;

import com.mycompany.mvvmexample.App;
import viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Person;

public class AccessFBView implements Initializable {

 
     @FXML
    private TextField nameField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField ageField;
    @FXML
    private Button writeButton;
    @FXML
    private Button readButton;
    
    @FXML
    private Button regButton;
    
    @FXML
    private Button logInButton;
    
    @FXML
    private Button signUpButton;
    
    @FXML
    private TextArea outputField;
     @FXML
    private TableView <Person> tableField = new TableView();
    
    
    @FXML
    private TableColumn<Person, String> nameCol;

    @FXML
    private TableColumn<Person, String> majorCol;
    @FXML
    private TableColumn<Person, Integer> ageCol;
    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;
    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        nameField.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
        majorField.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());
        
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        majorCol.setCellValueFactory(new PropertyValueFactory<>("Major"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("Age"));
    
        tableField.setOnMousePressed(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                       
                        nameField.setText(tableField.getSelectionModel().getSelectedItem().getName());
                        majorField.setText(tableField.getSelectionModel().getSelectedItem().getMajor());
                        ageField.setText(Integer.toString(tableField.getSelectionModel().getSelectedItem().getAge()));
                        //currentID = outputTable.getSelectionModel().getSelectedItem().getId();
                    }
                });
        
    }
    
 @FXML
    private void addRecord(ActionEvent event) {
        addData();
       nameField.clear();
       majorField.clear();
       ageField.clear();
       readRecord(event);
    }

    @FXML
    private void readRecord(ActionEvent event) {
        outputField.clear();
        tableField.getItems().clear();
        readFirebase();
    }
    
    
    
    @FXML
    private void regRecord(ActionEvent event) {
        registerUser();
    }
    
    
     @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("WebContainer.fxml");
    }
    
    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("RegisterView.fxml");
    }
    
     
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("LogInView.fxml");
    }
    
    public void addData() {

        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());
        // Add document data  with id "alovelace" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameField.getText());
        data.put("Major", majorField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }
    ///////////////////////////////////////////////////////////////////////////////////////
       public boolean readFirebase()
         {
             key = false;

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        
        try 
        {
            documents = future.get().getDocuments();
            if(documents.size()>0)
            {
                System.out.println("Outing....");
                for (QueryDocumentSnapshot document : documents) 
                {
                     person  = new Person(
                            String.valueOf(document.getData().get("Name")), 
                            document.getData().get("Major").toString(),
                            Integer.parseInt(document.getData().get("Age").toString())
                    );
       
                    listOfUsers.add(person);
        
                    
                    outputField.setText(
                            outputField.getText()+ 
                            document.getData().get("Name")+ " , Major: "+
                            document.getData().get("Major")+ " , Age: "+
                            document.getData().get("Age")+ " \n ");
                    System.out.println(document.getId() + " => " + document.getData().get("Name"));
                    
                          
                    tableField.setItems(listOfUsers); 
                }
            }
            else
            {
               System.out.println("No data"); 
            }
            key=true;
            
        }
        catch (InterruptedException | ExecutionException ex) 
        {
             ex.printStackTrace();
        }
        return key;
    }
        
        
        public void sendVerificationEmail() {
        try {
            UserRecord user = App.fauth.getUser("name");
            //String url = user.getPassword();

        } catch (Exception e) {
        }
    }

//    public boolean registerUser() {
//         UserRecord.CreateRequest request = new UserRecord.CreateRequest()
//                .setEmail(nameField.getText().trim()+"@example.com")
//                .setEmailVerified(false)
//                .setPassword("secretPassword")
//                .setPhoneNumber(ageField.getText())
//                .setDisplayName(nameField.getText().trim())
//                .setDisabled(false);
        
         public boolean registerUser() {
         UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user3@example.com")
                .setEmailVerified(false)
                .setPassword("secretPassword")
                .setPhoneNumber("+11329879384")
                .setDisplayName("James Doe")
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

