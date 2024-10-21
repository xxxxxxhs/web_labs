document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("pointForm");
    const errorDiv = document.createElement("div");
    errorDiv.style.color = "red";
    form.appendChild(errorDiv);

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const xValue = parseFloat(document.getElementById("x").value);
        const yValue = parseFloat(document.getElementById("number-dropdown").value);
        const rValue = parseFloat(document.querySelector("input[name='r']:checked").value);

        let errorMessage = "";
        if (isNaN(xValue) || xValue < -3 || xValue > 3) {
            errorMessage += "X должен быть числом от -3 до 3.<br>";
        }
        if (isNaN(yValue) || yValue < -4 || yValue > 4) {
            errorMessage += "Y должен быть выбран из диапазона -4 до 4.<br>";
        }
        if (isNaN(rValue)) {
            errorMessage += "Необходимо выбрать значение R.<br>";
        }

        if (errorMessage) {
            errorDiv.innerHTML = errorMessage;
            return;
        }

        errorDiv.innerHTML = "";

        const data = {
            x: xValue,
            y: yValue,
            r: rValue
        };

        fetch('http://localhost:9000/fcgi-bin/lab_1.jar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                console.log("Ответ сервера:", data);
                if (data.error === "all ok") {
                    updateTable(data);
                } else {
                    errorDiv.innerHTML = "Ошибка на сервере: " + data.error;
                }
            })
            .catch(error => {
                errorDiv.innerHTML = "Ошибка отправки данных: " + error;
            });
    });

    // Функция обновления таблицы
    function updateTable(result) {
        const tableBody = document.querySelector("#log tbody");
        const newRow = document.createElement("tr");

        if (result.result === "true") {
            newRow.classList.add("hit-row");
        } else {
            newRow.classList.add("miss-row");
        }

        newRow.innerHTML = `<td>${result.x}</td>
                            <td>${result.y}</td>
                            <td>${result.r}</td>
                            <td>${result.result === "true" ? 'hit' : 'miss'}</td>
                            <td>${result.time}</td>
                            <td>${result.workTime}</td>`;
        tableBody.appendChild(newRow);
    }
});
