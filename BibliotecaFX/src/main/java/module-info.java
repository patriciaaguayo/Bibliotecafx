module org.example.bibliotecafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires annotations;
    requires java.naming;

    // Abre este paquete para JavaFX FXML
    opens org.example.bibliotecafx to javafx.fxml;

    // Permite que Hibernate acceda a las entidades
    opens org.example.bibliotecafx.entities to org.hibernate.orm.core;

    // Abre GestionInterfaz para JavaFX FXML
    opens org.example.bibliotecafx.GestionInterfaz to javafx.fxml;  // Necesario para FXML

    // Exporta el paquete GestionInterfaz para que JavaFX pueda acceder a las clases
    exports org.example.bibliotecafx.GestionInterfaz to javafx.graphics; // Cambiado para permitir acceso
}