'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  
  .controller('AboutCtrl', function ($scope, $http) {

  	$scope.shortUrl = "" ;

  	$scope.getStats = function(){
  		var res = $http.get("/rest/stats" + "/" + $scope.shortUrl);

  		res.success(function(data) {
    		console.log('lets stats!');
    		$scope.rispostaJson = data;
        //stats = rispostaJson.browserStats;
        populateBrowserChart(data.browserStats);
        populateCountryChart(data.countryStats);

  		});

      res.error(function(data) {
        $scope.rispostaJson = data || "request failed";
        $scope.err = $scope.rispostaJson.err ;
        $scope.shortUrl= "";
        });

  	};

    var browserColor = {chrome:'#00933B', firefox:'#FF9800', safari:'#00BCD4', explorer:'#01579B', opera:'#BF360C'};

    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var pieData = [];
    var pieOptions;
    var bStats;
    var myPieChart;
    
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
      while (descContainer.firstChild) {
          descContainer.removeChild(descContainer.firstChild);
      }

      for (var key in stats) {
        var singleStat = {
          value: stats[key],
          color: browserColor[key],
          highlight: "#FF5A5E",
          label: key
        };
        pieData.push(singleStat);

        var rowLabel = document.createElement('div');
        rowLabel.setAttribute('class', 'row');
        var keyLabel = document.createElement('span');
        var t = document.createTextNode(key);
        keyLabel.appendChild(t);
        keyLabel.setAttribute('class', 'label label-default');
        keyLabel.setAttribute('style', 'background-color:' + browserColor[key]);
        rowLabel.appendChild(keyLabel);
        descContainer.appendChild(rowLabel);
      }

      pieOptions = {
        segmentShowStroke : false,
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
      while (descContainer.firstChild) {
          descContainer.removeChild(descContainer.firstChild);
      }

      for (var key in stats) {
        var currentColor = '#'+(function lol(m,s,c){
            return s[m.floor(m.random() * s.length)] + (c && lol(m,s,c-1));})(Math,'0123456789ABCDEF',4);
        var singleStat = {
          value: stats[key],
          color: currentColor,
          highlight: "#FF5A5E",
          label: key
        };
        pieCountryData.push(singleStat);

        var rowLabel = document.createElement('div');
        rowLabel.setAttribute('class', 'row');
        var keyLabel = document.createElement('span');
        var t = document.createTextNode(key);
        keyLabel.appendChild(t);
        keyLabel.setAttribute('class', 'label');
        keyLabel.setAttribute('style', 'background-color:' + currentColor);
        rowLabel.appendChild(keyLabel);
        descContainer.appendChild(rowLabel);
      }

      pieCountryOptions = {
        segmentShowStroke : false,
        animateScale : true
      };
      countryStats = document.getElementById("cStats").getContext("2d");
      myPieCountryChart = new Chart(countryStats).Pie(pieCountryData, pieCountryOptions);

    }


    /*function randomHexColor(m,s,c){
      return s[m.floor(m.random() * s.length)] + (c && lol(m,s,c-1));
    }*/

  }

);