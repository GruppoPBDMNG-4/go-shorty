'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  
  .controller('AboutCtrl', function ($scope, $http, $routeParams) {
    
  	$scope.shortUrl = "";

    
  	$scope.getStats = function(){
      var shorty = $scope.shortUrl;
      shorty = shorty.replace("http://" + location.host, "");

  		var res = $http.get("/rest/stats/" + shorty);

  		res.success(function(data) {
    		console.log('lets stats!');
    		$scope.rispostaJson = data;
        //if ($scope.rispostaJson.numClicks !== 0) {
          populateBrowserChart(data.browserStats);
          populateCountryChart(data.countryStats);
          populateDateChart(data.dateStats);
        //}
        $scope.err = "";

  		});

      res.error(function(data) {
        $scope.rispostaJson = data || "request failed";
        $scope.err = $scope.rispostaJson.err ;
        $scope.shortUrl = "";
      });

  	};

    if($routeParams.shorty){
      $scope.shortUrl = $routeParams.shorty;
      $scope.getStats();
    }

    var browserColor = {chrome:'#00796B', firefox:'#FDB45C', safari:'#00BCD4', explorer:'#01579B', opera:'#F7464A', other:'#607D8B'};
    var countryColor = {IT:'#8BC34A', DE:'#37474F', US:'#B71C1C', JP:'#F44336', FR:'#3F51B5', GB:'#0D47A1', IN:'#FF9800', ES:'#FFEB3B', MX:'#4CAF50'};
    
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var pieData = [];
    var pieOptions;
    var bStats;
    var myPieChart;
    
    // must ... not ... manipulate ... the DOM ... NEVER MIND
    function populateBrowserChart(stats){
      pieData = [];
      var canvas = document.createElement('canvas');
      canvas.width = '200';
      canvas.height = '200';
      canvas.setAttribute('id', 'bStats');

      var container = document.getElementById("browser-chart-container");
      container.removeChild(container.firstChild);
      container.appendChild(canvas);

      var descContainer = document.getElementById("browser-desc-container");
      clearContainer(descContainer);

      for (var key in stats) {
        var singleStat = {
          value: stats[key],
          color: browserColor[key],
          highlight: "#FF5A5E",
          label: key
        };
        pieData.push(singleStat);

        var keyLabel = document.createElement('span');
        var t = document.createTextNode(key);
        keyLabel.appendChild(t);
        keyLabel.setAttribute('class', 'label label-default');
        keyLabel.setAttribute('style', 'padding: 5px; margin:2px; background-color:' + browserColor[key]);
        
        descContainer.appendChild(keyLabel);
      }

      pieOptions = {
        segmentShowStroke : true,
        segmentStrokeColor : "#f5f5f5",
        animateScale : true
      };
      bStats = document.getElementById("bStats").getContext("2d");
      myPieChart = new Chart(bStats).Pie(pieData, pieOptions);

    }

    var pieCountryData = [];
    var pieCountryOptions;
    var countryStats;
    var myPieCountryChart;

    function populateCountryChart(stats){

      pieCountryData = [];
      var countryCanvas = document.createElement('canvas');
      countryCanvas.width = '200';
      countryCanvas.height = '200';
      countryCanvas.setAttribute('id', 'cStats');


      var container = document.getElementById("country-chart-container");
      container.removeChild(container.firstChild);
      container.appendChild(countryCanvas);

      var descContainer = document.getElementById("country-desc-container");
      clearContainer(descContainer);

      for (var key in stats) {
        // random color
        var currentColor = '#' + Math.floor(Math.random()*16777215).toString(16);
        if(countryColor.hasOwnProperty(key)){
          currentColor = countryColor[key];
        }

        var singleStat = {
          value: stats[key],
          color: currentColor,
          highlight: "#FF5A5E",
          label: key
        };
        pieCountryData.push(singleStat);

        var keyLabel = document.createElement('span');
        var t = document.createTextNode(key);
        keyLabel.appendChild(t);
        keyLabel.setAttribute('class', 'label');
        keyLabel.setAttribute('style', 'padding: 5px; margin:2px; background-color:' + currentColor);
        
        descContainer.appendChild(keyLabel);
      }

      pieCountryOptions = {
        segmentShowStroke : true,
        segmentStrokeColor : "#f5f5f5",
        animateScale : true
      };
      countryStats = document.getElementById("cStats").getContext("2d");
      myPieCountryChart = new Chart(countryStats).Pie(pieCountryData, pieCountryOptions);

    }


    var pieDateData = [];
    var pieDateOptions;
    var dStats;
    var myPieDateChart;
    
    function populateDateChart(stats){
      pieDateData = [];
      var container = document.getElementById("date-chart-container");
      var canvas = document.createElement('canvas');
      canvas.width = parseFloat(window.getComputedStyle(container).width);
      canvas.height = '300';
      canvas.setAttribute('id', 'dStats');

      container.removeChild(container.firstChild);
      container.appendChild(canvas);
      var dLabels = [];
      var dData = [];

      for (var key in stats) {
        dLabels.push(key);
      }
      dLabels.sort();

      for(var i = 0; i < dLabels.length; i++){
        dData.push(stats[ dLabels[i] ]);
      }

      pieDateData = {
        labels: dLabels,
        datasets: [
          {
              label: "date dataset",
              fillColor: "#FDD835",
              highlightFill: "#FFC107",
              data: dData
          }
        ]
      };
      dStats = document.getElementById("dStats").getContext("2d");
      myPieDateChart = new Chart(dStats).Bar(pieDateData, pieDateOptions);

    }

    function clearContainer(container){
      while (container.firstChild) {
        container.removeChild(container.firstChild);
      }
    }


  }

);