<html xmlns="http://www.w3.org/1999/html">
<style>
    *{
        font-size: 20px !important;
    }
</style>
<style>
    table, th, td {
     border: 2px solid #1e1a3d;
    border-collapse: collapse;
    }
</style>
<style>
    table>*{
        margin: 4px 2px;
    }
</style>

<link rel="stylesheet" href="../styles/button.css">


<body>

    <table >

        <tr bgcolor="#5348b0">
            <#list events as event>
                <th>${event.eventSignature.name}</th>
            </#list>
        </tr>

         <tr bgcolor="#5348b0">
            <#list events as event>
                <#if event.eventSignature.date??>
                    <td>${event.eventSignature.date?date}</td>
                <#else>
                    <td></td>
                </#if>
            </#list>
        </tr>
        <#list events[0].values as x>
            <#if x?index%2==0>
            <tr align="left" bgcolor="#0561f5">
            <#else >
                <tr align="left" bgcolor="#73a7fa">
            </#if>
                <#list events as event>
                    <#if event.values[x?index]?is_string>
                        <td >${event.values[x?index]}</td>
                    <#else>
                        <td>${event.values[x?index]?c}</td>
                    </#if>
                </#list>
            </tr>
        </#list>
    </table>
<br>
<div style="width: 408px">
<button onclick="window.location.replace('/create-event')"
        class="button">Добавить/изменить мероприятие
</button>
<br>
<button onclick="window.location.replace('/load-excell-file')"
        class="button">Загрузить список группы
</button>
<br>
<button onclick="window.location.replace('/correct-students-list')"
        class="button">Редактировать список группы
</button>
<br>
<button onclick="window.location.replace('/random-tickets')"
        class="button">Раздать билеты
</button>
</div>
</body>

</html>