package co.edu.escuelaing.arep;

import static spark.Spark.*;

public class SparkWebServer {
    public static void main(String... args) {
        port(getPort());

        get("/funciones", (req, res) -> {
            return renderFormulario();
        });

        post("/calcular", (req, res) -> {
            String funcion = req.queryParams("funcion");
            String valorStr = req.queryParams("valor");


            String resultado = "";
            if ("sin".equals(funcion)) {
                resultado = "Solución: " + Math.sin(Double.parseDouble(valorStr));
            } else if ("cos".equals(funcion)) {
                resultado = "Solución: " + Math.cos(Double.parseDouble(valorStr));
            } else if ("pal".equals(funcion)) {
                resultado = "Solución: " + esPalindromo(valorStr);
            } else if ("mag".equals(funcion)) {
                String valorYStr = req.queryParams("valorY");
                resultado = "Solución: " + vectorMagnitude(Double.parseDouble(valorStr), Double.parseDouble(valorYStr));
            }

            return resultado;
        });
    }

    private static String renderFormulario() {
        return "<html>" +
                "<head>" +
                "<title>Spark Formulario</title>" +
                "</head>" +
                "<body>" +
                "<h1>Funciones</h1>" +
                "<form action=\"/calcular\" method=\"post\">" +
                "<label for=\"funcion\">Funcionalidad: </label>" +
                "<select name=\"funcion\" id=\"funcion\">" +
                "<option value=\"sin\">Sin</option>" +
                "<option value=\"cos\">Cos</option>" +
                "<option value=\"pal\">Palíndromo</option>" +
                "<option value=\"mag\">Magnitud</option>" +
                "</select>" +
                "<br>" +
                "<br>" +
                "<div id=\"valores\">" +
                "<label for=\"valor\">Valor: </label>" +
                "<input type=\"text\" name=\"valor\" id=\"valor\">" +
                "</div>" +
                "<br>" +
                "<input type=\"submit\" value=\"Calcular\">" +
                "</form>" +
                "<script>" +
                "function mostrarCampos(funcionSeleccionada) {" +
                "   var valoresDiv = document.getElementById(\"valores\");" +
                "   valoresDiv.innerHTML = \"\";" +
                "   var labelValor = document.createElement(\"label\");" +
                "   labelValor.setAttribute(\"for\", \"valor\");" +
                "   labelValor.textContent = \"Valor: \";" +
                "   valoresDiv.appendChild(labelValor);" +
                "   var inputValor = document.createElement(\"input\");" +
                "   inputValor.type = \"text\";" +
                "   inputValor.name = \"valor\";" +
                "   inputValor.id = \"valor\";" +
                "   valoresDiv.appendChild(inputValor);" +
                "   if (funcionSeleccionada === \"mag\") {" +
                "       valor.placeholder = \"Valor X\";" +
                "       var inputX = document.createElement(\"input\");" +
                "       inputX.type = \"text\";" +
                "       inputX.name = \"valorY\";" +
                "       inputX.placeholder = \"Valor Y\";" +
                "       valoresDiv.appendChild(inputX);" +
                "   }" +
                "}" +
                "var funcionSeleccionada = document.getElementById(\"funcion\");" +
                "funcionSeleccionada.addEventListener(\"change\", function() {" +
                "   mostrarCampos(this.value);" +
                "});" +
                "</script>" +
                "</body>" +
                "</html>";
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }

        return 4567;
    }

    public static String esPalindromo(String palabra) {
        int i = 0;
        int j = palabra.length() - 1;
        while (i <= j && i < palabra.length() && j >= 0) {
            if (palabra.toLowerCase().charAt(i) != palabra.toLowerCase().charAt(j)) {
                return "La palabra " + palabra + " NO es palíndromo";
            }
            i++;
            j--;
        }
        return "La palabra " + palabra + " SÍ es palíndromo";
    }

    public static double vectorMagnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
}
