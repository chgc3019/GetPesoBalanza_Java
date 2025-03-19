import javax.swing.*;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.*;

import java.nio.charset.StandardCharsets;

import java.sql.*;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

public class Balanza {

    private static JLabel pesoLabel;

    private static JLabel statusLabel;

    private static final String URL = "jdbc:jtds:sqlserver://10.72.20.171:1433/Pruebas;";

    private static final String USER = "desarrolladorDev";

    private static final String PASSWORD = "desarrollador";

    public static void main(String[] args) {

        JFrame ventana = new JFrame("Peso en Tiempo Real");

        ventana.setSize(300, 200);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ventana.setLayout(new BorderLayout());

        ventana.setLocationRelativeTo(null);

        pesoLabel = new JLabel("Esperando datos...", SwingConstants.CENTER);

        pesoLabel.setFont(new Font("Arial", Font.BOLD, 36));

        pesoLabel.setForeground(Color.BLUE);

        ventana.add(pesoLabel, BorderLayout.CENTER);

        statusLabel = new JLabel("Conectando...", SwingConstants.CENTER);

        statusLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        ventana.add(statusLabel, BorderLayout.SOUTH);

        ventana.setVisible(true);

        
        probarConexion();

        leerDatosBalanza();
    }

    public static void leerDatosBalanza() {

        SerialPort puerto = SerialPort.getCommPort("COM8");

        puerto.setBaudRate(9600);

        if (!puerto.openPort()) {

            JOptionPane.showMessageDialog(null, "No se pudo conectar con la balanza.", "Error", JOptionPane.ERROR_MESSAGE);

            statusLabel.setText("No se pudo conectar con la balanza.");

            return;
        }

        SwingUtilities.invokeLater(() -> statusLabel.setText("Conectado a la balanza."));

        new Thread(() -> {

            byte[] buffer = new byte[1024];

            while (true) {

                if (puerto.bytesAvailable() > 0) {

                    int numBytes = puerto.readBytes(buffer, buffer.length);

                    String datos = new String(buffer, 0, numBytes, StandardCharsets.UTF_8).trim();

                    if (datos.contains("NET")) {

                        String peso = datos.split("NET")[1].trim();

                        if (peso.endsWith("kg")) {

                            peso = peso.substring(0, peso.length() - 2).trim();
                        }

                        StringBuilder stringBuilder = new StringBuilder();

                        stringBuilder.append(peso);

                        stringBuilder.append(" kg");

                        SwingUtilities.invokeLater(() -> pesoLabel.setText(stringBuilder.toString()));
                    
                       insertarPesoEnBaseDeDatos(peso);
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

  
    private static void probarConexion() {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println(" ✅ Conexión exitosa con la base de datos.");

            SwingUtilities.invokeLater(() -> statusLabel.setText(" Conectado a la base de datos."));

        } catch (SQLException e) {

            System.err.println(" ❌ Error al conectar a la base de datos: " + e.getMessage());

            SwingUtilities.invokeLater(() -> statusLabel.setText("Error al conectar a la base de datos."));
        }
    }

    private static void insertarPesoEnBaseDeDatos(String peso) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String query = "INSERT INTO [dbo].[registros_peso] (peso, fecha, hora) VALUES (?, ?, ?)";
    
            try (PreparedStatement stmt = conn.prepareStatement(query)) {

                BigDecimal pesoDecimal = new BigDecimal(peso);
    
                stmt.setBigDecimal(1, pesoDecimal);

                stmt.setDate(2, new Date(System.currentTimeMillis()));
    
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                String formattedTime = timeFormat.format(new java.util.Date());

                stmt.setString(3, formattedTime);
    
                stmt.executeUpdate();

                System.out.println("✅ Peso insertado correctamente en la base de datos.");
            }
        } catch (SQLException e) {
            
            System.err.println("❌ Error al insertar el peso en la base de datos: " + e.getMessage());
        }
    }
}