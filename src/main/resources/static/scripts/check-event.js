



function init(obj) {
    if (obj.name == "empty") { window.location.replace('/students-list') };

    var studentId = 0;
    var list = document.getElementById("stud");
    var progress = document.getElementById("progress");
    for (let i = 0; i < obj.studentsNames.length; i++) {
        let newOption = new Option(obj.studentsNames[i])

        list.add(newOption)

    }
    list.options[studentId].selected = true;

    if (obj.eventSignature.marksType == "BOOLEAN") {
        document.getElementById("numberic_mark").style.display = 'none';
    } else {
        var mark = document.getElementById("mark");
        document.getElementById("binary_mark").style.display = 'none';
        mark.value = obj.values[studentId];
        mark.focus();
        mark.select();
    }

    updateForm(obj);
    if (obj.eventSignature.marksType == "NUMBERIC") {
        document.addEventListener(
            "keydown",
            (event) => {
                const keyName = event.key;
                if (keyName === "Enter") {
                    setMark(obj);
                    return;
                }
            },
            false,
        );
    };
};
function handlerFunc(key, obj) {
    var list = document.getElementById("stud");
    var studentId = list.selectedIndex;
    if (key) {
        obj.values[studentId] = true;

    } else {
        obj.values[studentId] = false;
    }
    while (!obj.values[++studentId] == "true");
    if (studentId < obj.studentsNames.length) {
        list.options[studentId].selected = true;

    } else {

        request("students-list", obj);
    }
    updateForm(obj);
}
function setMark(obj) {
    var list = document.getElementById("stud");
    var studentId = list.selectedIndex;
    obj.values[studentId] = parseInt(document.getElementById("mark").value);
    studentId++;

    if (studentId < obj.studentsNames.length) {
        list.options[studentId].selected = true;
        mark.value = obj.values[studentId];
        mark.focus();
        mark.select();

    } else {
        request("students-list", obj);
    }
    updateForm();

}
function updateForm(obj) {
    var mark = document.getElementById("mark");
    var list = document.getElementById("stud");
    if (obj.eventSignature.marksType == "NUMBERIC") {
        mark.value = obj.values[list.selectedIndex];
        mark.focus();
        mark.select();
    } else {

        let trueButton = document.getElementById("true");
        let falseButton = document.getElementById("false");
        if (obj.values[list.selectedIndex] === true) {
            trueButton.style.backgroundColor = '#34eb3d';
            falseButton.style.backgroundColor = '#fc8683';
            falseButton.style.border = '2px solid #fa0702';
        } else {
            trueButton.style.backgroundColor = '#88fc8e';

            trueButton.style.border = '2px solid #34eb3d';

            falseButton.style.backgroundColor = '#fa0702';
        }
    }
    var progress = document.getElementById("progress");
    progress.value = (list.selectedIndex / obj.studentsNames.length) * 100;
}

function commit(obj) {
    request("students-list", obj);
}
function rollback(initialObject) {
    request("students-list", initialObject)
}
function deleteEvent(obj) {
    const result = confirm("Вы уверены что хотите удалить мероприятие");
    if (result === true)
        request("delete-event", obj);
}

async function request(path, object) {
    const response = await fetch(path, {
        method: "POST",
        body: JSON.stringify(object),
        headers: { "Content-type": "application/json; charset=UTF-8" }
    }

    );

    window.location.replace(response.url);
}