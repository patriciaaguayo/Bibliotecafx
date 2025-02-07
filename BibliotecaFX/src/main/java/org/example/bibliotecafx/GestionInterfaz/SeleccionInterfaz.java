package org.example.bibliotecafx.GestionInterfaz;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class SeleccionInterfaz {

    public SeleccionInterfaz(VBox currentAnchorPane, String fxml)throws IOException {
        VBox nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(InterfazPrincipal.class.getResource(fxml)));
        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }
}
