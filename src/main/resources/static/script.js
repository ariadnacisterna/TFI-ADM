document.getElementById('loginForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    // Obtener los valores del formulario
    const mail = document.getElementById('mail').value;
    const cuil = document.getElementById('cuil').value;

    // Crear el objeto para enviar
    const loginData = {
        mail: mail,
        cuil: cuil
    };

    try {
        // Enviar solicitud POST al backend
        const response = await fetch('http://localhost:8080/api/adm/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData)
        });

        const result = await response.json();

        // Verificar la respuesta
        if (response.ok) {
            document.getElementById('message').innerHTML = `<span style="color: green;">${result.message}</span>`;
        } else {
            document.getElementById('message').innerHTML = `<span style="color: red;">${result.message}</span>`;
        }
    } catch (error) {
        console.error('Error al hacer login:', error);
        document.getElementById('message').innerHTML = `<span style="color: red;">Error al hacer login, intente de nuevo.</span>`;
    }
});
