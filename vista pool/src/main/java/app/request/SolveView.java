package app.request;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import app.model.Response;
import app.persistence.ServicePersistence;

/**
 * SolveView
 */
public class SolveView extends JDialog {
    private Response response;
    private static final long serialVersionUID = 1L;
    private Object[] monitores;
    private Object[] categorias;
    private JButton btnMonitor;
    private JButton btnCategoria;
    private JButton btnEnviarSolucion;
    JTextArea Obs;
    private JPanel content;
    private String selectionMonitor;
    private String selectionCategoria;
    private String observaciones;
    ServicePersistence service;
    public SolveView(Response r,ServicePersistence service) {
        super();
        this.service=service;
        this.response = r;
        setTitle("Solucionar el problema al equipo " + response.getConsulta().getEquipo());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        List<String> moni=service.getStaff();
        List<String> cate=service.getAllCategories();
        monitores = new Object[moni.size()];
        categorias = new Object[cate.size()];
        for (int i = 0; i < moni.size(); i++) monitores[i]=moni.get(i);
        for (int i = 0; i < cate.size(); i++) categorias[i]=cate.get(i);
        initComponents();
        initActions();
        pack();
    }
    private void initComponents() {
        content=new JPanel(new GridLayout(3,1));
        btnCategoria = new JButton("Seleccionar categoria");
        btnMonitor = new JButton("Seleccionar monitor");
        btnEnviarSolucion = new JButton("Enviar solucion");
        Obs = new JTextArea ("Deja tus observaciones del problema",2,20);
        add (Obs, BorderLayout.CENTER);
        content.add (btnCategoria);
        content.add (btnMonitor);
        content.add (btnEnviarSolucion);
        add(content,BorderLayout.EAST);
    }
    private void initActions() {
        btnMonitor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionMonitor = (String) JOptionPane.showInputDialog(null,
                        "¿Cual monitor va a solucionar este problema?", "Monitores :", JOptionPane.QUESTION_MESSAGE,
                        null, monitores, "B0");
                response.setPersonal_nombre(selectionMonitor);
            }
        });
        btnCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionCategoria = (String) JOptionPane.showInputDialog(null,
                        "¿A que categoria pertenece esta solucion?", "Categorias :", JOptionPane.QUESTION_MESSAGE, null,
                        categorias, "B0");
                response.setCategoria(selectionCategoria);
            }
        });
        btnEnviarSolucion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observaciones=Obs.getText();
                response.setObservaciones(observaciones);
                if(observaciones!=null && selectionCategoria!=null && selectionMonitor!=null){
                    System.out.println(response.toString());
                    service.saveResponse(response);
                    service.updateEstado(response.getConsulta().getUsuario(),response.getConsulta().getEquipo());
                }
                dispose();
            }
        });
    }

    
}