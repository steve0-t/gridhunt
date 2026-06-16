"use strict";
document.addEventListener("DOMContentLoaded", () => {
  console.log("script loaded");

  const closeThemeModal = document.getElementById("closeThemeModal");
  const themeOpts = document.getElementsByClassName("theme-option");

  let currStatus = null;

  async function setGameStatus(status) {
    if (status === currStatus) return;
    currStatus = status;

    if (status === "FAILED") {
      document.removeEventListener("keydown", processKeyPress);
      document.getElementById("gameLostModal").showModal();
    } else if (status === "WON") {
      document.removeEventListener("keydown", processKeyPress);
      await savePlayerScore();
      document.getElementById("gameWonModal").showModal();
    } else if (status === "PLAYING") {
      document.addEventListener("keydown", processKeyPress);
    }
  }

  async function savePlayerScore() {
    const game = document.getElementById("gameName")?.innerText.trim();
    const loggedPlayer = document.getElementById("loggedPlayer")?.value;
    const score = document.getElementById("score")?.innerText.trim();
    if (loggedPlayer === null || score === null || game === null) return;
    console.log("saving score for " + loggedPlayer);

    const formData = {
      game: game,
      player: loggedPlayer,
      points: score,
      playedOn: new Date(),
    };
    try {
      const response = await fetch("/api/score", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
    } catch (error) {
      console.log(error);
    }
  }

  for (const themeOpt of themeOpts) {
    themeOpt.addEventListener("click", (e) => {
      console.log("chose theme");

      const theme = e.currentTarget.dataset.theme;
      setTheme(theme);
    });
  }

  let lastMove = 0;
  document.addEventListener("keydown", processKeyPress);

  function processKeyPress(e) {
    if (e.repeat) return;

    const now = Date.now();
    if (now - lastMove < 120) return;
    lastMove = now;

    const key = e.key.toLowerCase();

    let url = null;
    let swap_ = "outerHTML";
    let target_ = ".game-area";

    if (key === "w") url = "/gridhunt/move/NORTH";
    else if (key === "s") url = "/gridhunt/move/SOUTH";
    else if (key === "a") url = "/gridhunt/move/WEST";
    else if (key === "d") url = "/gridhunt/move/EAST";
    else if (key === "j") url = "/gridhunt/attack/SOUTH";
    else if (key === "k") url = "/gridhunt/attack/NORTH";
    else if (key === "h") url = "/gridhunt/attack/WEST";
    else if (key === "l") url = "/gridhunt/attack/EAST";
    else if (key === "v") {
      url = "/gridhunt/toggleView";
      target_ = ".grid";
      swap_ = "outerHTML";
    }

    if (!url) return;

    htmx.ajax("GET", url, {
      target: target_,
      swap: swap_,
    });
  }

  const profileBtn = document.getElementById("profileBtn");

  window.onclick = function (e) {
    if (!e.target.matches(".profile-btn")) {
      const menu = document.getElementById("dropdownMenu");
      if (menu?.style.display === "block") {
        menu.style.display = "none";
      }
    }
  };

  function loadTheme() {
    const saved = getThemeFromCookie() || "default";
    setTheme(saved);
  }

  function getThemeFromCookie() {
    const match = document.cookie.match(/(^| )theme=([^;]+)/);
    return match ? match[2] : null;
  }

  function setTheme(theme) {
    document.documentElement.setAttribute("data-theme", theme);

    document.querySelectorAll(".theme-option").forEach((opt) => {
      opt.classList.remove("selected");
      if (opt.dataset.theme === theme) {
        opt.classList.add("selected");
      }
    });

    document.cookie = "theme=" + theme + "; path=/; max-age=31536000";
  }

  document.addEventListener("htmx:afterSwap", (e) => {
    const st = e.target.querySelector("#game-status")?.innerHTML.trim();
    if (st === null) return;

    console.log(st);
    setGameStatus(st);
  });

  const saveGameBtn = document.getElementById("saveGameBtn");

  saveGameBtn?.addEventListener("click", () => {
    saveGame();
  });

  function saveGame() {
    const gameState = collectGameState();

    localStorage.setItem("myGameSave", JSON.stringify(gameState));

    console.log("Game saved");
  }

  function collectGameState() {
    const hints = [...document.querySelectorAll(".hint-item")].map(
      (el) => el.textContent,
    );

    const grid = [...document.querySelectorAll(".cell")].map((cell) => ({
      text: cell.querySelector(".result-text")?.textContent,
      visible: cell.classList.contains("visible"),
      hidden: cell.classList.contains("hidden"),
    }));

    const gameState = {
      score: document.getElementById("score")?.textContent,
      status: document.getElementById("game-status")?.textContent,
      hints,
      grid,
      savedAt: Date.now(),
    };

    return gameState;
  }

  const loadGameBtn = document.getElementById("loadGameBtn");

  loadGameBtn?.addEventListener("click", () => {
    loadGame();
  });

  function loadGame() {
    const raw = localStorage.getItem("myGameSave");

    if (!raw) {
      console.log("No save found");
      return;
    }

    const gameState = JSON.parse(raw);

    console.log(gameState);

    document.getElementById("score").textContent = gameState.score;

    document.getElementById("game-status").textContent = gameState.status;

    const hintsContainer = document.getElementById("hints-container");

    hintsContainer.innerHTML = "";

    gameState.hints.forEach((hint) => {
      const div = document.createElement("div");
      div.className = "hint-item";
      div.textContent = hint;
      hintsContainer.appendChild(div);
    });

    const cells = document.querySelectorAll(".cell");

    gameState.grid.forEach((savedCell, index) => {
      const cell = cells[index];

      cell.classList.toggle("visible", savedCell.visible);
      cell.classList.toggle("hidden", savedCell.hidden);

      const text = cell.querySelector(".result-text");

      if (text) {
        text.textContent = savedCell.text;
      }
    });
  }

  loadTheme();
});
