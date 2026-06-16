document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("commentForm");
  const submitBtn = document.getElementById("submitBtn");
  const messageEl = document.getElementById("message");

  function showMessage(text, type) {
    messageEl.textContent = text;
    messageEl.className = "message " + type;
    setTimeout(() => {
      messageEl.className = "message hidden";
    }, 3000);
  }

  form.addEventListener("submit", async function (e) {
    e.preventDefault();

    submitBtn.disabled = true;
    submitBtn.textContent = "Posting...";

    const formData = {
      player: document.getElementById("player").value,
      game: document.getElementById("game").value,
      comment: document.getElementById("comment").value,
      commentedOn: new Date(),
    };

    try {
      const response = await fetch("/api/comment", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        showMessage("Comment posted successfully!", "success");
        form.reset();
        await loadComments();
      } else {
        showMessage("Failed to post comment. Please try again.", "error");
      }
    } catch (error) {
      showMessage("An error occurred. Please try again.", "error");
    } finally {
      submitBtn.disabled = false;
      submitBtn.textContent = "Post Comment";
    }
  });

  async function loadComments() {
    const game = document.getElementById("game").value;
    try {
      const response = await fetch(
        "/api/comment/" + encodeURIComponent(game) + "/comments",
      );
      if (response.ok) {
        const comments = await response.json();
        renderComments(comments);
      }
    } catch (error) {
      console.error("Failed to load comments:", error);
    }
  }

  function renderComments(comments) {
    const list = document.getElementById("commentsList");
    if (comments.length === 0) {
      list.innerHTML =
        '<div class="empty-state"><p>No comments yet. Be the first to comment!</p></div>';
      return;
    }
    list.innerHTML = comments
      .map((c) => {
        const date = c.commentedOn
          ? new Date(c.commentedOn).toLocaleString()
          : "";
        return (
          '<div class="comment-card">' +
          '<div class="comment-header">' +
          '<span class="comment-player">' +
          escapeHtml(c.player) +
          "</span>" +
          '<span class="comment-date">' +
          date +
          "</span>" +
          "</div>" +
          '<p class="comment-text">' +
          escapeHtml(c.comment) +
          "</p>" +
          "</div>"
        );
      })
      .join("");
  }

  function escapeHtml(text) {
    const div = document.createElement("div");
    div.textContent = text;
    return div.innerHTML;
  }

  loadComments();
});
