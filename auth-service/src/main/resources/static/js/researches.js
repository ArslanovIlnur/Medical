document.getElementById('date').valueAsDate = new Date();

const addBtnEl = document.getElementById('addBtn');
const patSearchIdInput = document.getElementById('patSearchId');

addBtnEl.addEventListener("click", () => {
    const patIdV = document.getElementById('patId').value.trim();
    const initialsV = document.getElementById('initials').value.trim();
    const resTypeV = document.getElementById('resType').value.trim();
    const resZoneV = document.getElementById('resZone').value.trim();
    const dateV = document.getElementById('date').value.trim();

    if (!patIdV || !initialsV || !resTypeV || !resZoneV || !dateV) {
        alert('Пожалуйста, заполните все поля');
        return;
    }

    fetch('/research/api/research/save', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            date: dateV,
            patientId: patIdV,
            initials: initialsV,
            researchType: resTypeV,
            researchZone: resZoneV
        })
    })
    .then(res => {
        if (!res.ok) {
            return res.text().then(text => { throw new Error(text) });
        }
        return res.text();
    })
    .then(data => {
        alert(data);
        window.location.href = '/auth/research_list';
    })
    .catch(error => alert('Ошибка: ' + error.message));
});

async function getPatients() {
    try {
        const patSearchIdV = patSearchIdInput.value.trim();
        const url = patSearchIdV === ''
            ? '/research/api/research/list'
            : '/research/api/research/list?patientId=' + encodeURIComponent(patSearchIdV);

        const response = await fetch(url);
        const patients = await response.json();

        if (!Array.isArray(patients)) {
            throw new Error('Сервер вернул некорректный формат данных');
        }

        const tBody = document.getElementById('patTblBody');
        tBody.innerHTML = '';

        patients.forEach(patient => {
            const newRow = tBody.insertRow();

            const cellDate = newRow.insertCell();
            const formattedDate = new Date(patient.date).toLocaleDateString('ru-RU', {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });
            cellDate.textContent = formattedDate;

            const cellPatientId = newRow.insertCell();
            cellPatientId.textContent = patient.patientId;

            const cellPatientName = newRow.insertCell();
            cellPatientName.textContent = patient.initials;

            const cellPatientRType = newRow.insertCell();
            cellPatientRType.textContent = patient.researchType;

            const cellPatientRZone = newRow.insertCell();
            cellPatientRZone.textContent = patient.researchZone;
        });
    } catch (error) {
        console.error('Ошибка при загрузке пациентов:', error);
    }
}

patSearchIdInput.addEventListener('input', getPatients);

getPatients();
