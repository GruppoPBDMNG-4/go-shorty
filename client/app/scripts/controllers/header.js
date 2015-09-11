'use strict';

 angular.module('clientApp')
 	.controller('HeaderController', function($scope, $location) {
  
	  $scope.isActive = function (viewLocation) { 
	  	// Because of the optional parameter in the stats route
	  	if($location.path().startsWith("/stats/") && viewLocation.startsWith("/stats/")) {return true;}
	  	return viewLocation === $location.path();
	  };

	});