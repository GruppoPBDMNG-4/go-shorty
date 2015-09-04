'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  
  .controller('AboutCtrl', function ($scope,$http) {
  	$scope.shortUrl = "here" ;
  	$scope.getStats = function(){
  		$http.get("/rest/stats"+"/"+$scope.shortUrl)
  		.success(function(data) {
  			console.log('lets stats!');
  			$scope.rispostaJson = data;
  		});
  	};

    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

  });
