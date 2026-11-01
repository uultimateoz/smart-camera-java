package com.smartcamera.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CameraUI extends Application {

    private Stage primaryStage;
    private Scene loginScene, signupScene;

    private Label loginStatusLabel = new Label();
    private Label signupStatusLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Smart Home Camera - Authentication");

        initLoginScene();
        initSignupScene();

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void initLoginScene() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Label title = new Label("Resident Login");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Log In");
        loginButton.setDefaultButton(true);

        Hyperlink switchToSignup = new Hyperlink("Don't have an account? Sign up");

        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                loginStatusLabel.setText("Please fill in all fields.");
                loginStatusLabel.setStyle("-fx-text-fill: red;");
            } else {
                loginStatusLabel.setText("Login successful! (Mock Mode)");
                loginStatusLabel.setStyle("-fx-text-fill: green;");
            }
        });

        switchToSignup.setOnAction(e -> primaryStage.setScene(signupScene));

        root.getChildren().addAll(title, emailField, passwordField, loginButton, loginStatusLabel, switchToSignup);
        loginScene = new Scene(root, 380, 420);
    }

    private void initSignupScene() {
        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Label title = new Label("Resident Registration");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        Button signupButton = new Button("Register Account");
        signupButton.setDefaultButton(true);

        Hyperlink switchToLogin = new Hyperlink("Already have an account? Log in");

        signupButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String phone = phoneField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                signupStatusLabel.setText("All fields are required.");
                signupStatusLabel.setStyle("-fx-text-fill: red;");
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                signupStatusLabel.setText("Invalid email format.");
                signupStatusLabel.setStyle("-fx-text-fill: red;");
            } else if (password.length() < 8) {
                signupStatusLabel.setText("Password must be at least 8 characters long.");
                signupStatusLabel.setStyle("-fx-text-fill: red;");
            } else if (!password.equals(confirmPassword)) {
                signupStatusLabel.setText("Passwords do not match.");
                signupStatusLabel.setStyle("-fx-text-fill: red;");
            } else if (!phone.matches("^[0-9+\\-\\s()]{8,15}$")) {
                signupStatusLabel.setText("Please enter a valid phone number.");
                signupStatusLabel.setStyle("-fx-text-fill: red;");
            } else {
                signupStatusLabel.setText("Input valid! Account created (Mock Mode).");
                signupStatusLabel.setStyle("-fx-text-fill: green;");
            }
        });

        switchToLogin.setOnAction(e -> primaryStage.setScene(loginScene));

        root.getChildren().addAll(title, usernameField, emailField, passwordField, confirmPasswordField, phoneField, signupButton, signupStatusLabel, switchToLogin);
        signupScene = new Scene(root, 380, 530);
    }

    public static void main(String[] args) {
        launch(args);
    }
}