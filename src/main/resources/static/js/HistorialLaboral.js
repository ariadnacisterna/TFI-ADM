windowonlad=cargarHistoriales();

function cargarHistoriales() {
    document.addEventListener('DOMContentLoaded', async function () {
        // Obtener el valor de 'cuil' de la URL
        const urlParams = new URLSearchParams(window.location.search);
        const cuil = urlParams.get('cuil');

        if (cuil) {
            console.log('CUIL recibido: ', cuil);
            // Usar el valor de cuil para obtener el historial del empleado
            // Por ejemplo, llamar a una función que recupere los datos del historial
            const id_empleado = await obtenerIdEmpleado(cuil);
            console.log(id_empleado);
            obtenerHistoriales(id_empleado);
        } else {
            console.log("No se recibió un CUIL válido en la URL");
        }
    });
}

function mostrarHistorialEnTabla(historiales) {
    console.log(historiales);
    const tbody = document.getElementById('historiales-tbody');
    tbody.innerHTML = ''; // Limpiar la tabla antes de llenarla

    if (historiales.length === 0) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.colSpan = 9; // Asegúrate de que el colspan cubra todas las columnas
        cell.textContent = 'No se encontraron historiales.';
        row.appendChild(cell);
        tbody.appendChild(row);
        return;
    }
    // Recorrer los historiales y agregar las filas a la tabla
    historiales.forEach(historial => {
        const row = document.createElement('tr');

        const lugarTrabajoCell = document.createElement('td');
        lugarTrabajoCell.textContent = historial.lugar_trabajo;
        row.appendChild(lugarTrabajoCell);
        const rolCell = document.createElement('td');
        rolCell.textContent = historial.rol;
        row.appendChild(rolCell);
        const inicioCell = document.createElement('td');
        inicioCell.textContent = historial.fecha_inicio;
        row.appendChild(inicioCell);
        const finCell = document.createElement('td');
        if (historial.fecha_fin === null || historial.fecha_fin === '') {
            finCell.textContent = 'Vigente'; // Si la fecha de fin es nula o vacía, poner "vigente"
        } else {
            finCell.textContent = historial.fecha_fin; // Si tiene una fecha, mostrarla
        }

        row.appendChild(finCell);

        // Columna para los botones de acción
        const accionesCell = document.createElement('td');
        // Botón de modificar con icono de Material Icons
        const verDetallesBtn = document.createElement('button');
        verDetallesBtn.className = 'btn btn-info btn-sm me-2';
        verDetallesBtn.innerHTML = '<span class="material-icons">visibility</span>'; // Icono de ver detalles
        verDetallesBtn.onclick = () =>mostrarHistorialEnFormulario(historial);
        accionesCell.appendChild(verDetallesBtn);
        row.appendChild(accionesCell);
        tbody.appendChild(row);
    });
}
async function mostrarHistorialEnFormulario(historial){


    document.getElementById('rol').value =historial.rol;
    document.getElementById('lugarTrabajo').value=historial.lugar_trabajo;
    document.getElementById('fecha-inicio').value=historial.fecha_inicio;
    document.getElementById('fecha-fin').value=historial.fecha_fin;


}
function obtenerDatosHistorial(id_empleado) {
    const historialLaboralNuevo = {
        empleado_id:id_empleado,
        rol: document.getElementById('rolNuevo').value,
        lugar_trabajo:document.getElementById('lugarTrabajoNuevo').value,
        fecha_inicio: document.getElementById('fecha-inicio-nuevo').value,


    };

    return historialLaboralNuevo;
}




async function obtenerIdEmpleado(cuil) {
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

        const empleadoBuscado = await response.json();
        return empleadoBuscado.idempleado;



    } catch (error) {
        console.error('Error al buscar empleado:', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}
async function obtenerHistoriales(id) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/historialLaboral/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('empleado no encontrado');
        }

        const historiales = await response.json();
        console.log(historiales);
        mostrarHistorialEnTabla(historiales);




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
            alert('historial guardado exitosamente!');

        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta
            alert('Error: ' + (errorData.message || 'Hubo un error al guardar el historial.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}

document.getElementById('activar-form-nuevo-historial').addEventListener('click', function() {
    const formCrear = document.getElementById('form-crear-historial');
    formCrear.style.display = formCrear.style.display === 'none' ? 'block' : 'none';
});

document.getElementById('crerNuevoHistorial').addEventListener('click', async function (event) {
    event.preventDefault();
    const urlParams = new URLSearchParams(window.location.search);
    const cuil = urlParams.get('cuil');
    console.log(cuil);
    const id_empleado =await obtenerIdEmpleado(cuil);
    const historial_nuevo=  obtenerDatosHistorial(id_empleado);
    console.log(historial_nuevo);
    await crearHistorial(historial_nuevo);
    await obtenerHistoriales(id_empleado);



});


