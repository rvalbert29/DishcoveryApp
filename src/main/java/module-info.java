module ph.edu.dlsu.lbycpei.dishcoveryapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens ph.edu.dlsu.lbycpei.dishcoveryapp to javafx.fxml;
    exports ph.edu.dlsu.lbycpei.dishcoveryapp;
}