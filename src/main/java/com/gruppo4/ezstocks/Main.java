package com.gruppo4.ezstocks;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
    	
    	// Rest Call to GNET
    	JsonNode gnet = Rest.restGet("https://dwweb.gnet.it/dw2022/");
    	
    	String ticker = gnet.get("ticker").textValue();
    	LocalDate from = LocalDate.parse( 
    			gnet.get("starting_date").textValue(),
    			DateTimeFormatter.ofPattern( "dd/MM/uuuu" ) );
    	LocalDate to = LocalDate.parse(
    			gnet.get("ending_date").textValue(),
    			DateTimeFormatter.ofPattern( "dd/MM/uuuu" ));
    	
    	// Generate Polygon URL for Price Data
    	String priceDataURL = urlConstructor.generateUrl("v2", "aggs", ticker, from.toString(), to.toString());	
    	
    	// Rest Call to Polygon
    	JsonNode priceData = Rest.restGet(priceDataURL);
    	System.out.println(priceData.toString());
    	
    	
    	String html = ("<!DOCTYPE html>\r\n"
        		+ "<html lang=\"en\">\r\n"
        		+ "<head>\r\n"
        		+ "    <meta charset=\"UTF-8\">\r\n"
        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
        		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
        		+ "    <link rel=\"stylesheet\" href=\"style.css\">\r\n"
        		+ "    <title>ezStocks</title>\r\n"
        		+ "</head>\r\n"
        		+ "<body>\r\n"
        		+ "\r\n"
        		+ "    <!-- <div class=\"data\">\r\n"
        		+ "        <div>\r\n"
        		+ "            <h1>AAPL | Apple Inc.</h1>\r\n"
        		+ "            <p>Apple designs a wide variety of consumer electronic devices, including smartphones (iPhone), tablets (iPad), PCs (Mac), smartwatches (Apple Watch), AirPods, and TV boxes (Apple TV), among others. The iPhone makes up the majority of Apple's total revenue. In addition, Apple offers its customers a variety of services such as Apple Music, iCloud, Apple Care, Apple TV+, Apple Arcade, Apple Card, and Apple Pay, among others. Apple's products run internally developed software and semiconductors, and the firm is well known for its integration of hardware, software and services. Apple's products are distributed online as well as through company-owned stores and third-party retailers. The company generates roughly 40% of its revenue from the Americas, with the remainder earned internationally.</p>\r\n"
        		+ "        </div>\r\n"
        		+ "        <div>\r\n"
        		+ "            <h1>AAPL</h1>\r\n"
        		+ "            <p>Apple Inc.</p>\r\n"
        		+ "        </div>\r\n"
        		+ "    </div> -->\r\n"
        		+ "\r\n"
        		+ "    <!-- Grafico -->\r\n"
        		+ "    <div class=\"chart\" width=\"900vw\" height=\"500vh\">\r\n"
        		+ "        <canvas id=\"myChart\"></canvas>\r\n"
        		+ "        <div id=\"title\">\r\n"
        		+ "            <h1 class=\"title\">Loading the Ticker</h1>\r\n"
        		+ "            <p class=\"subtitle\">Please wait, we're working on your request..      <span style=\"font-family:Arial, FontAwesome\" class=\"icon\"><i class=\"fas fa-heart\"></i></span></p>\r\n"
        		+ "        </div>\r\n"
        		+ "    </div>\r\n"
        		+ "\r\n"
        		+ "    <!-- Input -->\r\n"
        		+ "    <!-- <form>\r\n"
        		+ "        <div class=\"in\">\r\n"
        		+ "            <label for=\"ticker\"><span style=\"font-family:Arial, FontAwesome\" class=\"icon\">&#xf069;</span>Ticker</label><br>\r\n"
        		+ "            <input type=\"text\" name=\"ticker\" id=\"ticker\" autocomplete=\"off\" value=\"AAPL\">\r\n"
        		+ "        </div>\r\n"
        		+ "        <div class=\"dates\">\r\n"
        		+ "            <div class=\"in\">\r\n"
        		+ "                <label for=\"from\"><span style=\"font-family:Arial, FontAwesome\" class=\"icon\">&#xf133;</span>From</label><br>\r\n"
        		+ "                <input type=\"date\" id=\"from\" name=\"from\" min=\"2019-01-01\" autocomplete=\"off\">\r\n"
        		+ "            </div>\r\n"
        		+ "            <div class=\"in\">\r\n"
        		+ "                <label for=\"to\"><span style=\"font-family:Arial, FontAwesome\" class=\"icon\">&#xf274;</span>To</label><br>\r\n"
        		+ "                <input type=\"date\" id=\"to\" name=\"to\" min=\"2019-01-01\" autocomplete=\"off\">\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "        <div class=\"d\">\r\n"
        		+ "            <div class=\"btn\">\r\n"
        		+ "                <input type=\"button\" name=\"submit\" id=\"btn\" value=\"Submit\">\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </form> -->\r\n"
        		+ "\r\n"
        		+ "\r\n"
        		+ "    <script src=\"https://cdn.jsdelivr.net/npm/luxon@2.3.0/build/global/luxon.min.js\"></script>\r\n"
        		+ "    <script src=\"https://cdn.jsdelivr.net/npm/chart.js@3.7.0/dist/chart.min.js\"></script>\r\n"
        		+ "    <script src=\"https://cdn.jsdelivr.net/npm/chartjs-adapter-luxon@1.1.0/dist/chartjs-adapter-luxon.min.js\"></script>\r\n"
        		+ "    <script src=\"https://cdn.jsdelivr.net/combine/npm/chartjs-plugin-zoom@1.2.0,npm/hammerjs@2.0.8\"></script>\r\n"
        		+ "    <script src=\"https://kit.fontawesome.com/0bd1bc4778.js\" crossorigin=\"anonymous\"></script>\r\n"
        		+ "    <script src=\"src/main.js\"></script>\r\n"
        		+ "</body>\r\n"
        		+ "</html>\r\n"
        		+ "");
        
        // HTML

        try {  
            File myObj = new File("web/index.html");  
            if (myObj.createNewFile()) {  
              System.out.println("File created: " + myObj.getName());  
            } else {  
              System.out.println("File already exists.");  
            }  
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();  
          }
        
        try {
            FileWriter myWriter = new FileWriter("web/index.html");
            myWriter.write(html);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
        // CSS
        
        try {  
            File myObj = new File("web/style.css");  
            if (myObj.createNewFile()) {  
              System.out.println("File created: " + myObj.getName());  
            } else {  
              System.out.println("File already exists.");  
            }  
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();  
          }
        
        try {
            FileWriter myWriter = new FileWriter("web/style.css");
            myWriter.write("@import url('https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');\r\n"
            		+ "\r\n"
            		+ "body {\r\n"
            		+ "    display: flex;\r\n"
            		+ "    align-items: center;\r\n"
            		+ "    justify-content: center;\r\n"
            		+ "    height: 100vh;\r\n"
            		+ "    background-color: #151520;\r\n"
            		+ "    font-family: 'Montserrat', sans-serif;\r\n"
            		+ "    overflow: hidden;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "input { \r\n"
            		+ "    color: #A2A7BC;\r\n"
            		+ "    font-size: 1.4vh;\r\n"
            		+ "    background-color: #181824;\r\n"
            		+ "    border: none;\r\n"
            		+ "    border-bottom: 1px solid #443D6B;\r\n"
            		+ "    margin-top: 0.5vh;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "label {\r\n"
            		+ "    color: #EEE7FF;\r\n"
            		+ "    border: none;\r\n"
            		+ "    font-size: 1.7vh;\r\n"
            		+ "    font-weight: 500;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "form {\r\n"
            		+ "    margin-left: 4vw;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".title {\r\n"
            		+ "    color: #A585FF;\r\n"
            		+ "    margin-bottom: 0;\r\n"
            		+ "    margin-left: 10vw;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".subtitle {\r\n"
            		+ "    margin-top: 0.3vh;\r\n"
            		+ "    margin-left: 10.1vw;\r\n"
            		+ "    color: #EEE7FF;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "#ticker{\r\n"
            		+ "    width: 15.5vw;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".in {\r\n"
            		+ "    background-color: #181824;\r\n"
            		+ "    padding: 0.7vw;\r\n"
            		+ "    margin: 1vh;\r\n"
            		+ "    border-radius: 0.7vh;\r\n"
            		+ "    width: 87%;\r\n"
            		+ "    height: 30%;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".icon {\r\n"
            		+ "    color: #A585FF;\r\n"
            		+ "    font-size: 1.2vh;\r\n"
            		+ "    margin-right: 0.4vw;\r\n"
            		+ "    display: inline-block;\r\n"
            		+ "    vertical-align: middle;\r\n"
            		+ "    line-height: normal;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".dates{\r\n"
            		+ "    display: flex;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".btn{\r\n"
            		+ "    background-color: #A585FF;\r\n"
            		+ "    border-radius: 20px;\r\n"
            		+ "    width: 10vh;\r\n"
            		+ "    height: 3vh;\r\n"
            		+ "    text-align: center;\r\n"
            		+ "    margin: 1vh;\r\n"
            		+ "    transition: all 0.2s ease-in-out;\r\n"
            		+ "}\r\n"
            		+ ".btn input{\r\n"
            		+ "    background-color: transparent;\r\n"
            		+ "    color: #EEE7FF;\r\n"
            		+ "    border: none;\r\n"
            		+ "    font-size: 1.4vh;\r\n"
            		+ "    font-family: 'Montserrat', sans-serif;\r\n"
            		+ "    font-weight: 600;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".btn:hover{\r\n"
            		+ "    box-shadow: 0 0px 15px rgba(165, 133, 255, .4);\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ ".d {\r\n"
            		+ "    display: flex;\r\n"
            		+ "    align-items: right;\r\n"
            		+ "    justify-content: right;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".chart {\r\n"
            		+ "    width: 45%;\r\n"
            		+ "    height: 50%;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "input[type=\"date\"]::-webkit-calendar-picker-indicator {\r\n"
            		+ "    background-image: url('data:image/svg+xml;utf8,<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"15\" viewBox=\"0 0 24 24\"><path fill=\"%23bbbbbb\" d=\"M20 3h-1V1h-2v2H7V1H5v2H4c-1.1 0-2 .9-2 2v16c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 18H4V8h16v13z\"/></svg>');\r\n"
            		+ "}\r\n"
            		+ "*:focus {\r\n"
            		+ "    outline: none;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".data{\r\n"
            		+ "    display: flex;\r\n"
            		+ "    position: absolute;\r\n"
            		+ "    margin-bottom: 80vh;\r\n"
            		+ "    /* margin-left: -60vw; */\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".data div {\r\n"
            		+ "    margin-right: 4vw;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".data h1 {\r\n"
            		+ "    color: #A585FF;\r\n"
            		+ "    margin-bottom: -1.7vh;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ ".data p {\r\n"
            		+ "    color: #EEE7FF;\r\n"
            		+ "    font-size: 1vh;\r\n"
            		+ "}\r\n"
            		+ "");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
        // Javascript
        
        try {  
            File myObj = new File("web/src/main.js");  
            if (myObj.createNewFile()) {  
              System.out.println("File created: " + myObj.getName());  
            } else {  
              System.out.println("File already exists.");  
            }  
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();  
          }
        
        try {
            FileWriter myWriter = new FileWriter("web/src/main.js");
            myWriter.write("/*\r\n"
            		+ "* SEZIONE DEDICATA A CHARTJS\r\n"
            		+ "*/\r\n"
            		+ "\r\n"
            		+ "const ctx = document.getElementById('myChart').getContext('2d');\r\n"
            		+ "const ord = 100; // Funzionale solo con piú datasets in un grafico\r\n"
            		+ "\r\n"
            		+ "// newChart genera un nuovo Grafico usando i dati nei parametri\r\n"
            		+ "function newChart(ticker, prices, dates){\r\n"
            		+ "    document.getElementById('title').style.display = 'none';\r\n"
            		+ "    const myChart = new Chart(ctx, {\r\n"
            		+ "        type: 'line',\r\n"
            		+ "        data: {\r\n"
            		+ "            labels: dates,\r\n"
            		+ "            datasets:\r\n"
            		+ "            [{\r\n"
            		+ "                label: [ticker],\r\n"
            		+ "                order: ord,\r\n"
            		+ "                radius: 0,\r\n"
            		+ "                fill: true,\r\n"
            		+ "                data: prices,\r\n"
            		+ "                color: [colors[0]],\r\n"
            		+ "                backgroundColor: [\r\n"
            		+ "                    gradients[0]\r\n"
            		+ "                ],\r\n"
            		+ "                borderColor: [ \r\n"
            		+ "                    'rgba(165, 133, 255, 1)'\r\n"
            		+ "                ],\r\n"
            		+ "                borderWidth: 2\r\n"
            		+ "            }]\r\n"
            		+ "        },\r\n"
            		+ "        options: {\r\n"
            		+ "            interaction: {\r\n"
            		+ "                mode: 'nearest',\r\n"
            		+ "                intersect: false,\r\n"
            		+ "                mode: 'index',\r\n"
            		+ "            },\r\n"
            		+ "            scales: {\r\n"
            		+ "                y: {\r\n"
            		+ "                    min: 0,\r\n"
            		+ "                    // stacked: true,\r\n"
            		+ "                    ticks: {\r\n"
            		+ "                        callback: function(value, index, ticks) {\r\n"
            		+ "                            return '$' + Math.round(value);\r\n"
            		+ "                        }\r\n"
            		+ "                    },\r\n"
            		+ "                    beginAtZero: true,\r\n"
            		+ "                    grid: {\r\n"
            		+ "                        color: '#181824',\r\n"
            		+ "                        display: true\r\n"
            		+ "                    }\r\n"
            		+ "                },\r\n"
            		+ "                x: {\r\n"
            		+ "                    // Combino chartjs con luxonjs\r\n"
            		+ "                    type: 'time',\r\n"
            		+ "                    time: {\r\n"
            		+ "                        unit: 'day',\r\n"
            		+ "                    },\r\n"
            		+ "                    grid: {\r\n"
            		+ "                        color: '#181824',\r\n"
            		+ "                        display: false\r\n"
            		+ "                    },\r\n"
            		+ "                }\r\n"
            		+ "            },\r\n"
            		+ "            plugins: {\r\n"
            		+ "                zoom: {\r\n"
            		+ "                    limits: {\r\n"
            		+ "                        y: {min: 0},\r\n"
            		+ "                    },\r\n"
            		+ "                    zoom: {\r\n"
            		+ "                        \r\n"
            		+ "                        wheel: {\r\n"
            		+ "                            enabled: true,\r\n"
            		+ "                        },\r\n"
            		+ "                        mode: 'y',\r\n"
            		+ "                    \r\n"
            		+ "                    }\r\n"
            		+ "                },\r\n"
            		+ "            },\r\n"
            		+ "        }\r\n"
            		+ "    });\r\n"
            		+ "    document.getElementById('from').disabled = true;\r\n"
            		+ "    document.getElementById('to').disabled = true;\r\n"
            		+ "    return myChart;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "// Aggiunge dati ad un grafico giá esistente\r\n"
            		+ "\r\n"
            		+ "// function addData(chart, label, data/*,n*/) {\r\n"
            		+ "//   const newDataset = {\r\n"
            		+ "//       label: label,\r\n"
            		+ "//       data: data,\r\n"
            		+ "//       color: colors[0],\r\n"
            		+ "//       fill: true,\r\n"
            		+ "//       backgroundColor: [gradients[0]],\r\n"
            		+ "//       borderColor: colors[0],\r\n"
            		+ "//       radius: 0,\r\n"
            		+ "//       borderWidth: 2,\r\n"
            		+ "//       //order: ord-n,\r\n"
            		+ "//   }\r\n"
            		+ "//   chart.data.datasets.push(newDataset);\r\n"
            		+ "//   chart.update();\r\n"
            		+ "// }\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "// Pura estetica (In caso si voglia vedere piú di un grafico)\r\n"
            		+ "\r\n"
            		+ "var gradient = ctx.createLinearGradient(0, 0, 0, 600);\r\n"
            		+ "gradient.addColorStop(0, 'rgba(165, 133, 255,0)');\r\n"
            		+ "gradient.addColorStop(0.5, 'rgba(165, 133, 255,0.1)');\r\n"
            		+ "gradient.addColorStop(1, 'rgba(165, 133, 255,0.8)');\r\n"
            		+ "\r\n"
            		+ "var gradient2 = ctx.createLinearGradient(0, 0, 0, 600);\r\n"
            		+ "gradient2.addColorStop(0, 'rgba(238,231,255,0)');\r\n"
            		+ "gradient2.addColorStop(0.5, 'rgba(238,231,255,0.1)');\r\n"
            		+ "gradient2.addColorStop(1, 'rgba(238,231,255,0.8)');\r\n"
            		+ "\r\n"
            		+ "var gradient3 = ctx.createLinearGradient(0, 0, 0, 600);\r\n"
            		+ "gradient3.addColorStop(0, 'rgba(0, 255, 255 ,0)');\r\n"
            		+ "gradient3.addColorStop(0.5, 'rgba(0, 255, 255 ,0.1)');\r\n"
            		+ "gradient3.addColorStop(1, 'rgba(0, 255, 255 ,0.8)');\r\n"
            		+ "\r\n"
            		+ "var gradient4 = ctx.createLinearGradient(0, 0, 0, 600);\r\n"
            		+ "gradient4.addColorStop(0, 'rgba(99, 253, 110,0)');\r\n"
            		+ "gradient4.addColorStop(0.5, 'rgba(99, 253, 110 ,0.1)');\r\n"
            		+ "gradient4.addColorStop(1, 'rgba(99, 253, 110 ,0.8)');\r\n"
            		+ "\r\n"
            		+ "var gradients = [gradient, gradient2, gradient3, gradient4];\r\n"
            		+ "\r\n"
            		+ "var colors = ['rgba(165, 133, 255,1)', 'rgba(238,231,255, 1)', 'rgba(0, 255, 255 ,1)', 'rgba(99, 253, 110 ,1)'];\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "/*\r\n"
            		+ "* SEZIONE DEDICATA A POLYGON\r\n"
            		+ "*/\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "// Valori immutabili\r\n"
            		+ "const key = '3EeuHppKTp5zgsIDr1KI2BtsfdveDj7G';\r\n"
            		+ "const base = 'https://api.polygon.io/v2/aggs/ticker/';\r\n"
            		+ "\r\n"
            		+ "var a = 0; // Contatore\r\n"
            		+ "var c; // Grafico\r\n"
            		+ "var DateTime = luxon.DateTime;\r\n"
            		+ "\r\n"
            		+ "var date = [];\r\n"
            		+ "var price = [];\r\n"
            		+ "\r\n"
            		+ "// Genera l'url collegato all'API di Polygon.io utilizzando i parametri\r\n"
            		+ "function generateURL(ticker, multipler, timespan, from, to){\r\n"
            		+ "    const generated = '' + base + ticker + '/range/' + multipler + '/' + timespan + '/' + from + '/' + to + '?adjusted=true&sort=desc&limit=800&apiKey=' + key;\r\n"
            		+ "    return generated;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "// Duplica i prezzi presenti nell'array di oggetti data[]\r\n"
            		+ "// in un nuovo array price[]\r\n"
            		+ "function prices(results){\r\n"
            		+ "    results.forEach(element => price.push(element.c));\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "// Partendo dalla prima data, per ogni elemento nell'array aumenta di un giorno\r\n"
            		+ "// e salva tutte le date in nuovo array (date[])\r\n"
            		+ "function dates(from, results){\r\n"
            		+ "    var dt = DateTime.fromISO(from);\r\n"
            		+ "    for(var i = 0; i < results.length; i++){\r\n"
            		+ "        date[i] = dt;\r\n"
            		+ "        dt = dt.plus({ hours: 24 });\r\n"
            		+ "    }\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "// Tramite il link generato, contatta api.polygon.io e ritorna i dati richiesti\r\n"
            		+ "async function callAPI(url, from){\r\n"
            		+ "    const response = await fetch(url);\r\n"
            		+ "    const data = await response.json();\r\n"
            		+ "    const { ticker, results } = data;\r\n"
            		+ "\r\n"
            		+ "    price = [];\r\n"
            		+ "    date = [];\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "    prices(results);\r\n"
            		+ "    dates(from, results)\r\n"
            		+ "\r\n"
            		+ "    if(a > 0){\r\n"
            		+ "        // Ripristino del Grafico\r\n"
            		+ "        //removeData(c, ticker, price, a);\r\n"
            		+ "        // Aggiungo i nuovi dati\r\n"
            		+ "        //addData(c, ticker, price, a);\r\n"
            		+ "    } else {\r\n"
            		+ "        // Nuovo Grafico\r\n"
            		+ "        c = newChart(ticker, price, date);\r\n"
            		+ "        a++;\r\n"
            		+ "    }\r\n"
            		+ "    return data;\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "// Funzione al momento inutile\r\n"
            		+ "function final(ticker, from, to){\r\n"
            		+ "  callAPI(generateURL(ticker, '1', 'day', from, to), from);\r\n"
            		+ "}\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "final("+gnet.get("ticker")+", DateTime.fromFormat("+ gnet.get("starting_date") + ", \"dd/MM/yyyy\").toISODate(), DateTime.fromFormat(" + gnet.get("ending_date") + ", \"dd/MM/yyyy\").toISODate());");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}










































