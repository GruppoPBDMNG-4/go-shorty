'use strict';


 angular.module('clientApp')
  .controller('HeaderController', function($scope, $location) {
  $scope.getClass = function (path) {
  if ($location.path().substr(0, path.length) === path) {
    return 'active';
  } else {
    return '';
  }
};
});

  //ng-class="getClass('#/')" ng-class="getClass('#/stat')"