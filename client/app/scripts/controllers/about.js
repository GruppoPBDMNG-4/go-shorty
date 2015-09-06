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

  	$scope.shortUrl = "here" ;
    //var stats; 

  	$scope.getStats = function(){

  		var res = $http.get("/rest/stats" + "/" + $scope.shortUrl);

  		res.success(function(data) {
    		console.log('lets stats!');
    		$scope.rispostaJson = data;
  		});

      res.error(function(data) {
        $scope.rispostaJson = data || "request failed";
        $scope.err = $scope.rispostaJson.err ;
        $scope.shortUrl= "";
        });

  	};

    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
/*
    var stats = JSON.parse($scope.rispostaJson);
*/
    var pieData = [
            {
                    value: 20,
                    color:"#878BB6",
                    highlight: "#FF5A5E",
                    label: "chrome"
            },
            {
                    value : 40,
                    color : "#4ACAB4"
            },
            {
                    value : 10,
                    color : "#FF8153"
            },
            {
                    value : 30,
                    color : "#FFEA88"
            }
    ];

    var pieOptions = {
            segmentShowStroke : false,
            animateScale : true
    };
    var browserStats = document.getElementById("browserStats").getContext("2d");
    var myPieChart = new Chart(browserStats).Pie(pieData, pieOptions);




    

  }


);
