 const playerId = sessionStorage.getItem("playerId");
const username = sessionStorage.getItem("username");
const role = sessionStorage.getItem("role");


    if (username) {

    document.getElementById("username").textContent =
        username;

    document.getElementById("loginLink").style.display =
    "none";

    document.getElementById("logoutLink").style.display =
    "inline";
}


    if (role === "ADMIN") {

    document.getElementById("adminPanel").style.display =
        "block";

}




    async function loadTournaments() {

    try {

    const response =
    await fetch("/tournaments");

    if (!response.ok) {

    throw new Error(
    "Unable to load tournaments."
    );

}

    const tournaments =
    await response.json();

    const container =
    document.getElementById("tournaments");

    container.innerHTML = "";


    if (tournaments.length === 0) {

    container.innerHTML = `
                    <div class="message">
                        No tournaments available.
                    </div>
                `;

    return;
}


    tournaments.forEach(tournament => {

    const players =
    tournament.players || [];




    const isJoined =
    players.some(player =>
    String(player.id) === String(playerId)
    );




    const isFull =
    players.length >= tournament.maxPlayers;


    let button = "";


    if (!playerId) {

    button = `
                        <button
                            class="join-btn"
                            onclick="goToLogin()">
                            Login to Join
                        </button>
                    `;

}



    else if (isJoined) {

    button = `
                        <button
                            class="leave-btn"
                            onclick="leaveTournament(${tournament.id})">
                            Leave Tournament
                        </button>
                    `;

}


    else if (isFull) {

    button = `
                        <button disabled>
                            Tournament Full
                        </button>
                    `;

}


    else if (tournament.status === "FINISHED") {

    button = `
                        <button disabled>
                            Tournament Finished
                        </button>
                    `;

}


    else {

    button = `
                        <button
                            class="join-btn"
                            onclick="joinTournament(${tournament.id})">
                            Join Tournament
                        </button>
                    `;
}




    const tournamentElement =
    document.createElement("div");

    tournamentElement.className =
    "tournament";


    tournamentElement.innerHTML = `

                    <h3>
                        ${escapeHtml(tournament.name)}
                    </h3>

                    <p>
                        <strong>Dates:</strong>
                        ${tournament.startDate}
                        →
                        ${tournament.endDate}
                    </p>

                    <p>
                        <strong>Players:</strong>
                        ${players.length}
                        /
                        ${tournament.maxPlayers}
                    </p>

                    <p>
                        <strong>Status:</strong>
                        <span class="status">
                            ${tournament.status}
                        </span>
                    </p>

                    <p>
                        <strong>Discipline:</strong>
                        ${tournament.discipline}
                    </p>

                    <div class="actions">
                        ${button}
                    </div>

                `;


    container.appendChild(
    tournamentElement
    );

});

} catch (error) {

    console.error(error);

    document.getElementById("tournaments").innerHTML = `
                <div class="message">
                    Unable to load tournaments.
                </div>
            `;

}

}



    async function joinTournament(tournamentId) {

    if (!playerId) {

    goToLogin();

    return;
}


    try {

    const response =
    await fetch(
    `/tournaments/${tournamentId}/players/${playerId}`,
{
    method: "PUT"
}
    );


    if (!response.ok) {

    const message =
    await response.text();

    alert(
    message ||
    "Unable to join the tournament."
    );

    return;
}


    alert(
    "You joined the tournament!"
    );


    loadTournaments();

} catch (error) {

    console.error(error);

    alert(
    "An error occurred while joining the tournament."
    );

}

}



    async function leaveTournament(tournamentId) {

    if (!playerId) {
    return;
}


    const confirmed =
    confirm(
    "Are you sure you want to leave this tournament?"
    );


    if (!confirmed) {
    return;
}


    try {

    const response =
    await fetch(
    `/tournaments/${tournamentId}/players/${playerId}`,
{
    method: "DELETE"
}
    );


    if (!response.ok) {

    const message =
    await response.text();

    alert(
    message ||
    "Unable to leave the tournament."
    );

    return;
}


    alert(
    "You left the tournament!"
    );


    loadTournaments();

} catch (error) {

    console.error(error);

    alert(
    "An error occurred while leaving the tournament."
    );

}

}



    function goToLogin() {

    window.location.href =
        "/login.html";

}



    function logout() {

    sessionStorage.clear();

    window.location.href =
    "/login.html";

}



    function escapeHtml(value) {

    const div =
    document.createElement("div");

    div.textContent =
    value;

    return div.innerHTML;

}




    loadTournaments();


