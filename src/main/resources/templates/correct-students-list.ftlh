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


<link rel="stylesheet" href="../styles/button.css">

<body>
    <table>
        <#list events[0].values as x>
            <tr align="left">
                <#list events as event>

                        <td>${event.values[x?index]}</td>

                </#list>
            </tr>
        </#list>
    </table>
<br>
<button onclick="window.location.replace('/students-list')"
        class="button">Принять изменения
</button>
<br>
<button onclick="window.location.replace('/students-list')"
        class="button">Отменить изменения
</button>
</body>

</html>