package app.request;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.MenuBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import app.model.Request;
import app.persistence.ServicePersistence;
import app.persistence.impl.ServicePersistenceInitial;
import app.sockets.serverSocket.Server;

/**
 * PoolView
 */
public class PoolView extends JFrame {

    private static final long serialVersionUID = 4203719214690923537L;
    JLabel title;
    JPanel up;
    JPanel center;
    JTable pool;
    private JMenuBar menu;
    private JMenuItem newCategory,newMonitor;
    DefaultTableModel dm;
    private Server server;
    private ServicePersistence service;

    public PoolView() {
        server = new Server(200, this);
        new Thread(server).start();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Requests");
        initComponents();
        try {   
            this.service = new ServicePersistenceInitial();
            List<Request> requestList = service.getAllRequest();
            Request request = null;
            System.out.println(requestList.size());
            for (int i = 0; i < requestList.size(); i++) {
                request = requestList.get(i);
                addRequest(request);
            }
        } catch (Exception e) {
            System.out.println("Error en la creación de la persistencia");
            e.printStackTrace();
        }
        // Testeillo
        //System.out.println(service.getAllCategories().toString());
        //System.out.println(service.getStaff().toString());
    }

    private void initComponents() {
        menuComponents();
        upComponents();
        centerComponents();
        pack();
    }

    private void menuComponents() {
        menu = new JMenuBar();
        newCategory = new JMenuItem("Nueva categoria");
                newCategory.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NewCategory newCategory = new NewCategory(service);
                newCategory.setVisible(true);               
            }
            
        });
         //newMonitor=new JMenuItem("Agregar Monitor");
        /*newMonitor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NewMonitor newMonitor = new NewMonitor(service);
                newMonitor.setVisible(true);
            }
        });*/


        //menu.add(newMonitor);
        menu.add(newCategory);
        this.setJMenuBar(menu);
    }

    private void upComponents() {
        up = new JPanel(new FlowLayout());
        title = new JLabel("Listado de solicitudes de ayuda");
        Font fontTitle = title.getFont();
        title.setFont(new Font(fontTitle.getFontName(), fontTitle.getStyle(), 20));
        up.add(title);
        add(up, BorderLayout.NORTH);
    }

    private void centerComponents() {
        center = new JPanel(new FlowLayout());
        pool = new JTable();
        dm = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headers = new String[] { "Id", "Equipo", "Usuario", "Estado", "Inicio", "Descripcion" };
        dm.setColumnIdentifiers(headers);
        pool = new JTable(dm);
        pool.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                createDetailsResponse(evt);
            }
        });
        TableColumnModel tcm = pool.getColumnModel();
        tcm.removeColumn(tcm.getColumn(5));
        tcm.removeColumn(tcm.getColumn(4));
        tcm.removeColumn(tcm.getColumn(0));
        JScrollPane scroll = new JScrollPane(pool);
        center.add(scroll);
        add(center, BorderLayout.CENTER);
    }

    public void createDetailsResponse(MouseEvent evt) {
        int ind = pool.getSelectedRow();
        String id = dm.getValueAt(ind, 0).toString();
        String equipo = dm.getValueAt(ind, 1).toString();
        String usuario = dm.getValueAt(ind, 2).toString();
        String estado = dm.getValueAt(ind, 3).toString();
        String inicio = dm.getValueAt(ind, 4).toString();
        String descripcion = pool.getModel().getValueAt(ind, 5).toString();
        Request req = new Request(usuario, descripcion, equipo, estado, new Date());
        req.setId(service.getIdByRequest(req));
        RequestDetail rd = new RequestDetail(ind, req,service);
       rd.setVisible(true);

    }

    public void addRequest(Request r) {
        dm.addRow(new Object[] { r.getId(), r.getEquipo(), r.getUsuario(), r.getEstado(), r.getInicio().toString(),
                r.getDescripcion() });
        //JOptionPane.showMessageDialog(null, "Solicitud Pendiente");
    }

    public void saveRequestInDB(Request r){
        service.saveRequest(r);
    }

    public static void main(String[] args) {
        new PoolView().setVisible(true);
    }
}
