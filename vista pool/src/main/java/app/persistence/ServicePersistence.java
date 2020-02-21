package app.persistence;

import java.util.List;

import app.model.Request;
import app.model.Response;

/**
 * ServicePersistence
 */
public interface ServicePersistence {
    public void saveRequest(Request request);
    public void saveCategory(String name, String description);
    public void saveResponse(Response response);
    public void updateEstado(String usuario, String equipo);
    public int getIdByRequest(Request request);
    public List<Request> getAllRequest();
    public List<String> getAllCategories();
    public List<String> getStaff();
}