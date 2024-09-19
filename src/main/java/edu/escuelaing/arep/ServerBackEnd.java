package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ServerBackEnd {
    public static void main(String[] args) throws IOException, URISyntaxException, NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }

        boolean run = true;
        while (run) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean isfirsline = true;
            String firstline = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if (isfirsline) {
                    firstline = inputLine;
                    isfirsline = false;
                }
                if (!in.ready()) {
                    break;
                }
            }

            URI requestURL = getRequestURL(firstline);
            if (requestURL.getPath().startsWith("/compreflex")) {
                String operation = requestURL.getQuery().split("=")[1].split(" ")[0];
                String command = removeParams(operation);
                List<Double> params = getParams(operation);
                String response = invoqueReflexion(command, params);
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + "{\"response\":" + response + "}\r\n";
            } else {
                outputLine = getDefaultClient();
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }

        serverSocket.close();

    }

    private static String getDefaultClient() {
        String responce = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Request Default</h1>\n"
                + "</body>\n"
                + "</html>\n";
        return responce;
    }

    private static URI getRequestURL(String firstline) throws URISyntaxException, MalformedURLException {
        String urlR = firstline.split(" ")[1];
        return new URI(urlR);

    }

    private static String removeParams(String input) {
        return input.substring(0, input.indexOf('('));
    }

    private static List<Double> getParams(String input) {
        List<Double> params = new ArrayList<>();
        String paramStr = input.substring(input.indexOf('(') + 1, input.indexOf(')'));
        if (!paramStr.isEmpty()) {
            for (String param : paramStr.split(",")) {
                params.add(Double.parseDouble(param.trim()));
            }
        }
        return params;
    }

    private static String invoqueReflexion(String operation, List<Double> params) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if(operation == "bbl"){
            List<Double> response = bubbleSort(params);
            return response.toString();
        }else{
            Class[] parametClasses = {double.class};
            Class<?> mclass = Math.class;
            Method method = mclass.getMethod( operation, parametClasses);
            Double reponse = (Double) method.invoke(null, params.get(0));
            return reponse.toString();
        }  
    }

    public static List<Double> bubbleSort(List<Double> list) {
        int n = list.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j) > list.get(j + 1)) {

                    double temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
        return list;
    }

}
