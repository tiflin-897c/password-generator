import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 54321;

        try (Socket socket = new Socket(servidor, puerto)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

            String respuesta;
            while ((respuesta = reader.readLine()) != null) {
                System.out.println(respuesta);
                if (respuesta.equals("¿Cuál es tu nombre?")) {
                    String nombreCliente = inputReader.readLine();
                    writer.println(nombreCliente);
                } else if (respuesta.equals("¿Deseas generar una contraseña ahora? (si/no)")) {
                    String deseaGenerar = inputReader.readLine();
                    writer.println(deseaGenerar);
                    if (deseaGenerar.equals("si")) {

                        System.out.println("Introduce la cantidad de contraseñas que deseas generar:");
                        String cantidad = inputReader.readLine();
                        writer.println(cantidad);
                    }
                } else if (respuesta.contains("¿Cuántas letras mayúsculas")) {
                    String cantidadMayusculas = inputReader.readLine();
                    writer.println(cantidadMayusculas);
                } else if (respuesta.contains("¿Cuántas letras minúsculas")) {
                    String cantidadMinusculas = inputReader.readLine();
                    writer.println(cantidadMinusculas);
                } else if (respuesta.contains("¿Cuántos dígitos")) {
                    String cantidadDigitos = inputReader.readLine();
                    writer.println(cantidadDigitos);
                } else if (respuesta.contains("¿Cuántos caracteres especiales")) {
                    String cantidadEspeciales = inputReader.readLine();
                    writer.println(cantidadEspeciales);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error en el cliente: " + ex.getMessage());
        }
    }
}
