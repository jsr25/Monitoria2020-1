package app.request;

import java.util.Date;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import app.model.Request;
import app.model.Response;
import app.persistence.ServicePersistence;

/**
 * RequestDetail
 */
public class RequestDetail extends JDialog {
    private static final long serialVersionUID = -1056808006751808682L;
    private int ind;
    private Request request;
    private Response response;
    private JPanel content;
    private JPanel botton;
    private JButton solve;
    ServicePersistence service;

    public RequestDetail(int ind, Request request,ServicePersistence service) {
        super();
        this.ind = ind;
        this.request = request;
        this.service = service;
        this.response = new Response();
        request.setEstado("Atendiendo");
        response.setConsulta(request);
        response.setDuracion((int) (new Date().getTime() - request.getInicio().getTime()));
        setTitle("Detalles de la solicitud: " + Integer.toString(ind));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        pack();
    }

    private void initComponents() {
        contentCenter();
        contentBotton();
    }

    private void contentBotton() {
        botton = new JPanel(new GridLayout(1, 5));
        solve = new JButton("Solucionado");
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SolveView sv=new SolveView(response, service);
                sv.setVisible(true);
                dispose();
            }
        });
        botton.add(new JLabel());
        botton.add(new JLabel());
        botton.add(solve);
        botton.add(new JLabel());
        botton.add(new JLabel());
        add(botton, BorderLayout.SOUTH);
    }

    private void contentCenter() {
        content = new JPanel(new GridLayout(0, 2));
        content.add(new JLabel("Indice: "));
        content.add(new JLabel(Integer.toString(ind)));
        content.add(new JLabel("Usuario: "));
        content.add(new JLabel(request.getUsuario()));
        content.add(new JLabel("Equipo: "));
        content.add(new JLabel(request.getEquipo()));
        content.add(new JLabel("Descripcion: "));
        content.add(new JLabel(request.getDescripcion()));
        content.add(new JLabel("Estado: "));
        content.add(new JLabel(request.getEstado()));
        content.add(new JLabel("Fecha de inicio: "));
        content.add(new JLabel(request.getInicio().toString()));
        content.add(new JLabel("Duracion actual: "));
        content.add(new JLabel(Integer.toString(response.getDuracion())));
        add(content, BorderLayout.CENTER);
    }
}