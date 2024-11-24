function obtenerDatosFormulario() {
    const formData = {
        CUIL: parseInt(document.getElementById('cuil').value), // Convertir a número

        nombre_completo: document.getElementById('nombre').value,
        direccion: document.getElementById('direccion').value,
        mail: document.getElementById('email').value,
        contrato_inicio: document.getElementById('fecha-inicio').value,
        contrato_fin:document.getElementById('fecha-fin').value,


        localidad_id : document.getElementById('localidad').value
    };

    return formData;
}

async function crearProveedor(datos) {
    try {
        const response = await fetch('http://localhost:8080/api/adm/proveedor', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        if (response.ok) {
            alert('Proveedor guardado exitosamente!');  // Mensaje de éxito
        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta
            alert('Error: ' + (errorData.message || 'Hubo un error al guardar el proveedor.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}

document.getElementById('crearProveedorForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const datos = obtenerDatosFormulario();
    if (datos) {  // Solo enviar los datos si no son nulos
        console.log(datos);
        crearProveedor(datos);
    }
});
