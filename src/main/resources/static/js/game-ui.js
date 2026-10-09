"use strict";

document.addEventListener("DOMContentLoaded", () => {
  const themeOptions = [...document.querySelectorAll(".theme-option")];
  const savedTheme = document.cookie.match(/(?:^|; )theme=([^;]*)/)?.[1];

  function setTheme(theme) {
    if (!themeOptions.some((option) => option.dataset.theme === theme)) theme = "default";
    document.documentElement.dataset.theme = theme;
    themeOptions.forEach((option) => {
      const selected = option.dataset.theme === theme;
      option.classList.toggle("selected", selected);
      option.setAttribute("aria-pressed", String(selected));
    });
  }

  setTheme(savedTheme || document.documentElement.dataset.theme || "default");
  themeOptions.forEach((option) => option.addEventListener("click", () => {
    setTheme(option.dataset.theme);
    document.cookie = `theme=${option.dataset.theme}; path=/; max-age=31536000; SameSite=Lax`;
  }));

  document.querySelectorAll(".site-nav a").forEach((link) => {
    if (new URL(link.href).pathname === window.location.pathname) link.setAttribute("aria-current", "page");
  });

  document.querySelectorAll("dialog").forEach((dialog) => {
    dialog.addEventListener("click", (event) => {
      const bounds = dialog.getBoundingClientRect();
      if (event.target === dialog && (event.clientX < bounds.left || event.clientX > bounds.right || event.clientY < bounds.top || event.clientY > bounds.bottom)) dialog.close();
    });
  });

  document.addEventListener("click", (event) => {
    document.querySelectorAll(".profile-menu[open]").forEach((menu) => {
      if (!menu.contains(event.target)) menu.open = false;
    });
  });
});
