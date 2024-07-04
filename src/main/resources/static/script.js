document.addEventListener('DOMContentLoaded', function() {
  var clickableItems = document.querySelectorAll('.clickable');
  clickableItems.forEach(function(item) {
      item.onclick = function() {
          window.location.href = 'eventos.html';
      };
  });
});