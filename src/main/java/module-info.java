module org.example.carwashfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    requires net.sf.jasperreports.core;


    opens org.example.carwashfx to javafx.fxml;
    exports org.example.carwashfx;
    exports org.example.carwashfx.controllers;
    exports org.example.carwashfx.controllers.exceptions;
    exports org.example.carwashfx.models.domain;
    exports org.example.carwashfx.report;
    opens org.example.carwashfx.controllers to javafx.fxml;
    opens org.example.carwashfx.models.domain to javafx.base;
}