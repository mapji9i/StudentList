<html xmlns="http://www.w3.org/1999/html">
<style>
    *{
        font-size: 20px !important;

    }
</style>
<style>
    table, th, td {
      border:1px solid black;
    }
</style>
<style>
    table>*{

        margin: 4px 2px;
    }
</style>
<style>
    .button {
      border: none;
      background-color: #008CBA;
      color: white;
      //padding: 15px 5px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
      margin: 4px 2px;
      border-radius: 25px;
      height: 50px;
      width: 408px;
    }

</style>

<body>
    <table>

        <tr>
            <#list events as event>
                <th>${event.eventSignature.name}</th>
            </#list>
        </tr>

        <tr>
            <#list events as event>
                <#if event.eventSignature.date??>
                    <td>${event.eventSignature.date?date}</td>
                <#else>
                    <td></td>
                </#if>
            </#list>
        </tr>
        <#list events[0].values as x>
            <tr align="left">

                <#list events as event>
                    <#if event.values[x?index]?is_string>
                        <td>${event.values[x?index]}</td>
                    <#else>
                        <td>${event.values[x?index]?c}</td>
                    </#if>
                </#list>
            </tr>
        </#list>
    </table>
<br>
<button onclick="window.location.replace('/create-event')"
        class="button">Добавить/изменить мероприятие
</button>
<br>
<button onclick="window.location.replace('/load-excell-file')"
        class="button">Загрузить список группы
</button>
</body>

</html>