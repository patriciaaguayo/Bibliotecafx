package org.example.bibliotecafx.GestionInterfaz;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class SeleccionInterfaz {

    public SeleccionInterfaz(VBox currentVBox, String fxml) throws IOException {
        System.out.println("Intentando cargar: " + fxml);
        VBox nextVBox = FXMLLoader.load(getClass().getResource(fxml));

        if (nextVBox == null) {
            throw new IOException("No se pudo cargar el archivo FXML: " + fxml);
        }

        currentVBox.getChildren().clear();
        currentVBox.getChildren().add(nextVBox);
    }
}
