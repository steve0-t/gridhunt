"use strict";

document.addEventListener("DOMContentLoaded", () => {
  let currentStatus = null;
  let lastMove = 0;

  async function savePlayerScore() {
    const gameTitle = document.getElementById("gameName");
    const game = gameTitle?.dataset.game || gameTitle?.textContent.trim();
    const player = document.getElementById("loggedPlayer")?.value;
    const points = document.getElementById("score")?.textContent.trim();
    if (!player || !game || points == null) return;
    try {
      await fetch("/api/score", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ game, player, points, playedOn: new Date() }),
      });
    } catch (error) {
      console.error("Failed to save score:", error);
    }
  }

  function setGameStatus() {
    const status = document.getElementById("game-status")?.textContent.trim();
    if (!status || status === currentStatus) return;
    currentStatus = status;
    if (status === "FAILED") document.getElementById("gameLostModal").showModal();
    if (status === "WON") {
      document.getElementById("gameWonModal").showModal();
      savePlayerScore();
    }
  }

  document.addEventListener("keydown", (event) => {
    if (event.repeat || currentStatus !== "PLAYING" || document.querySelector("dialog[open]")) return;
    if (event.target.closest("input, textarea, select, [contenteditable='true']")) return;
    const key = event.key.toLowerCase();
    const actions = {
      w: "/gridhunt/move/NORTH", s: "/gridhunt/move/SOUTH",
      a: "/gridhunt/move/WEST", d: "/gridhunt/move/EAST",
      k: "/gridhunt/attack/NORTH", j: "/gridhunt/attack/SOUTH",
      h: "/gridhunt/attack/WEST", l: "/gridhunt/attack/EAST",
      v: "/gridhunt/toggleView",
    };
    if (!actions[key]) return;
    const now = Date.now();
    if (now - lastMove < 120) return;
    lastMove = now;
    event.preventDefault();
    htmx.ajax("GET", actions[key], { target: key === "v" ? ".grid" : ".game-area", swap: "outerHTML" });
  });

  document.addEventListener("htmx:afterSwap", setGameStatus);
  setGameStatus();
});
