function obtenerDatosFormulario() {
    const formData = {
        CUIL: parseInt(document.getElementById('cuil').value), // Convertir a número
        dni: parseInt(document.getElementById('dni').value), // Convertir a número
        nombre_completo: document.getElementById('nombre').value,
        direccion: document.getElementById('direccion').value,
        mail: document.getElementById('email').value,
        fecha_nacimiento: document.getElementById('fecha-nacimiento').value,
        telefono: parseInt(document.getElementById('telefono').value), // Convertir a número
        condicion_impositiva: "Consumidor Final",
        localidad_id : document.getElementById('localidad').value
    };

    return formData;
}

async function crearCliente(datos) {
    try {
        const response = await fetch('http://localhost:8080/api/adm/cliente/crear', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        if (response.ok) {
            alert('Cliente guardado exitosamente!');  // Mensaje de éxito
        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta
            alert('Error: ' + (errorData.message || 'Hubo un error al guardar el cliente.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}

document.getElementById('crearClienteForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const datos = obtenerDatosFormulario();
    if (datos) {  // Solo enviar los datos si no son nulos
        console.log(datos);
        crearCliente(datos);
    }
});
