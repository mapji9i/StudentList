<html xmlns="">
<meta>
<head>
    <style>
        *{
            font-size: 20px;
        }
        body, html
        {
            width:408px
        }

    </style>
    <style>
        .button {
          border: none;
          color: white;
          padding: 15px 10px;
          text-align: center;
          text-decoration: none;
          display: inline-block;
          margin: 2px 0px;
          border-radius: 25px;
        }
    </style>

</head>

<body>
<label for="stud">ФИО:</label>
<br>
<select id="stud" style="height:40px;width:100%;" onchange="updateForm()">
</select><br>


<progress id="progress"  max="100" value="0" style="height:30px;width:100%"></progress>
<br><br>
<div id="binary_mark">
<button onclick="handlerFunc(true)" id="true" style="background-color: #34eb3d;height:50px;width:200px" class="button">Присутствует</button>
    <button onclick=handlerFunc(false) id="false" style="background-color: #fa0702;height:50px;width:200px" class="button">Отсутствует</button>
</div>
<div id="numberic_mark">
    <label for="mark" >Оценка:</label>
    <input type="number" name="mark" id="mark"/><br>
    <button onclick="setMark()" id="apply" style="background-color: #34eb3d;height:50px;width:100%" class="button">Принять</button>
</div>
<button onclick="commit()" style="background-color: #008CBA;height:50px;width:100%" class="button">Принять изменения</button>
<br><button onclick="rollback()" style="background-color: #008CBA;height:50px;width:100%" class="button">Отменить изменения</button>
<br><button onclick="deleteEvent()" style="background-color: #008CBA;height:50px;width:100%" class="button">Удалить мероприятие</button>

<script>

    let counter=0;

    var JSONstring=<#if JSONobj??>'${JSONobj}'<#else>{"name":"empty"}}</#if>

    JSONstring=JSONstring.replaceAll("&quot;","\"")

    let obj = JSON.parse(JSONstring);
    let initialObject=JSON.parse(JSONstring);

    if(obj.name=="empty"){ window.location.replace('/students-list')};

    var studentId=0;
    var list = document.getElementById("stud");
 var progress = document.getElementById("progress");
    for (let i = 0; i < obj.studentsNames.length; i++) {
        let newOption = new Option(obj.studentsNames[i])

        list.add(newOption)

    }
    list.options[studentId].selected = true;

    if (obj.eventSignature.marksType=="BOOLEAN"){
        document.getElementById("numberic_mark").style.display = 'none';
    }else{
        var mark=document.getElementById("mark");
        document.getElementById("binary_mark").style.display = 'none';
        mark.value=obj.values[studentId];
        mark.focus();
        mark.select();
    }
    updateForm();
if(obj.eventSignature.marksType=="NUMBERIC"){
  document.addEventListener(
  "keydown",
  (event) => {
    const keyName = event.key;
    if (keyName === "Enter") {
      setMark();
      return;
    }
  },
  false,
);
};

    function handlerFunc(key){
        studentId=list.selectedIndex;
        if (key) {
            obj.values[studentId]=true;

        }else{
            obj.values[studentId]=false;
        }
        while(!obj.values[++studentId]=="true");
        if(studentId<obj.studentsNames.length){
            list.options[studentId].selected = true;

        }else{

            request("students-list",obj);
        }
            updateForm();
        }
    function setMark(){
        obj.values[studentId]=parseInt(document.getElementById("mark").value);
        studentId++;

         if(studentId<obj.studentsNames.length){
           list.options[studentId].selected = true;
        mark.value=obj.values[studentId];
        mark.focus();
        mark.select();

        }else{
            request("students-list",obj);
        }
        updateForm();

    }
    function updateForm(){
       if(obj.eventSignature.marksType=="NUMBERIC"){
           mark.value=obj.values[list.selectedIndex];
           mark.focus();
           mark.select();
       }else{
   //background-color: white;
  //color: black;
  //border: 2px solid #04AA6D;
        let trueButton=document.getElementById("true");
        let falseButton=document.getElementById("false");
        if(obj.values[list.selectedIndex]===true){
            trueButton.style.backgroundColor  = '#34eb3d';
            falseButton.style.backgroundColor  ='#fc8683';
            falseButton.style.border  = '2px solid #fa0702';
        }else{
            trueButton.style.backgroundColor  = '#88fc8e';

            trueButton.style.border  = '2px solid #34eb3d';

            falseButton.style.backgroundColor  ='#fa0702';
        }
       }
       progress.value=(list.selectedIndex/obj.studentsNames.length)*100;
    }
    function commit(){
        request("students-list",obj)
    }
    function rollback(){
        request("students-list",initialObject)
    }
    function deleteEvent(){
        const result = confirm("Вы уверены что хотите удалить мероприятие");
        if(result===true)
            request("delete-event",obj);
    }

    function request(path, object){
      fetch(path, {
      method: "POST",
      body: JSON.stringify(object),
      headers: {"Content-type": "application/json; charset=UTF-8"}

    });
    //sleep(100);
    setTimeout(() => { window.location.replace('/students-list'); }, 100);
    //window.location.replace('/students-list');
    }

</script>
</body>
</html>