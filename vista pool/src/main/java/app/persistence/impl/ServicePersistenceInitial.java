package app.persistence.impl;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.model.Request;
import app.model.Response;
import app.persistence.ServicePersistence;

/**
 * ServicePersistenceInitial
 */
public class ServicePersistenceInitial implements ServicePersistence {
    // Conexión
    private Connection connection = null;

    // URL y Driver
    private static final String DB_URL = "jdbc:postgresql://ec2-50-19-95-77.compute-1.amazonaws.com:5432/dl4l4esmgafsq?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static final String driver = "org.postgresql.Driver";

    // Credenciales
    private static final String user = "bycpaxcnvvkzyg";
    private static final String pass = "6afd55f69e65017cb75dd299ae941948aa963c8ea2398ef7eaa859b732c8752a";

    // Constructor
    public ServicePersistenceInitial() throws Exception {
        Class.forName(driver);
        connection = DriverManager.getConnection(DB_URL, user, pass);
    }

    // Registrar consulta
    // Mejorar seguridad, SQLInjection es 2000000% posible.
    @Override
    public void saveRequest(Request request) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String statementSql = "INSERT INTO consulta (usuario," + "descripcion," + "equipo," + "estado) VALUES ('"
                    + request.getUsuario() + "', '" + request.getDescripcion() + "', '" + request.getEquipo() + "', '"
                    + request.getEstado() + "');";
            // Guarda en DB
            statement.execute(statementSql);
            // Inicio de la asignación de ID para la sección volatil.
            String idFromDB = "SELECT max(idConsulta) FROM consulta;";
            ResultSet rs = statement.executeQuery(idFromDB);
            while (rs.next()) {
                request.setId(rs.getInt(1));
            }
            // Fin de la asignación
        } catch (Exception e) {
            System.out.println("Error - save request");
            e.printStackTrace();
        }

        return;
    }

    // Registrar solución
    // Mejorar seguridad, SQLInjection es 2000000% posible.
    @Override
    public void saveResponse(Response response) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String statementSql = "INSERT INTO solucion (duracion," + "observaciones," + "Consulta_idConsulta,"
                    + "Personal_nombre," + "Categoria_nombre) VALUES ('" + response.getDuracion() + "', '"
                    + response.getObservaciones() + "', '" + response.getConsulta().getId() + "', '"
                    + response.getPersonal_nombre() + "', '" + response.getCategoria() + "');";
            // Guarda en DB
            statement.execute(statementSql);
        } catch (Exception e) {
            System.out.println("Error - Save response");
            e.printStackTrace();
        }
        return;
    }

    // Guardado de categorias
    // Mejorar seguridad, SQLInjection es 2000000% posible.
    @Override
    public void saveCategory(String name, String description) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String statementSql = "INSERT INTO categoria (nombre, " + "descripcion) VALUES ('" + name + "', '"
                    + description + "');";
            statement.execute(statementSql);

        } catch (Exception e) {
            System.out.println("Error - save category");
            e.printStackTrace();
        }
        return;
    }

    // Trae de base de datos todos las consultas que aun no han sido solucionadas
    @Override
    public List<Request> getAllRequest() {
        List<Request> requestList = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            // Consulta que solicita la información en base de datos de los problemas que
            // aun no han sido solucionados
            /**
             * A arreglar el asunto de traer consultas con fechas anteriores a las del día
             * actual. Literalmente esas consultas ya no se solucionaron y su estado debe
             * cambiar a uno diferente para evitar conflictos.
             **/
            String statementSql = "SELECT idConsulta, usuario, descripcion, equipo, estado FROM consulta WHERE NOT estado='Solucionado'";
            ResultSet rs = statement.executeQuery(statementSql);
            // Ciclo para sacar información del conjunto resultado de la consulta anterior
            requestList = new ArrayList<>();
            while (rs.next()) {
                // Mapeo de la base de datos a objetos volatiles
                int id_consulta = Integer.parseInt(rs.getString("idConsulta"));
                String usuario = rs.getString("usuario");
                String descripcion = rs.getString("descripcion");
                String equipo = rs.getString("equipo");
                String estado = rs.getString("estado");
                Request request = new Request(usuario, descripcion, equipo, estado, new Date());
                request.setId(id_consulta);
                requestList.add(request);
            }
        } catch (Exception e) {
            System.out.println("Error - consult request");
            e.printStackTrace();
        }
        return requestList;
    }

    @Override
    public List<String> getAllCategories() {
        List<String> categories = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            categories = new ArrayList<>();
            String statementSql = "SELECT nombre FROM categoria;";
            ResultSet rs = statement.executeQuery(statementSql);
            while (rs.next()) {
                String category = rs.getString("nombre");
                categories.add(category);
            }
        } catch (Exception e) {
            System.out.println("Error - Consult all categories");
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<String> getStaff() {
        List<String> staff = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            staff = new ArrayList<>();
            String statementSql = "SELECT nombre FROM personal;";
            ResultSet rs = statement.executeQuery(statementSql);
            while (rs.next()) {
                String monitor = rs.getString("nombre");
                staff.add(monitor);
            }
        } catch (Exception e) {
            System.out.println("Error - Consult staff");
            e.printStackTrace();
        }
        return staff;
    }

    @Override
    public int getIdByRequest(Request request) {
        int id=0;
        Statement statement = null;
        try{
            statement = connection.createStatement();
            String statementSql = "SELECT idConsulta FROM consulta where usuario='"+ request.getUsuario()+"'  and equipo='"+ request.getEquipo()+"' and descripcion='"+ request.getDescripcion()+"';";
            ResultSet rs = statement.executeQuery(statementSql);
            while(rs.next()){
                id= rs.getInt("idConsulta");
            }
        }catch(Exception e){
            System.out.println("Error - Consult staff");
            e.printStackTrace();
        }
        return id;
    }
}