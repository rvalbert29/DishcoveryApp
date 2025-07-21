module ph.edu.dlsu.lbycpei.dishcoveryapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.desktop;

    opens ph.edu.dlsu.lbycpei.dishcoveryapp to javafx.fxml;
    opens ph.edu.dlsu.lbycpei.dishcoveryapp.controller to javafx.fxml;

    exports ph.edu.dlsu.lbycpei.dishcoveryapp;
    exports ph.edu.dlsu.lbycpei.dishcoveryapp.controller;
}
