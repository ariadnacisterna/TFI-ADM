// Función para mostrar los empleados en la tabla con los botones de eliminar y modificar
function mostrarEmpleadosEnTabla(empleados) {
    const tbody = document.getElementById('empleados-tbody');
    tbody.innerHTML = ''; // Limpiar la tabla antes de llenarla

    if (empleados.length === 0) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.colSpan = 9; // Asegúrate de que el colspan cubra todas las columnas
        cell.textContent = 'No se encontraron empleados.';
        row.appendChild(cell);
        tbody.appendChild(row);
        return;
    }

    // Recorrer los empleados y agregar las filas a la tabla
    empleados.forEach(empleado => {
        const row = document.createElement('tr');


        const nombreCell = document.createElement('td');
        nombreCell.textContent = empleado.nombre_completo;
        row.appendChild(nombreCell);

        const direccionCell = document.createElement('td');
        direccionCell.textContent = empleado.direccion;
        row.appendChild(direccionCell);

        const emailCell = document.createElement('td');
        emailCell.textContent = empleado.mail;
        row.appendChild(emailCell);


        const cuilCell = document.createElement('td');
        cuilCell.textContent = empleado.cuil;
        row.appendChild(cuilCell);

        const localidadCell = document.createElement('td');
        localidadCell.textContent = empleado.localidad_id.nombre;
        row.appendChild(localidadCell);

        // Columna para los botones de acción (Eliminar y Modificar)
        const accionesCell = document.createElement('td');

        // Botón de modificar con icono de Material Icons
        const modificarBtn = document.createElement('button');
        modificarBtn.className = 'btn btn-warning btn-sm me-2';
        modificarBtn.innerHTML = '<span class="material-icons">edit</span>'; // Icono de editar
        modificarBtn.onclick = () => modificarCliente(cliente.idcliente); // Función para modificar
        accionesCell.appendChild(modificarBtn);

        // Botón de eliminar con icono de Material Icons
        const eliminarBtn = document.createElement('button');
        eliminarBtn.className = 'btn btn-danger btn-sm';
        eliminarBtn.innerHTML = '<span class="material-icons">delete</span>'; // Icono de eliminar
        eliminarBtn.onclick = () => eliminarCliente(cliente.idcliente); // Función para eliminar
        accionesCell.appendChild(eliminarBtn);

        row.appendChild(accionesCell);

        tbody.appendChild(row);
    });
}

async function mostrarEmpleadoEnFormulario(empleado){


        document.getElementById('cuil2').value = empleado.CUIL;
        document.getElementById('nombre').value = empleado.nombre_completo;
        document.getElementById('dni').value=empleado.dni;
        document.getElementById('direccion').value=empleado.direccion;

}





// Cargar todos los empleados al iniciar la página
async function cargarEmpleados() {
    try {
        // Enviar la solicitud GET para obtener todos los clientes
        const response = await fetch('http://localhost:8080/api/adm/empleados', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const empleados = await response.json();
            console.log('Empleados cargados:', empleados);
            mostrarEmpleadosEnTabla(empleados); // Llamar a la función para mostrar todos los empleados
        } else {
            const errorData = await response.json();
            alert('Error: ' + (errorData.message || 'Hubo un error al cargar los empleados.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}
async function buscarEmpleado(cuil) {
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

        // Convertir la respuesta a un arreglo para usar la misma función de mostrar
        mostrarEmpleadosEnTabla([empleadoBuscado]); // Llamamos la función para mostrar el empleado
        mostrarEmpleadoEnFormulario(empleadoBuscado);
    } catch (error) {
        console.error('Error al buscar empleado:', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}


window.onload = cargarEmpleados;

// Función para obtener los datos del formulario (Cuil)
function obtenerDatosBusqueda() {
    const cuil = document.getElementById('cuil').value;
    return cuil;
};



// Manejar el envío del formulario de búsqueda
document.getElementById('buscar-empleado-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar el comportamiento por defecto del formulario

    const cuil = obtenerDatosBusqueda(); // Obtener el CUIL ingresado
    if (cuil) {
       buscarEmpleado(cuil); // Buscar empleado por cuil
    } else {
       alert('Por favor, ingrese un cuil para buscar.');
}


});
