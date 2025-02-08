package org.example.bibliotecafx.GestionInterfaz;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.bibliotecafx.DAO.AutorDAOImpl;
import org.example.bibliotecafx.DAO.IAutorDAO;
import org.example.bibliotecafx.entities.Autor;

import java.io.IOException;
import java.util.List;

public class GestionInterfaces {

    // Interfaces

    @FXML
    private VBox InterfazInicio;

    @FXML
    private VBox InterfazAutor;

    @FXML
    private VBox InterfazLibro;

    @FXML
    private VBox InterfazPrestamo;

    @FXML
    private VBox InterfazSocio;

    // Objetos de la interfaz Inicio

    // Objetos de la interfaz Autor

    @FXML
    private TextField txtNombreAutor;

    @FXML
    private TextField txtNacionalidadAutor;

    @FXML
    private TextField txtIdAutor;

    @FXML
    private TextArea txtAreaAutores;

    private IAutorDAO autorDAO = new AutorDAOImpl(); // Asegúrate de que esta instancia es correcta

    // Objetos de la interfaz Socio

    // Objetos de la interfaz Prestamo

    // Objetos de la interfaz Libro

    // Acciones de los botones

    // Interfaz Inicio

    @FXML
    private void BotonAutores() throws IOException {
        new SeleccionInterfaz(InterfazInicio, "/org/example/bibliotecafx/InterfazAutor.fxml");
    }

    @FXML
    private void BotonLibros() throws IOException {
        new SeleccionInterfaz(InterfazInicio, "/org/example/bibliotecafx/InterfazLibro.fxml");
    }

    @FXML
    private void BotonSocios() throws IOException {
        new SeleccionInterfaz(InterfazInicio, "/org/example/bibliotecafx/InterfazSocio.fxml");
    }

    @FXML
    private void BotonPrestamos() throws IOException {
        new SeleccionInterfaz(InterfazInicio, "/org/example/bibliotecafx/InterfazPrestamo.fxml");
    }

    @FXML
    private void BotonSalir(){
        System.exit(0);
    }

    // Interfaz Autor

    @FXML
    private void BotonVolverAutor() throws IOException {
        new SeleccionInterfaz(InterfazAutor, "/org/example/bibliotecafx/InterfazInicio.fxml");
    }

    @FXML
    private void BotonAgregarAutor() {
        System.out.println("Botón Agregar Autor presionado");

        String nombre = txtNombreAutor.getText().trim();
        String nacionalidad = txtNacionalidadAutor.getText().trim();

        // Validación básica
        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El nombre y la nacionalidad deben ser llenados.");
            return;
        }

        Autor nuevoAutor = new Autor(nombre, nacionalidad);

