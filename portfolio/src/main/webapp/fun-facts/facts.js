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
google.charts.setOnLoadCallback(loadColors);

/** Creates a chart and adds it to the page. */
function loadColors() {
    fetch('/colors').then(response => response.json()).then((colorVotes) => {
        const data = google.visualization.arrayToDataTable([
         ['Color', 'Votes', { role: 'style' }],
         ['Red', colorVotes['red'], '#ff0000'],          
         ['Orange', colorVotes['orange'], '#ff7f00'],
         ['Yellow', colorVotes['yellow'], '#ffff00'],
         ['Green', colorVotes['green'], '#00ff00' ],
         ['Blue', colorVotes['blue'], '#0000ff'],
         ['Indigo', colorVotes['indigo'], '#4b0082'],
         ['Violet', colorVotes['violet'], '#9400d3']
      ]); 
      
      const view = new google.visualization.DataView(data);
      view.setColumns([0, 1,
                    { calc: "stringify",
                      sourceColumn: 1,
                      type: "string",
                      role: "annotation" },
                      2]);

        const options = {
            'title': 'Favorite Colors',
            'width': 600,
            'height': 500
        };

        const chart = new google.visualization.ColumnChart(
            document.getElementById('color-chart'));
        chart.draw(view, options);
    });
}