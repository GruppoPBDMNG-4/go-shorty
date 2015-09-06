'use strict';


 angular.module('clientApp')
 	.controller('HeaderController', function($scope, $location) {
  
	  $scope.isActive = function (viewLocation) { 
	  	return viewLocation === $location.path();
	  };

	});

  //ng-class="getClass('#/')" ng-class="getClass('#/stat')"