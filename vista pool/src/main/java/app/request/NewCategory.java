package app.request;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import app.persistence.ServicePersistence;

/**
 * NewCategory
 */
public class NewCategory extends JDialog {
    private JLabel tittle;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JPanel up;
    private JPanel center;
    private JPanel bot;
    private JTextArea description;
    private JTextField name;
    private JButton send;
    private JButton cancel;
    private ServicePersistence service; // Para que sirva por ahora. A futuo quitar

    public NewCategory(ServicePersistence service) {
        this.service = service;
        this.setTitle("Crear nueva categoria");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        upComponents();
        centerComponents();
        botComponents();
        prepareActions();
        pack();
    }

    private void upComponents() {
        up = new JPanel(new FlowLayout());
        tittle = new JLabel("Información de la nueva categoría");
        up.add(tittle);
        this.add(up, BorderLayout.NORTH);
    }

    private void centerComponents() {
        center = new JPanel(new GridLayout(2, 2));
        nameLabel = new JLabel("  Nombre: ");
        descriptionLabel = new JLabel("  Descripción: ");
        name = new JTextField(40);
        description = new JTextArea(2, 20);
        JScrollPane scroll = new JScrollPane(description);
        center.add(nameLabel);
        center.add(name);
        center.add(descriptionLabel);
        center.add(scroll);
        this.add(center, BorderLayout.CENTER);
    }

    private void botComponents() {
        bot = new JPanel(new FlowLayout());
        send = new JButton("Crear");
        cancel = new JButton("Cancelar");
        bot.add(send);
        bot.add(cancel);
        this.add(bot, BorderLayout.SOUTH);
    }

    private void prepareActions() {
        // Enviar la acción de crear
        send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                service.saveCategory(name.getText(), description.getText());
                JOptionPane.showMessageDialog(null, "Creado");
                dispose();
            }
        });

        // Cancela
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}