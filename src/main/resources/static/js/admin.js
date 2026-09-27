const role = sessionStorage.getItem("role");
const username = sessionStorage.getItem("username");

// Only admins can access this page
if (role !== "ADMIN") {
    alert("You are not authorized to access this page.");
    window.location.href = "/index.html";
}

document.getElementById("username").textContent =
    username || "Admin";

let editingId = null;


const statuses = [
    "UPCOMING",
    "ONGOING",
    "FINISHED"
];

const disciplines = [
    "TABLE_TENNIS",
    "TENNIS",
    "BADMINTON",
    "FOOTBALL",
    "BASKETBALL",
    "CHESS"
];

function fillSelect(id, values) {

    const select = document.getElementById(id);

    select.innerHTML = "";

    values.forEach(value => {

        const option = document.createElement("option");

        option.value = value;
        option.textContent = value;

        select.appendChild(option);
    });
}

fillSelect("status", statuses);
fillSelect("discipline", disciplines);


async function loadTournaments() {

    const response = await fetch("/tournaments");

    if (!response.ok) {
        showMessage("Unable to load tournaments.");
        return;
    }

    const tournaments = await response.json();

    const container =
        document.getElementById("tournaments");

    container.innerHTML = "";

    if (tournaments.length === 0) {

        container.innerHTML =
            "<p>No tournaments available.</p>";

        return;
    }

    tournaments.forEach(tournament => {

        const div = document.createElement("div");

        div.className = "tournament";

        div.innerHTML = `
                <h3>${escapeHtml(tournament.name)}</h3>

                <p>
                    <strong>Dates:</strong>
                    ${tournament.startDate}
                    → ${tournament.endDate}
                </p>

                <p>
                    <strong>Maximum players:</strong>
                    ${tournament.maxPlayers}
                </p>

                <p>
                    <strong>Status:</strong>
                    ${tournament.status}
                </p>

                <p>
                    <strong>Discipline:</strong>
                    ${tournament.discipline}
                </p>

                <p>
                    <strong>Players registered:</strong>
                    ${tournament.players
            ? tournament.players.length
            : 0}
                </p>

                <div class="actions">

                    <button
                        class="edit-btn"
                        onclick="editTournament(${tournament.id})"
                    >
                        Edit
                    </button>

                    <button
                        class="delete-btn"
                        onclick="deleteTournament(${tournament.id})"
                    >
                        Delete
                    </button>

                </div>
            `;

        container.appendChild(div);
    });
}


document
    .getElementById("tournamentForm")
    .addEventListener("submit", async function (event) {

        event.preventDefault();

        const tournament = {

            name: document.getElementById("name").value,

            startDate:
            document.getElementById("startDate").value,

            endDate:
            document.getElementById("endDate").value,

            maxPlayers:
                Number(document.getElementById("maxPlayers").value),

            status:
            document.getElementById("status").value,

            discipline:
            document.getElementById("discipline").value
        };


        let response;

        if (editingId === null) {

            response = await fetch("/tournaments", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(tournament)
            });

        } else {

            response = await fetch(
                `/tournaments/${editingId}`,
                {
                    method: "PUT",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify(tournament)
                }
            );
        }


        if (!response.ok) {

            showMessage(
                "Error while saving tournament."
            );

            return;
        }


        showMessage(
            editingId === null
                ? "Tournament created."
                : "Tournament updated."
        );

        cancelEdit();

        loadTournaments();
    });


async function editTournament(id) {

    const response =
        await fetch(`/tournaments/${id}`);

    if (!response.ok) {

        showMessage("Tournament not found.");

        return;
    }

    const tournament = await response.json();

    editingId = id;

    document.getElementById("name").value =
        tournament.name;

    document.getElementById("startDate").value =
        tournament.startDate;

    document.getElementById("endDate").value =
        tournament.endDate;

    document.getElementById("maxPlayers").value =
        tournament.maxPlayers;

    document.getElementById("status").value =
        tournament.status;

    document.getElementById("discipline").value =
        tournament.discipline;

    document.getElementById("formTitle").textContent =
        "Edit Tournament";

    document.getElementById("submitButton").textContent =
        "Save Changes";

    document.getElementById("cancelButton").style.display =
        "inline-block";

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}


function cancelEdit() {

    editingId = null;

    document.getElementById("tournamentForm").reset();

    document.getElementById("formTitle").textContent =
        "Create Tournament";

    document.getElementById("submitButton").textContent =
        "Create Tournament";

    document.getElementById("cancelButton").style.display =
        "none";
}


async function deleteTournament(id) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this tournament?"
        );

    if (!confirmed) {
        return;
    }

    const response =
        await fetch(`/tournaments/${id}`, {
            method: "DELETE"
        });

    if (!response.ok) {

        showMessage(
            "Unable to delete tournament."
        );

        return;
    }

    showMessage("Tournament deleted.");

    loadTournaments();
}


function showMessage(message) {

    document.getElementById("message")
        .textContent = message;
}


function escapeHtml(value) {

    const div = document.createElement("div");

    div.textContent = value;

    return div.innerHTML;
}


function logout() {

    sessionStorage.clear();

    window.location.href = "/login.html";
}


loadTournaments();

