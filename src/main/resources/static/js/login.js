document
    .getElementById("loginForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const username =
            document.getElementById("username").value;

        const password =
            document.getElementById("password").value;

        const message =
            document.getElementById("message");

        try {

            const response = await fetch("/api/auth/login", {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });

            const data = await response.json();

            if (response.ok) {

                sessionStorage.setItem("playerId", data.id);
                sessionStorage.setItem("username", data.username);
                sessionStorage.setItem("role", data.role);


                window.location.href = "/index.html";

            } else {

                message.textContent =
                    data.message || "Nom d'utilisateur ou mot de passe incorrect.";

            }

        } catch (error) {

            console.error(error);

            message.textContent =
                "error.";
        }
    });
