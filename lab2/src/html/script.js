document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("pointForm");
    const errorDiv = document.createElement("div");
    errorDiv.style.color = "red";
    form.appendChild(errorDiv);

    const svgArea = document.querySelector("svg");
    const pointsGroup = document.getElementById("points-group");
    let currentR = parseFloat(document.getElementById("r").value);
    let savedPoints = [];


    function drawPoint(x, y, r) {
        const svgRect = svgArea.getBoundingClientRect();
        const multiplier = svgRect.width / 200;

        const cx = (x / r) * 80
        const cy = -(y / r) * 80

        const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        point.setAttribute("cx", cx);
        point.setAttribute("cy", cy);
        point.setAttribute("r", 0.5);
        point.setAttribute("fill", "red");
        pointsGroup.appendChild(point);
    }

    function redrawPoints(r) {

        pointsGroup.innerHTML = '';

        savedPoints.forEach(point => {
            drawPoint(point.x, point.y, r);
        });
    }

    function fetchSavedPoints() {
        fetch('http://localhost:8080/lab2-1.0-SNAPSHOT/checkArea')
            .then(response => response.json())
            .then(data => {
                savedPoints = data;
                redrawPoints(currentR);
            })
            .catch(error => {
                console.error('Ошибка получения сохранённых точек:', error);
            });
    }


    fetchSavedPoints();

    document.getElementById("r").addEventListener("change", function(event) {
        currentR = parseFloat(event.target.value);
        redrawPoints(currentR);
    });

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const xValue = parseFloat(document.querySelector("input[name='x']:checked").value);
        const yValue = parseFloat(document.getElementById("y").value);
        const rValue = parseFloat(document.getElementById("r").value);

        let errorMessage = "";
        if (isNaN(xValue) || xValue < -3 || xValue > 5) {
            errorMessage += "Необходимо выбрать значение X.<br>";
        }
        if (isNaN(yValue) || yValue <= -5 || yValue >= 5) {
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

        const formData = new URLSearchParams();
        formData.append("x", xValue);
        formData.append("y", yValue);
        formData.append("r", rValue);

        sendDataToServer(formData);
    });

    function sendDataToServer(formData) {
        fetch('http://localhost:8080/lab2-1.0-SNAPSHOT/ControllerServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        })
            .then(response => {
                if (response.ok) {

                    window.location.href = 'http://localhost:8080/lab2-1.0-SNAPSHOT/full_results.jsp';
                } else {
                    throw new Error('Ошибка при отправке данных');
                }
            })
            .catch(error => {
                errorDiv.innerHTML = "Ошибка отправки данных: " + error;
            });
    }

    svgArea.addEventListener("click", function(event) {
        const rValue = parseFloat(document.getElementById("r").value);

        if (isNaN(rValue)) {
            errorDiv.innerHTML = "Необходимо выбрать значение R.";
            return;
        }

        const svgRect = svgArea.getBoundingClientRect();
        const xClick = event.clientX - svgRect.left;
        const yClick = event.clientY - svgRect.top;


        const multiplier = svgRect.width / 200;

        const xGraph = (((xClick - svgRect.width / 2) / multiplier) * rValue) / 80;
        const yGraph = (((-(yClick - svgRect.height / 2) / multiplier) * rValue) / 80);

        const formData = new URLSearchParams();
        formData.append("x", xGraph);
        formData.append("y", yGraph);
        formData.append("r", rValue);

        sendDataToServer(formData);
    });
});
