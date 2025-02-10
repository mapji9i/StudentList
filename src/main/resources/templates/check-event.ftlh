<html xmlns="">
<meta>

<head>
    <style>
        * {
            font-size: 20px;
        }

        body,
        html {
            width: 408px
        }
    </style>
    <link rel="stylesheet" href="../styles/button.css">
<script src="../scripts/check-event.js"></script>
</head>

<body>
    <label for="stud">ФИО:</label>
    <br>
    <select id="stud" style="height:40px;width:100%;" onchange="updateForm(obj)">
    </select><br>


    <progress id="progress" max="100" value="0" style="height:30px;width:100%"></progress>
    <br><br>
    <div id="binary_mark">
        <button onclick="handlerFunc(true,obj)" id="true" style="background-color: #34eb3d;height:50px;width:200px"
            class="button">Присутствует</button>
        <button onclick=handlerFunc(false,obj) id="false" style="background-color: #fa0702;height:50px;width:200px"
            class="button">Отсутствует</button>
    </div>
    <div id="numberic_mark">
        <label for="mark">Оценка:</label>
        <input type="number" name="mark" id="mark" /><br>
        <button onclick="setMark(obj)" id="apply" style="background-color: #34eb3d;height:50px;width:100%"
            class="button">Принять</button>
    </div>
    <button onclick="commit(obj)"  class="button">Принять
        изменения</button>
    <br><button onclick="rollback(initialObject)"  class="button">Отменить
        изменения</button>
    <br><button onclick="deleteEvent(obj)"  class="button">Удалить
        мероприятие</button>


    
    <script>
        let counter = 0;
        var JSONstring = <#if JSONobj??> '${JSONobj}' <#else> { "name": "empty" } </#if >
        JSONstring=JSONstring.replaceAll("&quot;","\"")
        let obj = JSON.parse(JSONstring);
        let initialObject = JSON.parse(JSONstring);
        init(obj)
    </script>
</body>

</html>