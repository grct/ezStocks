/*
* SEZIONE DEDICATA A CHARTJS
*/

const ctx = document.getElementById('myChart').getContext('2d');
const ord = 100; // Funzionale solo con piú datasets in un grafico

// newChart genera un nuovo Grafico usando i dati nei parametri
function newChart(ticker, prices, dates){
    document.getElementById('title').style.display = 'none';
    const myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: dates,
            datasets:
            [{
                label: [ticker],
                order: ord,
                radius: 0,
                fill: true,
                data: prices,
                color: [colors[0]],
                backgroundColor: [
                    gradients[0]
                ],
                borderColor: [ 
                    'rgba(165, 133, 255, 1)'
                ],
                borderWidth: 2
            }]
        },
        options: {
            interaction: {
                mode: 'nearest',
                intersect: false,
                mode: 'index',
            },
            scales: {
                y: {
                    min: 0,
                    // stacked: true,
                    ticks: {
                        callback: function(value, index, ticks) {
                            return '$' + Math.round(value);
                        }
                    },
                    beginAtZero: true,
                    grid: {
                        color: '#181824',
                        display: true
                    }
                },
                x: {
                    // Combino chartjs con luxonjs
                    type: 'time',
                    time: {
                        unit: 'day',
                    },
                    grid: {
                        color: '#181824',
                        display: false
                    },
                }
            },
            plugins: {
                zoom: {
                    limits: {
                        y: {min: 0},
                    },
                    zoom: {
                        
                        wheel: {
                            enabled: true,
                        },
                        mode: 'y',
                    
                    }
                },
            },
        }
    });
    document.getElementById('from').disabled = true;
    document.getElementById('to').disabled = true;
    return myChart;
}

// Aggiunge dati ad un grafico giá esistente

// function addData(chart, label, data/*,n*/) {
//   const newDataset = {
//       label: label,
//       data: data,
//       color: colors[0],
//       fill: true,
//       backgroundColor: [gradients[0]],
//       borderColor: colors[0],
//       radius: 0,
//       borderWidth: 2,
//       //order: ord-n,
//   }
//   chart.data.datasets.push(newDataset);
//   chart.update();
// }





// Pura estetica (In caso si voglia vedere piú di un grafico)

var gradient = ctx.createLinearGradient(0, 0, 0, 600);
gradient.addColorStop(0, 'rgba(165, 133, 255,0)');
gradient.addColorStop(0.5, 'rgba(165, 133, 255,0.1)');
gradient.addColorStop(1, 'rgba(165, 133, 255,0.8)');

var gradient2 = ctx.createLinearGradient(0, 0, 0, 600);
gradient2.addColorStop(0, 'rgba(238,231,255,0)');
gradient2.addColorStop(0.5, 'rgba(238,231,255,0.1)');
gradient2.addColorStop(1, 'rgba(238,231,255,0.8)');

var gradient3 = ctx.createLinearGradient(0, 0, 0, 600);
gradient3.addColorStop(0, 'rgba(0, 255, 255 ,0)');
gradient3.addColorStop(0.5, 'rgba(0, 255, 255 ,0.1)');
gradient3.addColorStop(1, 'rgba(0, 255, 255 ,0.8)');

var gradient4 = ctx.createLinearGradient(0, 0, 0, 600);
gradient4.addColorStop(0, 'rgba(99, 253, 110,0)');
gradient4.addColorStop(0.5, 'rgba(99, 253, 110 ,0.1)');
gradient4.addColorStop(1, 'rgba(99, 253, 110 ,0.8)');

var gradients = [gradient, gradient2, gradient3, gradient4];

var colors = ['rgba(165, 133, 255,1)', 'rgba(238,231,255, 1)', 'rgba(0, 255, 255 ,1)', 'rgba(99, 253, 110 ,1)'];




/*
* SEZIONE DEDICATA A POLYGON
*/


// Valori immutabili
const key = '3EeuHppKTp5zgsIDr1KI2BtsfdveDj7G';
const base = 'https://api.polygon.io/v2/aggs/ticker/';

var a = 0; // Contatore
var c; // Grafico
var DateTime = luxon.DateTime;

var date = [];
var price = [];

// Genera l'url collegato all'API di Polygon.io utilizzando i parametri
function generateURL(ticker, multipler, timespan, from, to){
    const generated = '' + base + ticker + '/range/' + multipler + '/' + timespan + '/' + from + '/' + to + '?adjusted=true&sort=desc&limit=800&apiKey=' + key;
    return generated;
}

// Duplica i prezzi presenti nell'array di oggetti data[]
// in un nuovo array price[]
function prices(results){
    results.forEach(element => price.push(element.c));
}

// Partendo dalla prima data, per ogni elemento nell'array aumenta di un giorno
// e salva tutte le date in nuovo array (date[])
function dates(from, results){
    var dt = DateTime.fromISO(from);
    for(var i = 0; i < results.length; i++){
        date[i] = dt;
        dt = dt.plus({ hours: 24 });
    }
}

// Tramite il link generato, contatta api.polygon.io e ritorna i dati richiesti
async function callAPI(url, from){
    const response = await fetch(url);
    const data = await response.json();
    const { ticker, results } = data;

    price = [];
    date = [];


    prices(results);
    dates(from, results)

    if(a > 0){
        // Ripristino del Grafico
        //removeData(c, ticker, price, a);
        // Aggiungo i nuovi dati
        //addData(c, ticker, price, a);
    } else {
        // Nuovo Grafico
        c = newChart(ticker, price, date);
        a++;
    }
    return data;
}

// Funzione al momento inutile
function final(ticker, from, to){
  callAPI(generateURL(ticker, '1', 'day', from, to), from);
}


final("AAPL", DateTime.fromFormat("01/12/2021", "dd/MM/yyyy").toISODate(), DateTime.fromFormat("31/12/2021", "dd/MM/yyyy").toISODate());