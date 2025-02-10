function init(students) {
    const input = document.getElementById("tickets-number");
    input.addEventListener("change", (event) => {
        if (event.target.value < 1) {
            event.target.value = 1;
        }
        generateRandomTicketsNumbers(students)
    });
    generateRandomTicketsNumbers(students);
}
function generateRandomTicketsNumbers(students) {
    if (students instanceof Array) {
        var tbl = document.getElementById("students-tickets-table");
        if (tbl != null) tbl.remove();
        var ticketsNumbers = generateRandomTicketsNumber(students.length);
        tableCreate(students, ticketsNumbers);
    }
}
function tableCreate(studentsArr, ticketsNumbers) {
    if (students instanceof Array) {
        const body = document.body,
            tbl = document.createElement('table');
        tbl.id = "students-tickets-table";
        for (let i = 0; i < studentsArr.length; i++) {
            const tr = tbl.insertRow();
            td = tr.insertCell();
            td.appendChild(document.createTextNode(studentsArr[i]));
            td = tr.insertCell();
            td.style.width = "40px"
            td.style.textAlign = "right";
            td.appendChild(document.createTextNode(ticketsNumbers[i]));
        }

        body.insertBefore(tbl, body.firstChild);
    }
}
function generateRandomTicketsNumber(studentsArrLength) {
    ticketsNumber = document.getElementById("tickets-number").value;
    ticketsRepetitions = Math.ceil(studentsArrLength / ticketsNumber);

    ticketsRepetitionCounters = [];
    ticketsRepetitionCounters.length = ticketsNumber;

    if (studentsArrLength % ticketsNumber == 0) {
        ticketsRepetitionCounters.fill(ticketsRepetitions);
    } else {
        ticketsRepetitionCounters.fill(ticketsRepetitions - 1);
        for (let i = 0; i < studentsArrLength - Math.floor(studentsArrLength / ticketsNumber) * ticketsNumber; i++) {
            do {
                expanseTicket = getRandomInt(ticketsNumber);
            } while (ticketsRepetitionCounters[expanseTicket - 1] > (ticketsRepetitions - 1))
            ticketsRepetitionCounters[expanseTicket - 1]++;
        }
    }

    ticketsNumbers = [];
    ticketsNumbers.length = studentsArrLength;
    for (let i = 0; i < studentsArrLength; i++) {
        while (true) {
            ticketNumber = getRandomInt(ticketsNumber);
            if (ticketsRepetitionCounters[ticketNumber - 1] > 0) break;
        }
        ticketsNumbers[i] = ticketNumber
        ticketsRepetitionCounters[ticketNumber - 1]--;
    }
    return ticketsNumbers;
}
function getRandomInt(max) {
    return Math.floor(Math.random() * max) + 1;
}