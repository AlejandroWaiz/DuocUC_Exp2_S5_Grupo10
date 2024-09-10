/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.teatromoro;

/**
 *
 * @author Ignacio Toro
 */

import java.util.ArrayList;
import java.util.Scanner;

class Entrada {
    // Variables de instancia
    int numero;
    String ubicacion;
    double precio;
    String tipo;
    double descuento;
    double precioFinal;

    // Constructor
    public Entrada(int numero, String ubicacion, double precio, String tipo, double descuento) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.tipo = tipo;
        this.descuento = descuento;
        calcularPrecioFinal();
    }

    // Método para calcular el precio final aplicando el descuento
    private void calcularPrecioFinal() {
        this.precioFinal = precio - (precio * descuento / 100);
    }

    // Método para mostrar la información de la entrada
    public void mostrarInformacion() {
        System.out.println("Número: " + numero);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Precio: $" + precio);
        System.out.println("Tipo: " + tipo);
        System.out.println("Descuento: " + descuento + "%");
        System.out.println("Precio Final: $" + precioFinal);
    }
}

public class TeatroMoro {

    static ArrayList<Entrada> entradas = new ArrayList<>();
    static boolean[] asientosOcupados = new boolean[60];
    private static double totalConDescuento;
    
    

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("");
            System.out.println("\nMenú:");
            System.out.println("1. Venta de entradas.");
            System.out.println("2. Promociones.");
            System.out.println("3. Búsqueda de entradas.");
            System.out.println("4. Eliminación de entradas.");
            System.out.println("5. Salir.");
            System.out.println("");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ventaDeEntradas(scanner);
                    break;
                case 2:
                    promociones(scanner);
                    break;
                case 3:
                    busquedaDeEntradas(scanner);
                    break;
                case 4:
                    eliminacionDeEntradas(scanner);
                    break;
                case 5:
                    mostrarPrecioTotal();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        scanner.close();
    }

    public static void ventaDeEntradas(Scanner scanner) {
        System.out.println("");
        System.out.println("Elige una ubicación:");
        System.out.println("1. VIP (1-10) - Precio: $100");
        System.out.println("2. Platea (11-30) - Precio: $50");
        System.out.println("3. General (31-60) - Precio: $30");
        System.out.println("4. Volver al menú principal");
        System.out.println("");

        int opcionUbicacion = scanner.nextInt();
        if (opcionUbicacion == 4) return;

        String ubicacion = "";
        double precio = 0;
        int inicio = 0, fin = 0;

        switch (opcionUbicacion) {
            case 1:
                ubicacion = "VIP";
                precio = 100;
                inicio = 1;
                fin = 10;
                break;
            case 2:
                ubicacion = "Platea";
                precio = 50;
                inicio = 11;
                fin = 30;
                break;
            case 3:
                ubicacion = "General";
                precio = 30;
                inicio = 31;
                fin = 60;
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        // Mostrar los asientos disponibles en la ubicación seleccionada
        System.out.println("");
        System.out.println("Selecciona un número de asiento entre " + inicio + " y " + fin + ": ");
        for (int i = inicio; i <= fin; i++) {
            if (!asientosOcupados[i - 1]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        int numeroAsiento = scanner.nextInt();
        if (numeroAsiento < inicio || numeroAsiento > fin || asientosOcupados[numeroAsiento - 1]) {
            System.out.println("Número de asiento no válido o ya está ocupado.");
            return;
        }

        // Solicitar el tipo de entrada
        System.out.println("");
        System.out.println("Selecciona el tipo de entrada:");
        System.out.println("1. Público General (sin descuento)");
        System.out.println("2. Estudiante (10% descuento)");
        System.out.println("3. Tercera Edad (15% descuento)");
        System.out.println("4. Volver al menú principal");
        System.out.println("");

        int opcionTipo = scanner.nextInt();
        if (opcionTipo == 4) return;

        String tipo = "";
        double descuento = 0;

        switch (opcionTipo) {
            case 1:
                tipo = "Público General";
                descuento = 0;
                break;
            case 2:
                tipo = "Estudiante";
                descuento = 10;
                break;
            case 3:
                tipo = "Tercera Edad";
                descuento = 15;
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        asientosOcupados[numeroAsiento - 1] = true;
        Entrada entrada = new Entrada(numeroAsiento, ubicacion, precio, tipo, descuento);
        entradas.add(entrada);
        entrada.mostrarInformacion();
    }

    // Función para la opción 2 "Promociones"
    public static void promociones(Scanner scanner) {
        if (entradas.size() >= 3) {
            System.out.println("");
            System.out.println("Has comprado 3 o más entradas, tienes un 10% de descuento adicional.");
            double totalSinDescuento = 0;
            for (Entrada entrada : entradas) {
                totalSinDescuento += entrada.precioFinal;
            }
            double descuentoAdicional = totalSinDescuento * 0.10;
            totalConDescuento = totalSinDescuento - descuentoAdicional;
            System.out.println("");
            System.out.println("Total sin descuento: $" + totalSinDescuento);
            System.out.println("Descuento adicional: -$" + descuentoAdicional);
            System.out.println("Total a pagar: $" + totalConDescuento);
        } else {
            System.out.println("");
            System.out.println("No tienes suficientes entradas para aplicar la promoción.");
        }
    }

    // Función para la opción 3 "Búsqueda de entradas"
    public static void busquedaDeEntradas(Scanner scanner) {
        System.out.println("");
        System.out.println("Asientos vendidos:");
        for (Entrada entrada : entradas) {
            System.out.print(entrada.numero + " ");
        }
        System.out.println();
        System.out.println("");
        System.out.print("Introduce el número de asiento para buscar: ");
        int numeroAsiento = scanner.nextInt();

        if (numeroAsiento < 1 || numeroAsiento > 60) {
            System.out.println("");
            System.out.println("Número de asiento no válido.");
            return;
        }

        boolean encontrado = false;
        for (Entrada entrada : entradas) {
            if (entrada.numero == numeroAsiento) {
                entrada.mostrarInformacion();
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("El asiento " + numeroAsiento + " está disponible.");
        }
    }

    // Función para la opción 4 "Eliminación de entradas"
    public static void eliminacionDeEntradas(Scanner scanner) {
        System.out.println("");
        System.out.println("Asientos vendidos:");
        for (Entrada entrada : entradas) {
            System.out.print(entrada.numero + " ");
        }
        System.out.println();
        System.out.println("");
        System.out.print("Introduce el número de asiento para eliminar la entrada: ");
        int numeroAsiento = scanner.nextInt();

        if (numeroAsiento < 1 || numeroAsiento > 60) {
            System.out.println("");
            System.out.println("Número de asiento no válido.");
            return;
        }

        boolean eliminado = false;
        for (int i = 0; i < entradas.size(); i++) {
            if (entradas.get(i).numero == numeroAsiento) {
                entradas.remove(i);
                asientosOcupados[numeroAsiento - 1] = false;
                System.out.println("");
                System.out.println("Entrada eliminada correctamente.");
                eliminado = true;
                break;
            }
        }
        if (!eliminado) {
            System.out.println("");
            System.out.println("No hay ninguna entrada registrada en ese asiento.");
        }
    }
    
    //Función para la opción 5 y mostrar el precio total de las entradas compradas
    public static void mostrarPrecioTotal(){
        double precio_total  = 0;
        
        if (totalConDescuento > 0) {
            System.out.println("\nPrecio total: $" + totalConDescuento);
            System.out.println("Que tenga un buen dia!");
        } else {
            for(Entrada entrada: entradas) {
            precio_total = precio_total + entrada.precioFinal;
            }
        
        System.out.println("\nPrecio total: $" + precio_total);
        System.out.println("Que tenga un buen dia!");
        } 
    }
}








