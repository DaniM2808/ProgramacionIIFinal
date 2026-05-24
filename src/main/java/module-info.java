module co.edu.uniquindio.manejoEventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires annotations;
    requires jdk.compiler;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires javafx.graphics;

    opens co.edu.uniquindio.manejoEventos.model to javafx.base;
    opens co.edu.uniquindio.manejoEventos to javafx.fxml;

    exports co.edu.uniquindio.manejoEventos;
    exports co.edu.uniquindio.manejoEventos.viewController;
    exports co.edu.uniquindio.manejoEventos.model;
    exports co.edu.uniquindio.manejoEventos.model.Enums;

    opens co.edu.uniquindio.manejoEventos.viewController to javafx.fxml;
    opens co.edu.uniquindio.manejoEventos.viewController.modifyView to javafx.fxml;

    exports co.edu.uniquindio.manejoEventos.model.Interfaces;
    exports co.edu.uniquindio.manejoEventos.controller;

    opens co.edu.uniquindio.manejoEventos.controller to javafx.base;
    opens co.edu.uniquindio.manejoEventos.model.Interfaces to javafx.base;
}