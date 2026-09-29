const BASE="http://localhost:8080";

let chart;

showPage("dashboard");

async function refreshDashboard(){

    const houses=await fetch(BASE+"/households").then(r=>r.json());

    const gen=await fetch(BASE+"/generation").then(r=>r.json());

    const con=await fetch(BASE+"/consumption").then(r=>r.json());

    document.getElementById("houseCount").innerText=houses.length;

    const totalGen=gen.reduce((a,b)=>a+b.totalUnits,0);

    const totalCon=con.reduce((a,b)=>a+b.consumedUnits,0);

    document.getElementById("genCount").innerText=totalGen;

    document.getElementById("conCount").innerText=totalCon;

    document.getElementById("exportCount").innerText=Math.max(totalGen-totalCon,0);

    loadHouseholds(houses);

    loadGeneration(gen);

    loadConsumption(con);

    drawChart(gen,con);

}

function showPage(id){

    document.querySelectorAll(".page")
        .forEach(p=>p.classList.remove("active"));

    document.getElementById(id).classList.add("active");

    refreshDashboard();

}

function loadHouseholds(data){

    householdTable.innerHTML=data.map(h=>`
<tr>
<td>${h.id}</td>
<td>${h.ownerName}</td>
<td>${h.shareRatio}</td>
</tr>`).join("");

    const options=data.map(h=>`
<option value="${h.id}">
${h.ownerName}
</option>`).join("");

    householdSelect.innerHTML=options;
    summarySelect.innerHTML=options;

}

function loadGeneration(data){

    generationCards.innerHTML=data.map(g=>`
<div class="record">
<div>
<b>${g.date}</b>
</div>
<div>${g.totalUnits} Units</div>
</div>`).join("");

}

function loadConsumption(data){

    consumptionCards.innerHTML=data.map(c=>`
<div class="record">
<div>
<b>${c.household.ownerName}</b>
</div>
<div>${c.consumedUnits} Units</div>
</div>`).join("");

}

async function addHousehold(){

    await fetch(BASE+"/households",{

        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify({

            ownerName:ownerName.value,
            shareRatio:Number(shareRatio.value)

        })

    });

    ownerName.value="";
    shareRatio.value="";

    refreshDashboard();

}

async function addGeneration(){

    await fetch(BASE+"/generation",{

        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify({

            date:genDate.value,
            totalUnits:Number(totalUnits.value)

        })

    });

    refreshDashboard();

}

async function addConsumption(){

    await fetch(BASE+"/consumption",{

        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify({

            date:conDate.value,
            consumedUnits:Number(consumedUnits.value),
            household:{id:Number(householdSelect.value)}

        })

    });

    refreshDashboard();

}

async function loadSummary(){

    const text=await fetch(BASE+"/summary/"+summarySelect.value)
        .then(r=>r.text());

    summaryResult.textContent=text;

}

function drawChart(gen,con){

    const ctx=document.getElementById("energyChart");

    if(chart) chart.destroy();

    chart=new Chart(ctx,{

        type:"line",

        data:{

            labels:gen.map(g=>g.date),

            datasets:[

                {
                    label:"Generation",
                    data:gen.map(g=>g.totalUnits),
                    borderColor:"#16a34a",
                    tension:.4
                },
                {
                    label:"Consumption",
                    data:con.map(c=>c.consumedUnits),
                    borderColor:"#2563eb",
                    tension:.4
                }

            ]

        }

    });

}