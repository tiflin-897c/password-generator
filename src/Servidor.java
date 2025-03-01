import java.io.*;
import java.net.*;
import java.util.Random;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 54321;  

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (true) {
                try (Socket socket = servidor.accept()) {
                    System.out.println("Cliente conectado desde " + socket.getInetAddress());

                    // Inicializar las entradas y salidas
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                    // Preguntar el nombre del cliente
                    writer.println("¿Cuál es tu nombre?");
                    String nombreCliente = reader.readLine();  // Bloquea hasta recibir una respuesta
                    writer.println("¡Hola, " + nombreCliente + "! Bienvenido al generador de contraseñas.");

                    // Preguntar los requisitos para la contraseña
                    writer.println("¿Cuántas letras mayúsculas debe tener la contraseña?");
                    int numMayusculas = Integer.parseInt(reader.readLine());

                    writer.println("¿Cuántas letras minúsculas debe tener la contraseña?");
                    int numMinusculas = Integer.parseInt(reader.readLine());

                    writer.println("¿Cuántos dígitos debe tener la contraseña?");
                    int numDigitos = Integer.parseInt(reader.readLine());

                    writer.println("¿Cuántos caracteres especiales debe tener la contraseña?");
                    int numEspeciales = Integer.parseInt(reader.readLine());

                    int longitudContraseña = numMayusculas + numMinusculas + numDigitos + numEspeciales;
                    writer.println("La longitud de la contraseña generada será de " + longitudContraseña + " caracteres.");

                    // Preguntar si desea generar contraseñas
                    writer.println("¿Deseas generar una contraseña ahora? (si/no)");
                    String respuesta = reader.readLine().trim().toLowerCase();

                    if (respuesta.equals("si")) {
                        // Preguntar cuántas contraseñas quiere generar 
                        writer.println("¿Cuántas contraseñas deseas generar?");
                        int cantidadContraseñas = Integer.parseInt(reader.readLine()); // El cliente respondera

                        // Bucle para generar y mostrar las contraseñas
                        for (int i = 1; i <= cantidadContraseñas; i++) {
                            // Llamada a la clase para generar una contraseña
                            String contraseña = generarContraseña(numMayusculas, numMinusculas, numDigitos, numEspeciales);

                            // Mostrar la contraseña generada
                            writer.println("Aquí tienes tu contraseña número " + i + ": " + contraseña);
                        }
                    } else {
                        writer.println("No se generará ninguna contraseña.");
                    }

                    writer.println("Cliente " + nombreCliente + " se ha desconectado.");
                } catch (IOException | NumberFormatException ex) {
                    System.out.println("Error al comunicar con el cliente: " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println("Error en el servidor: " + ex.getMessage());
        }
    }


    public static String generarContraseña(int numMayusculas, int numMinusculas, int numDigitos, int numEspeciales) {
        String mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String minusculas = "abcdefghijklmnopqrstuvwxyz";
        String digitos = "0123456789";
        String especiales = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        StringBuilder contraseña = new StringBuilder();
        Random rand = new Random();

        // Añadir mayusculas
        for (int i = 0; i < numMayusculas; i++) {
            contraseña.append(mayusculas.charAt(rand.nextInt(mayusculas.length())));
        }

        // Añadir minusculas
        for (int i = 0; i < numMinusculas; i++) {
            contraseña.append(minusculas.charAt(rand.nextInt(minusculas.length())));
        }

        // Añadir digitos
        for (int i = 0; i < numDigitos; i++) {
            contraseña.append(digitos.charAt(rand.nextInt(digitos.length())));
        }

        // Añadir caracteres especiales
        for (int i = 0; i < numEspeciales; i++) {
            contraseña.append(especiales.charAt(rand.nextInt(especiales.length())));
        }

        // Si la contraseña tiene menos de la longitud total completamos con caracteres aleatorios
        String allChars = mayusculas + minusculas + digitos + especiales;
        while (contraseña.length() < numMayusculas + numMinusculas + numDigitos + numEspeciales) {
            contraseña.append(allChars.charAt(rand.nextInt(allChars.length())));
        }


        for (int i = 0; i < contraseña.length(); i++) {
            int j = rand.nextInt(contraseña.length());
            char temp = contraseña.charAt(i);
            contraseña.setCharAt(i, contraseña.charAt(j));
            contraseña.setCharAt(j, temp);
        }

        return contraseña.toString();
    }
}

