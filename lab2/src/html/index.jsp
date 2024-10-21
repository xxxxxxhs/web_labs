<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>xxxxxx</title>

    <style>

        .hit-row {
            background-color: #98FB98;
        }
        .miss-row {
            background-color: #FFCCCB;
        }
        .main-area {
            background-color: #111;
            padding: 20px;
            width: 100%;
            height: 100%;
            border: 1px solid #555;
            display: block;
            box-sizing: border-box;
        }

        svg {
            width: 100%;
            height: 100%;
        }
        .area {
            fill: rgba(0, 0, 255, 0.2);
        }

        .axis-text {
            fill: white;
            font-family: serif;
            font-size: 10px;
        }

        .axis-line {
            stroke: white;
            stroke-width: 0.5;
        }
        body {
            font-family: serif;
            background-color: #333;
            color: #fff;
            margin: 0;
            padding: 0;
        }

        table {
            width: 100%;
            border-spacing: 2px;
        }

        td.main-area {
            width: 100%;
        }

        .header {
            font-family: serif;
            font-style: normal;
            font-size: 20px;
            color: #fff;
            background-color: #140839;
            padding: 4px 20px;
            width: 100%;
            box-sizing: border-box;
        }
        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .form-section {
            background-color: #222;
            padding: 20px;
            width: 10%;
            vertical-align: top;
            border: 1px solid #555;
        }

        .form-section input,
        .form-section select{
            display: block;
            width: 100%;
            margin-bottom: 10px;
            margin-top: 5px;
            padding: 10px;
            border: 1px solid #555;
            background-color: #333;
            color: #fff;
            border-radius: 5px;
        }

        .form-section input {
            width: 81%;
        }

        .radio-group {
            display: flex;
            flex-direction: column;
            margin-bottom: 10px;
            margin-top: 7px;
        }

        .radio-group label {
            margin-bottom: 5px;
            display: flex;
            gap: 5px;
        }

        .radio-group label span {
            margin: 0px;
        }
        .radio-group input[type="radio"] {
            padding: 0px;
            margin: 0px;
            width: auto;
        }

        button {
            width: 100%;
            padding: 10px;
        }

        .main-area {
            background-color: #111;
            padding: 20px;
            width: 50%;
            border: 1px solid #555;
        }

        .log-section {
            background-color: #222;
            padding: 20px;
            width: 25%;
            vertical-align: top;
            border: 1px solid #555;
        }

        .log-section table {
            width: 100%;
            border-collapse: collapse;
        }

        .log-section th, .log-section td {
            padding: 10px;
            border: 1px solid #555;
            text-align: center;
        }

        input[type="number"] {
            border-radius: 5px;
        }

        .form-section input:focus {
            border-color: #777;
        }

        .form-section input {
            margin-bottom: 15px;
        }

    </style>
</head>
<body>

<table>
    <tr>
        <td colspan="3" class="header">
            <div class="header-content">
                <span style="text-align: left">Шаматульский Роман</span>
                <span style="text-align: center">Р3212</span>
                <span style="text-align: right">409870</span>
            </div>
        </td>
    </tr>


    <tr>
        <td class="form-section">
            <form id="pointForm">
                <label>X:</label>
                <div class="radio-group">
                    <label><span>-3</span><input type="radio" name="x" value="-3"></label>
                    <label><span>-2</span><input type="radio" name="x" value="-2"></label>
                    <label><span>-1</span><input type="radio" name="x" value="-1"></label>
                    <label><span>0</span><input type="radio" name="x" value="0"></label>
                    <label><span>1</span><input type="radio" name="x" value="1"></label>
                    <label><span>2</span><input type="radio" name="x" value="2"></label>
                    <label><span>3</span><input type="radio" name="x" value="3"></label>
                    <label><span>4</span><input type="radio" name="x" value="4"></label>
                    <label><span>5</span><input type="radio" name="x" value="5"></label>
                </div>

                <label for="y">Y (-5; 5):</label>
                <input type="text" id="y" name="y" pattern="^-?[0-5](\.\d+)?$" title="Введите число от -5 до 5" required>

                <label>R:</label>
                <select id="r">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>


                <button type="submit" id="submitBtn">Проверить</button>
            </form>
        </td>


        <td class="main-area">

            <svg viewBox="-100 -100 200 200">
                <line x1="-90" y1="0" x2="90" y2="0" class="axis-line" />
                <line x1="0" y1="-90" x2="0" y2="90" class="axis-line" />

                <line x1="90" y1="0" x2="85" y2="-5" class="axis-line" />
                <line x1="90" y1="0" x2="85" y2="5" class="axis-line" />

                <line x1="0" y1="-90" x2="-5" y2="-85" class="axis-line" />
                <line x1="0" y1="-90" x2="5" y2="-85" class="axis-line" />

                <line x1="-80" y1="5" x2="-80" y2="-5" class="axis-line" />
                <line x1="-40" y1="5" x2="-40" y2="-5" class="axis-line" />
                <line x1="40" y1="5" x2="40" y2="-5" class="axis-line" />
                <line x1="80" y1="5" x2="80" y2="-5" class="axis-line" />

                <line x1="5" y1="80" x2="-5" y2="80" class="axis-line" />
                <line x1="5" y1="40" x2="-5" y2="40" class="axis-line" />
                <line x1="5" y1="-40" x2="-5" y2="-40" class="axis-line" />
                <line x1="5" y1="-80" x2="-5" y2="-80" class="axis-line" />

                <text x="-85" y="15" class="axis-text">-R</text>
                <text x="-50" y="15" class="axis-text">-R/2</text>
                <text x="33" y="15" class="axis-text">R/2</text>
                <text x="77" y="15" class="axis-text">R</text>

                <text x="10" y="82" class="axis-text">-R</text>
                <text x="10" y="42" class="axis-text">-R/2</text>
                <text x="10" y="-37" class="axis-text">R/2</text>
                <text x="10" y="-77" class="axis-text">R</text>

                <text x="90" y="-10" class="axis-text">X</text>
                <text x="6" y="-93" class="axis-text">Y</text>

                <rect x="-80" y="-80" width="80" height="80" class="area" />
                <path d="M 80,0 A 80,80 0 0 0 0,-80 L 0,0 Z" class="area"/>
                <polygon points="0,0 -80,0 0,80" class="area" />

                <g id="points-group"></g>
            </svg>
        </td>


        <td class="log-section">
            <h2>Лог запросов</h2>
            <jsp:include page="/results.jsp" />
        </td>
    </tr>
</table>
<script src="script.js"></script>
</body>
</html>
