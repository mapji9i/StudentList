<html xmlns="http://www.w3.org/1999/html">

<style>
    *{
    font-size: 20px;
}
.button {
border: none;
color: white;
padding: 15px 32px;
text-align: center;
text-decoration: none;
display: inline-block;

margin: 4px 2px;
border-radius: 25px;
}

.MainDiv{
width:408px;
}

@media screen and max-width<1300px{
.MainDiv{
    width:100%;
}
}

</style>

<body>
<div class="MainDiv">
<form action="/load-excell-file" class="form" method="post" enctype="multipart/form-data">
    <label for="select-excell-file">Загрузите файл со списком группы</label>
    <br>
    <input  type="file" name="file" id="select-excell-file" style="width:100%;"/><br>
    <input type="submit" value="Принять" class="button" style="background-color: #008CBA;height:50px;width:100%">
</form>
<#if files?? && files?size!=0>
<form action="/load-backup" class="form">
    <label for="select-backup-file">Выберите файл бэкапа</label>
    <br>
    <select id="existedEvents"  name="backupFileName" id="select-backup-file" style="width:100%;">
        <option> </option>
        <#list files as file>
        <option>${file}</option>
        </#list>
    </select>
    <br>
    <input type="submit" value="Восстановить" class="button" style="background-color: #008CBA;height:50px;width:100%">
</form>
</#if>
<br>
<button onclick="window.location.replace('/students-list')" style="background-color: #008CBA;height:50px;width:100%"
        class="button">Отменить
</button>
</div>
</body>

</html>