        try {
            // Llamamos al método registrarAutor de AutorDAOImpl
            autorDAO.registrarAutor(nuevoAutor);

            // Mostrar mensaje de éxito
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Autor registrado exitosamente.");

            // Limpiar campos después de agregar
            txtNombreAutor.clear();
            txtNacionalidadAutor.clear();
            txtIdAutor.clear();

        } catch (Exception e) {
            // Si hay un error, mostrar mensaje emergente con error
            showAlert(Alert.AlertType.ERROR, "Error", "Error al registrar el autor: " + e.getMessage());
        }
    }

    @FXML
    private void BotonEliminarAutor() {
        System.out.println("Botón Eliminar Autor presionado");

        String idTexto = txtIdAutor.getText().trim();

        if (idTexto.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El campo de ID no puede estar vacío.");
            return;
        }

        try {

            Integer idAutor = Integer.parseInt(idTexto);
            boolean autorEliminado = autorDAO.eliminarAutor(idAutor);

            if (autorEliminado) {

                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Autor eliminado exitosamente.");

                txtIdAutor.clear();
                txtNombreAutor.clear();
                txtNacionalidadAutor.clear();

            } else {
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró un autor con ese ID.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ID debe ser un número válido.");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error al eliminar el autor: " + e.getMessage());
        }
    }

    @FXML
    private void BotonModificarAutor() {
        System.out.println("Botón Agregar Autor presionado");

        // Recoger los valores de los campos
        String idTexto = txtIdAutor.getText().trim();
        String nombre = txtNombreAutor.getText().trim();
        String nacionalidad = txtNacionalidadAutor.getText().trim();

        // Validación de entrada
        if (idTexto.isEmpty() || nombre.isEmpty() || nacionalidad.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben estar llenos.");
            return;
        }

        try {
            // Verificamos si el ID es un número válido
            Integer idAutor = Integer.parseInt(idTexto);

            // Buscar el autor en la base de datos por el ID
            Autor autorExistente = autorDAO.buscarPorId(idAutor);

            if (autorExistente != null) {
                // Si el autor existe, lo modificamos
                autorExistente.setNombreAutor(nombre);
                autorExistente.setNacionalidad(nacionalidad);

                // Llamamos al método del DAO para actualizar el autor
                autorDAO.modificarAutor(autorExistente);

                // Informamos al usuario que el autor ha sido modificado
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Autor modificado exitosamente.");

                // Limpiamos los campos después de la modificación
                txtIdAutor.clear();
                txtNombreAutor.clear();
                txtNacionalidadAutor.clear();

            } else {
                // Si el autor no se encuentra en la base de datos, mostramos un mensaje de error
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró un autor con ese ID.");
            }

        } catch (NumberFormatException e) {
            // Si el ID no es un número válido
            showAlert(Alert.AlertType.ERROR, "Error", "El ID debe ser un número válido.");
        } catch (Exception e) {
            // Si ocurre algún otro error
            showAlert(Alert.AlertType.ERROR, "Error", "Error al modificar el autor: " + e.getMessage());
        }
    }

    @FXML
    private void BotonBuscarAutor() {
        System.out.println("Botón Agregar Autor presionado");

        String nombre = txtNombreAutor.getText().trim();

        capitalizarPrimeraLetraYDespuesDeEspacios(nombre);

        // Validación de entrada
        if (nombre.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El campo de nombre no puede estar vacío.");
            return;
        }

        try {
            // Llamamos al método buscarPorNombre del DAO para buscar el autor
            List<Autor> autoresEncontrados = (List<Autor>) autorDAO.buscarPorNombre(nombre);

            // Limpiamos el área de texto antes de mostrar los resultados
            txtAreaAutores.clear();

            if (autoresEncontrados != null && !autoresEncontrados.isEmpty()) {
                if (autoresEncontrados.size() == 1) {
                    // Si solo encontramos un autor con ese nombre, llenamos los campos para modificar
                    Autor autor = autoresEncontrados.get(0);
                    txtIdAutor.setText(String.valueOf(autor.getIdAutor())); // Rellenamos el ID
                    txtNombreAutor.setText(autor.getNombreAutor()); // Rellenamos el nombre
                    txtNacionalidadAutor.setText(autor.getNacionalidad()); // Rellenamos la nacionalidad

                    showAlert(Alert.AlertType.INFORMATION, "Autor encontrado",
                            "Se encontró un autor con ese nombre. Los campos han sido rellenados para modificar.");
                } else {
                    // Si encontramos más de un autor, mostramos todos los resultados en el TextArea
                    StringBuilder autoresText = new StringBuilder();
                    for (Autor autor : autoresEncontrados) {
                        autoresText.append(autor.toString()).append("\n");  // Usamos el método toString() de Autor
                    }
                    txtAreaAutores.setText(autoresText.toString());  // Mostramos todos los autores encontrados
                    showAlert(Alert.AlertType.INFORMATION, "Múltiples autores encontrados",
                            "Se han encontrado varios autores con ese nombre.");
                }
            } else {
                // Si no se encontraron autores, mostramos un mensaje en el TextArea
                txtAreaAutores.setText("No se encontraron autores con ese nombre.");
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró un autor con ese nombre.");
            }

        } catch (Exception e) {
            // Si hay un error en el proceso, mostramos un mensaje de error
            showAlert(Alert.AlertType.ERROR, "Error", "Error al buscar el autor: " + e.getMessage());
        }
    }

    @FXML
    private void BotonListarAutores() {
        System.out.println("Botón Agregar Autor presionado");

        try {
            // Llamamos al método obtenerAutores del DAO para obtener la lista completa de autores
            List<Autor> autoresListados = autorDAO.obtenerAutores();

            // Limpiamos el área de texto antes de mostrar los resultados
            txtAreaAutores.clear();

            if (autoresListados != null && !autoresListados.isEmpty()) {
                // Si se encontraron autores, los mostramos en el TextArea usando el método toString() de Autor
                StringBuilder autoresText = new StringBuilder();
                for (Autor autor : autoresListados) {
                    autoresText.append(autor.toString()).append("\n");  // Usamos el método toString() de Autor
                }
                txtAreaAutores.setText(autoresText.toString());  // Mostramos todos los autores listados
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Autores listados exitosamente.");
            } else {
                // Si no se encontraron autores, mostramos un mensaje en el TextArea
                txtAreaAutores.setText("No se encontraron autores en la base de datos.");
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontraron autores.");
            }

        } catch (Exception e) {
            // Si hay un error en el proceso, mostramos un mensaje de error
            showAlert(Alert.AlertType.ERROR, "Error", "Error al listar los autores: " + e.getMessage());
        }
    }

    @FXML
    private void BotonLimpiarAutor() {
        System.out.println("Botón Limpiar Autor presionado");
        txtNombreAutor.clear();
        txtNacionalidadAutor.clear();
        txtIdAutor.clear();
        txtAreaAutores.clear();
    }

    @FXML
    private void BotonSalirAutor(){
        System.exit(0);
    }

    // Interfaz Libro

    @FXML
    private void BotonVolverLibro() throws IOException {
        new SeleccionInterfaz(InterfazLibro, "/org/example/bibliotecafx/InterfazInicio.fxml");
    }

    // Interfaz Socio

    @FXML
    private void BotonVolverSocio() throws IOException {
        new SeleccionInterfaz(InterfazSocio, "/org/example/bibliotecafx/InterfazInicio.fxml");
    }

    // Interfaz Prestamo

    @FXML
    private void BotonVolverPrestamo() throws IOException {
        new SeleccionInterfaz(InterfazPrestamo, "/org/example/bibliotecafx/InterfazInicio.fxml");
    }

    // Método para mostrar alertas

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // MÉTODOS

    private static String capitalizarPrimeraLetra(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return palabra;
        }
        return palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
    }

    private static String capitalizarPrimeraLetraYDespuesDeEspacios(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return palabra;
        }

        StringBuilder resultado = new StringBuilder();
        boolean siguienteMayuscula = true;  // Esto indica si la siguiente letra debe ir en mayúscula

        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);

            // Si encontramos un espacio, la siguiente letra debe ser mayúscula
            if (c == ' ') {
                resultado.append(c);
                siguienteMayuscula = true;
            } else {
                if (siguienteMayuscula) {
                    // Convertimos el carácter a mayúscula
                    resultado.append(Character.toUpperCase(c));
                    siguienteMayuscula = false;
                } else {
                    // Convertimos el carácter a minúscula
                    resultado.append(Character.toLowerCase(c));
                }
            }
        }

        return resultado.toString();
    }
}
