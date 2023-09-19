package co.edu.escuelaing.arep;

import static spark.Spark.port;
import static spark.Spark.get;
import static java.lang.Math.*;

public class SparkWebServer {
    public static void main(String... args){
        port(getPort());
        get("hello", (req,res) -> "Hello Docker!");
        get("/sin/:valor", (req, res) -> {
           double valor = Double.parseDouble(req.params(":valor"));
           double solucion = Math.sin(valor);
           return "{\"solucion\": " + solucion + "}";
        });

        get("/cos/:valor", (req, res) -> {
            double valor = Double.parseDouble(req.params(":valor"));
            double solucion = Math.cos(valor);
            return "{\"solucion\": " + solucion + "}";
        });

        get("/pal/:valor", (req, res) -> {
            String valor = req.params(":valor");
            return "{\"solucion\": " + esPalindromo(valor) + "}";
        });

        get("/mag/:valorX/:valorY", (req, res) -> {
            double valorX = Double.parseDouble(req.params(":valorX"));
            double valorY = Double.parseDouble(req.params(":valorY"));
            return "{\"solucion\": " + vectorMagnitude(valorX, valorY) + "}";
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }

        return 4567;
    }

    public static boolean esPalindromo(String palabra) {
        int i = 0;
        int j = palabra.length() - 1;
        while (i <= j) {
            if (palabra.charAt(i) != palabra.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static double vectorMagnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

}

