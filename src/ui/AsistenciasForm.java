/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import app.Utility;
import controller.AsistenciaController;
import data.Clase;
import data.DataContract;
import data.SQLData.AsistenciasFiller;
import data.SQLData.ClasesTableModel;
import data.SQLData.Parser.HorariosParse;
import data.SQLData.Parser.UsuariosParser;
import data.Usuario;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

/**
 *
 * @author Yknx
 */
public class AsistenciasForm extends javax.swing.JFrame {
    private final static Logger LOGGER = Logger.getLogger(AsistenciasForm.class.getName());

    public static boolean isDebug=Utility.isDebug;

    public static int fixedDay = 4;
    public static int [] fixedHorarios = {14,15};
    /**
     * Creates new form AsistenciasForm
     */
    public AsistenciasForm() {
        initComponents();
    }

    private void setProfesorImagen(ImageIcon icon){
       
        
       imageLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(90, 112,Image.SCALE_SMOOTH)));
                
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        infoLabel = new javax.swing.JLabel();
        DetallesPanel = new javax.swing.JPanel();
        UsuarioPanel = new javax.swing.JPanel();
        datosUsuarioPanel = new javax.swing.JPanel();
        usuarioLabel = new javax.swing.JLabel();
        UsuarioField = new javax.swing.JTextField();
        contrasenaLabel = new javax.swing.JLabel();
        contrasenaField = new javax.swing.JPasswordField();
        UsuariosDetallesPanel = new javax.swing.JPanel();
        infoPanel = new javax.swing.JPanel();
        maestroLabel = new javax.swing.JLabel();
        maestroNombreLabel = new javax.swing.JLabel();
        claseLabel = new javax.swing.JLabel();
        claseNombreLabel = new javax.swing.JLabel();
        inicioLabel = new javax.swing.JLabel();
        horaInicioLabel = new javax.swing.JLabel();
        salonNombreLabel = new javax.swing.JLabel();
        salonLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        ClasesPanel = new javax.swing.JPanel();
        EnCursoPanel = new javax.swing.JPanel();
        ecInfoPanel = new javax.swing.JPanel();
        ecTituloLabel = new javax.swing.JLabel();
        ecHoraLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        enCursoTable = new javax.swing.JTable();
        FuturasPanel = new javax.swing.JPanel();
        fInfoPanel = new javax.swing.JPanel();
        fTituloLabel = new javax.swing.JLabel();
        futurasHoraLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        futurasTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Control de Asistencias");
        setMinimumSize(new java.awt.Dimension(1024, 600));
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        infoLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        infoLabel.setText("Ingrese sus datos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(infoLabel, gridBagConstraints);

        java.awt.GridBagLayout DetallesPanelLayout = new java.awt.GridBagLayout();
        DetallesPanelLayout.columnWidths = new int[] {0, 16, 0, 16, 0};
        DetallesPanelLayout.rowHeights = new int[] {0};
        DetallesPanel.setLayout(DetallesPanelLayout);

        java.awt.GridBagLayout UsuarioPanelLayout = new java.awt.GridBagLayout();
        UsuarioPanelLayout.columnWidths = new int[] {0, 5, 0};
        UsuarioPanelLayout.rowHeights = new int[] {0, 5, 0};
        UsuarioPanel.setLayout(UsuarioPanelLayout);

        datosUsuarioPanel.setLayout(new java.awt.GridLayout(2, 2, 4, 4));

        usuarioLabel.setText("Usuario:");
        datosUsuarioPanel.add(usuarioLabel);

        UsuarioField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioFieldActionPerformed(evt);
            }
        });
        datosUsuarioPanel.add(UsuarioField);

        contrasenaLabel.setText("Contraseña:");
        datosUsuarioPanel.add(contrasenaLabel);

        contrasenaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasenaFieldActionPerformed(evt);
            }
        });
        datosUsuarioPanel.add(contrasenaField);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        UsuarioPanel.add(datosUsuarioPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        DetallesPanel.add(UsuarioPanel, gridBagConstraints);

        UsuariosDetallesPanel.setLayout(new java.awt.GridBagLayout());

        java.awt.GridBagLayout infoPanelLayout = new java.awt.GridBagLayout();
        infoPanelLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0};
        infoPanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0};
        infoPanel.setLayout(infoPanelLayout);

        maestroLabel.setText("Maestro:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        infoPanel.add(maestroLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        infoPanel.add(maestroNombreLabel, gridBagConstraints);

        claseLabel.setText("Clase:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        infoPanel.add(claseLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        infoPanel.add(claseNombreLabel, gridBagConstraints);

        inicioLabel.setText("Inicio:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        infoPanel.add(inicioLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 16);
        infoPanel.add(horaInicioLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        infoPanel.add(salonNombreLabel, gridBagConstraints);

        salonLabel.setText("Salón:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        infoPanel.add(salonLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        UsuariosDetallesPanel.add(infoPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        DetallesPanel.add(UsuariosDetallesPanel, gridBagConstraints);

        imageLabel.setMaximumSize(new java.awt.Dimension(90, 112));
        imageLabel.setMinimumSize(new java.awt.Dimension(90, 112));
        imageLabel.setPreferredSize(new java.awt.Dimension(90, 112));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        DetallesPanel.add(imageLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(DetallesPanel, gridBagConstraints);

        ClasesPanel.setAlignmentY(0.0F);
        ClasesPanel.setLayout(new java.awt.GridLayout(1, 2, 8, 8));

        java.awt.GridBagLayout EnCursoPanelLayout = new java.awt.GridBagLayout();
        EnCursoPanelLayout.columnWidths = new int[] {0, 5, 0};
        EnCursoPanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0};
        EnCursoPanel.setLayout(EnCursoPanelLayout);

        java.awt.GridBagLayout ecInfoPanelLayout = new java.awt.GridBagLayout();
        ecInfoPanelLayout.columnWidths = new int[] {0, 5, 0};
        ecInfoPanelLayout.rowHeights = new int[] {0};
        ecInfoPanel.setLayout(ecInfoPanelLayout);

        ecTituloLabel.setText("Clases en Curso");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        ecInfoPanel.add(ecTituloLabel, gridBagConstraints);

        ecHoraLabel.setText("07:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        ecInfoPanel.add(ecHoraLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        EnCursoPanel.add(ecInfoPanel, gridBagConstraints);

        enCursoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        enCursoTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(enCursoTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.8;
        EnCursoPanel.add(jScrollPane1, gridBagConstraints);

        ClasesPanel.add(EnCursoPanel);

        java.awt.GridBagLayout FuturasPanelLayout = new java.awt.GridBagLayout();
        FuturasPanelLayout.columnWidths = new int[] {0, 5, 0};
        FuturasPanelLayout.rowHeights = new int[] {0, 5, 0, 5, 0};
        FuturasPanel.setLayout(FuturasPanelLayout);

        fInfoPanel.setLayout(new java.awt.GridBagLayout());

        fTituloLabel.setText("Clases siguientes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.2;
        fInfoPanel.add(fTituloLabel, gridBagConstraints);

        futurasHoraLabel.setText("07:50");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        fInfoPanel.add(futurasHoraLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        FuturasPanel.add(fInfoPanel, gridBagConstraints);

        futurasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(futurasTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.8;
        FuturasPanel.add(jScrollPane2, gridBagConstraints);

        ClasesPanel.add(FuturasPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(ClasesPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UsuarioFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioFieldActionPerformed
        // TODO add your handling code here:
        contrasenaFieldActionPerformed(evt);
    }//GEN-LAST:event_UsuarioFieldActionPerformed

    private void contrasenaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasenaFieldActionPerformed
        
        
        try {
            // TODO add your handling code here:
            
            
            //String usuario = "alc.ina@telematica.mx";
            //String password = "109514";
            if(UsuarioField.getText().isEmpty()){
                UsuarioField.requestFocus();
                infoLabel.setText("Favor de ingresar un usuario.");
                return;
            }
            String usuario = UsuarioField.getText();
            usuario = usuario.replaceAll("@telematica.mx", "");
            usuario = usuario.trim();
            usuario = usuario.concat("@telematica.mx");
            if(contrasenaField.getText().isEmpty()){
                contrasenaField.requestFocus();
                infoLabel.setText("Favor de ingresar la contraseña.");
                return;
            }
            String password = contrasenaField.getText();
            
            mAsistenciaController = AsistenciaController.getInstance(dia,horarios[0]);
            //ResultSet maestro = mAsistenciaController.setAsistencia(usuario, password,1,13);
            boolean maestro = mAsistenciaController.setAsistencia(usuario, password);
               infoLabel.setText(mAsistenciaController.getMessage());
               
            if(!maestro){
                return;
            }
            
            setProfessorData(mAsistenciaController.getRawData());
            resetUserData();
            resetTables();
        } catch (SQLException ex) {
            Logger.getLogger(AsistenciasForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_contrasenaFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    static AsistenciasForm form;
    private AsistenciasFiller mAsistenciasFiller;
    private UsuariosParser mMaestrosParser;
    public static void main(String args[]) {
        /*Handler systemOutHandler = new StreamHandler(System.out, new SimpleFormatter()); 
        systemOutHandler.setLevel(Level.FINEST); 
         Logger rootLogger = Logger.getLogger(""); 
        rootLogger.addHandler(systemOutHandler); 
        rootLogger.setLevel(Level.FINEST);*/
        
                
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (UIManager.getCrossPlatformLookAndFeelClassName().equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AsistenciasForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                form = new AsistenciasForm();
                form.setVisible(true);
                /*URL iconURL = getClass().getResource("/images/app_icon.png");
                
                ImageIcon icon = new ImageIcon(iconURL);
                form.setIconImage(icon.getImage());*/
                //form.setProfesorImagen(icon);
                form.prepareData();
                
        
                
            }
        });
        
        
        // iconURL is null when not found
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ClasesPanel;
    private javax.swing.JPanel DetallesPanel;
    private javax.swing.JPanel EnCursoPanel;
    private javax.swing.JPanel FuturasPanel;
    private javax.swing.JTextField UsuarioField;
    private javax.swing.JPanel UsuarioPanel;
    private javax.swing.JPanel UsuariosDetallesPanel;
    private javax.swing.JLabel claseLabel;
    private javax.swing.JLabel claseNombreLabel;
    private javax.swing.JPasswordField contrasenaField;
    private javax.swing.JLabel contrasenaLabel;
    private javax.swing.JPanel datosUsuarioPanel;
    private javax.swing.JLabel ecHoraLabel;
    private javax.swing.JPanel ecInfoPanel;
    private javax.swing.JLabel ecTituloLabel;
    private javax.swing.JTable enCursoTable;
    private javax.swing.JPanel fInfoPanel;
    private javax.swing.JLabel fTituloLabel;
    private javax.swing.JLabel futurasHoraLabel;
    private javax.swing.JTable futurasTable;
    private javax.swing.JLabel horaInicioLabel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel inicioLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel maestroLabel;
    private javax.swing.JLabel maestroNombreLabel;
    private javax.swing.JLabel salonLabel;
    private javax.swing.JLabel salonNombreLabel;
    private javax.swing.JLabel usuarioLabel;
    // End of variables declaration//GEN-END:variables

    
    private ClasesTableModel modelEnCurso;
    private ClasesTableModel modelFuturas;
    
    private int getDate(){
        Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            
            cal.setTime(new Date());
            int rec = cal.get(Calendar.DAY_OF_WEEK);
            
            if(rec<0) rec = 7-rec;
            else rec -= 2;
            
            
            return rec;
    }
    HorariosParse horas;
    int[] horarios;
    
    private void setProfessorData(ResultSet raw) throws SQLException{
       raw.first();
        maestroNombreLabel.setText(raw.getString(DataContract.AsistenciaDataViewEntry.COLUMN_MAESTRO));
        claseNombreLabel.setText(raw.getString(DataContract.AsistenciaDataViewEntry.COLUMN_MATERIA));
        salonNombreLabel.setText(raw.getString(DataContract.AsistenciaDataViewEntry.COLUMN_SALON));
        horaInicioLabel.setText(raw.getString(DataContract.AsistenciaDataViewEntry.COLUMN_HORARIO_INICIO));
        //TODO: Handle everything else
        String pic = raw.getString(DataContract.AsistenciaDataViewEntry.COLUMN_MAESTRO_PICTURES);
        if(pic!=null){
            setProfesorImagen(pic);
        }
    }
    /*private void setProfessorData(int t){
        setProfessorData(mMaestrosParser.get(t), new Clase());
    }*/
    
    private int dia;
    private AsistenciaController mAsistenciaController;
    private void prepareData() {
        
            dia = getDate();
            
        try {
            horas = HorariosParse.with(Utility.DB_STRING);
            horarios = horas.getClosest();
            if(isDebug) {
                horarios = fixedHorarios;
                if(getDate()>4)
                dia = fixedDay;
            }
            mMaestrosParser = UsuariosParser.with(Utility.DB_STRING);
            mAsistenciaController = AsistenciaController.getInstance();
            
            resetTables();
            
        } catch (ClassNotFoundException | SQLException | ParseException ex) {
            Logger.getLogger(AsistenciasForm.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    }
    private void resetTables(){
        
                modelEnCurso = ClasesTableModel.with( horarios[0],dia);
                modelFuturas = ClasesTableModel.with( horarios[1],dia);
            
            updateTables();
    }
    
    private void setTable(JTable table, TableModel model, JLabel indicator, int pos){
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowSelectionAllowed(false);
        table.setModel(model);
        table.setFocusable(false);
        indicator.setText(horas.getHora(horarios[pos]));
    }
    private void updateTables(){
        setTable(enCursoTable,modelEnCurso,ecHoraLabel,0);
        setTable(futurasTable,modelFuturas,futurasHoraLabel,1);
    }

    private void setProfesorImagen(String picture_uri) {
        boolean t = true;
        int cnt = 0;
        String filename = picture_uri;
        while(t || cnt>10){
            cnt++;
        try {
            //URL url = new URL(picture_uri);
            URL url = new URL(Utility.SERVER_PATH+Utility.IMG_PATH+filename);
            BufferedImage c = ImageIO.read(url);
            ImageIcon image = new ImageIcon(c);
            setProfesorImagen(image);
            t = false;
        } catch (IOException ex) {
            //Logger.getLogger(AsistenciasForm.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.info("Image not found. Loading Default.");
            filename = "no_exist.jpg";
            }
        }
    }

    private void resetUserData() {
        contrasenaField.setText("");
        UsuarioField.setText("");
    }
}
