package org.example.bibliotecafx.GestionInterfaz;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GestionInterfaces {

    @FXML
    private VBox InterfazInicio;

    // Acciones de los botones

    @FXML
    private void BotonAutores() throws IOException {
        new SeleccionInterfaz(InterfazInicio, "/org/example/bibliotecafx/InterfazAutor.fxml");
    }
}
