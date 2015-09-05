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
    $scope.longUrl = null;
    $scope.custom = null;
    $scope.btn = "GO!";
    
  	$scope.takeShort = function() {
  		var res = $http.post("/rest/go-shorty",{longUrl : $scope.longUrl,custom:$scope.custom});
  		res.success(function(data) {
  			console.log('Inserted!');
  			$scope.rispostaJson = data;
  			$scope.shortUrl = "http://localhost:4567/" + $scope.rispostaJson.shortUrl;
        $scope.err= "";
  		}
      );
      res.error(function(data) {
        $scope.rispostaJson = data || "request failed";
        $scope.err = $scope.rispostaJson.err ;
        $scope.shortUrl= "";
        });
    };
  });
