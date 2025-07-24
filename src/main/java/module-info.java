module ph.edu.dlsu.lbycpei.dishcoveryapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.desktop;
    requires com.google.gson;

    opens ph.edu.dlsu.lbycpei.dishcoveryapp to javafx.fxml;
    opens ph.edu.dlsu.lbycpei.dishcoveryapp.controller to javafx.fxml;
    opens ph.edu.dlsu.lbycpei.dishcoveryapp.model to com.google.gson;

    exports ph.edu.dlsu.lbycpei.dishcoveryapp;
    exports ph.edu.dlsu.lbycpei.dishcoveryapp.controller;
}
