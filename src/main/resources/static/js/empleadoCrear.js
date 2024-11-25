function obtenerDatosFormularioEmpleado() {
    const empleado = {
        CUIL: parseInt(document.getElementById('cuil').value), // Convertir a número
        dni: parseInt(document.getElementById('dni').value), // Convertir a número
        nombre_completo: document.getElementById('nombre').value,
        direccion: document.getElementById('direccion').value,
        mail: document.getElementById('email').value,
        fecha_nacimiento: document.getElementById('fecha-nacimiento').value,
        localidad_id : document.getElementById('localidad').value
    };

    return empleado;
}
function obtenerDatosHistorial(id_empleado) {
    const historialLaboral = {
        empleado_id:id_empleado,
        rol: document.getElementById('rol').value,
        lugar_trabajo:document.getElementById('lugarTrabajo').value,
        fecha_inicio: document.getElementById('fecha-inicio').value,


    };

    return historialLaboral;
}


async function crearEmpleado(datos) {
    try {
        const response = await fetch('http://localhost:8080/api/adm/empleados', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        if (response.ok) {
            alert('Empleado guardado exitosamente!');  // Mensaje de éxito
        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta
            alert('Error: ' + (errorData.message || 'Hubo un error al guardar el empleado.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}
async function  obtenerIdEmpleado(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/empleados/${cuil}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('empleado no encontrado');
        }
        const empleadoBuscado = await response.json()
        console.log("empleado encontrado:",empleadoBuscado);
        return empleadoBuscado.idempleado;


    } catch (error) {
        console.error('Error al buscar empleado:', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }

}
async function crearHistorial(historial) {
    try {
        const response = await fetch('http://localhost:8080/api/adm/historialLaboral', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(historial)
        });

        if (response.ok) {
            alert('historial guardado exitosamente!');  // Mensaje de éxito
        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta
            alert('Error: ' + (errorData.message || 'Hubo un error al guardar el historial.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}




document.getElementById('crearEmpleadoForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const datos = obtenerDatosFormularioEmpleado();

    if (datos) { // Validar que los datos no sean nulos
        try {
            // Crear empleado y esperar a que se complete
            console.log("Creando empleado...");
            await crearEmpleado(datos);

            // Obtener CUIL para buscar al empleado
           // const cuil = datos.CUIL;

            // Esperar la respuesta del ID del empleado
            console.log("Obteniendo ID del empleado...");
            const id =await obtenerIdEmpleado(datos.CUIL);
            console.log(id);

            if (id) {
                // Crear el historial laboral con el ID obtenido
                console.log("Creando historial laboral...");
                const historial = obtenerDatosHistorial(id);
                console.log(historial);
                await crearHistorial(historial);
            } else {
                console.error("No se pudo obtener el ID del empleado.");
            }
        } catch (error) {
            console.error("Error en el flujo de creación del empleado y su historial:", error);
        }
    }
});

