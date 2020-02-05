package app.request;

import app.persistence.ServicePersistence;

import javax.swing.*;
import java.awt.*;

public class NewMonitor extends JDialog {
    private JLabel tittle;
    private ServicePersistence service;
    private JButton agregar;
    private JButton cancelar;
    private JPanel up;
    private JPanel center;
    private JPanel bot;
    private JTextField monitor;
    private JLabel nombre;

    public NewMonitor(ServicePersistence service){
        this.service=service;
        this.setTitle("Agregar Monitor");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        generarComponentes();

    }

    private void generarComponentes(){
        upComponents();
        botComponents();
        centerComponents();
    }

    private void upComponents() {
        up = new JPanel(new FlowLayout());
        tittle = new JLabel("Nombre Monitor");
        up.add(tittle);
        this.add(up, BorderLayout.NORTH);
    }

    private void botComponents() {
        bot= new JPanel(new GridLayout(1, 2));
        agregar=new JButton();
        cancelar=new JButton();
        agregar.setText("Agregar");
        cancelar.setText("Cancelar");
        //JScrollPane scroll = new JScrollPane(description);
        bot.add(agregar);
        bot.add(cancelar);
        this.add(bot,BorderLayout.SOUTH);
    }

    private void centerComponents(){
        center=new JPanel(new GridLayout(1, 2));
        nombre = new JLabel();
        nombre.setText("Nombre:");
        monitor= new JTextField(40);
        center.add(nombre);
        center.add(monitor);
        this.add(center,BorderLayout.CENTER);


    }


}
