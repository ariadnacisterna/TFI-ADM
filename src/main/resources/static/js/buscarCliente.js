// Función para mostrar los clientes en la tabla con los botones de eliminar y modificar
function mostrarClientesEnTabla(clientes) {
    const tbody = document.getElementById('clientes-tbody');
    tbody.innerHTML = ''; // Limpiar la tabla antes de llenarla

    if (clientes.length === 0) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.colSpan = 9; // Asegúrate de que el colspan cubra todas las columnas
        cell.textContent = 'No se encontraron clientes.';
        row.appendChild(cell);
        tbody.appendChild(row);
        return;
    }

    // Recorrer los clientes y agregar las filas a la tabla
    clientes.forEach(cliente => {
        const row = document.createElement('tr');

        const idCell = document.createElement('td');
        idCell.textContent = cliente.idcliente;
        row.appendChild(idCell);

        const nombreCell = document.createElement('td');
        nombreCell.textContent = cliente.nombre_completo;
        row.appendChild(nombreCell);

        const direccionCell = document.createElement('td');
        direccionCell.textContent = cliente.direccion;
        row.appendChild(direccionCell);

        const emailCell = document.createElement('td');
        emailCell.textContent = cliente.mail;
        row.appendChild(emailCell);

        const telefonoCell = document.createElement('td');
        telefonoCell.textContent = cliente.telefono;
        row.appendChild(telefonoCell);

        const dniCell = document.createElement('td');
        dniCell.textContent = cliente.dni;
        row.appendChild(dniCell);

        const cuilCell = document.createElement('td');
        cuilCell.textContent = cliente.cuil;
        row.appendChild(cuilCell);

        const localidadCell = document.createElement('td');
        localidadCell.textContent = cliente.localidad_id.nombre;
        row.appendChild(localidadCell);

        // Columna para los botones de acción (Eliminar y Modificar)
        const accionesCell = document.createElement('td');

        // Botón de modificar con icono de Material Icons
        const verDetallesBtn = document.createElement('button');
        verDetallesBtn.className = 'btn btn-info btn-sm me-2';
        verDetallesBtn.innerHTML = '<span class="material-icons">visibility</span>'; // Icono de ver detalles
        verDetallesBtn.onclick = () => mostrarDetalles(cliente.idcliente);
        accionesCell.appendChild(verDetallesBtn);
        row.appendChild(accionesCell);
        tbody.appendChild(row);
    });
}


// Cargar todos los clientes al iniciar la página
async function cargarClientes() {
    try {
        // Enviar la solicitud GET para obtener todos los clientes
        const response = await fetch('http://localhost:8080/api/adm/cliente/todos', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const clientes = await response.json();
            console.log('Clientes cargados:', clientes);
            mostrarClientesEnTabla(clientes); // Llamar a la función para mostrar todos los clientes
        } else {
            const errorData = await response.json();
            alert('Error: ' + (errorData.message || 'Hubo un error al cargar los clientes.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}

window.onload = cargarClientes;

// Función para obtener los datos del formulario (DNI)
function obtenerDatosBusqueda() {
    const dni = document.getElementById('dni').value;
    return dni;
};

// Función para buscar el cliente por DNI
async function buscarCliente(dni) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/cliente/${dni}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Cliente no encontrado');
        }

        const clienteBuscado = await response.json();

        // Convertir la respuesta a un arreglo para usar la misma función de mostrar
        mostrarClientesEnTabla([clienteBuscado]); // Llamamos la función para mostrar el cliente

    } catch (error) {
        console.error('Error al buscar cliente:', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}

// Manejar el envío del formulario de búsqueda
document.getElementById('buscar-cliente-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar el comportamiento por defecto del formulario

    const dni = obtenerDatosBusqueda(); // Obtener el DNI ingresado
    if (dni) {
        buscarCliente(dni); // Buscar cliente por DNI
    } else {
        alert('Por favor, ingrese un DNI para buscar.');
    }
});
