// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

/** Creates a chart and adds it to the page. */
function drawChart() {
    const data = google.visualization.arrayToDataTable([
         ['Color', 'Votes', { role: 'style' }],
         ['Red', 1, '#ff0000'],          
         ['Orange', 1, '#ff7f00'],
         ['Yellow', 1, '#ffff00'],
         ['Green', 1, '#00ff00' ],
         ['Blue', 1, '#0000ff'],
         ['Indigo', 1, '#4b0082'],
         ['Violet', 1, '#9400d3']
      ]);

    const view = new google.visualization.DataView(data);
    view.setColumns([0, 1,
                    { calc: "stringify",
                      sourceColumn: 1,
                      type: "string",
                      role: "annotation" },
                      2]);

    const options = {
     'title': 'Your Favorite Colors',
     'width':500,
     'height':400,
     bar: {groupWidth: "90%"},
     legend: { position: "none" },
    };

    const chart = new google.visualization.BarChart(document.getElementById("color-chart"));
    chart.draw(view, options);
}