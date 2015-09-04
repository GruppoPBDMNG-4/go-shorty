'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  

  .controller('MainCtrl', function ($scope,$http) {
    $scope.longUrl = "here";
    $scope.custom = "here";
    $scope.btn = "GO!"
  	$scope.takeShort = function() {
  		$http.post("/rest/go-shorty",{longUrl : $scope.longUrl,custom:$scope.custom})
  		.success(function(data) {
  			console.log('Inserted!');
  			$scope.rispostaJson = data;
  			$scope.shortUrl = "http://localhost:4567/"+$scope.rispostaJson.shortUrl;
  			
  		});};
  });
