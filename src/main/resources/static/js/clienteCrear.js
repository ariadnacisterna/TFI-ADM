// Función para manejar el envío del formulario
async function enviarFormulario(event) {
    event.preventDefault(); // Prevenir el comportamiento por defecto del formulario

    // Obtener los valores de los campos del formulario
    const cuil = document.getElementById('cuil').value;
    const dni = document.getElementById('dni').value;
    const nombreCompleto = document.getElementById('nombre').value;
    const direccion = document.getElementById('direccion').value;
    const mail = document.getElementById('email').value;
    const fechaNacimiento = document.getElementById('fecha-nacimiento').value;
    const telefono = document.getElementById('telefono').value;
    const localidad = document.getElementById('localidad').value;


    const clienteData = {
        cuil: cuil,
        dni: dni,
        nombre_completo: nombreCompleto,
        direccion: direccion,
        mail: mail,
        fecha_nacimiento: fechaNacimiento,
        telefono: telefono,
        condicion_impositiva: "Consumidor Final", // Este campo debería ajustarse a tu lógica
        localidad_id: 1 // Este campo puede necesitar lógica adicional para capturar el ID correcto
    };

    const response = await fetch('http://localhost:8080/api/adm/cliente/crear', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(clienteData)
    });

    if (response.ok) {
        alert('Cliente guardado exitosamente!'); // Mensaje de éxito
    } else {
        const errorData = await response.json(); // Obtener el cuerpo de la respuesta
        alert('Error: ' + errorData.message); // Mostrar el mensaje de error
    }
}

// Asociar la función al evento de envío del formulario
document.querySelector('form').addEventListener('submit', enviarFormulario);
