document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("ratingForm");
  const submitBtn = document.getElementById("submitBtn");
  const messageEl = document.getElementById("message");
  const ratingInput = document.getElementById("rating");
  const ratingLabel = document.getElementById("ratingLabel");
  const stars = document.querySelectorAll(".star");

  // Load average rating from API
  async function loadAverageRating() {
    const game = document.getElementById("game").value;
    try {
      const response = await fetch(
        "/api/rating/" + encodeURIComponent(game) + "/average",
      );
      if (response.ok) {
        const avg = await response.json();
        document.querySelector(".average-value span").textContent = avg;
        const fill = Math.max(0, Math.min(5, Number(avg))) * 20;
        document.getElementById("averageStars").style.setProperty("--rating-fill", `${fill}%`);
      }
    } catch (error) {
      console.error("Failed to load average rating:", error);
    }
  }

  let selectedRating = 0;

  stars.forEach((star) => {
    star.addEventListener("mouseenter", function () {
      const value = parseInt(this.dataset.value);
      highlightStars(value, "hover");
    });

    star.addEventListener("mouseleave", function () {
      highlightStars(selectedRating, "active");
    });

    star.addEventListener("click", function () {
      selectedRating = parseInt(this.dataset.value);
      ratingInput.value = selectedRating;
      stars.forEach((item) => item.setAttribute("aria-pressed", String(item === this)));
      highlightStars(selectedRating, "active");
      ratingLabel.textContent =
        "You selected: " +
        selectedRating +
        " star" +
        (selectedRating > 1 ? "s" : "");
    });
  });

  function highlightStars(count, className) {
    stars.forEach((star) => {
      star.classList.remove("active", "hover");
      if (parseInt(star.dataset.value) <= count) {
        star.classList.add(className);
      }
    });
  }

  function showMessage(text, type) {
    messageEl.textContent = text;
    messageEl.className = "message " + type;
    setTimeout(() => {
      messageEl.className = "message hidden";
    }, 3000);
  }

  form.addEventListener("submit", async function (e) {
    e.preventDefault();

    if (selectedRating === 0) {
      showMessage("Please select a rating.", "error");
      return;
    }

    submitBtn.disabled = true;
    submitBtn.textContent = "Submitting...";

    const formData = {
      game: document.getElementById("game").value,
      player: document.getElementById("player").value,
      points: selectedRating,
      ratedOn: new Date(),
    };

    try {
      const response = await fetch("/api/rating", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        showMessage("Rating submitted successfully!", "success");
        await loadPlayerRating();
        await loadAverageRating();
      } else {
        showMessage("Failed to submit rating. Please try again.", "error");
      }
    } catch (error) {
      showMessage("An error occurred. Please try again.", "error");
    } finally {
      submitBtn.disabled = false;
      submitBtn.textContent = "Submit Rating";
    }
  });

  // Load player's rating from API
  async function loadPlayerRating() {
    const game = document.getElementById("game")?.value;
    const player = document.getElementById("loggedPlayer")?.value;
    if (!player) return;

    try {
      const response = await fetch(
        "/api/rating/" +
          encodeURIComponent(game) +
          "/player/" +
          encodeURIComponent(player),
      );

      if (response.ok) {
        const rating = await response.json();
        const yourRatingEl = document.querySelector(".your-rating");
        if (rating > 0) {
          yourRatingEl.hidden = false;
          yourRatingEl.querySelector("span").textContent = rating;
          selectedRating = Number(rating);
          ratingInput.value = selectedRating;
          highlightStars(selectedRating, "active");
          stars.forEach((star) => star.setAttribute("aria-pressed", String(Number(star.dataset.value) === selectedRating)));
          ratingLabel.textContent = `Your rating: ${selectedRating} / 5`;
        }
      }
    } catch (error) {
      console.error("Failed to load player rating:", error);
    }
  }

  loadPlayerRating();
  loadAverageRating();
});
