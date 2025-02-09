package org.example.bibliotecafx.GestionInterfaz;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.bibliotecafx.DAO.*;
import org.example.bibliotecafx.entities.Autor;
import org.example.bibliotecafx.entities.Libro;
import org.example.bibliotecafx.entities.Prestamo;
import org.example.bibliotecafx.entities.Socio;

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

    private IAutorDAO autorDAO = new AutorDAOImpl();

    // Objetos de la interfaz Socio

    @FXML
    private TextField txtIdSocio;

    @FXML
    private TextField txtNombreSocio;

    @FXML
    private TextField txtDireccionSocio;

    @FXML
    private TextField txtTelefonoSocio;

    @FXML
    private TextArea txtAreaSocios;

    private final SocioDAOImpl socioDAO = new SocioDAOImpl();

    // Objetos de la interfaz Prestamo

    @FXML
    private TextField txIdSocioPrestamo;

    @FXML
    private TextField txtISBNPrestamo;

    @FXML
    private TextArea txtAreaPrestamos;

    private final PrestamoDAOImpl prestamoDAO = new PrestamoDAOImpl();

    // Objetos de la interfaz Libro

    @FXML
    private TextField txtISBNLibro;

    @FXML
    private TextField txtTituloLibro;

    @FXML
    private TextField txtEditorialLibro;

    @FXML
    private TextField txtAnyoPublicacionLibro;

    @FXML
    private TextField txtAutorLibro;

    @FXML
    private TextArea txtAreaLibros;

    private final ILibroDAO libroDAO = new LibroDAOImpl();

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

        String nombre = txtNombreAutor.getText().trim();
        String nacionalidad = txtNacionalidadAutor.getText().trim();

        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El nombre y la nacionalidad deben ser llenados.");
            return;
        }

        Autor nuevoAutor = new Autor(nombre, nacionalidad);

        try {
            autorDAO.registrarAutor(nuevoAutor);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Autor registrado exitosamente.");
            BotonLimpiarAutor();

        } catch (Exception e) {
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
                BotonLimpiarAutor();

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

        String idTexto = txtIdAutor.getText().trim();
        String nombre = txtNombreAutor.getText().trim();
        String nacionalidad = txtNacionalidadAutor.getText().trim();

        if (idTexto.isEmpty() || nombre.isEmpty() || nacionalidad.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben estar llenos.");
            return;
        }

        try {
            Integer idAutor = Integer.parseInt(idTexto);
            Autor autorExistente = autorDAO.buscarPorId(idAutor);

            if (autorExistente != null) {

                autorExistente.setNombreAutor(nombre);
                autorExistente.setNacionalidad(nacionalidad);
                autorDAO.modificarAutor(autorExistente);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Autor modificado exitosamente.");
                BotonLimpiarAutor();

            } else {
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró un autor con ese ID.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ID debe ser un número válido.");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error al modificar el autor: " + e.getMessage());
        }
    }

    @FXML
    private void BotonBuscarAutor() {

        String nombre = txtNombreAutor.getText().trim();
        capitalizarPrimeraLetraYDespuesDeEspacios(nombre);

        if (nombre.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El campo de nombre no puede estar vacío.");
            return;
        }

        try {
            List<Autor> autoresEncontrados = (List<Autor>) autorDAO.buscarPorNombre(nombre);
            txtAreaAutores.clear();

            if (autoresEncontrados != null && !autoresEncontrados.isEmpty()) {
                if (autoresEncontrados.size() == 1) {

                    Autor autor = autoresEncontrados.get(0);
                    txtIdAutor.setText(String.valueOf(autor.getIdAutor()));
                    txtNombreAutor.setText(autor.getNombreAutor());
                    txtNacionalidadAutor.setText(autor.getNacionalidad());

                    showAlert(Alert.AlertType.INFORMATION, "Autor encontrado",
                            "Se encontró un autor con ese nombre. Los campos han sido rellenados para modificar.");
                } else {
                    StringBuilder autoresText = new StringBuilder();

                    for (Autor autor : autoresEncontrados) {
                        autoresText.append(autor.toString()).append("\n");
                    }
                    txtAreaAutores.setText(autoresText.toString());
                    showAlert(Alert.AlertType.INFORMATION, "Múltiples autores encontrados",
                            "Se han encontrado varios autores con ese nombre.");
                }
            } else {
                txtAreaAutores.setText("No se encontraron autores con ese nombre.");
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró un autor con ese nombre.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error al buscar el autor: " + e.getMessage());
        }
    }

    @FXML
    private void BotonListarAutores() {

        try {
            List<Autor> autoresListados = autorDAO.obtenerAutores();
            txtAreaAutores.clear();

            if (autoresListados != null && !autoresListados.isEmpty()) {
                StringBuilder autoresText = new StringBuilder();

                for (Autor autor : autoresListados) {
                    autoresText.append(autor.toString()).append("\n");
                }
                txtAreaAutores.setText(autoresText.toString());
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Autores listados exitosamente.");

            } else {
                txtAreaAutores.setText("No se encontraron autores en la base de datos.");
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontraron autores.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error al listar los autores: " + e.getMessage());
        }
    }

    @FXML
    private void BotonLimpiarAutor() {
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

    @FXML
    private void BotonSalirLibro(){
        System.exit(0);
    }

    @FXML
    private void BotonAgregarLibro() {
        String titulo = txtTituloLibro.getText().trim();
        String isbn = txtISBNLibro.getText().trim();
        String autorStr = txtAutorLibro.getText().trim();
        String editorial = txtEditorialLibro.getText().trim();
        String anyoStr = txtAnyoPublicacionLibro.getText().trim();

        if (titulo.isEmpty() || isbn.isEmpty() || autorStr.isEmpty() || editorial.isEmpty() || anyoStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
            return;
        }

        if (!esISBNValido(isbn)) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ISBN debe tener 13 dígitos, con o sin guiones.");
            return;
        }

        isbn = isbn.replace("-", "");

        int anyoPublicacion = 0;
        try {
            anyoPublicacion = Integer.parseInt(anyoStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El año de publicación debe ser un número válido.");
            return;
        }

        int idAutor = 0;
        try {
            idAutor = Integer.parseInt(autorStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ID del autor debe ser un número válido.");
            return;
        }

        Autor autor = autorDAO.buscarPorId(idAutor);
        if (autor == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se encontró un autor con el ID proporcionado.");
            return;
        }

        Libro libro = new Libro();
        libro.setISBN(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setAnyo(anyoPublicacion);
        libroDAO.registrarLibro(libro);
        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Libro registrado exitosamente.");
        BotonLimpiarLibro();
    }

    @FXML
    private void BotonModificarLibro() {
        String isbn = txtISBNLibro.getText().trim();
        String titulo = txtTituloLibro.getText().trim();
        String editorial = txtEditorialLibro.getText().trim();
        String anyoStr = txtAnyoPublicacionLibro.getText().trim();

        if (isbn.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar un ISBN para modificar el libro.");
            return;
        }

        if (titulo.isEmpty() || editorial.isEmpty() || anyoStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El título, editorial y año de publicación son obligatorios.");
            return;
        }

        int anyo;
        try {
            anyo = Integer.parseInt(anyoStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El año de publicación debe ser un número válido.");
            return;
        }

        List<Libro> librosEncontrados = libroDAO.buscarPorParametro(isbn);
        if (librosEncontrados.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se encontró un libro con el ISBN proporcionado.");
            return;
        }

        Libro libro = librosEncontrados.get(0);
        libro.setTitulo(titulo);
        libro.setEditorial(editorial);
        libro.setAnyo(anyo);
        libroDAO.modificarLibro(libro);
        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Libro modificado correctamente.");
    }

    @FXML
    private void BotonEliminarLibro() {
        String ISBN = txtISBNLibro.getText().trim();

        if (!esISBNValido(ISBN)) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ISBN debe tener 13 dígitos, con o sin guiones.");
            return;
        }

        if (ISBN.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar un ISBN para eliminar un libro.");
            return;
        }

        boolean eliminado = libroDAO.eliminarLibro(ISBN);
        if (eliminado) {
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Libro eliminado correctamente.");
            BotonLimpiarLibro();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el libro.");
        }
    }

    @FXML
    private void BotonBuscarLibro() {
        String titulo = txtTituloLibro.getText().trim();
        String isbn = txtISBNLibro.getText().trim();
        String autor = txtAutorLibro.getText().trim();
        int filledCount = 0;
        if (!titulo.isEmpty()) filledCount++;
        if (!isbn.isEmpty()) filledCount++;
        if (!autor.isEmpty()) filledCount++;

        if (filledCount > 1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Solo se puede buscar por un parámetro: Título, ISBN o Autor.");
            return;
        }
        String parametro = !titulo.isEmpty() ? titulo : (!isbn.isEmpty() ? isbn : autor);

        if (parametro.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ingrese un título, ISBN o nombre de autor.");
            return;
        }
        List<Libro> librosEncontrados = libroDAO.buscarPorParametro(parametro);
        txtAreaLibros.clear();

        if (librosEncontrados.isEmpty()) {
            txtAreaLibros.setText("No se encontraron libros con ese criterio.");
            showAlert(Alert.AlertType.WARNING, "Aviso", "No se encontraron libros con ese criterio.");
        } else if (librosEncontrados.size() == 1) {
            Libro libro = librosEncontrados.get(0);
            txtTituloLibro.setText(libro.getTitulo());
            txtISBNLibro.setText(libro.getISBN());
            txtAutorLibro.setText(libro.getAutor().getNombreAutor());
            txtAnyoPublicacionLibro.setText(String.valueOf(libro.getAnyo()));
            txtEditorialLibro.setText(libro.getEditorial());

            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Libro encontrado. Datos cargados en los campos.");
        } else {
            StringBuilder resultado = new StringBuilder();
            for (Libro libro : librosEncontrados) {
                resultado.append(libro.toString()).append("\n");
            }
            txtAreaLibros.setText(resultado.toString());
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Múltiples libros encontrados.");
        }
    }

    @FXML
    private void BotonListarLibros() {
        List<Libro> librosDisponibles = libroDAO.obtenerLibrosDisponibles();
        txtAreaLibros.clear();

        if (!librosDisponibles.isEmpty()) {
            StringBuilder resultado = new StringBuilder();
            for (Libro libro : librosDisponibles) {
                resultado.append(libro.toString()).append("\n");
            }
            txtAreaLibros.setText(resultado.toString());
        } else {
            txtAreaLibros.setText("No hay libros disponibles.");
        }
    }

    @FXML
    private void BotonLimpiarLibro() {
        txtISBNLibro.clear();
        txtTituloLibro.clear();
        txtAutorLibro.clear();
        txtEditorialLibro.clear();
        txtAnyoPublicacionLibro.clear();
        txtAreaLibros.clear();
    }

    // Interfaz Socio

    @FXML
    private void BotonVolverSocio() throws IOException {
        new SeleccionInterfaz(InterfazSocio, "/org/example/bibliotecafx/InterfazInicio.fxml");
    }

    @FXML
    private void BotonSalirSocio(){
        System.exit(0);
    }

    @FXML
    private void BotonAgregarSocio() {
        String nombre = txtNombreSocio.getText();
        String direccion = txtDireccionSocio.getText();
        String telefono = txtTelefonoSocio.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El nombre, la direccion y el telefono deben ser llenados.");
            return;
        }

        Socio nuevoSocio = new Socio(nombre, direccion, telefono);
        socioDAO.registrarSocio(nuevoSocio);
        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Socio agregado correctamente.");
        BotonLimpiarSocios();
    }

    @FXML
    private void BotonModificarSocio() {
        String idTexto = txtIdSocio.getText().trim();
        String nombre = txtNombreSocio.getText().trim();
        String direccion = txtDireccionSocio.getText();
        String telefono = txtTelefonoSocio.getText().trim();

        if (idTexto.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben estar llenos.");
            return;
        }

        try {
            Integer id = Integer.parseInt(idTexto);
            Socio socioExistente = socioDAO.buscarPorId(id);

            if (socioExistente != null) {
                socioExistente.setNombreSocio(nombre);
                socioExistente.setDireccion(direccion);
                socioExistente.setTelefono(telefono);
                socioDAO.modificarSocio(socioExistente);
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Socio modificado correctamente.");
            } else {
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró un socio con ese ID.");
            }
            BotonLimpiarSocios();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ID debe ser un número válido.");
        }
    }

    @FXML
    private void BotonEliminarSocio() {
        String idTexto = txtIdSocio.getText().trim();

        if (idTexto.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar un ID.");
            return;
        }

        try {
            Integer id = Integer.parseInt(idTexto);
            boolean eliminado = socioDAO.eliminarSocio(id);
            if (eliminado) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Socio eliminado correctamente.");
            } else {
                showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró un socio con ese ID.");
            }
            BotonLimpiarSocios();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ID debe ser un número válido.");
        }
    }

    @FXML
    private void BotonBuscarSocio() {
        String nombre = txtNombreSocio.getText().trim();
        String telefono = txtTelefonoSocio.getText().trim();
        int filledCount = 0;
        if (!nombre.isEmpty()) filledCount++;
        if (!telefono.isEmpty()) filledCount++;

        if (filledCount > 1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Solo se puede buscar por un parámetro: Nombre o Teléfono.");
            return;
        }

        String parametro = !nombre.isEmpty() ? nombre : telefono;

        if (parametro.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ingrese un nombre o teléfono para buscar.");
            return;
        }

        List<Socio> sociosEncontrados = socioDAO.buscarPorParametro(parametro);
        txtAreaSocios.clear();

        if (sociosEncontrados.isEmpty()) {
            txtAreaSocios.setText("No se encontraron socios con ese criterio.");
            showAlert(Alert.AlertType.WARNING, "No encontrado", "No se encontró ningún socio con ese dato.");
        } else if (sociosEncontrados.size() == 1) {
            Socio socio = sociosEncontrados.get(0);
            txtNombreSocio.setText(socio.getNombreSocio());
            txtDireccionSocio.setText(socio.getDireccion());
            txtTelefonoSocio.setText(socio.getTelefono());
            txtIdSocio.setText(String.valueOf(socio.getIdSocio()));
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Socio encontrado. Datos cargados en los campos.");
        } else {
            StringBuilder resultado = new StringBuilder();
            for (Socio socio : sociosEncontrados) {
                resultado.append(socio.toString()).append("\n");
            }
            txtAreaSocios.setText(resultado.toString());
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Múltiples socios encontrados.");
        }
    }

    @FXML
    private void BotonListarSocios() {
        List<Socio> listaSocios = socioDAO.obtenerSocios();
        txtAreaSocios.clear();

        if (!listaSocios.isEmpty()) {
            StringBuilder lista = new StringBuilder();
            for (Socio socio : listaSocios) {
                lista.append(socio.toString()).append("\n");
            }
            txtAreaSocios.setText(lista.toString());
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Lista de socios obtenida.");
        } else {
            txtAreaSocios.setText("No hay socios registrados.");
            showAlert(Alert.AlertType.WARNING, "Vacío", "No hay socios en la base de datos.");
        }
    }

    @FXML
    private void BotonLimpiarSocios() {
        txtNombreSocio.clear();
        txtIdSocio.clear();
        txtDireccionSocio.clear();
        txtTelefonoSocio.clear();
        txtAreaAutores.clear();
    }

    // Interfaz Prestamo

    @FXML
    private void BotonVolverPrestamo() throws IOException {
        new SeleccionInterfaz(InterfazPrestamo, "/org/example/bibliotecafx/InterfazInicio.fxml");
    }

    @FXML
    private void BotonSalirPrestamo(){
        System.exit(0);
    }

    @FXML
    private void BotonAgregarPrestamo() {
        String idSocioStr = txIdSocioPrestamo.getText().trim();
        String isbn = txtISBNPrestamo.getText().trim();

        if (idSocioStr.isEmpty() || isbn.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar el ID del socio y el ISBN del libro.");
            return;
        }

        if(!esISBNValido(isbn)){
            showAlert(Alert.AlertType.ERROR, "Error", "El ISBN ingresado no es valido.");
            return;
        }

        try {
            int idSocio = Integer.parseInt(idSocioStr);
            Socio socio = socioDAO.buscarPorId(idSocio);
            if (socio == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "El socio con ID " + idSocio + " no existe.");
                return;
            }

            Libro libro = libroDAO.buscarPorISBN(isbn);
            if (libro == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "El libro con ISBN " + isbn + " no existe.");
                return;
            }

            Prestamo prestamo = new Prestamo(libro, socio);
            prestamoDAO.registrarPrestamo(prestamo);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Préstamo registrado exitosamente.");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ID del socio debe ser un número válido.");
        }
    }

    @FXML
    private void BotonListarPrestamos() {
        List<Prestamo> prestamosActivos = prestamoDAO.listarLibrosPrestados();
        txtAreaPrestamos.clear();

        if (prestamosActivos.isEmpty()) {
            txtAreaPrestamos.setText("No hay préstamos activos en este momento.");
            showAlert(Alert.AlertType.INFORMATION, "Información", "No hay préstamos activos.");
        } else {
            StringBuilder resultado = new StringBuilder();
            for (Prestamo p : prestamosActivos) {
                resultado.append(p.toString()).append("\n");
            }
            txtAreaPrestamos.setText(resultado.toString());
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Préstamos activos listados.");
        }
    }

    @FXML
    private void BotonBuscarPrestamoSocio() {
        String idSocioStr = txIdSocioPrestamo.getText().trim();

        if (idSocioStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe ingresar un ID de socio.");
            return;
        }

        try {
            int idSocio = Integer.parseInt(idSocioStr);
            Socio socio = socioDAO.buscarPorId(idSocio);
            if (socio == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "El socio con ID " + idSocio + " no existe.");
                return;
            }

            List<Prestamo> prestamos = prestamoDAO.listarHistorialPrestamos(idSocio);
            txtAreaPrestamos.clear();

            if (prestamos.isEmpty()) {
                txtAreaPrestamos.setText("El socio no tiene historial de préstamos.");
                showAlert(Alert.AlertType.INFORMATION, "Información", "El socio no tiene historial de préstamos.");
            } else {
                StringBuilder resultado = new StringBuilder();
                for (Prestamo p : prestamos) {
                    resultado.append(p.toString()).append("\n");
                }
                txtAreaPrestamos.setText(resultado.toString());
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Historial de préstamos encontrado.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El ID del socio debe ser un número válido.");
        }
    }

    @FXML
    private void BotonLimpiarPrestamo() {
        txtISBNPrestamo.clear();
        txIdSocioPrestamo.clear();
        txtAreaPrestamos.clear();
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

    private static String capitalizarPrimeraLetraYDespuesDeEspacios(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }

        String[] palabras = texto.toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }

        return resultado.toString().trim();
    }

    private boolean esISBNValido(String isbn) {
        // Eliminar los guiones si los hay
        String isbnSinGuiones = isbn.replace("-", "");

        // Verificar que el ISBN tiene exactamente 13 dígitos
        return isbnSinGuiones.matches("\\d{13}");
    }
}
