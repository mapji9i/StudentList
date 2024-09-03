<html>
<head>
    <style>
        *{
            font-size: 20px !important;
        }
        body, html
        .MainDiv{
        width:408px;
        }

        //@media (max-width:1500px){
        .MainDiv{
            width:100%;

        }
        *{
            font-size: 30% !important;
        }
        .button {
          border: none;
          background-color: #008CBA;
          color: white;
          padding: 15px 5px;
          text-align: center;
          text-decoration: none;
          display: inline-block;
          margin: 4px 2px;
          border-radius: 25px;
          height: 10%;
          width: 100%;
        }
        }
        .button {
          border: none;
          background-color: #008CBA;
          color: white;
          padding: 15px 5px;
          text-align: center;
          text-decoration: none;
          display: inline-block;
          margin: 4px 2px;
          border-radius: 25px;
          height: 50px;
          width: 100%;
        }


    </style>
</head>

<body>
<div class="MainDiv">
<form action="/check-event" class="form" >
    <label for="event-name">Название мероприятия:</label>
    <input  type="text" name="name" id="event-name" value="Лекция" /><br>
    <label for="event-date">Дата мероприятия:</label>
    <input type="date" name="date" id="event-date" required pattern="dd-mm-yyyy"/><br>
    <label for="marks">Тип оценки:</label>
    <select id="marks" name="marksType" >
        <#list marksTypes as markType>
        <option>${markType.friendlyName}</option>
        </#list>
    </select>
    <br>
       <#if exsistEventsSignatures?size != 0>
        <label for="existedEvents">Прошедшие мероприятия:</label>
        <select id="existedEvents"  name="existedEvents" onchange="updateForm()" style="width=100%">
            <option> </option>
            <#list exsistEventsSignatures as exsistEventsSignature>
            <option>${exsistEventsSignature.date?date?string["yyyy-MM-dd"]} ${exsistEventsSignature.name} ${exsistEventsSignature.marksType.friendlyName}</option>
            </#list>
        </select>
        </#if><br>
    <input type="submit" value="Принять" class="button">
</form>
</div>
<script>

    const now = new Date();
    var nowText = now.toLocaleDateString();
    var dateArr=nowText.split(".");
    nowText=dateArr[2]+"-"+dateArr[1]+"-"+dateArr[0]
    document.getElementById("event-date").value=nowText;
    function updateForm(){
        let select = document.getElementById("existedEvents");
        let valueString=select.value;
        let values = valueString.split(" ")
        document.getElementById("event-name").value=values[1];
        document.getElementById("marks").value=values[2];

        document.getElementById("event-date").value=values[0];

    }
</script>
</body>

</html>