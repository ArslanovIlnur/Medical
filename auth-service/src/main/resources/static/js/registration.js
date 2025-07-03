const regBtnEl = document.getElementById("regBtn");

regBtnEl.addEventListener("click", () => {
    const lastnameV = document.getElementById("lastname").value.trim();
    const firstnameV = document.getElementById("firstname").value.trim();
    const patronymicV = document.getElementById("patronymic").value.trim();
    const passwordV = document.getElementById("password").value;

    if (!lastnameV || !firstnameV || !passwordV) {
        alert('Пожалуйста, заполните все поля');
        return;
    }

    fetch('api/user/save', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            username: lastnameV + ' ' + firstnameV + ' ' + patronymicV,
            password: passwordV
        })
    })
    .then(res => {
        if (!res.ok) {
            return res.text().then(text => { throw new Error(text); });
        }
        return res.text();
    })
    .then(data => {
        alert(data);
        window.location.href = '/';
    })
    .catch(error => alert('Ошибка: ' + error.message));
});
