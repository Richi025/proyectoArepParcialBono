package edu.escuelaing.arep;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;


public class ServerCalFacade {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean run = true;
        while (run){
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
                if (isfirsline){
                    firstline = inputLine;
                    isfirsline = false;
                }
                if (!in.ready()) {
                    break;
                }
            }

            URI requestURL = getRequestURL(firstline);
            if (requestURL.getPath().startsWith("/computar")){
                
                String backendResponse = HttpConnectionExample.getResponse("/compreflex?" + requestURL.getQuery());
                outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + backendResponse;
                
            }else{
                outputLine = gethtmlClient();
            }
                        
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        
        serverSocket.close();
        
        
    }

    private static URI getRequestURL(String firstline) throws URISyntaxException, MalformedURLException {
        String urlR = firstline.split(" ")[1];
        return new URI(urlR);
        
        
    }

    public static String gethtmlClient(){
        String htmlCode = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            + "<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "<head>\n"
                            + "<meta charset=\"UTF-8\">\n"
                            + "<title>Reflexive Calculator</title>\n"
                            + "</head>\n"
                            + "<body>\n"
                            + "<h1>Reflexive Calculator</h1>\n"
                            + "<form>\n"
                            + "<label for=\"command\">Command with parameters:</label><br>\n"
                            + "<label for=\"command\">Example: sin(4), bbl(2,4,5,1,6)</label><br>\n"
                            + "<input type=\"text\" id=\"command\" name=\"command\" placeholder=\"insertOperation(param1, param2, ..)\"><br><br>\n"
                            + "<input type=\"button\" value=\"Calculate\" onclick=\"sendRequest()\">\n"
                            + "</form>\n"
                            + "<div id=\"response\"></div>\n"
                            + "<script>\n"
                            + "function sendRequest() {\n"
                            + "let command = document.getElementById(\"command\").value;\n"
                            + "const xhttp = new XMLHttpRequest();\n"
                            + "xhttp.onload = function() {\n"
                            + "document.getElementById(\"response\").innerHTML = this.responseText;\n"
                            + "}\n"
                            + "xhttp.open(\"GET\", \"/computar?command=\" + command);\n"
                            + "xhttp.send();\n"
                            + "}\n"
                            + "</script>\n"
                            + "</body>\n"
                            + "</html>";
                            
        return htmlCode;
        
    }
}
