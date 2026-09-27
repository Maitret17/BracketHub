document.getElementById("registerForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const player = {
        first_name: document.getElementById("firstName").value,
        last_name: document.getElementById("lastName").value,
        age: parseInt(document.getElementById("age").value),
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    try {

        const response = await fetch("/api/auth/register", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(player)
        });

        const data = await response.json();

        if (response.ok) {

            document.getElementById("message").textContent =
                "Account created! Redirecting to login...";

            setTimeout(() => {
                window.location.href = "/login.html";
            }, 1000);

        } else {

            document.getElementById("message").textContent =
                data.message || "Registration failed.";

        }

    } catch (error) {

        document.getElementById("message").textContent =
            "Unable to connect to the server.";

        console.error(error);
    }

});