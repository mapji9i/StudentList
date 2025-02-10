<html xmlns="http://www.w3.org/1999/html">

<head>
    <style>
        * {
            font-size: 20px !important;
        }
    </style>
    <style>
        table,
        th,
        td {
            border: 2px solid #1e1a3d;
            border-collapse: collapse;
        }
    </style>
    <style>
        table>* {
            margin: 4px 2px;
        }
    </style>

    <style>
        html {
            width: 408px
        }
    </style>
    <link rel="stylesheet" href="../styles/button.css">
    <script src="../scripts/random-tickets.js"> </script>
</head>

<body>
    <br>
    <label for="tickets-number" id="tickets-number-label">Число билетов:</label>
    <input type="number" name="name" id="tickets-number" value="15" /><br>
    <br>
    <button onclick="generateRandomTicketsNumbers(students)" class="button">Раздать билеты
    </button>
    <br>
    <button onclick="window.location.replace('/students-list')" class="button">Вернуться
    </button>
    <script>
        var students = [<#list students as student>"${student}",</#list>];
        init(students);    
    </script>
</body>

</html>