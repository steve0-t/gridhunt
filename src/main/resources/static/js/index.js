(function () {
  "use strict";

  // Text morphing animation
  const phrases = [
    "Welcome to Gamestudio",
    "Hunt. Seek. Conquer.",
    "The Grid Awaits",
  ];

  let currentIndex = 0;
  const morphText = document.getElementById("morphText");

  function morphToNextPhrase() {
    morphText.classList.add("morphing");

    setTimeout(function () {
      currentIndex = (currentIndex + 1) % phrases.length;
      morphText.textContent = phrases[currentIndex];
      morphText.classList.remove("morphing");
    }, 300);
  }

  setInterval(morphToNextPhrase, 3000);

  // Grid highlight animation
  const highlightGroup = document.getElementById("highlightCells");
  const cellSize = 60;

  function getGridDimensions() {
    return {
      cols: Math.ceil(window.innerWidth / cellSize),
      rows: Math.ceil(window.innerHeight / cellSize),
    };
  }

  function createHighlightCell() {
    var dims = getGridDimensions();
    var col = Math.floor(Math.random() * dims.cols);
    var row = Math.floor(Math.random() * dims.rows);

    var rect = document.createElementNS("http://www.w3.org/2000/svg", "rect");
    rect.setAttribute("x", col * cellSize);
    rect.setAttribute("y", row * cellSize);
    rect.setAttribute("width", cellSize);
    rect.setAttribute("height", cellSize);
    rect.setAttribute("class", "highlight-cell");

    highlightGroup.appendChild(rect);

    // Remove after animation completes
    setTimeout(function () {
      if (rect.parentNode) {
        rect.parentNode.removeChild(rect);
      }
    }, 2000);
  }

  // Create initial highlights
  for (var i = 0; i < 5; i++) {
    setTimeout(createHighlightCell, i * 200);
  }

  // Continue creating highlights
  setInterval(createHighlightCell, 400);

  // Cleanup old cells periodically
  setInterval(function () {
    var cells = highlightGroup.querySelectorAll(".highlight-cell");
    if (cells.length > 20) {
      for (var i = 0; i < cells.length - 15; i++) {
        if (cells[i].parentNode) {
          cells[i].parentNode.removeChild(cells[i]);
        }
      }
    }
  }, 5000);
})();
